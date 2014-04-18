package com.dglapps.simuloc.geocoding;

import java.util.Locale;

import com.dglapps.simuloc.entities.Address;
import com.dglapps.simuloc.entities.AddressLocation;
import com.dglapps.simuloc.entities.Position;

public class TestGoogleGeocoding {

	public static void main(String[] args) {
		TestGoogleGeocoding test = new TestGoogleGeocoding();
		test.testAddressToPosition();
		test.testAddressToPositionNull();
	}
	
	public void testAddressToPosition() {
		GoogleGeocoding geocoding = new GoogleGeocoding(Locale.getDefault());
		Address address = new Address();
		address.setAddress("CC Splau, Cornella de Llobregat");
		AddressLocation addressLocation = geocoding.addressToPosition(address);
		if (addressLocation != null) {
			Position position = addressLocation.getPosition();
			System.out.println(position.getLatitude() + "," + position.getLongitude());
		} else {
			System.out.println("Result null");
		}
	}
	
	public void testAddressToPositionNull() {
		GoogleGeocoding geocoding = new GoogleGeocoding(Locale.getDefault());
		AddressLocation addressLocation = geocoding.addressToPosition(null);
		
		
	}
	
}
