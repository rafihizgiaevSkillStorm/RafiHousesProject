package com.skillstorm.data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.sql.Statement;
import com.skillstorm.beans.House;
import com.skillstorm.exceptions.invalidHouseAddressException;

public class housesDAO { // Here we define our DAO object class for the whole project

	// DAO attributes: we define our credentials used for connections to our DB
	private static final String url = "jdbc:mysql://localhost:3306/houses";
	private static final String username = "root";
	private static final String password = "root";
//
//	private static final String url = "jdbc:mysql://rafi-houses-db-rrh.mysql.database.azure.com:3306/houses?useSSL=true&requireSSL=false"; 
//	private static final String username = "iftakon@rafi-houses-db-rrh";
//	private static final String password = "OIE71r04!";

	
	// Loading our class into memory
	static {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	
	
	
	// DAO create method to create a new house, receives a house object(without an
	// ID) from our houseServlet "POST" method
	public House create(House house) throws invalidHouseAddressException {

		//Check that the house does not exists already and only then enter
		if(checkIfHouseExists(house) == -1){
			
		
		
		// Defining our connection with ACID Transaction
		Connection conn = null;
		try {
			conn = DriverManager.getConnection(url, username, password);
			conn.setAutoCommit(false);
		} catch (SQLException e2) {
			e2.printStackTrace();
		}

		try {
			// Setting our query that will return the ID key as well
			String sql = "Insert into Houses(streetAddress, city, state, country, postalCode, sqft, estPrice, numberBedRooms, numberBathRooms, forSale, typeOfProperty) VALUES (?,?,?,?,?,?,?,?,?,?,?)";
			PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			stmt.setString(1, house.getStreetAddress());
			stmt.setString(2, house.getCity());
			stmt.setString(3, house.getState());
			stmt.setString(4, house.getCountry());
			stmt.setString(5, house.getPostalCode());
			stmt.setInt(6, house.getSqft());
			stmt.setLong(7, house.getEstPrice());
			stmt.setInt(8, house.getNumberBedRooms());
			stmt.setInt(9, house.getNumberBathRooms());
			stmt.setBoolean(10, house.isForSale());
			stmt.setString(11, house.getTypeOfProperty());
			stmt.executeUpdate();
			// Returning the generated key for our new house
			ResultSet keys = stmt.getGeneratedKeys();
			keys.next();
			int houseid = keys.getInt(1);
			// Giving the created house the id generated from the DB
			house.setHouseid(houseid);
			
			// debug print//System.out.println(house.toString());
			
			conn.commit();
			
		} // Rollback if any error and close connection
		catch (SQLException e) {
			e.printStackTrace();
			try {
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return house;
	}
			return null;
	}
	
	
	// DAO finds all houses in our DB method to create a new List of all current
	// houses in our DB, invoked from our houseServlet "GET" method
	public List<House> FindAll() {

		// Defining our List of houses to return to client
		List<House> allHouses = new LinkedList<>();

		// Defining our connection with try with resources
		try (Connection conn = DriverManager.getConnection(url, username, password)){

			// Setting our query that will return all the houses with their ID
			String sql = "select houseid, streetAddress, city, state, country, postalCode , sqft, estPrice, numberBedRooms, numberBathRooms, forSale, typeOfProperty  from houses";
			PreparedStatement stmt = conn.prepareStatement(sql);

			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				// Taking each record returned from the DB and creating a house object
				int houseID = rs.getInt(1);
				String streetAdd = rs.getString(2);
				String city = rs.getString(3);
				String state = rs.getString(4);
				String country = rs.getString(5);
				String postalCode = rs.getString(6);
				int sqft = rs.getInt(7);
				long estPrice = rs.getLong(8);
				int numberOfRooms = rs.getInt(9);
				int numberOfBaths = rs.getInt(10);
				boolean forSale = rs.getBoolean(11);
				String typeOfProperty = rs.getString(12);
				House house = new House(houseID, streetAdd, city, state, country, postalCode, sqft, estPrice,
						numberOfRooms, numberOfBaths, forSale, typeOfProperty);
				// Push the record = new house to List
				allHouses.add(house);
			}

		}catch (SQLException e) {
			e.printStackTrace();
		}
		return allHouses;

	}

	
	
	// NOT USED!
	public boolean checkHouseValid(House house) {
		if ((house.getStreetAddress() == null) || (house.getCity() == null) || (house.getState() == null)
				|| (house.getPostalCode() == null)) {
			return false;
		}
		return true;
	}

	
	
	// NOT USED!
	public Set<House> FindAllForSale() {

		Set<House> allHouses = new HashSet<>();

		try (Connection conn = DriverManager.getConnection(url, username, password)){

			String sql = "select houseid, streetAddress, city, state, country, postalCode , sqft, estPrice, numberBedRooms, numberBathRooms, forSale, typeOfProperty  from houses where forSale = true";
			PreparedStatement stmt = conn.prepareStatement(sql);

			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {

				int houseID = rs.getInt(1);
				String streetAdd = rs.getString(2);
				String city = rs.getString(3);
				String state = rs.getString(4);
				String country = rs.getString(5);
				String postalCode = rs.getString(6);
				int sqft = rs.getInt(7);
				long estPrice = rs.getLong(8);
				int numberOfRooms = rs.getInt(9);
				int numberOfBaths = rs.getInt(10);
				boolean forSale = rs.getBoolean(11);
				String typeOfProperty = rs.getString(12);
				House house = new House(houseID, streetAdd, city, state, country, postalCode, sqft, estPrice,
						numberOfRooms, numberOfBaths, forSale, typeOfProperty);
				allHouses.add(house);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return allHouses;

	}

	
	
	// DAO find method that is invoked by servlet when the attribute is a number type, 
	// receives what attribute to put in "where" clause, operator '=, !=, <, >' ,
	// a value to search and returns a Set of houses that match
	public Set<House> FindAllWithConditionNumber(String attr, String operator, int value) {

		// Define the Set that will hold the result
		Set<House> allHouses = new HashSet<>();
		
		// Defining our connection with try with resources
		try (Connection conn = DriverManager.getConnection(url, username, password)){
			String sql = "";
			// We receive an operator parameter from our houseServlet that defines the operator of our where clause
			switch (operator) {
			//operator = '>'
			case ("greaterThan"):
				sql = "select houseid, streetAddress, city, state, country, postalCode , sqft, estPrice, numberBedRooms, numberBathRooms, forSale, typeOfProperty  from houses where ? > ?";
				break;
			// operator = '<'
			case ("lessThan"):
				sql = "select houseid, streetAddress, city, state, country, postalCode , sqft, estPrice, numberBedRooms, numberBathRooms, forSale, typeOfProperty  from houses where ? < ?";
				break;
			// operator = '='
			case ("equals"):
				sql = "select houseid, streetAddress, city, state, country, postalCode , sqft, estPrice, numberBedRooms, numberBathRooms, forSale, typeOfProperty  from houses where ? = ?";
				break;
			// operator = '!='
			case ("notEqual"):
				sql = "select houseid, streetAddress, city, state, country, postalCode , sqft, estPrice, numberBedRooms, numberBathRooms, forSale, typeOfProperty  from houses where ? != ?";
				break;
			}
			//prepare our statement and fill in the missing parameters for our select query
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setString(1, attr);
			stmt.setInt(2, value);
			// when we put a parameter for one of our table attributes the 
			// statements adds double to the column name and this will give
			// us an error when trying to execute in MYSQL, so we remove the
			// quotes from the column name
			String stmt2 = (stmt.toString().replaceAll("\'", ""));
			String[] temp = stmt2.split(":");
			PreparedStatement stmt3 = conn.prepareStatement(temp[1]);
			ResultSet rs = stmt3.executeQuery();

			// create a house object with every table record retrieved from the query
			// and add each house to the returned set
			while (rs.next()) {

				int houseID = rs.getInt(1);
				String streetAdd = rs.getString(2);
				String city = rs.getString(3);
				String state = rs.getString(4);
				String country = rs.getString(5);
				String postalCode = rs.getString(6);
				int sqft = rs.getInt(7);
				long estPrice = rs.getLong(8);
				int numberOfRooms = rs.getInt(9);
				int numberOfBaths = rs.getInt(10);
				boolean forSale = rs.getBoolean(11);
				String typeOfProperty = rs.getString(12);
				House house = new House(houseID, streetAdd, city, state, country, postalCode, sqft, estPrice,
						numberOfRooms, numberOfBaths, forSale, typeOfProperty);
				allHouses.add(house);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return allHouses;

	}

	
	// DAO find method that is invoked by servlet when the attribute is a string type, 
	// receives what attribute to put in "where" clause, operator '=, !=' ,
	// a value to search and returns a Set of houses that match
	public Set<House> FindAllWithConditionString(String attr, String operator, String value) {

		// Define the Set that will hold the result
		Set<House> allHouses = new HashSet<>();

		// Defining our connection with try with resources
		try (Connection conn = DriverManager.getConnection(url, username, password)){
			String sql = "";
			// We receive an operator parameter from our houseServlet that defines the operator of our where clause
			switch (operator) {
			// operator = '='
			case ("equals"):
				sql = "select houseid, streetAddress, city, state, country, postalCode , sqft, estPrice, numberBedRooms, numberBathRooms, forSale, typeOfProperty from houses where ? = ";
				break;
			// operator = '!='
			case ("notEqual"):
				sql = "select houseid, streetAddress, city, state, country, postalCode , sqft, estPrice, numberBedRooms, numberBathRooms, forSale, typeOfProperty from houses where ? != ";
				break;
			}
			//prepare our statement and fill in the missing parameters for our select query
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setString(1, attr);
			// when we put a parameter for one of our table attributes the
			// statements adds double to the column name and this will give
			// us an error when trying to execute in MYSQL, so we remove the
			// quotes from the column name and only then add the value parameter
			// because our string values will not work without quotes
			String stmt2 = (stmt.toString().replaceAll("\'", "")).concat("?");
			System.out.println(stmt2);
			String[] temp = stmt2.split(":");
			PreparedStatement stmt3 = conn.prepareStatement(temp[1]);
			stmt3.setString(1, value);
			System.out.println(stmt3.toString());
			ResultSet rs = stmt3.executeQuery();

			// create a house object with every table record retrieved from the query
			// and add each house to the returned set
			while (rs.next()) {

				int houseID = rs.getInt(1);
				String streetAdd = rs.getString(2);
				String city = rs.getString(3);
				String state = rs.getString(4);
				String country = rs.getString(5);
				String postalCode = rs.getString(6);
				int sqft = rs.getInt(7);
				long estPrice = rs.getLong(8);
				int numberOfRooms = rs.getInt(9);
				int numberOfBaths = rs.getInt(10);
				boolean forSale = rs.getBoolean(11);
				String typeOfProperty = rs.getString(12);
				House house = new House(houseID, streetAdd, city, state, country, postalCode, sqft, estPrice,
						numberOfRooms, numberOfBaths, forSale, typeOfProperty);
				allHouses.add(house);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return allHouses;

	}

	
	
	
	
	
	// DAO find method that retrieves a record matching the id that is passed
	// creates and returns that house of it exists
	public House findById(int id) {

		// Defining our connection with try with resources
				try (Connection conn = DriverManager.getConnection(url, username, password)){
			String sql = "select houseid, streetAddress, city, state, country, postalCode , sqft, estPrice, numberBedRooms, numberBathRooms, forSale, typeOfProperty from houses where houseid = ?";
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setInt(1, id);
			ResultSet rs = stmt.executeQuery();
			rs.next();
			int houseID = rs.getInt(1);
			String streetAdd = rs.getString(2);
			String city = rs.getString(3);
			String state = rs.getString(4);
			String country = rs.getString(5);
			String postalCode = rs.getString(6);
			int sqft = rs.getInt(7);
			long estPrice = rs.getLong(8);
			int numberOfRooms = rs.getInt(9);
			int numberOfBaths = rs.getInt(10);
			boolean forSale = rs.getBoolean(11);
			String typeOfProperty = rs.getString(12);
			House house = new House(houseID, streetAdd, city, state, country, postalCode, sqft, estPrice, numberOfRooms,
					numberOfBaths, forSale, typeOfProperty);
			return house;

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	
	// NOT USED!
	public House findByStreetAddress(String add) {

		try (Connection conn = DriverManager.getConnection(url, username, password)) {
			String sql = "select houseid, streetAddress, city, state, country, postalCode , sqft, estPrice, numberBedRooms, numberBathRooms, forSale, typeOfProperty  from houses where streetAddress = ?";
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setString(1, add);
			ResultSet rs = stmt.executeQuery();
			rs.next();
			int houseID = rs.getInt(1);
			String streetAdd = rs.getString(2);
			String city = rs.getString(3);
			String state = rs.getString(4);
			String country = rs.getString(5);
			String postalCode = rs.getString(6);
			int sqft = rs.getInt(7);
			long estPrice = rs.getLong(8);
			int numberOfRooms = rs.getInt(9);
			int numberOfBaths = rs.getInt(10);
			boolean forSale = rs.getBoolean(11);
			String typeOfProperty = rs.getString(12);
			House house = new House(houseID, streetAdd, city, state, country, postalCode, sqft, estPrice, numberOfRooms,
					numberOfBaths, forSale, typeOfProperty);
			return house;

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;

	}

	
	// DAO update record function, receives a house object from the server
	// and first it checks with the checkIfHouseExists(house) function. 
	// If the house exists it returns the updated house, if the house does 
	// not exist it returns null to the servlet. 
	public House update(House house) {

		// Defining our connection with ACID Transaction
		Connection conn = null;
		try {
			conn = DriverManager.getConnection(url, username, password);
			conn.setAutoCommit(false);
		} catch (SQLException e2) {
			e2.printStackTrace();
		}
		try {

			// Here we check if the house exists and if it does we assign its key to the 'key' variable, 
			// if the house does not exists the 'key' variable will hold -1;
			int key = checkIfHouseExists(house);
			if (key > 0) {
				house.setHouseid(key);
				String sql = "Update houses Set streetAddress = ?, city = ?, state = ?, country = ?, postalCode = ?, sqft = ?, estPrice = ?, numberBedRooms = ?, numberBathRooms = ?, forSale = ?, typeOfProperty = ? where houseid = ?";
				PreparedStatement stmt = conn.prepareStatement(sql);
				stmt.setString(1, house.getStreetAddress());
				stmt.setString(2, house.getCity());
				stmt.setString(3, house.getState());
				stmt.setString(4, house.getCountry());
				stmt.setString(5, house.getPostalCode());
				stmt.setInt(6, house.getSqft());
				stmt.setLong(7, house.getEstPrice());
				stmt.setInt(8, house.getNumberBedRooms());
				stmt.setInt(9, house.getNumberBathRooms());
				stmt.setBoolean(10, house.isForSale());
				stmt.setString(11, house.getTypeOfProperty());
				stmt.setInt(12, key);
				// Debug print // System.out.println(stmt.toString());
				stmt.executeUpdate();
				
				conn.commit();
				return house;
				
			} 
		} // Rollback if any error and close connection
		catch (SQLException e) {
			e.printStackTrace();
			try {
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		// return null if the house exists
		return null;
	}

	
	
	// DAO here we check if the house exists by checking all of its NOT NULL attributes
	// if it does exists the 'key' variable will hold the existing house's id
	public int checkIfHouseExists(House house) {
		int key = -1;
		try (Connection conn = DriverManager.getConnection(url, username, password)) {
			String checkSQL = "Select houseid from houses where streetAddress = ? and city = ? and state = ? and country = ? and postalCode = ?";
			PreparedStatement stmt = conn.prepareStatement(checkSQL);
			stmt.setString(1, house.getStreetAddress());
			stmt.setString(2, house.getCity());
			stmt.setString(3, house.getState());
			stmt.setString(4, house.getCountry());
			stmt.setString(5, house.getPostalCode());
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				key = rs.getInt(1);
				System.out.println(key);
			} else {
				return -1;
			}

		} catch (SQLException e) {

			e.printStackTrace();
		}
		return key;
	}

	
	
	
	
	// DAO delete function that receives a house from the servlet,
	// deletes a record where the id is equal to the houses id
	public void delete(House house) {
		//check if house exists first
		if(checkIfHouseExists(house) != -1) {
			
			// Defining our connection with ACID Transaction
			Connection conn = null;
			try {
				conn = DriverManager.getConnection(url, username, password);
				conn.setAutoCommit(false);
			} catch (SQLException e2) {
				e2.printStackTrace();
			}
			try {
			String sql = "Delete from houses where houseid = (?)";
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setInt(1, house.getHouseid());
			stmt.execute();

			conn.commit();
		} // Rollback if any error and close connection
			catch (SQLException e) {
				e.printStackTrace();
				try {
					conn.rollback();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			} finally {
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}

	}
	}
}
////////////////////////////////RAFI HIZGIAEV ///////////////////////////////