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
import uk.ac.york.eng2.cli.clients.UsersClient;
import uk.ac.york.eng2.cli.clients.VideosClient;
import uk.ac.york.eng2.cli.commands.SubscribeCommand;
import uk.ac.york.eng2.cli.commands.TrendingHashtagsCommand;
import uk.ac.york.eng2.cli.commands.UnsubscribeCommand;
import uk.ac.york.eng2.cli.dto.HashtagDTO;
import uk.ac.york.eng2.cli.dto.UserDTO;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.PrintStream;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import static java.lang.Thread.sleep;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.testcontainers.shaded.org.awaitility.Awaitility.await;


@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@MicronautTest
public class UnsubscribeCommandTest {
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
  public void testUnsubscribeInvalidUserError() {
    System.setOut(new PrintStream(baos));

    HashtagDTO hashtagDTO = new HashtagDTO("test");
    HttpResponse<Void> hashtagResponse = hashtagClient.add(hashtagDTO);
    Long hashtagId = Long.parseLong(hashtagResponse.header("location").split("/")[2]);
    try (ApplicationContext ctx = ApplicationContext.run(Environment.CLI, Environment.TEST)) {
      Awaitility.await().atMost(30, TimeUnit.SECONDS).until(ctx::isRunning);
      String[] args = new String[] { "0", "-id", hashtagId.toString() };
      PicocliRunner.run(UnsubscribeCommand.class, ctx, args);

      assertTrue(baos.toString().contains("User with id 0 does not exist\n"));
    }
  }

  @Test
  public void testUnsubscribeInvalidHashtagError() {
    System.setOut(new PrintStream(baos));

    UserDTO userDTO = new UserDTO("TestUser");
    HttpResponse<Void> response = userClient.add(userDTO);
    Long userId = Long.parseLong(response.header("location").split("/")[2]);

    try (ApplicationContext ctx = ApplicationContext.run(Environment.CLI, Environment.TEST)) {
      Awaitility.await().atMost(30, TimeUnit.SECONDS).until(ctx::isRunning);
      String[] args = new String[] {userId.toString(),  "-id","0"};
      PicocliRunner.run(UnsubscribeCommand.class, ctx, args);

      assertTrue(baos.toString().contains("Hashtag with id 0 does not exist\n"));
    }
  }

  @Test
  public void testUnsubscribeValid() throws InterruptedException {
    System.setOut(new PrintStream(baos));

    UserDTO userDTO = new UserDTO("TestUser");
    HttpResponse<Void> response = userClient.add(userDTO);
    Long userId = Long.parseLong(response.header("location").split("/")[2]);

    HashtagDTO hashtagDTO = new HashtagDTO("test2");
    HttpResponse<Void> hashtagResponse = hashtagClient.add(hashtagDTO);
    Long hashtagId = Long.parseLong(hashtagResponse.header("location").split("/")[2]);
    sleep(10000);
    try (ApplicationContext ctx = ApplicationContext.run(Environment.CLI, Environment.TEST)) {
      Awaitility.await().atMost(30, TimeUnit.SECONDS).until(ctx::isRunning);
      String[] args = new String[] {userId.toString(), "-id", hashtagId.toString()};
      PicocliRunner.run(UnsubscribeCommand.class, ctx, args);

      assertTrue(baos.toString().contains("Successfully unsubscribed user " + userId + " from hashtag " + hashtagId));
    }
  }
  
}
