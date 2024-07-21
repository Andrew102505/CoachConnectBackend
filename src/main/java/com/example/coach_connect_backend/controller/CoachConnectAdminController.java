package com.example.coach_connect_backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.coach_connect_backend.exceptions.ResourceNotFoundException;
import com.example.coach_connect_backend.model.CoachConnectAdmin;
import com.example.coach_connect_backend.repository.CoachConnectAdminRepository;

//requests that have an origin of localhost:3000 are allowed
@CrossOrigin(origins = "http://localhost:3000")//have to add this because spring blocks cross site request mappings if if don't specify which origins we will allow requests from
@RestController
@RequestMapping("/coachconnect/admin")
public class CoachConnectAdminController {
	@Autowired
	private CoachConnectAdminRepository coachConnectAdminRepository;
	
	@GetMapping("/{adminId}")
	public ResponseEntity<CoachConnectAdmin> getAdminById(@PathVariable int adminId){
		CoachConnectAdmin a = coachConnectAdminRepository.findById(adminId).orElseThrow(
				() -> new ResourceNotFoundException("Admin", "Id", adminId)
				);
		return ResponseEntity.ok(a);
	}
	
	
	
}