package com.skillstorm.exceptions;


// Exception is thrown when we try to set an invalid street address
public class invalidHouseAddressException extends IllegalArgumentException{

	String message;
	
	
	public invalidHouseAddressException() {
		super();
	}
	public invalidHouseAddressException(String message) {
		super();
		this.message = message;
	}
	@Override
	public String toString() {
		return "invalidHouseAddressException [message=" + message + "]";
	}
}
////////////////////////////////RAFI HIZGIAEV ///////////////////////////////