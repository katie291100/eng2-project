package uk.ac.york.eng2.cli.dto;

import io.micronaut.serde.annotation.Serdeable;

@Serdeable
public record UserDTO(String name) {


    public String getName() {
        return name;
    }




}