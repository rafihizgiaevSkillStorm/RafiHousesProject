package com.skillstorm.exceptions;


//Exception is thrown when we try to set an invalid city
public class invalidHouseCityException extends IllegalArgumentException{

	String message;

	public invalidHouseCityException() {
		super();
	}

	public invalidHouseCityException(String message) {
		super();
		this.message = message;
		
	}

	@Override
	public String toString() {
		return "invalidHouseCityException [message=" + message + "]";
	}
	
}

////////////////////////////////RAFI HIZGIAEV ///////////////////////////////