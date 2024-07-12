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
import com.example.coach_connect_backend.model.Session;
import com.example.coach_connect_backend.repository.SessionRepository;

@CrossOrigin(origins = "http://localhost:3000")//have to add this because spring blocks cross site request mappings if if don't specify which origins we will allow requests from
@RestController
@RequestMapping("/coachconnect/session")
public class SessionController {

	@Autowired
	private SessionRepository sessionRepository;
	
	@PostMapping("/")
	public Session createSession(@RequestBody Session session) {
		System.out.println("here-------------");
		Session s = new Session(session.getTime(), session.getDate(), session.getListingId(), session.getCapacity());
		return sessionRepository.save(s);
	}
	
	@GetMapping("/{id}")//this method will get a specific session by the session id
	public ResponseEntity<Session> getSessionById(@PathVariable int id){
		Session s = sessionRepository.findById(id).orElseThrow(
				() -> new ResourceNotFoundException("Session", "Id", id)
				);
		return ResponseEntity.ok(s);
	}
	
	@GetMapping("/listingsessions/{listingId}")//will get all of the sessions for a listing with the listing id
	public ResponseEntity<List<Session>> getListingSessions(@PathVariable int listingId){
		//dont need to check if the list is null because it's possible that a coach hasn't added any sessions to his/her listings yet
		return ResponseEntity.ok(sessionRepository.getListingSessions(listingId));
	}
	
}
