package com.dglapps.simuloc.geocoding;

import com.dglapps.simuloc.entities.Address;
import com.dglapps.simuloc.entities.AddressLocation;
import com.dglapps.simuloc.entities.Position;

import java.util.List;

public interface Geocoding {

    AddressLocation addressToPosition(Address address);

    AddressLocation positionToAddress(Position position);

    List<AddressLocation> positionToAddresses(Position position);

    List<AddressLocation> positionToAddresses(Position position, int maxResults);
}
