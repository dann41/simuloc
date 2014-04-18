package com.dglapps.simuloc.geocoding;

import java.util.List;

import com.dglapps.simuloc.entities.Address;
import com.dglapps.simuloc.entities.AddressLocation;
import com.dglapps.simuloc.entities.Position;

public interface Geocoding {

	public AddressLocation addressToPosition(Address address);
	
	public AddressLocation positionToAddress(Position position);
	
	public List<AddressLocation> positionToAddresses(Position position);
	
	public List<AddressLocation> positionToAddresses(Position position, int maxResults);
}
