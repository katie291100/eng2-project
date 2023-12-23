package uk.ac.york.eng2.commands;

import io.micronaut.configuration.picocli.PicocliRunner;
import io.micronaut.context.ApplicationContext;
import io.micronaut.context.env.Environment;
import io.micronaut.http.HttpResponse;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import uk.ac.york.eng2.clients.UsersClient;
import uk.ac.york.eng2.clients.VideosClient;
import uk.ac.york.eng2.dto.HashtagDTO;
import uk.ac.york.eng2.dto.UserDTO;
import uk.ac.york.eng2.dto.VideoDTO;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertTrue;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@MicronautTest
public class LikeVideoCommandTest {

  @Inject
  VideosClient videosClient;

  @Inject
  UsersClient userClient;

  private final ByteArrayOutputStream baos = new ByteArrayOutputStream();


  @BeforeEach
  public void clearSysOut() {
    baos.reset();
  }

  private Long videoId;
  private Long userId;

  @BeforeAll
  public void setup() {
      UserDTO userDTO = new UserDTO("TestUser");

      HttpResponse<Void> response = userClient.add(userDTO);
      userId = Long.parseLong(response.header("location").split("/")[2]);

      VideoDTO videoDTO = new VideoDTO();
      videoDTO.setTitle("Video Test Title");
      videoDTO.setPostedBy(userId);

      Set<HashtagDTO> hashtags = Set.of(new HashtagDTO("hashtag1"), new HashtagDTO("hashtag2"));
      videoDTO.setHashtags(hashtags);

      HttpResponse<Void> responseVid = videosClient.add(videoDTO);
      videoId = Long.parseLong(responseVid.header("location").split("/")[2]);
  }

  @Test
  public void cannotGetUser() {

    System.setOut(new PrintStream(baos));

    try (ApplicationContext ctx = ApplicationContext.run(Environment.CLI, Environment.TEST)) {
      String[] args = new String[] {"0", "0"};
      PicocliRunner.run(LikeVideoCommand.class, ctx, args);

      assertTrue(baos.toString().contains("User with id 0 does not exist\n"));
    }
  }

  @Test
  public void cannotGetVideo() {

    System.setOut(new PrintStream(baos));

    try (ApplicationContext ctx = ApplicationContext.run(Environment.CLI, Environment.TEST)) {
      String[] args = new String[] {userId.toString(), "0"};
      PicocliRunner.run(LikeVideoCommand.class, ctx, args);

      assertTrue(baos.toString().contains("Video with id 0 does not exist"));
    }
  }


  @Test
  public void canLike() {
    System.setOut(new PrintStream(baos));
    try (ApplicationContext ctx = ApplicationContext.run(Environment.CLI, Environment.TEST)) {
      String[] args = new String[] {"", "hashtag2"};
      PicocliRunner.run(LikeVideoCommand.class, userId.toString(), videoId.toString());

      assertTrue(baos.toString().contains("Successfully liked video with id " + videoId));
    }
  }
}
