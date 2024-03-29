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
import uk.ac.york.eng2.cli.clients.VideosClient;
import uk.ac.york.eng2.cli.commands.ListVideosCommand;
import uk.ac.york.eng2.cli.domain.Video;
import uk.ac.york.eng2.cli.dto.UserDTO;
import uk.ac.york.eng2.cli.dto.VideoDTO;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.PrintStream;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import static java.lang.Thread.sleep;
import static org.junit.jupiter.api.Assertions.assertTrue;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@MicronautTest
public class ListVideosCommandTest {
  private final ByteArrayOutputStream baos = new ByteArrayOutputStream();

  @Inject
  VideosClient videosClient;

  @Inject
  UsersClient userClient;
  @ClassRule
  public static ComposeContainer environment = new ComposeContainer(new File("src/test/resources/compose-test.yml"))
          .withExposedService("video-microservice", 8080, Wait.forHttp("/health").forStatusCode(200));

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
  public void testListVideosWithVideoValid() {
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
      Awaitility.await().atMost(30, TimeUnit.SECONDS).until(ctx::isRunning);
      PicocliRunner.run(ListVideosCommand.class, ctx);
      sleep(1000);
      for (Video video : videos) {
        assertTrue(baos.toString().contains(video.getId() + " - " + video.getTitle()));
      }

    } catch (InterruptedException e) {
        throw new RuntimeException(e);
    }
  }
}
