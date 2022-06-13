package com.dglapps.simuloc.geocoding;

import com.dglapps.simuloc.entities.Address;
import com.dglapps.simuloc.entities.AddressLocation;
import com.dglapps.simuloc.entities.Position;
import com.dglapps.simuloc.entities.PositionFactory;
import com.google.code.geocoder.Geocoder;
import com.google.code.geocoder.model.*;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

public class TestGoogleGeocoding {

    private GoogleGeocoding geocoding;
    private Geocoder geocoder;

    @Before
    public void setUp() throws IOException {
        geocoder = Mockito.mock(Geocoder.class);
        mockGeocodeValid();

        geocoding = new GoogleGeocoding(Locale.getDefault(), geocoder);
    }

    private void mockGeocodeNull() throws IOException {
        when(geocoder.geocode(any())).thenReturn(null);
    }

    private void mockGeocodeValid() throws IOException {
        GeocodeResponse validGeocodeResponse = new GeocodeResponse();
        validGeocodeResponse.setStatus(GeocoderStatus.OK);
        validGeocodeResponse.setResults(List.of(
                validResult()
        ));

        when(geocoder.geocode(any())).thenReturn(validGeocodeResponse);
    }

    private GeocoderResult validResult() {
        GeocoderResult result = new GeocoderResult();
        result.setGeometry(geometry());
        return result;
    }

    private GeocoderGeometry geometry() {
        GeocoderGeometry geometry = new GeocoderGeometry();
        geometry.setLocationType(GeocoderLocationType.GEOMETRIC_CENTER);
        geometry.setLocation(new LatLng("42.234", "10.3245"));
        return geometry;
    }

    @Test
    public void testAddressToPosition() throws IOException {
        Address address = new Address("Plaça Catalunya, Barcelona");
        AddressLocation addressLocation = geocoding.addressToPosition(address);

        assertNotNull(addressLocation);
    }

    @Test
    public void testAddressToPositionNull() {
        AddressLocation addressLocation = geocoding.addressToPosition(null);
        assertNull(addressLocation);
    }

    @Test
    public void testPositionToAddress() {
        Position position = PositionFactory.createPosition(41.386919, 2.170047); // Plaça Catalunya
        AddressLocation addressLocation = geocoding.positionToAddress(position);

        assertNotNull(addressLocation);
    }

    @Test
    public void testDummyPositionToNullAddress() throws IOException {
        mockGeocodeNull();
        Position position = PositionFactory.createPosition(0, 0);
        AddressLocation addressLocation = geocoding.positionToAddress(position);

        assertNull(addressLocation);
    }

    @Test
    public void testNullPositionToNullAddress() {
        AddressLocation addressLocation = geocoding.positionToAddress(null);
        assertNull(addressLocation);
    }

    @Test
    public void testPositionToAddresses() {
        Position position = PositionFactory.createPosition(41.386919, 2.170047); // Plaça Catalunya
        List<AddressLocation> results = geocoding.positionToAddresses(position);
        assertFalse(results.isEmpty());
    }

    @Test
    public void testDummyPositionToAddresses() throws IOException {
        mockGeocodeNull();
        Position position = PositionFactory.createPosition(0, 0);
        List<AddressLocation> results = geocoding.positionToAddresses(position);
        assertTrue(results.isEmpty());
    }

    @Test
    public void testNullPositionToAddresses() {
        List<AddressLocation> results = geocoding.positionToAddresses(null);
        assertTrue(results.isEmpty());
    }

}
