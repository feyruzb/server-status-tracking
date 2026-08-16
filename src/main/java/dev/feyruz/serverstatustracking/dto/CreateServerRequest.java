package dev.feyruz.serverstatustracking.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public record CreateServerRequest(@NotBlank String ip,
                                  @Min(1) @Max(65535) Integer port,
                                  @NotBlank String name,
                                  String description,
                                  boolean enabled) {
}