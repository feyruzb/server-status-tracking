package dev.feyruz.serverstatustracking.repository;

import dev.feyruz.serverstatustracking.entity.CheckResult;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CheckResultRepository extends JpaRepository<CheckResult, Long> {
    List<CheckResult> findByServerIdOrderByCheckedAtDesc(Long serverId);
    Optional<CheckResult> findFirstByServerIdOrderByCheckedAtDesc(Long serverId);

}
