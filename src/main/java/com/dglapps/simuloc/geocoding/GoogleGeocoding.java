package com.dglapps.simuloc.geocoding;

import com.dglapps.simuloc.entities.Address;
import com.dglapps.simuloc.entities.AddressLocation;
import com.dglapps.simuloc.entities.Position;
import com.dglapps.simuloc.entities.PositionFactory;
import com.google.code.geocoder.Geocoder;
import com.google.code.geocoder.GeocoderRequestBuilder;
import com.google.code.geocoder.model.*;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class GoogleGeocoding implements Geocoding {

    private final Geocoder geocoder;
    private final Locale locale;

    public GoogleGeocoding(Locale locale, Geocoder geocoder) {
        this.locale = locale;
        this.geocoder = geocoder;
    }

    public GoogleGeocoding(Locale locale) {
        this(locale, new Geocoder());
    }

    @Override
    public AddressLocation addressToPosition(Address address) {
        if (address == null) {
            return null;
        }

        GeocodeResponse geocoderResponse = getGeocodeFromAddress(address);
        if (geocoderResponse == null) {
            return null;
        }

        for (GeocoderResult result : geocoderResponse.getResults()) {
            GeocoderGeometry geometry = result.getGeometry();
            if (geometry != null) {
                LatLng latLng = geometry.getLocation();
                Position position = PositionFactory.createPosition(latLng.getLat().doubleValue(), latLng.getLng().doubleValue());

                AddressLocation addressLocation = new AddressLocation();
                addressLocation.setAddress(address);
                addressLocation.setPosition(position);
                return addressLocation;
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
        List<AddressLocation> list = new ArrayList<>();

        if (position == null) {
            return list;
        }

        GeocodeResponse response = getGeocodeFromPosition(position);
        if (response == null) {
            return list;
        }

        for (GeocoderResult result : response.getResults()) {
            String formattedAddress = result.getFormattedAddress();
            Address address = new Address(formattedAddress);

            AddressLocation addressLocation = new AddressLocation();
            addressLocation.setAddress(address);
            addressLocation.setPosition(position);

            list.add(addressLocation);
        }
        return list;
    }

    @Override
    public List<AddressLocation> positionToAddresses(Position position, int maxResults) {
        List<AddressLocation> list = new ArrayList<>();

        if (position == null) {
            return list;
        }

        GeocodeResponse response = getGeocodeFromPosition(position);
        if (response == null) {
            return list;
        }

        for (GeocoderResult result : response.getResults()) {
            if (list.size() == maxResults)
                return list;

            String formattedAddress = result.getFormattedAddress();
            Address address = new Address(formattedAddress);

            AddressLocation addressLocation = new AddressLocation();
            addressLocation.setAddress(address);
            addressLocation.setPosition(position);

            list.add(addressLocation);
        }
        return list;
    }

    private GeocodeResponse getGeocodeFromPosition(Position position) {
        LatLng latLng = new LatLng(BigDecimal.valueOf(position.getLatitude()),
                BigDecimal.valueOf(position.getLongitude()));
        GeocoderRequest request = new GeocoderRequestBuilder().setLocation(latLng).getGeocoderRequest();
        try {
            return geocoder.geocode(request);
        } catch (IOException e) {
            return null;
        }
    }

    private GeocodeResponse getGeocodeFromAddress(Address address) {
        GeocoderRequest geocoderRequest = new GeocoderRequestBuilder()
                .setAddress(address.getAddress())
                .setLanguage(locale.getCountry())
                .getGeocoderRequest();

        try {
            return geocoder.geocode(geocoderRequest);
        } catch (IOException e) {
            return null;
        }
    }
}
