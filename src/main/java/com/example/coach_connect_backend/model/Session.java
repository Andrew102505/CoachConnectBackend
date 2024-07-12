package com.example.coach_connect_backend.model;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import lombok.Data;

@Data
@Entity
public class Session {
	// will have input fields where we can parse these together
	@Id@GeneratedValue
	private int id;
	private String time;
	private String date;
	//the listing this session is associated with
	private int listingId;
	//number of participants signed up, this will change as people sign up
	private int numParticipants;
	private int capacity;
	@ManyToMany
	private List<Customer> participants = new ArrayList<>();
	public Session() {
		super();
	}
	public Session(String time, String date, int listingId, int capacity) {
		this.time = time;
		this.date = date;
		this.listingId = listingId;
		this.numParticipants = 0;//will always start at 0
		this.capacity = capacity;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public int getListingId() {
		return listingId;
	}
	public void setListingId(int listingId) {
		this.listingId = listingId;
	}
	public int getNumParticipants() {
		return numParticipants;
	}
	public void setNumParticipants(int numParticipants) {
		this.numParticipants = numParticipants;
	}
	public int getCapacity() {
		return capacity;
	}
	public void setCapacity(int capacity) {
		this.capacity = capacity;
	}
	
}
