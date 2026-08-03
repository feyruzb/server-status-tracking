package dev.feyruz.serverstatustracking.repository;

import dev.feyruz.serverstatustracking.entity.CheckResult;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CheckResultRepository extends JpaRepository<CheckResult, Long> {
    List<CheckResult> findByServerIdOrderByCheckedAtDesc(Long serverId);

}
