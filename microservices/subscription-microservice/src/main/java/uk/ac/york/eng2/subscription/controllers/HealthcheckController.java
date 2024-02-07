package uk.ac.york.eng2.subscription.controllers;

import io.micronaut.configuration.kafka.streams.InteractiveQueryService;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Controller;
import io.micronaut.runtime.EmbeddedApplication;
import jakarta.inject.Inject;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.state.QueryableStoreTypes;
import uk.ac.york.eng2.subscription.events.SubscriptionIdentifier;

import java.util.List;

@Controller("/health")
public class HealthcheckController implements HealthcheckControllersInterface{

    @Inject
    EmbeddedApplication<?> application;
    @Override
    public HttpResponse<Void> health() {
        if (application.isRunning()){
            return HttpResponse.ok();
        }
        return HttpResponse.serverError();    }
}
