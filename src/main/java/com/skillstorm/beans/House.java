package com.skillstorm.beans;

import com.skillstorm.exceptions.invalidHouseAddressException;
import com.skillstorm.exceptions.invalidHouseCityException;
import com.skillstorm.exceptions.invalidHouseCountryException;
import com.skillstorm.exceptions.invalidHousePostalCodeException;
import com.skillstorm.exceptions.invalidHouseStateException;

public class House {	// Here we define our House object class for the whole project
	
	// House attributes:
	
	private int houseid;
	private String streetAddress;
	private String city;
	private String state;
	private String country;
	private String postalCode;
	private int sqft;
	private Long estPrice;
	private int numberBedRooms;
	private int numberBathRooms;
	private boolean forSale;
	private String typeOfProperty;
	
	
	// House no parameter Constructor:
	public House() {
		super();
	}

	// House *No Id Constructor with all the attributes that are set to NOT NULL in our DB: ///Not in Use
	public House(String streetAddress, String city, String state, String country, String postalCode) {
		super();
		this.notNullSetters(streetAddress, city, state, country, postalCode);
	}
	// House *No Id Constructor with all the attributes that are set to NOT NULL in our DB and the 'sqft' attribute: ///Not in Use
	public House(String streetAddress, String city, String state, String country, String postalCode, int sqft) {
		super();
		this.notNullSetters(streetAddress, city, state, country, postalCode);
		this.sqft = sqft;
	}
	// House *No Id Constructor with all the attributes that are set to NOT NULL in our DB, the 'sqft' and the estPrice: ///Not in Use
	public House(String streetAddress, String city, String state, String country, String postalCode, int sqft, Long EstPrice) {
		super();
		this.notNullSetters(streetAddress, city, state, country, postalCode);
		this.sqft = sqft;
		this.estPrice = EstPrice;
	}
	// House *No Id Constructor with all the attributes that are set to NOT NULL in our DB, the 'sqft', the estPrice and the numberBedRooms: ///Not in Use
	public House(String streetAddress, String city, String state, String country, String postalCode, int sqft, Long EstPrice, int numberBedRooms) {
		super();
		this.notNullSetters(streetAddress, city, state, country, postalCode);
		this.sqft = sqft;
		this.estPrice = EstPrice;
		this.numberBedRooms = numberBedRooms;
	}
	// House *No Id Constructor with all the attributes that are set to NOT NULL in our DB, the 'sqft', the estPrice, the numberBedRooms and the numberBathRooms: ///Not in Use
	public House(String streetAddress, String city, String state, String country, String postalCode, int sqft, Long EstPrice, int numberBedRooms, int numberBathRooms) {
		super();
		this.notNullSetters(streetAddress, city, state, country, postalCode);
		this.sqft = sqft;
		this.estPrice = EstPrice;
		this.numberBedRooms = numberBedRooms;
		this.numberBathRooms = numberBathRooms;
	}
	// House *No Id Constructor with all the attributes that are set to NOT NULL in our DB, the 'sqft', the estPrice, the numberBedRooms, the numberBathRooms and if forSale: ///Not in Use
	public House(String streetAddress, String city, String state, String country, String postalCode, int sqft, Long EstPrice, int numberBedRooms, int numberBathRooms, boolean forSale) {
		super();
		this.notNullSetters(streetAddress, city, state, country, postalCode);
		this.sqft = sqft;
		this.estPrice = EstPrice;
		this.numberBedRooms = numberBedRooms;
		this.numberBathRooms = numberBathRooms;
		this.forSale = forSale;
	}
	///In Use
	// House *No Id Constructor with all the attributes that are set to NOT NULL in our DB, the 'sqft', the estPrice, the numberBedRooms, the numberBathRooms, if forSale and the typeOfProperty: 
	public House(String streetAddress, String city, String state, String country, String postalCode, int sqft, Long EstPrice, int numberBedRooms, int numberBathRooms, boolean forSale, String typeOfProperty) {
		super();
		this.notNullSetters(streetAddress, city, state, country, postalCode);
		this.sqft = sqft;
		this.estPrice = EstPrice;
		this.numberBedRooms = numberBedRooms;
		this.numberBathRooms = numberBathRooms;
		this.forSale = forSale;
		this.typeOfProperty = typeOfProperty;
	}
	// House Constructor with all the attributes that are set to NOT NULL in our DB, the 'sqft', the estPrice, the numberBedRooms, the numberBathRooms, if forSale and the typeOfProperty:
	public House(int houseid, String streetAddress, String city, String state, String country, String postalCode,
			int sqft, Long estPrice, int numberBedRooms, int numberBathRooms, boolean forSale, String typeOfProperty) {
		super();
		this.houseid = houseid;
		this.notNullSetters(streetAddress, city, state, country, postalCode);
		this.sqft = sqft;
		this.estPrice = estPrice;
		this.numberBedRooms = numberBedRooms;
		this.numberBathRooms = numberBathRooms;
		this.forSale = forSale;
		this.typeOfProperty = typeOfProperty;
	}

	
	//House ID getters and setters:
	public int getHouseid() {
		return houseid;
	}

	public void setHouseid(int houseid) {
		this.houseid = houseid;
	}

	
	

	//House street address getters and setters:
	public String getStreetAddress() {
		return streetAddress;
	}
	//Setter with address exception
	public void setStreetAddress(String streetAddress) throws invalidHouseAddressException {
		if(streetAddress == "") {
			throw new invalidHouseAddressException("Street Address not Valid");
		}
		this.streetAddress = streetAddress;
	}

	
	

