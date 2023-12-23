package uk.ac.york.eng2.commands;

import io.micronaut.configuration.picocli.PicocliRunner;
import io.micronaut.context.ApplicationContext;
import io.micronaut.context.annotation.Bean;
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
import uk.ac.york.eng2.domain.Hashtag;
import uk.ac.york.eng2.domain.User;
import uk.ac.york.eng2.domain.Video;
import uk.ac.york.eng2.dto.HashtagDTO;
import uk.ac.york.eng2.dto.UserDTO;
import uk.ac.york.eng2.dto.VideoDTO;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Collection;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertTrue;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@MicronautTest
public class GetVideoCommandTest {

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
    VideoDTO videoDTO = new VideoDTO();
    UserDTO userDTO = new UserDTO("TestUser");
    HttpResponse<Void> response = userClient.add(userDTO);
    userId = Long.parseLong(response.header("location").split("/")[2]);

    videoDTO.setTitle("Video Test Title");
    videoDTO.setPostedBy(userId);

    Set<HashtagDTO> hashtags = Set.of(new HashtagDTO("hashtag1"), new HashtagDTO("hashtag2"));
    videoDTO.setHashtags(hashtags);
    HttpResponse<Void> responseVid = videosClient.add(videoDTO);
    videoId = Long.parseLong(responseVid.header("location").split("/")[2]);

  }

  @Test
  public void cannotGetById() {
    System.setOut(new PrintStream(baos));

    try (ApplicationContext ctx = ApplicationContext.run(Environment.CLI, Environment.TEST)) {
      String[] args = new String[] {"-id", "0"};
      PicocliRunner.run(GetVideoCommand.class, ctx, args);

      assertTrue(baos.toString().contains("Video with id 0 does not exist"));
    }
  }

  @Test
  public void canGetById() {
    System.setOut(new PrintStream(baos));

    try (ApplicationContext ctx = ApplicationContext.run(Environment.CLI, Environment.TEST)) {
      String[] args = new String[] {"-id", videoId.toString()};
      PicocliRunner.run(GetVideoCommand.class, ctx, args);

      assertTrue(baos.toString().contains(videoId + " - Video Test Title - "));
    }
  }

  @Test
  public void canGetByHashtag() {
    System.setOut(new PrintStream(baos));

    try (ApplicationContext ctx = ApplicationContext.run(Environment.CLI, Environment.TEST)) {
      String[] args = new String[] {"-hashtag", "hashtag2"};
      PicocliRunner.run(GetVideoCommand.class, ctx, args);

      assertTrue(baos.toString().contains(videoId + " - Video Test Title - "));
    }
  }
  @Test
  public void cannotGetByHashtag() {
    System.setOut(new PrintStream(baos));

    try (ApplicationContext ctx = ApplicationContext.run(Environment.CLI, Environment.TEST)) {
      String[] args = new String[] {"-hashtag", "notahashtag"};
      PicocliRunner.run(GetVideoCommand.class, ctx, args);

      assertTrue(baos.toString().contains("Video with hashtag notahashtag does not exist"));
    }
  }

  @Test
  public void cannotGetByUserNotExist() {
    System.setOut(new PrintStream(baos));

    try (ApplicationContext ctx = ApplicationContext.run(Environment.CLI, Environment.TEST)) {
      String[] args = new String[] {"-postedBy", "0"};
      PicocliRunner.run(GetVideoCommand.class, ctx, args);

      assertTrue(baos.toString().contains("User with id 0 does not exist"));
    }
  }

  @Test
  public void cannotGetByUserNotPosted() {
    System.setOut(new PrintStream(baos));
    UserDTO userDTO = new UserDTO("TestUser");
    HttpResponse<Void> response = userClient.add(userDTO);
    Long userIdNew = Long.parseLong(response.header("location").split("/")[2]);

    try (ApplicationContext ctx = ApplicationContext.run(Environment.CLI, Environment.TEST)) {
      String[] args = new String[] {"-postedBy", userIdNew.toString()};
      PicocliRunner.run(GetVideoCommand.class, ctx, args);

      assertTrue(baos.toString().contains("User with id " + userIdNew + " has not posted any videos"));
    }
  }

  @Test
  public void getByUserPosted() {
    System.setOut(new PrintStream(baos));

    try (ApplicationContext ctx = ApplicationContext.run(Environment.CLI, Environment.TEST)) {
      String[] args = new String[] {"-postedBy", userId.toString()};
      PicocliRunner.run(GetVideoCommand.class, ctx, args);

      assertTrue(baos.toString().contains(videoId + " - Video Test Title - "));
    }
  }
}
