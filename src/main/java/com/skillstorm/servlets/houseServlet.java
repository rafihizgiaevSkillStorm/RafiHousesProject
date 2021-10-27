package com.skillstorm.servlets;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.skillstorm.beans.House;
import com.skillstorm.data.housesDAO;


// Our HouseServlet that receives requests from our housefilter
@WebServlet({ "/firstServlet" })
public class houseServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	
	
	// Here we create our DAO which we use the whole servlet
	housesDAO DAO = new housesDAO();
	

	
    public houseServlet() {
      
    }
    
    // Our sevlet GET request, returns all the houses in our DB
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
    	// retrieve all the houses and put them in a list
        List<House> allHouses = DAO.FindAll();
		
        // Turns our list with houses objects into a JSON array of houses
		String json = new ObjectMapper().writeValueAsString(allHouses);
		
		// Print the JSON array to the response, set content and status
		response.getWriter().print(json);
		response.setContentType("application/json");
		response.setStatus(200);
		
	}
    
    
    // Our servlet DELETE request, deletes the house record
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	
    	 // retrieve the JSON object sent in the request
		 InputStream requestBody = req.getInputStream();	
		 // Turn the JSON into a House object using the House.class
		 House house = new ObjectMapper().readValue(requestBody, House.class);
		 // The DAO deletes the house object in the DB
		 DAO.delete(house);
		 resp.setStatus(200);
	}
	
    
    // Our servlet POST request, saves a new house to the DB
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		 // retrieve the JSON object sent in the request
		 InputStream requestBody = req.getInputStream();	
		 // Turn the JSON into a House object using the House.class
		 House house = new ObjectMapper().readValue(requestBody, House.class);
		 // The DAO creates the house object in the DB and returns the new object with the generated key
		 House newHouse = DAO.create(house);
		 // Turns our new house into a JSON object and prints to the response
		 resp.getWriter().print(new ObjectMapper().writeValueAsString(newHouse));
		 // Sets status to created
		 resp.setStatus(201);
		 resp.setContentType("application/json");
		
	 }
	
	// Our servlet PUT request, update a house in the DB
	protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		// retrieve the JSON object sent in the request
		InputStream requestBody = req.getInputStream();	
		// Turn the JSON into a House object using the House.class
		 House house = new ObjectMapper().readValue(requestBody, House.class);
		// The DAO updates the house object in the DB
		 House afterUpdateHouse = DAO.update(house);
		 // Turns our updated house into a JSON object and prints to the response
		 resp.getWriter().print(new ObjectMapper().writeValueAsString(afterUpdateHouse));
		 resp.setStatus(200);
		 resp.setContentType("application/json");
		 }
		
	}
////////////////////////////////RAFI HIZGIAEV ///////////////////////////////
