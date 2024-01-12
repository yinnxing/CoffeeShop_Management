package com.example.project_server.repository;

import com.example.project_server.entity.Timekeeping;
import com.example.project_server.serializable.TimekeepingId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TimekeepingRepository extends JpaRepository<Timekeeping, TimekeepingId> {
}
