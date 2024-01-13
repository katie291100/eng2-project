package uk.ac.york.eng2.commands;

import io.micronaut.configuration.picocli.PicocliRunner;
import io.micronaut.context.ApplicationContext;
import io.micronaut.context.env.Environment;
import io.micronaut.http.HttpResponse;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import jakarta.inject.Inject;
import org.junit.ClassRule;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.ComposeContainer;
import org.testcontainers.containers.wait.strategy.Wait;
import uk.ac.york.eng2.cli.clients.UsersClient;
import uk.ac.york.eng2.cli.commands.GetUserCommand;
import uk.ac.york.eng2.cli.dto.UserDTO;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.PrintStream;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@MicronautTest
public class GetUserCommandTest {
  private final ByteArrayOutputStream baos = new ByteArrayOutputStream();

  @Inject
  UsersClient usersClient;

  @ClassRule
  public static ComposeContainer environment = new ComposeContainer(new File("src/test/resources/compose-test.yml"))
          .withExposedService("video-microservice", 8080, Wait.forHttp("/health").forStatusCode(200))
          .withExposedService("trending-hashtag-microservice", 8080, Wait.forHttp("/health").forStatusCode(200))
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
  public void GetByIDAfterCreateUser() {
    System.setOut(new PrintStream(baos));

    UserDTO userDTO = new UserDTO("TestUser");
    HttpResponse<Void> response = usersClient.add(userDTO);
    Long userId = Long.parseLong(response.header("location").split("/")[2]);

    UserDTO userDTO2 = new UserDTO("TestUser2");
    HttpResponse<Void> response2 = usersClient.add(userDTO2);
    Long userId2 = Long.parseLong(response.header("location").split("/")[2]);


    try (ApplicationContext ctx = ApplicationContext.run(Environment.CLI, Environment.TEST)) {
      String[] args = new String[]{"-id", userId.toString()};
      PicocliRunner.run(GetUserCommand.class, ctx, args);

      assertTrue(baos.toString().contains(userId + " - TestUser"));
      assertFalse(baos.toString().contains(userId2 + " - TestUser2"));

    }
  }
  @Test
  public void GetByNameAfterCreateUser() {
    System.setOut(new PrintStream(baos));

    UserDTO userDTO = new UserDTO("TestUser");
    HttpResponse<Void> response = usersClient.add(userDTO);
    Long userId = Long.parseLong(response.header("location").split("/")[2]);

    UserDTO userDTO2 = new UserDTO("TestUser");
    HttpResponse<Void> response2 = usersClient.add(userDTO2);
    Long userId2 = Long.parseLong(response.header("location").split("/")[2]);

    UserDTO userDTO3 = new UserDTO("TestUser3");
    HttpResponse<Void> response3 = usersClient.add(userDTO3);
    Long userId3 = Long.parseLong(response.header("location").split("/")[2]);

    try (ApplicationContext ctx = ApplicationContext.run(Environment.CLI, Environment.TEST)) {
      String[] args = new String[]{"-name", "TestUser"};
      PicocliRunner.run(GetUserCommand.class, ctx, args);

      assertTrue(baos.toString().contains(userId + " - TestUser"));
      assertTrue(baos.toString().contains(userId2 + " - TestUser"));
      assertFalse(baos.toString().contains(userId3 + " - TestUser3"));
    }
  }
}
