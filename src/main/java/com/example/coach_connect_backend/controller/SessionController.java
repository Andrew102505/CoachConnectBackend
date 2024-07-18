package com.example.coach_connect_backend.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.coach_connect_backend.exceptions.ResourceNotFoundException;
import com.example.coach_connect_backend.model.Customer;
import com.example.coach_connect_backend.model.Session;
import com.example.coach_connect_backend.repository.CustomerRepository;
import com.example.coach_connect_backend.repository.SessionRepository;

@CrossOrigin(origins = "http://localhost:3000")//have to add this because spring blocks cross site request mappings if if don't specify which origins we will allow requests from
@RestController
@RequestMapping("/coachconnect/session")
public class SessionController {

	@Autowired
	private SessionRepository sessionRepository;
	@Autowired
	private CustomerRepository customerRepository;
	
	@PostMapping("/")
	public Session createSession(@RequestBody Session session) {
		System.out.println("here-------------");
		Session s = new Session(session.getTime(), session.getDate(), session.getListingId(), session.getCapacity(), session.getName(), session.getTitle());
		return sessionRepository.save(s);
	}
	
	@GetMapping("/{sessionId}")//this method will get a specific session by the session id
	public ResponseEntity<Session> getSessionById(@PathVariable int sessionId){
		Session s = sessionRepository.findById(sessionId).orElseThrow(
				() -> new ResourceNotFoundException("Session", "Id", sessionId)
				);
		return ResponseEntity.ok(s);
	}
	@GetMapping("/participantsessions/{customerId}")//returns all the sessions that a participant is in
	public ResponseEntity<List<Session>> getParticipantSessions(@PathVariable int customerId){
		List<Session> sessions = sessionRepository.findAll();
		List<Session> customerSessions = new ArrayList<>();
		for(Session session : sessions) {
			for(int i = 0; i<session.getParticipants().size(); i++) {
				if(session.getParticipants().get(i).getId()==customerId) {
					customerSessions.add(session);
				}
			}
		}
		System.out.println(customerSessions);
		return ResponseEntity.ok(customerSessions);
	}
	@GetMapping("/listingsessions/{listingId}")//will get all of the sessions for a listing with the listing id
	public ResponseEntity<List<Session>> getListingSessions(@PathVariable int listingId){
		//dont need to check if the list is null because it's possible that a coach hasn't added any sessions to his/her listings yet
		return ResponseEntity.ok(sessionRepository.getListingSessions(listingId));
	}
	
	@PutMapping("/addparticipant/{sessionId}")//did put mapping since we're updating something that already exists
	public ResponseEntity<List<Customer>> addParticipantToSession(@PathVariable int sessionId, @RequestBody Customer customer){
		Session s = sessionRepository.findById(sessionId).orElseThrow(
				() -> new ResourceNotFoundException("Session", "Id", sessionId)
				);
		//return ResponseEntity.ok(s);
		//System.out.println(customer);
		//unsaved transient error - this happened because we were saving customer object in the parameter which is not in the db - its representing an object in the db but is not the actual reference to the object in the dbin our case, so we need to use the param customer to recover the customer object in the db that it is representing
		System.out.println(customer.getId());
		Customer c = customerRepository.findById(customer.getId()).orElseThrow(
				() -> new ResourceNotFoundException("Customer", "Id", customer.getId())
				);
		s.getParticipants().add(c);
		//increasing the session enrollment by 1(number of participants signed up)
		s.setNumParticipants(s.getNumParticipants() + 1);
		sessionRepository.save(s);//saving the updated session since its arraylist of customer participants was changed and the session's number of participants increased
		System.out.println(s.getParticipants());
		return ResponseEntity.ok(s.getParticipants());
		
	}
	/*@GetMapping("/getsessionparticipants/{sessionId}")
	public ResponseEntity<List<Customer>> getSessionParticipants(@PathVariable int sessionId){
		List<Customer> participants = sessionRepository.getParticipantsBySessionId(sessionId);
		return ResponseEntity.ok(participants); 
	}*/
	@GetMapping("/getsessionparticipants/{sessionId}")
	public ResponseEntity<List<Customer>> getSessionParticipants(@PathVariable int sessionId){
		Session s = sessionRepository.findById(sessionId).orElseThrow(
				() -> new ResourceNotFoundException("Session", "Id", sessionId)
				);
		List<Customer> participants = s.getParticipants();
		return ResponseEntity.ok(participants);
	}
	//remember params in a post request are not displayed in the url in the browser
	@PostMapping("/containsparticipant/{sessionId}/{customerId}")
	public boolean containsParticipant(@PathVariable int sessionId, @PathVariable int customerId) {
		Session s = sessionRepository.findById(sessionId).orElseThrow(
				() -> new ResourceNotFoundException("Session", "Id", sessionId)
				);
		Customer c = customerRepository.findById(customerId).orElseThrow(
				() -> new ResourceNotFoundException("Customer", "Id", customerId)
				);
		
		return s.getParticipants().contains(c);
	}
	/*@GetMapping("/participants/{id}")
	public ResponseEntity<List<Customer>> addParticipantToSession(@PathVariable int sessionId){
		Session s = sessionRepository.findById(sessionId).orElseThrow(
				() -> new ResourceNotFoundException("Session", "Id", sessionId)
				);
		//unsaved transient instance - we need to find the 
		System.out.println(s.getParticipants());
		return ResponseEntity.ok(s.getParticipants());
}*/
}