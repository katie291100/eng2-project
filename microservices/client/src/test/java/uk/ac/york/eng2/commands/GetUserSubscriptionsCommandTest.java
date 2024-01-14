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
import uk.ac.york.eng2.cli.commands.GetUserSubscriptionsCommand;
import uk.ac.york.eng2.cli.commands.SubscriptionVideosCommand;
import uk.ac.york.eng2.cli.commands.TrendingHashtagsCommand;
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

/**
 * Tests for the {@link TrendingHashtagsCommand} command.
 * Uses test containers to run the microservices.
 */

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@MicronautTest
public class GetUserSubscriptionsCommandTest {
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
  public void testGetUserSubscriptionValidUserNoSubscription() throws InterruptedException {
    System.setOut(new PrintStream(baos));

    UserDTO userDTO = new UserDTO("TestUser");
    HttpResponse<Void> response = userClient.add(userDTO);
    Long userId = Long.parseLong(response.header("location").split("/")[2]);

    sleep(5000);
    try (ApplicationContext ctx = ApplicationContext.run(Environment.CLI, Environment.TEST)) {
      Awaitility.await().atMost(30, TimeUnit.SECONDS).until(ctx::isRunning);
      String[] args = new String[] {userId.toString()};
      PicocliRunner.run(GetUserSubscriptionsCommand.class, ctx, args);

      assertTrue(baos.toString().contains("User " + userId + " has no subscriptions"));
    }
  }

  @Test
  public void testGetUserSubscriptionInvalidSubscription() {
    System.setOut(new PrintStream(baos));

    try (ApplicationContext ctx = ApplicationContext.run(Environment.CLI, Environment.TEST)) {
      Awaitility.await().atMost(30, TimeUnit.SECONDS).until(ctx::isRunning);
      String[] args = new String[] {"0"};
      PicocliRunner.run(GetUserSubscriptionsCommand.class, ctx, args);

      assertTrue(baos.toString().contains("User with id 0 does not exist\n"));
    }
  }

  @Test
  public void testGetUserSubscriptionValidSubscription() throws InterruptedException {
    System.setOut(new PrintStream(baos));

    UserDTO userDTO = new UserDTO("TestUser");
    HttpResponse<Void> response = userClient.add(userDTO);
    Long userId = Long.parseLong(response.header("location").split("/")[2]);

    HashtagDTO hashtagDTO = new HashtagDTO("test1");
    HttpResponse<Void> hashtagResponse = hashtagClient.add(hashtagDTO);
    Long hashtagId = Long.parseLong(hashtagResponse.header("location").split("/")[2]);

    HashtagDTO hashtagDTO2 = new HashtagDTO("test2");
    HttpResponse<Void> hashtagResponse2 = hashtagClient.add(hashtagDTO2);
    Long hashtagId2 = Long.parseLong(hashtagResponse2.header("location").split("/")[2]);
    sleep(10000);
    HttpResponse<Void> r = subscriptionClient.subscribe(hashtagId, userId);
    HttpResponse<Void> r2 = subscriptionClient.subscribe(hashtagId2, userId);

    try (ApplicationContext ctx = ApplicationContext.run(Environment.CLI, Environment.TEST)) {
      Awaitility.await().atMost(30, TimeUnit.SECONDS).until(ctx::isRunning);
      String[] args = new String[] {userId.toString()};
      PicocliRunner.run(GetUserSubscriptionsCommand.class, ctx, args);

      assertTrue(baos.toString().contains("User " + userId + " is subscribed to the following hashtag with videos ids to watch:"));
      assertTrue(baos.toString().contains("Hashtag " + hashtagId2 + "- #" + hashtagDTO2.getName() + " with videos ids to watch: "));
      assertTrue(baos.toString().contains("Hashtag " + hashtagId + "- #" + hashtagDTO.getName() + " with videos ids to watch: "));
    }
  }

}
