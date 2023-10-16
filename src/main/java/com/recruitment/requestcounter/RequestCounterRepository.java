package com.recruitment.requestcounter;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RequestCounterRepository extends JpaRepository<RequestCounterEntity, Long> {
    Optional<RequestCounterEntity> findByLogin(String login);
}
