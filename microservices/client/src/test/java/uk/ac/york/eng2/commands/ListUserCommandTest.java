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
import uk.ac.york.eng2.domain.User;
import uk.ac.york.eng2.dto.UserDTO;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Iterator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

@MicronautTest
public class ListUserCommandTest {
  private final ByteArrayOutputStream baos = new ByteArrayOutputStream();

  @Inject
  UsersClient usersClient;

  @BeforeEach
  public void clearSysOut() {
    baos.reset();
  }

  @Test
  public void ListAfterCreateUser() {
    System.setOut(new PrintStream(baos));

    UserDTO userDTO = new UserDTO("TestUser");
    HttpResponse<Void> response = usersClient.add(userDTO);
    Long userId = Long.parseLong(response.header("location").split("/")[2]);

    Iterable<User> users = usersClient.list();

    try (ApplicationContext ctx = ApplicationContext.run(Environment.CLI, Environment.TEST)) {
      PicocliRunner.run(ListUsersCommand.class, ctx);

      for (User user : users) {
        assertTrue(baos.toString().contains(user.getId() + " - " + user.getName()));
      }

    }
  }
}
