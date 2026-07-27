package dev.feyruz.serverstatustracking.dto;

import java.time.Instant;

public record CheckResultResponse(
        Long id,
        Long serverId,
        String status,
        Long responseTimeMs,
        Instant checkedAt
) {
}