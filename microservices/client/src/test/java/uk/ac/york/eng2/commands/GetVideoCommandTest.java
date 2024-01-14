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
import uk.ac.york.eng2.cli.commands.GetVideoCommand;
import uk.ac.york.eng2.cli.dto.HashtagDTO;
import uk.ac.york.eng2.cli.dto.UserDTO;
import uk.ac.york.eng2.cli.dto.VideoDTO;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.PrintStream;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertTrue;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@MicronautTest
public class GetVideoCommandTest {

  @Inject
  VideosClient videosClient;

  @Inject
  UsersClient usersClient;

  private final ByteArrayOutputStream baos = new ByteArrayOutputStream();

  @ClassRule
  public static ComposeContainer environment = new ComposeContainer(new File("src/test/resources/compose-test.yml"))
          .withExposedService("video-microservice", 8080, Wait.forHttp("/health").forStatusCode(200))
          .withExposedService("trending-hashtag-microservice", 8080, Wait.forHttp("/health").forStatusCode(200))
          .withLogConsumer("video-microservice", (outputFrame) -> {
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
    VideoDTO videoDTO = new VideoDTO();
    UserDTO userDTO = new UserDTO("TestUser");
    HttpResponse<Void> response = usersClient.add(userDTO);
    userId = Long.parseLong(response.header("location").split("/")[2]);

    videoDTO.setTitle("Video Test Title");
    videoDTO.setPostedBy(userId);

    Set<HashtagDTO> hashtags = Set.of(new HashtagDTO("hashtag1"), new HashtagDTO("hashtag2"));
    videoDTO.setHashtags(hashtags);
    HttpResponse<Void> responseVid = videosClient.add(videoDTO);
    videoId = Long.parseLong(responseVid.header("location").split("/")[2]);

  }

  @Test
  public void testGetVideoByIdInvalidIdError() {
    System.setOut(new PrintStream(baos));

    try (ApplicationContext ctx = ApplicationContext.run(Environment.CLI, Environment.TEST)) {
      Awaitility.await().atMost(30, TimeUnit.SECONDS).until(ctx::isRunning);
      String[] args = new String[] {"-id", "0"};
      PicocliRunner.run(GetVideoCommand.class, ctx, args);

      assertTrue(baos.toString().contains("Video with id 0 does not exist"));
    }
  }

  @Test
  public void testGetVideoByIdValidId() {
    System.setOut(new PrintStream(baos));

    try (ApplicationContext ctx = ApplicationContext.run(Environment.CLI, Environment.TEST)) {
      Awaitility.await().atMost(30, TimeUnit.SECONDS).until(ctx::isRunning);
      String[] args = new String[] {"-id", videoId.toString()};
      PicocliRunner.run(GetVideoCommand.class, ctx, args);

      assertTrue(baos.toString().contains(videoId + " - Video Test Title - "));
    }
  }

  @Test
  public void testGetVideoByHashtagValid() {
    System.setOut(new PrintStream(baos));

    try (ApplicationContext ctx = ApplicationContext.run(Environment.CLI, Environment.TEST)) {
      Awaitility.await().atMost(30, TimeUnit.SECONDS).until(ctx::isRunning);
      String[] args = new String[] {"-hashtag", "hashtag2"};
      PicocliRunner.run(GetVideoCommand.class, ctx, args);

      assertTrue(baos.toString().contains(videoId + " - Video Test Title - "));
    }
  }
  @Test
  public void testGetVideoByHashtagInvalidError() {
    System.setOut(new PrintStream(baos));

    try (ApplicationContext ctx = ApplicationContext.run(Environment.CLI, Environment.TEST)) {
      Awaitility.await().atMost(30, TimeUnit.SECONDS).until(ctx::isRunning);
      String[] args = new String[] {"-hashtag", "notahashtag"};
      PicocliRunner.run(GetVideoCommand.class, ctx, args);

      assertTrue(baos.toString().contains("Video with hashtag notahashtag does not exist"));
    }
  }

  @Test
  public void testGetVideoByUserInvalidError() {
    System.setOut(new PrintStream(baos));

    try (ApplicationContext ctx = ApplicationContext.run(Environment.CLI, Environment.TEST)) {
      Awaitility.await().atMost(30, TimeUnit.SECONDS).until(ctx::isRunning);
      String[] args = new String[] {"-postedBy", "0"};
      PicocliRunner.run(GetVideoCommand.class, ctx, args);

      assertTrue(baos.toString().contains("User with id 0 does not exist"));
    }
  }

  @Test
  public void testGetVideoByUserValidNoVideosError() {
    System.setOut(new PrintStream(baos));
    UserDTO userDTO = new UserDTO("TestUser2");
    HttpResponse<Void> response = usersClient.add(userDTO);
    Long userIdNew = Long.parseLong(response.header("location").split("/")[2]);

    try (ApplicationContext ctx = ApplicationContext.run(Environment.CLI, Environment.TEST)) {
      Awaitility.await().atMost(30, TimeUnit.SECONDS).until(ctx::isRunning);
      String[] args = new String[] {"-postedBy", userIdNew.toString()};
      PicocliRunner.run(GetVideoCommand.class, ctx, args);

      assertTrue(baos.toString().contains("User with id " + userIdNew + " has not posted any videos"));
    }
  }

  @Test
  public void testGetVideoByUserValid() {
    System.setOut(new PrintStream(baos));

    try (ApplicationContext ctx = ApplicationContext.run(Environment.CLI, Environment.TEST)) {
      Awaitility.await().atMost(30, TimeUnit.SECONDS).until(ctx::isRunning);
      String[] args = new String[] {"-postedBy", userId.toString()};
      PicocliRunner.run(GetVideoCommand.class, ctx, args);

      assertTrue(baos.toString().contains(videoId + " - Video Test Title - "));
    }
  }
}
