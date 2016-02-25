package com.dglapps.simuloc.geocoding;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import com.dglapps.simuloc.entities.Address;
import com.dglapps.simuloc.entities.AddressLocation;
import com.dglapps.simuloc.entities.Position;
import com.google.code.geocoder.Geocoder;
import com.google.code.geocoder.GeocoderRequestBuilder;
import com.google.code.geocoder.model.GeocodeResponse;
import com.google.code.geocoder.model.GeocoderGeometry;
import com.google.code.geocoder.model.GeocoderRequest;
import com.google.code.geocoder.model.GeocoderResult;
import com.google.code.geocoder.model.LatLng;

public class GoogleGeocoding implements Geocoding {

	Geocoder geocoder;
	Locale locale;
	
	public GoogleGeocoding(Locale locale) {
		this.geocoder = new Geocoder();
		this.locale = locale;
	}
	
	@Override
	public AddressLocation addressToPosition(Address address) {
		if (address != null) {
			GeocoderRequest geocoderRequest = new GeocoderRequestBuilder().setAddress(address.getAddress()).setLanguage(locale.getCountry()).getGeocoderRequest();
			GeocodeResponse geocoderResponse = geocoder.geocode(geocoderRequest);
			if (geocoderResponse != null) {
				for (GeocoderResult result : geocoderResponse.getResults()) {
					GeocoderGeometry geometry = result.getGeometry();
					if (geometry != null) {
						LatLng latLng = geometry.getLocation();
						Position position = new Position();
						position.setLatitude(latLng.getLat().doubleValue());
						position.setLongitude(latLng.getLng().doubleValue());
						
						AddressLocation addressLocation = new AddressLocation();
						addressLocation.setAddress(address);
						addressLocation.setPosition(position);
						return addressLocation;
					}
				}
			}
		}
		return null;
	}

	@Override
	public AddressLocation positionToAddress(Position position) {
		List<AddressLocation> list = positionToAddresses(position, 1);
		return list != null && !list.isEmpty() ? list.get(0) : null;
	}

	@Override
	public List<AddressLocation> positionToAddresses(Position position) {
		List<AddressLocation> list = new ArrayList<AddressLocation>();
		
		if (position != null) {
			LatLng latLng = new LatLng(BigDecimal.valueOf(position.getLatitude()), BigDecimal.valueOf(position.getLongitude()));
			GeocoderRequest request = new GeocoderRequestBuilder().setLocation(latLng).getGeocoderRequest();
			GeocodeResponse response = geocoder.geocode(request);
			if (response != null) {
				for (GeocoderResult result : response.getResults()) {
					String formattedAddress = result.getFormattedAddress();
					Address address = new Address();
					address.setAddress(formattedAddress);
					
					AddressLocation addressLocation = new AddressLocation();
					addressLocation.setAddress(address);
					addressLocation.setPosition(position);
					
					list.add(addressLocation);
				}
			}
		}
		return list;
	}
	
	@Override
	public List<AddressLocation> positionToAddresses(Position position,
			int maxResults) {
		List<AddressLocation> list = new ArrayList<AddressLocation>();

		if (position != null) {
			LatLng latLng = new LatLng(BigDecimal.valueOf(position.getLatitude()), BigDecimal.valueOf(position.getLongitude()));
			GeocoderRequest request = new GeocoderRequestBuilder().setLocation(latLng).getGeocoderRequest();
			GeocodeResponse response = geocoder.geocode(request);
			if (response != null) {
				for (GeocoderResult result : response.getResults()) {
					if (list.size() == maxResults)
						return list;
					
					String formattedAddress = result.getFormattedAddress();
					Address address = new Address();
					address.setAddress(formattedAddress);
					
					AddressLocation addressLocation = new AddressLocation();
					addressLocation.setAddress(address);
					addressLocation.setPosition(position);
					
					list.add(addressLocation);
				}
			}
		}
		return list;
	}
}