	//House city getters and setters:
	public String getCity() {
		return city;
	}
	//Setter with city exception
	public void setCity(String city) throws invalidHouseCityException {
		if(city == "") {
			throw new invalidHouseCityException("Invalid City");
		}
		this.city = city;
	}


	
	
	//House state getters and setters:
	public String getState() {
		return state;
	}
	//Setter with state exception
	public void setState(String state) throws invalidHouseStateException {
		if(state == "") {
			throw new invalidHouseStateException("Invalid State");
		}
		this.state = state;
	}


	
	
	
	//House country getters and setters
	public String getCountry() {
		return country;
	}
	//Setter with country exception
	public void setCountry(String country) throws invalidHouseCountryException{
		if(country == "") {
			throw new invalidHouseCountryException("Invalid Country");
		}
		this.country = country;
	}

	
	

	//House postalCode getters and setters
	public String getPostalCode() {
		return postalCode;
	}
	//Setter with postalCode exception
	public void setPostalCode(String postalCode) throws invalidHousePostalCodeException{
		if(postalCode == "") {
			throw new invalidHousePostalCodeException("Invalid Postal Code");
		}
		this.postalCode = postalCode;
	}


	
	
	//House sqft getters and setters
	public int getSqft() {
		return sqft;
	}
	public void setSqft(int sqft) {
		this.sqft = sqft;
	}


	
	
	
	
	//House estPrice getters and setters
	public Long getEstPrice() {
		return estPrice;
	}
	public void setEstPrice(Long estPrice) {
		this.estPrice = estPrice;
	}



	
	
	//House numberBedRooms getters and setters
	public int getNumberBedRooms() {
		return numberBedRooms;
	}
	public void setNumberBedRooms(int numberBedRooms) {
		this.numberBedRooms = numberBedRooms;
	}



	
	//House numberBathRooms getters and setters
	public int getNumberBathRooms() {
		return numberBathRooms;
	}
	public void setNumberBathRooms(int numberBathRooms) {
		this.numberBathRooms = numberBathRooms;
	}
	
	
	
	
	//House forSale getters and setters
	public boolean isForSale() {
		return forSale;
	}
	public void setForSale(boolean forSale) {
		this.forSale = forSale;
	}


	//House typeOfProperty getters and setters
	public String getTypeOfProperty() {
		return typeOfProperty;
	}
	public void setTypeOfProperty(String typeOfProperty) {
		this.typeOfProperty = typeOfProperty;
	}



	// House hashCode method
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((state == null) ? 0 : state.hashCode());
		result = prime * result + ((city == null) ? 0 : city.hashCode());
		result = prime * result + ((country == null) ? 0 : country.hashCode());
		result = prime * result + ((estPrice == null) ? 0 : estPrice.hashCode());
		result = prime * result + (forSale ? 1231 : 1237);
		result = prime * result + houseid;
		result = prime * result + numberBathRooms;
		result = prime * result + numberBedRooms;
		result = prime * result + ((postalCode == null) ? 0 : postalCode.hashCode());
		result = prime * result + sqft;
		result = prime * result + ((streetAddress == null) ? 0 : streetAddress.hashCode());
		result = prime * result + ((typeOfProperty == null) ? 0 : typeOfProperty.hashCode());
		return result;
	}


	// House equals method
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		House other = (House) obj;
		if (state == null) {
			if (other.state != null)
				return false;
		} else if (!state.equals(other.state))
			return false;
		if (city == null) {
			if (other.city != null)
				return false;
		} else if (!city.equals(other.city))
			return false;
		if (country == null) {
			if (other.country != null)
				return false;
		} else if (!country.equals(other.country))
			return false;
		if (estPrice == null) {
			if (other.estPrice != null)
				return false;
		} else if (!estPrice.equals(other.estPrice))
			return false;
		if (forSale != other.forSale)
			return false;
		if (houseid != other.houseid)
			return false;
		if (numberBathRooms != other.numberBathRooms)
			return false;
		if (numberBedRooms != other.numberBedRooms)
			return false;
		if (postalCode == null) {
			if (other.postalCode != null)
				return false;
		} else if (!postalCode.equals(other.postalCode))
			return false;
		if (sqft != other.sqft)
			return false;
		if (streetAddress == null) {
			if (other.streetAddress != null)
				return false;
		} else if (!streetAddress.equals(other.streetAddress))
			return false;
		if (typeOfProperty == null) {
			if (other.typeOfProperty != null)
				return false;
		} else if (!typeOfProperty.equals(other.typeOfProperty))
			return false;
		return true;
	}


	// House toString method
	@Override
	public String toString() {
		return "House [houseid = " + houseid + ", streetAddress = " + streetAddress + ", city = " + city + ", state = " + state
				+ ", country = " + country + ", postalCode = " + postalCode + ", sqft = " + sqft + ", estPrice = " + estPrice
				+ ", numberBedRooms = " + numberBedRooms + ", numberBathRooms = " + numberBathRooms + ", forSale = " + forSale
				+ ", typeOfPropeerty = " + typeOfProperty + "]\n";
	}
	
	
	// House one setter for all NOT NULL attributes from DB with all exceptions checked// Implemented in all of the constructors
	public void notNullSetters(String streetAddress, String city, String state, String country, String postalCode) {
	
	try {	
		this.setStreetAddress(streetAddress);
		this.setCity(city);	
		this.setState(state);
		this.setCountry(country); 
		this.setPostalCode(postalCode);
} catch(invalidHouseAddressException | invalidHouseCityException | invalidHouseStateException | invalidHouseCountryException | invalidHousePostalCodeException e) {
	e.printStackTrace();
	e.toString();
}
	}
}

//////////////////////////////// RAFI HIZGIAEV ///////////////////////////////