package com.example.coach_connect_backend.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.coach_connect_backend.exceptions.ResourceNotFoundException;
import com.example.coach_connect_backend.model.Coach;
import com.example.coach_connect_backend.repository.CoachRepository;

@CrossOrigin(origins = "http://localhost:3000")//have to add this because spring blocks cross site request mappings if if don't specify which origins we will allow requests from
@RestController
@RequestMapping("/coachconnect/coach")
public class CoachController {
	
	@Autowired
	private CoachRepository coachRepository;
	
	//@PostMapping("/")
	//inside this post method you need to perform a check that the username and password hasn't
	//already been taken and transfer the values in the levels array into an arraylist
	@PostMapping("/coaches")
	public Coach createCoach(@RequestBody Coach coach) {
		//our constructor will convert levelsArray to an arraylist
		//the field names we pass in the frontend must match the field names in the backend for the entity and these getters and setters in the backend are tied to those fields(since we defined them in the entity class)
		Coach c = new Coach(coach.getFirstName(), coach.getLastName(), coach.getEmail(), coach.getPassword(), 
				coach.getLevelsObjectArray(), coach.getYearsOfExperience(), coach.getUstaRating(), coach.getBio(), coach.getLocation());
		return coachRepository.save(c);//returns the saved entity(c in this case)
	}
	@GetMapping("/coaches")
	private List<Coach> getAllCoaches(){
		return coachRepository.findAll();
	}
	
	@GetMapping("/coaches/{id}")//the type of the ResponseEntity specifies the type of data in the body of the ResponseEntity instance
	public ResponseEntity<Coach> getCoachById(@PathVariable int id) {
		Coach coach = coachRepository.findById(id).orElseThrow(
				() -> new ResourceNotFoundException("Coach", "Id", id)
				);
		//ResponseEntity allows us to pass back a response body(coach) and a status coach(ok -> 200)
		return ResponseEntity.ok(coach);
				
	}
}