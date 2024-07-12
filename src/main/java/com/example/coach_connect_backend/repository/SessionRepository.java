package com.example.coach_connect_backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.coach_connect_backend.model.Session;

@Repository
public interface SessionRepository extends JpaRepository<Session, Integer>{
	@Query(value = "SELECT * FROM session WHERE listing_id = :listingId", nativeQuery = true)
	List<Session> getListingSessions(int listingId);
}
