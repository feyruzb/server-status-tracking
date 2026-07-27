package dev.feyruz.serverstatustracking.repository;

import dev.feyruz.serverstatustracking.entity.CheckResult;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CheckResultRepository extends JpaRepository<CheckResult, Long> {

}
