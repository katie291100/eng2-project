package uk.ac.york.eng2.trendingHashtags;

import io.micronaut.configuration.kafka.streams.InteractiveQueryService;
import io.micronaut.test.annotation.MockBean;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import jakarta.inject.Inject;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.state.KeyValueIterator;
import org.apache.kafka.streams.state.ReadOnlyKeyValueStore;
import org.apache.kafka.streams.state.ValueAndTimestamp;

import org.awaitility.Awaitility;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;


import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;


@MicronautTest(transactional = false)
public class TrendingControllerTest {

    @Inject
    InteractiveQueryService interactiveQueryService;

    @Inject
    TrendingClient trendingClient;

    @Inject
    KafkaStreams kStreams;

    @BeforeEach
    public void setUp() {
//        Awaitility.await().atMost(30, TimeUnit.SECONDS).until(() -> kStreams.state().equals(KafkaStreams.State.RUNNING));
    }


    @Test
    public void testListEmpty() {
        List<Long> result = trendingClient.list();

        assertNull(result);
    }

}