package com.dglapps.simuloc.geocoding;

import com.dglapps.simuloc.entities.Address;
import com.dglapps.simuloc.entities.AddressLocation;
import com.dglapps.simuloc.entities.Position;
import com.dglapps.simuloc.entities.PositionFactory;
import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.util.Locale;

public class TestGoogleGeocoding {

    private GoogleGeocoding geocoding;

    @Before
    public void setUp() {
        geocoding = new GoogleGeocoding(Locale.getDefault());
    }

    @Test
    public void testAddressToPosition() {
        Address address = new Address("Plaça Catalunya, Barcelona");
        AddressLocation addressLocation = geocoding.addressToPosition(address);

        Assert.assertNotNull(addressLocation);
    }

    @Test
    public void testAddressToPositionNull() {
        AddressLocation addressLocation = geocoding.addressToPosition(null);
        Assert.assertNull(addressLocation);
    }

    @Test
    public void testPositionToAddress() {
        Position position = PositionFactory.createPosition(41.386919, 2.170047); // Plaça Catalunya
        AddressLocation addressLocation = geocoding.positionToAddress(position);

        Assert.assertNotNull(addressLocation);
    }

    @Test
    public void testDummyPositionToNullAddress() {
        Position position = PositionFactory.createPosition(0, 0);
        AddressLocation addressLocation = geocoding.positionToAddress(position);

        Assert.assertNull(addressLocation);
    }

    @Test
    public void testNullPositionToNullAddress() {
        AddressLocation addressLocation = geocoding.positionToAddress(null);
        Assert.assertNull(addressLocation);
    }

    @Test
    public void testPositionToAddresses() {
        Position position = PositionFactory.createPosition(41.386919, 2.170047); // Plaça Catalunya
        List<AddressLocation> results = geocoding.positionToAddresses(position);
        Assert.assertTrue(!results.isEmpty());
    }

    @Test
    public void testDummyPositionToAddresses() {
        Position position = PositionFactory.createPosition(0, 0);
        List<AddressLocation> results = geocoding.positionToAddresses(position);
        Assert.assertTrue(results.isEmpty());
    }

    @Test
    public void testNullPositionToAddresses() {
        List<AddressLocation> results = geocoding.positionToAddresses(null);
        Assert.assertTrue(results.isEmpty());
    }

}
