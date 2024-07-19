package com.example.coach_connect_backend.model;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Data
@Entity
public class Listing {
	
	@Id@GeneratedValue
	private int id;
	private String title;
	private String description;
	//we wont use inheritance because when the user selects to filter between its easier to just create  jpql query that just selects all of type clinic or private 
	private String type;
	//private String image;//url of image;
	private double price;
	private String location;
	private String address;
	@ManyToOne
	private Coach coach;//id of the coach who is running this listing
	//only make the list of participants accessible to the coach and admin
	//create a tab for coaches called your listings and it would display all of this data
	//we don't need this because we are attaching a the id of the listing to the session.
	//private List<Integer> sessions = new ArrayList<>();//stores the id's of all the session under this listing.
	
	public Listing() {
		super();
	}
	//when allowing the coach to add sessions make a button called add session that elicits a function call for a create session component to be shown. This is done by conditional rednering the function called will change a state var from false to true; when false the popup will not be shown
	public Listing(String title, String description, String type, double price,/*String image,*/ String location, Coach coach, String address) {
		this.title = title;
		this.description = description;
		this.type = type;
		this.price = price;
		this.location = location;
		this.coach = coach;
		this.address = address;
	}
	
	//on the listing details we will display each session with map function
	//we will add a button on each which will fire a function and that session id to the array
	//make the array use state so it can be updated
	//figure out a way to change the session to green when clicked on
	//also add a remove button to the session
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	/*public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	*/
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public Coach getCoach() {
		return coach;
	}
	public void setCoach(Coach coach) {
		this.coach = coach;
	}
	
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	
	
	
	
}
