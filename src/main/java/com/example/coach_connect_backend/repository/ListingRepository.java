package com.example.coach_connect_backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.coach_connect_backend.model.Listing;

@Repository
public interface ListingRepository extends JpaRepository<Listing, Integer>{
	@Query(value = "SELECT * FROM listing WHERE coach_id = :coachId", nativeQuery = true)
	List<Listing> getAllListingsByCoachId(int coachId);
}
