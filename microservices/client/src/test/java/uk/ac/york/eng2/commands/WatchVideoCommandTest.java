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
import uk.ac.york.eng2.cli.clients.UsersClient;
import uk.ac.york.eng2.cli.clients.VideosClient;
import uk.ac.york.eng2.cli.commands.WatchVideoCommand;
import uk.ac.york.eng2.cli.dto.HashtagDTO;
import uk.ac.york.eng2.cli.dto.UserDTO;
import uk.ac.york.eng2.cli.dto.VideoDTO;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.PrintStream;
import java.util.Objects;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertTrue;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@MicronautTest
public class WatchVideoCommandTest {

  @Inject
  VideosClient videosClient;

  @Inject
  UsersClient userClient;

  private final ByteArrayOutputStream baos = new ByteArrayOutputStream();


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
      PicocliRunner.run(WatchVideoCommand.class, ctx, args);

      assertTrue(baos.toString().contains("User with id 0 does not exist\n"));
    }
  }

  @Test
  public void cannotGetVideo() {

    System.setOut(new PrintStream(baos));

    try (ApplicationContext ctx = ApplicationContext.run(Environment.CLI, Environment.TEST)) {
      String[] args = new String[] {userId.toString(), "0"};
      PicocliRunner.run(WatchVideoCommand.class, ctx, args);

      assertTrue(baos.toString().contains("Video with id 0 does not exist"));
    }
  }


  @Test
  public void canLike() {
    System.setOut(new PrintStream(baos));
    try (ApplicationContext ctx = ApplicationContext.run(Environment.CLI, Environment.TEST)) {
      PicocliRunner.run(WatchVideoCommand.class, userId.toString(), videoId.toString());

      assertTrue(baos.toString().contains("Successfully watched video with id " + videoId));
    }
  }
}
