package uk.ac.york.eng2.commands;

import io.micronaut.configuration.picocli.PicocliRunner;
import io.micronaut.context.ApplicationContext;
import io.micronaut.context.env.Environment;
import io.micronaut.http.HttpResponse;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import jakarta.inject.Inject;
import org.junit.ClassRule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.DockerComposeContainer;
import org.testcontainers.containers.wait.strategy.Wait;
import uk.ac.york.eng2.DockerComposeReset;
import uk.ac.york.eng2.clients.UsersClient;
import uk.ac.york.eng2.clients.VideosClient;
import uk.ac.york.eng2.domain.Video;
import uk.ac.york.eng2.dto.UserDTO;
import uk.ac.york.eng2.dto.VideoDTO;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertTrue;

@MicronautTest
public class TrendingHashtagCommandTest {
  private final ByteArrayOutputStream baos = new ByteArrayOutputStream();

  @Inject
  VideosClient videosClient;

  @Inject
  UsersClient userClient;

  @ClassRule
  public static DockerComposeContainer environment = new DockerComposeContainer(new File("src/test/resources/compose-test.yml"))
            .withExposedService("video-microservice", 8080, Wait.forHttp("/healthcheck")
                    .forStatusCode(200).withStartupTimeout(Duration.ofSeconds(300)))
            .withExposedService("trending-hashtag-microservice", 8082);




  @BeforeEach
  public void clearSysOut() throws IOException, InterruptedException {
    baos.reset();
    environment.start();
    String address = "http://" + environment.getServiceHost("video-microservice", 8080) + ":" + environment.getServicePort("video-microservice", 8080);
    environment.withEnv("VIDEO_URL", address);
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
