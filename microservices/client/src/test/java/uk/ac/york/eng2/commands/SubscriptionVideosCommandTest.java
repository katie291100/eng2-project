package uk.ac.york.eng2.commands;

import io.micronaut.configuration.picocli.PicocliRunner;
import io.micronaut.context.ApplicationContext;
import io.micronaut.context.env.Environment;
import io.micronaut.http.HttpResponse;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import jakarta.inject.Inject;
import org.junit.ClassRule;
import org.junit.jupiter.api.*;
import org.testcontainers.containers.ComposeContainer;
import org.testcontainers.containers.wait.strategy.Wait;
import org.testcontainers.shaded.org.awaitility.Awaitility;
import uk.ac.york.eng2.cli.clients.HashtagsClient;
import uk.ac.york.eng2.cli.clients.SubscriptionClient;
import uk.ac.york.eng2.cli.clients.UsersClient;
import uk.ac.york.eng2.cli.clients.VideosClient;
import uk.ac.york.eng2.cli.commands.SubscriptionVideosCommand;
import uk.ac.york.eng2.cli.commands.TrendingHashtagsCommand;
import uk.ac.york.eng2.cli.commands.UnsubscribeCommand;
import uk.ac.york.eng2.cli.dto.HashtagDTO;
import uk.ac.york.eng2.cli.dto.UserDTO;
import uk.ac.york.eng2.cli.dto.VideoDTO;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.PrintStream;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import static java.lang.Thread.sleep;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.testcontainers.shaded.org.awaitility.Awaitility.await;

/**
 * Tests for the {@link TrendingHashtagsCommand} command.
 * Uses test containers to run the microservices.
 */

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@MicronautTest
public class SubscriptionVideosCommandTest {
  private final ByteArrayOutputStream baos = new ByteArrayOutputStream();

  @Inject
  VideosClient videosClient;

  @Inject
  UsersClient userClient;

  @Inject
  HashtagsClient hashtagClient;

  @Inject
  SubscriptionClient subscriptionClient;


  @ClassRule
  public static ComposeContainer environment = new ComposeContainer(new File("src/test/resources/compose-test.yml"))
            .withExposedService("video-microservice", 8080, Wait.forHttp("/health").forStatusCode(200))
          .withExposedService("subscription-microservice", 8080, Wait.forHttp("/health").forStatusCode(200))
            .withLogConsumer("subscription-microservice", (outputFrame) -> {
            System.out.println(outputFrame.getUtf8String());
          });

  @BeforeAll
  public static void startServices() {
      if (!Objects.equals(System.getenv("USE_TEST_CONTAINERS"), "false")) {
          environment.start();
          return;
      }
      System.out.println("Warning: Not using test containers, using local services, TrendingHashtag tests will likely fail");
  }

  @BeforeEach
  public void clearSysOut(){
    baos.reset();
  }

  @AfterAll
  public static void stopEnvironment() throws InterruptedException {
    environment.stop();
    sleep(5000);
  }


  @Test
  public void testSubscriptionVideosInvalidUserError() throws InterruptedException {
    System.setOut(new PrintStream(baos));

    HashtagDTO hashtagDTO = new HashtagDTO("test");
    HttpResponse<Void> hashtagResponse = hashtagClient.add(hashtagDTO);
    Long hashtagId = Long.parseLong(hashtagResponse.header("location").split("/")[2]);
    try (ApplicationContext ctx = ApplicationContext.run(Environment.CLI, Environment.TEST)) {
      Awaitility.await().atMost(30, TimeUnit.SECONDS).until(ctx::isRunning);
      String[] args = new String[] { "0", "-id", hashtagId.toString() };
      PicocliRunner.run(SubscriptionVideosCommand.class, ctx, args);

      assertTrue(baos.toString().contains("User with id 0 does not exist\n"));
    }
  }

  @Test
  public void testSubscriptionVideosInvalidHashtagError() {
    System.setOut(new PrintStream(baos));

    UserDTO userDTO = new UserDTO("TestUser");
    HttpResponse<Void> response = userClient.add(userDTO);
    Long userId = Long.parseLong(response.header("location").split("/")[2]);

    try (ApplicationContext ctx = ApplicationContext.run(Environment.CLI, Environment.TEST)) {
      Awaitility.await().atMost(30, TimeUnit.SECONDS).until(ctx::isRunning);
      String[] args = new String[] {userId.toString(), "-id", "0"};
      PicocliRunner.run(SubscriptionVideosCommand.class, ctx, args);

      assertTrue(baos.toString().contains("Hashtag with id 0 does not exist\n"));
    }
  }

  @Test
  public void testSubscriptionVideosValid() throws InterruptedException {
    System.setOut(new PrintStream(baos));

    UserDTO userDTO = new UserDTO("TestUser");
    HttpResponse<Void> response = userClient.add(userDTO);
    Long userId = Long.parseLong(response.header("location").split("/")[2]);

    UserDTO userDTO2 = new UserDTO("TestUser");
    HttpResponse<Void> response2 = userClient.add(userDTO2);
    Long userId2 = Long.parseLong(response2.header("location").split("/")[2]);

    HashtagDTO hashtagDTO = new HashtagDTO("test3");
    HttpResponse<Void> hashtagResponse = hashtagClient.add(hashtagDTO);
    Long hashtagId = Long.parseLong(hashtagResponse.header("location").split("/")[2]);
    sleep(10000);

    HttpResponse<Void> r = subscriptionClient.subscribe(hashtagId, userId);

    VideoDTO videoDTO = new VideoDTO();
    videoDTO.setTitle("test2");
    videoDTO.setPostedBy(userId2);
    videoDTO.setHashtags(Set.of(new HashtagDTO("test3")));
    HttpResponse<Void> videoResponse = videosClient.add(videoDTO);
    Long videoId = Long.parseLong(videoResponse.header("location").split("/")[2]);


    VideoDTO videoDTO2 = new VideoDTO();
    videoDTO2.setTitle("test3");
    videoDTO2.setPostedBy(userId2);
    videoDTO2.setHashtags(Set.of(new HashtagDTO("test3")));
    HttpResponse<Void> videoResponse2 = videosClient.add(videoDTO);
    Long videoId2 = Long.parseLong(videoResponse2.header("location").split("/")[2]);
    sleep(10000);

    userClient.watchedVideo(userId2, videoId);
    sleep(30000);

    try (ApplicationContext ctx = ApplicationContext.run(Environment.CLI, Environment.TEST)) {
      Awaitility.await().atMost(30, TimeUnit.SECONDS).until(ctx::isRunning);
      String[] args = new String[] { userId.toString(), "-id", hashtagId.toString()};
      PicocliRunner.run(SubscriptionVideosCommand.class, ctx, args);

      assertTrue(baos.toString().contains("User " + userId + " has the following videos to watch for hashtag " + hashtagId + " - #test3:"));
      assertTrue(baos.toString().contains("Video " + videoId + " - test2"));
      assertFalse(baos.toString().contains("Video " + videoId2 + " - test3"));
    }
    }
  
}
