package uk.ac.york.eng2.subscription.controllers;

import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Controller;
import io.micronaut.runtime.EmbeddedApplication;
import jakarta.inject.Inject;

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
