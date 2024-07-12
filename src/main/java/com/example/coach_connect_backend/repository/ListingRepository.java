package com.example.coach_connect_backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.coach_connect_backend.model.Listing;

@Repository
public interface ListingRepository extends JpaRepository<Listing, Integer>{
	
}
