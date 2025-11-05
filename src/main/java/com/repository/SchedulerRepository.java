package com.repository;

import com.entity.Scheduler;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SchedulerRepository extends JpaRepository<Scheduler, Long> {
    List<Scheduler> findAllByWriter(String writer);
    // findAllByWriter()는 JPA의 메서드 네이밍 규칙에 따라 자동으로 SQL을 만들어줌
    //→ "SELECT * FROM scheduler WHERE writer = ?"
}
