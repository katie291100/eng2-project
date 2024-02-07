package uk.ac.york.eng2.videos.controllers;

import io.micronaut.http.HttpStatus;
import io.micronaut.runtime.EmbeddedApplication;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import jakarta.inject.Inject;
import org.awaitility.Awaitility;
import org.junit.jupiter.api.Test;
import uk.ac.york.eng2.videos.clients.HealthcheckClient;

import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;

@MicronautTest(environments = "no_streams")
class HealthcheckControllerTest {

    @Inject
    HealthcheckClient healthcheckController;

    @Inject
    EmbeddedApplication<?> application;

    @Test
    void health() {

        Awaitility.await().atMost(30, TimeUnit.SECONDS).until(() -> application.isRunning());
        assertEquals(HttpStatus.OK, healthcheckController.health().status());
    }
}