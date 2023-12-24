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
import uk.ac.york.eng2.clients.UsersClient;
import uk.ac.york.eng2.clients.VideosClient;
import uk.ac.york.eng2.domain.User;
import uk.ac.york.eng2.domain.Video;
import uk.ac.york.eng2.dto.UserDTO;
import uk.ac.york.eng2.dto.VideoDTO;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertTrue;

@MicronautTest
public class ListVideosCommandTest {
  private final ByteArrayOutputStream baos = new ByteArrayOutputStream();

  @Inject
  VideosClient videosClient;

  @Inject
  UsersClient userClient;
  @ClassRule
  public static ComposeContainer environment = new ComposeContainer(new File("src/test/resources/compose-test.yml"))
          .withExposedService("video-microservice", 8080, Wait.forHttp("/healthcheck").forStatusCode(200))
          .withExposedService("trending-hashtag-microservice", 8081, Wait.forHttp("/healthcheck").forStatusCode(200))
          .withLogConsumer("trending-hashtag-microservice", (outputFrame) -> {
            System.out.println(outputFrame.getUtf8String());
          });

  @BeforeAll
  public static void waitForServices() {
    environment.start();
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
  public void ListAfterCreateUser() {
    System.setOut(new PrintStream(baos));

    UserDTO userDTO = new UserDTO("TestUser");
    HttpResponse<Void> responseUser = userClient.add(userDTO);
    Long userId = Long.parseLong(responseUser.header("location").split("/")[2]);


    VideoDTO videoDTO = new VideoDTO();
    videoDTO.setTitle("Video Test Title2");
    videoDTO.setPostedBy(userId);
    HttpResponse<Void> response = videosClient.add(videoDTO);
    Long videoID = Long.parseLong(response.header("location").split("/")[2]);

    Iterable<Video> videos = videosClient.list();


    try (ApplicationContext ctx = ApplicationContext.run(Environment.CLI, Environment.TEST)) {
      PicocliRunner.run(ListVideosCommand.class, ctx);

      for (Video video : videos) {
        assertTrue(baos.toString().contains(video.getId() + " - " + video.getTitle()));
      }

    }
  }
}
