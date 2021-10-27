package com.skillstorm.servlets;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.skillstorm.beans.House;
import com.skillstorm.data.housesDAO;

//Our searchServlet that receives requests from our searchfilter
@WebServlet(urlPatterns = "/searchServlet")
public class searchServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	// Here we create our DAO which we use the whole servlet
	housesDAO DAO = new housesDAO();
	
    public searchServlet() {
        
    }

    // Our sevlet GET request, returns all the houses in our DB that match the query
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		// Debug print // System.out.println("SearchServlet");
		
		// retrieve all the houses and put them in a set
		Set<House> foundHouses = new HashSet<>();
		
		// We retrieve all the parameters passed from our XHR request by their id
		String attr = request.getParameter("attr");
		String operator = request.getParameter("operator");
		String text = request.getParameter("searchValue"); 
		String flag = request.getParameter("flag"); 
		// Debug print // System.out.println(attr + "," + operator + "," + text + "," + flag);
		
		// Check if the attribute we want to search by is the forSale attribute and if so check if the user
		// wanted for it to be true or false. If it is 'y'(yes/true) then assign to the search operator '=' else assign '!='
		if( attr.equals("forSale") && text.equals("y")) {
			text = "1";
			operator = "equals";
			System.out.println("in y");
		}
		if( attr.equals("forSale") && text.equals("n")) {
			text = "1";
			operator = "notEqual";
			System.out.println("in n");
		}
		
		// Check the flag to see of it is a String or Number||Boolean and invoke the corresponding method
		
		if(flag.equals("string")) {
			// The search receives the attr = column name, operator, and text to search by
			foundHouses = DAO.FindAllWithConditionString(attr, operator, text);
		}
		
		if( flag.equals("number") || flag.equals("boolean")) {
			// The search receives the attr = column name, operator, and  it parses the text to an integer
		    foundHouses = DAO.FindAllWithConditionNumber(attr, operator, Integer.parseInt(text));
		}
		// Turns our set with houses objects into a JSON array of houses
		String json = new ObjectMapper().writeValueAsString(foundHouses);
		
		// Print the JSON array to the response, set content and status
		response.getWriter().print(json);
		response.setContentType("application/json");
		response.setStatus(202);

       
			}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	
	}

}
////////////////////////////////RAFI HIZGIAEV ///////////////////////////////