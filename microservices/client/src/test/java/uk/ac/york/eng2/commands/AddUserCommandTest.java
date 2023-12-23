package uk.ac.york.eng2.commands;

import io.micronaut.configuration.picocli.PicocliRunner;
import io.micronaut.context.ApplicationContext;
import io.micronaut.context.env.Environment;
import io.micronaut.http.HttpResponse;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import uk.ac.york.eng2.clients.UsersClient;
import uk.ac.york.eng2.dto.UserDTO;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertTrue;

@MicronautTest
public class AddUserCommandTest {
  private final ByteArrayOutputStream baos = new ByteArrayOutputStream();

  @Inject
  UsersClient usersClient;

  @BeforeEach
  public void clearSysOut() {
    baos.reset();
  }

  @Test
  public void canCreateUser() {
    System.setOut(new PrintStream(baos));

    try (ApplicationContext ctx = ApplicationContext.run(Environment.CLI, Environment.TEST)) {
      String[] args = new String[] {"Test User"};
      PicocliRunner.run(AddUserCommand.class, ctx, args);
      assertTrue(baos.toString().contains("Successfully created user with name Test User\n" +
              "Remember your user ID"));
    }
  }
}
