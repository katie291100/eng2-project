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
import uk.ac.york.eng2.cli.clients.UsersClient;
import uk.ac.york.eng2.cli.commands.PostVideoCommand;
import uk.ac.york.eng2.cli.dto.UserDTO;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.PrintStream;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertTrue;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@MicronautTest
public class PostVideoCommandTest {
  private final ByteArrayOutputStream baos = new ByteArrayOutputStream();

  @Inject
  UsersClient usersClient;

  @ClassRule
  public static ComposeContainer environment = new ComposeContainer(new File("src/test/resources/compose-test.yml"))
          .withExposedService("video-microservice", 8080, Wait.forHttp("/health").forStatusCode(200))
          .withLogConsumer("trending-hashtag-microservice", (outputFrame) -> {
            System.out.println(outputFrame.getUtf8String());
          });

  @BeforeAll
  public static void waitForServices() {
    if (!Objects.equals(System.getenv("USE_TEST_CONTAINERS"), "false")) {
      environment.start();
      return;
    }
    System.out.println("Warning: Not using test containers, using local services, TrendingHashtag tests will likely fail");

  }

  @BeforeEach
  public void clearSysOut() {
    baos.reset();
  }

  @AfterAll
  public static void stopEnvironment() {
    environment.stop();
  }
  @Test
  public void testCreateVideoValidUser() {
    System.setOut(new PrintStream(baos));

    UserDTO userDTO = new UserDTO("TestUser");
    HttpResponse<Void> response = usersClient.add(userDTO);
    Long userId = Long.parseLong(response.header("location").split("/")[2]);

    try (ApplicationContext ctx = ApplicationContext.run(Environment.CLI, Environment.TEST)) {
      Awaitility.await().atMost(30, TimeUnit.SECONDS).until(ctx::isRunning);
      String[] args = new String[] {"Video Test Title", userId.toString(), "hashtag1", "hashtag2"};
      PicocliRunner.run(PostVideoCommand.class, ctx, args);

      assertTrue(baos.toString().contains("Successfully created video with id "));
    }
  }

  @Test
  public void testCreateVideoVaInvalidUserError() {
    System.setOut(new PrintStream(baos));

    try (ApplicationContext ctx = ApplicationContext.run(Environment.CLI, Environment.TEST)) {
      Awaitility.await().atMost(30, TimeUnit.SECONDS).until(ctx::isRunning);
      String[] args = new String[] {"Video Test Title", "0", "hashtag1", "hashtag2"};
      PicocliRunner.run(PostVideoCommand.class, ctx, args);

      assertTrue(baos.toString().contains("User with id 0 does not exist"));
    }
  }


}
