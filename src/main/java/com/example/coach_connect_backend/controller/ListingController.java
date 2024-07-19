package com.example.coach_connect_backend.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.coach_connect_backend.exceptions.ResourceNotFoundException;
import com.example.coach_connect_backend.model.Listing;
import com.example.coach_connect_backend.repository.ListingRepository;
@CrossOrigin(origins = "http://localhost:3000")//have to add this because spring blocks cross site request mappings if if don't specify which origins we will allow requests from
@RestController
@RequestMapping("/coachconnect/listing")
public class ListingController {
	@Autowired
	private ListingRepository listingRepository;
	
	@PostMapping("/")
	public Listing createListing(@RequestBody Listing listing) {
		Listing l = new Listing(listing.getTitle(), listing.getDescription(), listing.getType(), 
				listing.getPrice(), listing.getLocation(), listing.getCoach(), listing.getAddress());
		 return listingRepository.save(l);
	}
	@GetMapping("/")
	public List<Listing> getAllListings(){
		return listingRepository.findAll();
	}
	@GetMapping("/{id}")
	public ResponseEntity<Listing> getListingById(@PathVariable int id){
		System.out.println("here--------------");
		Listing l = listingRepository.findById(id).orElseThrow(
				() -> new ResourceNotFoundException("Listing", "Id", id));
		return ResponseEntity.ok(l);
	} 
	@GetMapping("/coachlistings/{coachId}")
	public ResponseEntity<List<Listing>> getAllCoachListings(@PathVariable int coachId){
		
		return ResponseEntity.ok(listingRepository.getAllListingsByCoachId(coachId));
	}
	
}
