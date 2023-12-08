package uk.ac.york.eng2.videos.controllers;


import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.runtime.EmbeddedApplication;
import jakarta.inject.Inject;

@Controller("/healthcheck")
public class HealthcheckController{

    @Inject
    EmbeddedApplication<?> application;

    @Get("/")
    public String check(){
        if (application.isRunning()){
            return "OK";
        }
        return "NOT RUNNING";
    }

}