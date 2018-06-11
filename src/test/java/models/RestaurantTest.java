package models;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class RestaurantTest {

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void getNameCorrectlyGetsName() {
        Restaurant restaurant = setupRestaurant();
        assertEquals("Fish Witch", restaurant.getName());
    }

    @Test
    public void setNameChangesName() {
        Restaurant restaurant = setupRestaurant();
        restaurant.setName("List Fish");
        assertNotEquals("Fish Witch", restaurant.getName());
    }

    @Test
    public void getAddressGetsCorrectAddress() {
        Restaurant restaurant = setupRestaurant();
        assertEquals("214 NE Broadway", restaurant.getAddress());
    }

    @Test
    public void setAddressChangesAddress() {
        Restaurant restaurant = setupRestaurant();
        restaurant.setAddress("222 NE Broadway");
        assertNotEquals("214 NE Broadway", restaurant.getAddress());
    }

    @Test
    public void getZipcodeCorrectlyGetsZipcode() {
        Restaurant restaurant = setupRestaurant();
        assertEquals("97232", restaurant.getZipcode());
    }

    @Test
    public void setZipcodeChangesZipcode() {
        Restaurant restaurant = setupRestaurant();
        restaurant.setZipcode("97222");
        assertNotEquals("97232", restaurant.getZipcode());
    }

    @Test
    public void getPhoneCorrectlyGetsPhoneNumber() {
        Restaurant restaurant = setupRestaurant();
        assertEquals("503-402-9874", restaurant.getPhone());
    }

    @Test
    public void setPhoneChangesPhoneNumber() {
        Restaurant restaurant = setupRestaurant();
        restaurant.setPhone("503-555-5555");
        assertNotEquals("503-402-9874", restaurant.getPhone());
    }

    @Test
    public void getWebsiteCorrectlyGetsWebsite() {
        Restaurant restaurant = setupRestaurant();
        assertEquals("http://fishwitch.com", restaurant.getWebsite());
    }

    @Test
    public void setWebsiteChangesWesite() {
        Restaurant restaurant = setupRestaurant();
        restaurant.setWebsite("http://fishlist.com");
        assertNotEquals("http://fishwitch.com", restaurant.getWebsite());
    }

    @Test
    public void getEmailCorrectlyGetsEmail() {
        Restaurant restaurant = setupRestaurant();
        assertEquals("hellofishy@fishwitch.com", restaurant.getEmail());
    }

    @Test
    public void setEmailChangesEmail() {
        Restaurant restaurant = setupRestaurant();
        restaurant.setEmail("fishybit@fishwitch.com");
        assertNotEquals("hellofishy@fishwitch.com", restaurant.getEmail());
    }


    // helpers
    public Restaurant setupRestaurant (){
        return new Restaurant("Fish Witch", "214 NE Broadway", "97232", "503-402-9874", "http://fishwitch.com", "hellofishy@fishwitch.com");

    }

    public Restaurant setupAltRestaurant (){
        return new Restaurant("Fish Witch", "214 NE Broadway", "97232", "503-402-9874");

    }
}