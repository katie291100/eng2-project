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
import org.testcontainers.shaded.org.awaitility.Awaitility;
import uk.ac.york.eng2.cli.clients.HashtagsClient;
import uk.ac.york.eng2.cli.clients.TrendingClient;
import uk.ac.york.eng2.cli.clients.UsersClient;
import uk.ac.york.eng2.cli.clients.VideosClient;
import uk.ac.york.eng2.cli.commands.SubscribeCommand;
import uk.ac.york.eng2.cli.commands.TrendingHashtagsCommand;
import uk.ac.york.eng2.cli.commands.WatchVideoCommand;
import uk.ac.york.eng2.cli.domain.Hashtag;
import uk.ac.york.eng2.cli.domain.User;
import uk.ac.york.eng2.cli.domain.Video;
import uk.ac.york.eng2.cli.dto.HashtagDTO;
import uk.ac.york.eng2.cli.dto.UserDTO;
import uk.ac.york.eng2.cli.dto.VideoDTO;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.PrintStream;
import java.time.Duration;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import static java.lang.Thread.sleep;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Tests for the {@link TrendingHashtagsCommand} command.
 * Uses test containers to run the microservices.
 */

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@MicronautTest
public class SubscribeCommandTest {
  private final ByteArrayOutputStream baos = new ByteArrayOutputStream();

  @Inject
  VideosClient videosClient;

  @Inject
  UsersClient userClient;

  @Inject
  HashtagsClient hashtagClient;


  @ClassRule
  public static ComposeContainer environment = new ComposeContainer(new File("src/test/resources/compose-test.yml"))
            .withExposedService("video-microservice", 8080)
            .withExposedService("trending-hashtag-microservice", 8080)
          .withExposedService("subscription-microservice", 8080)
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
  public static void stopEnvironment() {
    environment.stop();
  }


  @Test
  public void subscribeNoUser() throws InterruptedException {
    System.setOut(new PrintStream(baos));

    HashtagDTO hashtagDTO = new HashtagDTO("test");
    HttpResponse<Void> hashtagResponse = hashtagClient.add(hashtagDTO);
    Long hashtagId = Long.parseLong(hashtagResponse.header("location").split("/")[2]);
    try (ApplicationContext ctx = ApplicationContext.run(Environment.CLI, Environment.TEST)) {
      Awaitility.await().atMost(30, TimeUnit.SECONDS).until(ctx::isRunning);
      String[] args = new String[] { "0", "-id", hashtagId.toString()};
      PicocliRunner.run(SubscribeCommand.class, ctx, args);

      assertTrue(baos.toString().contains("User with id 0 does not exist\n"));
    }
  }

  @Test
  public void subscribeNoHashtag() throws InterruptedException {
    System.setOut(new PrintStream(baos));

    UserDTO userDTO = new UserDTO("TestUser");
    HttpResponse<Void> response = userClient.add(userDTO);
    Long userId = Long.parseLong(response.header("location").split("/")[2]);

    try (ApplicationContext ctx = ApplicationContext.run(Environment.CLI, Environment.TEST)) {
      Awaitility.await().atMost(30, TimeUnit.SECONDS).until(ctx::isRunning);
      String[] args = new String[] { userId.toString(), "-id", "0"};
      PicocliRunner.run(SubscribeCommand.class, ctx, args);

      assertTrue(baos.toString().contains("Hashtag with id 0 does not exist\n"));
    }
  }


  @Test
  public void subscribeUserHashtag() throws InterruptedException {
    System.setOut(new PrintStream(baos));
    sleep(10000);

    UserDTO userDTO = new UserDTO("TestUser");
    HttpResponse<Void> response = userClient.add(userDTO);
    Long userId = Long.parseLong(response.header("location").split("/")[2]);

    HashtagDTO hashtagDTO = new HashtagDTO("test2");
    HttpResponse<Void> hashtagResponse = hashtagClient.add(hashtagDTO);
    Long hashtagId = Long.parseLong(hashtagResponse.header("location").split("/")[2]);
    sleep(10000);
    try (ApplicationContext ctx = ApplicationContext.run(Environment.CLI, Environment.TEST)) {
      Awaitility.await().atMost(30, TimeUnit.SECONDS).until(ctx::isRunning);
      String[] args = new String[] { userId.toString(), "-id", hashtagId.toString(),};
      PicocliRunner.run(SubscribeCommand.class, ctx, args);

      assertTrue(baos.toString().contains("Successfully subscribed user " + userId + " to hashtag " + hashtagId + ": #test2\n"));
    }
  }

}
