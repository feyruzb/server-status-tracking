package dev.feyruz.serverstatustracking.entity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
@Entity
public class CheckResult {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "server_id")
    private MonitoredServer server;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private CheckStatus status;
    private Long responseTimeMs;
    private Instant checkedAt;

    protected CheckResult(){
    }

    public CheckResult(MonitoredServer server, CheckStatus status, Long responseTimeMs, Instant checkedAt){
        this.server = server;
        this.status = status;
        this.responseTimeMs = responseTimeMs;
        this.checkedAt = checkedAt;
    }

}
