package dev.feyruz.serverstatustracking.dto;

import java.time.Instant;

public record CheckResultResponse(
        Long id,
        Long serverId,
        dev.feyruz.serverstatustracking.entity.CheckStatus status,
        Long responseTimeMs,
        Instant checkedAt
) {
}