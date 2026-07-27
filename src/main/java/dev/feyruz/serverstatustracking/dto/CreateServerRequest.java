package dev.feyruz.serverstatustracking.dto;

import jakarta.validation.constraints.NotBlank;

public record CreateServerRequest(@NotBlank String ip,
                                  @NotBlank String name,
                                  String description) {
}