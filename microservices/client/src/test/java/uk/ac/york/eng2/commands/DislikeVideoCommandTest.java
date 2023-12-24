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
import uk.ac.york.eng2.clients.UsersClient;
import uk.ac.york.eng2.clients.VideosClient;
import uk.ac.york.eng2.dto.HashtagDTO;
import uk.ac.york.eng2.dto.UserDTO;
import uk.ac.york.eng2.dto.VideoDTO;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.PrintStream;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertTrue;
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@MicronautTest
public class DislikeVideoCommandTest {

  @Inject
  VideosClient videosClient;

  @Inject
  UsersClient userClient;

  private final ByteArrayOutputStream baos = new ByteArrayOutputStream();


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
      PicocliRunner.run(DislikeVideoCommand.class, ctx, args);

      assertTrue(baos.toString().contains("Video with id 0 does not exist"));
    }
  }


  @Test
  public void canDislike() {
    System.setOut(new PrintStream(baos));
    try (ApplicationContext ctx = ApplicationContext.run(Environment.CLI, Environment.TEST)) {
      String[] args = new String[] {"", "hashtag2"};
      PicocliRunner.run(DislikeVideoCommand.class, userId.toString(), videoId.toString());

      assertTrue(baos.toString().contains("Successfully disliked video with id " + videoId));
    }
  }
}
