package com.skillstorm.exceptions;


//Exception is thrown when we try to set an invalid country
public class invalidHouseCountryException extends IllegalArgumentException{

	String message;

	public invalidHouseCountryException() {
		super();
	}

	public invalidHouseCountryException(String message) {
		super();
		this.message = message;
	}

	@Override
	public String toString() {
		return "invalidHouseCountryException [message=" + message + "]";
	}
	
	
}
////////////////////////////////RAFI HIZGIAEV ///////////////////////////////