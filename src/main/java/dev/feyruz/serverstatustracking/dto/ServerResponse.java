package dev.feyruz.serverstatustracking.dto;

public record ServerResponse(Long id,
                             String ip,
                             Integer port,
                             String name,
                             String description,
                             boolean enabled) {

}

