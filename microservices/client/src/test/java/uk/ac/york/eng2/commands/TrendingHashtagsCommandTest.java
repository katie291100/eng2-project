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
import org.testcontainers.containers.DockerComposeContainer;
import org.testcontainers.containers.wait.strategy.Wait;
import org.testcontainers.shaded.org.awaitility.Awaitility;
import uk.ac.york.eng2.DockerComposeReset;
import uk.ac.york.eng2.clients.TrendingClient;
import uk.ac.york.eng2.clients.UsersClient;
import uk.ac.york.eng2.clients.VideosClient;
import uk.ac.york.eng2.domain.Hashtag;
import uk.ac.york.eng2.domain.Video;
import uk.ac.york.eng2.dto.HashtagDTO;
import uk.ac.york.eng2.dto.UserDTO;
import uk.ac.york.eng2.dto.VideoDTO;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.time.Duration;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Tests for the {@link TrendingHashtagsCommand} command.
 * Uses test containers to run the microservices.
 */
@MicronautTest
public class TrendingHashtagsCommandTest {
  private final ByteArrayOutputStream baos = new ByteArrayOutputStream();

  @Inject
  VideosClient videosClient;

  @Inject
  UsersClient userClient;

  @Inject
  TrendingClient trendingClient;

  @ClassRule
  public static ComposeContainer environment = new ComposeContainer(new File("src/test/resources/compose-test.yml"))
            .withExposedService("video-microservice", 8080)
            .withExposedService("trending-hashtag-microservice", 8081);

  @BeforeAll
  public static void waitForServices() {
      environment.start();
  }

  @BeforeEach
  public void clearSysOut() throws IOException, InterruptedException {
    baos.reset();
  }
//  @AfterAll
//  public static void stopEnvironment() {
//    environment.stop();
//  }

  @Test
  public void trendingHashtag10Hashtags() {
    System.setOut(new PrintStream(baos));

    UserDTO userDTO = new UserDTO("TestUser");
    HttpResponse<Void> responseUser = userClient.add(userDTO);
    Long userId = Long.parseLong(responseUser.header("location").split("/")[2]);

    // create 10 hashtagDTOs
    Set<HashtagDTO> hashtagDTOs = new HashSet<>();
    for (int i = 0; i < 10; i++) {
      HashtagDTO hashtagDTO = new HashtagDTO("hashtag" + i);
      hashtagDTOs.add(hashtagDTO);
    }


    VideoDTO videoDTO = new VideoDTO();
    videoDTO.setTitle("Video Test Title1");
    videoDTO.setPostedBy(userId);
    videoDTO.setHashtags(hashtagDTOs);
    HttpResponse<Void> response = videosClient.add(videoDTO);
    Long videoID = Long.parseLong(response.header("location").split("/")[2]);
    Video video = videosClient.getVideo(videoID);
    HttpResponse<Void> response2 = videosClient.likeVideo(videoID, userId);
    Awaitility.await().atLeast(Duration.ofSeconds(10)).atMost(Duration.ofSeconds(300)).until(() -> trendingClient.list().size() == 10);

    try (ApplicationContext ctx = ApplicationContext.run(Environment.CLI, Environment.TEST)) {
      PicocliRunner.run(ListVideosCommand.class, ctx);

      for (Hashtag hashtag : video.getHashtags()) {
        assertTrue(baos.toString().contains(hashtag.getId() + " - " + hashtag.getName()));
      }

    }
  }
}
