package dao;

import models.*;
import org.junit.*;
import org.sql2o.*;

import static org.junit.Assert.*;

public class Sql2oRestaurantDaoTest {
    private Connection conn;
    private Sql2oRestaurantDao restaurantDao;
    private Sql2oFoodTypeDao foodTypeDao;
    private Sql2oReviewDao reviewDao;

    @Before
    public void setUp() throws Exception {
        String connectionString = "jdbc:h2:mem:testing;INIT=RUNSCRIPT from 'classpath:db/create.sql'";
        Sql2o sql2o = new Sql2o(connectionString, "", "");
        restaurantDao = new Sql2oRestaurantDao(sql2o);
        foodTypeDao = new Sql2oFoodTypeDao(sql2o);
        reviewDao = new Sql2oReviewDao(sql2o);
        conn = sql2o.open();
    }

    @After
    public void tearDown() throws Exception {
        conn.close();
    }

    @Test
    public void add() throws Exception {
        Restaurant restaurant = setupRestaurant();
        assertEquals(1, restaurantDao.getAll().size());
    }

    @Test
    public void getAllForNoEntry() {
        assertEquals(0, restaurantDao.getAll().size());
    }

    @Test
    public void getAll() {
        Restaurant restaurant1 = setupRestaurant();
        Restaurant restaurant2 = setupRestaurant();
        assertEquals(2, restaurantDao.getAll().size());
    }

    @Test
    public void findById() {
        Restaurant restaurant1 = setupRestaurant();
        Restaurant restaurant2 = setupRestaurant();
        assertEquals(restaurant1, restaurantDao.findById(restaurant1.getId()));
    }

    @Test
    public void update() {
        Restaurant restaurant = setupRestaurant();
        restaurantDao.update(restaurant.getId(), "a", "b", "c", "d", "e", "f");
        Restaurant foundRestaurant = restaurantDao.findById(restaurant.getId());
        assertEquals("a", foundRestaurant.getName());
        assertEquals("b", foundRestaurant.getAddress());
        assertEquals("c", foundRestaurant.getZipcode());
        assertEquals("d", foundRestaurant.getPhone());
        assertEquals("e", foundRestaurant.getWebsite());
        assertEquals("f", foundRestaurant.getEmail());
    }

    @Test
    public void deleteById() {
        Restaurant restaurant1 = setupRestaurant();
        Restaurant restaurant2 = setupRestaurant();
        restaurantDao.deleteById(restaurant1.getId());
        assertEquals(1, restaurantDao.getAll().size());
    }

    @Test
    public void clearAll() {
        Restaurant restaurant1 = setupRestaurant();
        Restaurant restaurant2 = setupRestaurant();
        restaurantDao.clearAll();
        assertEquals(0, restaurantDao.getAll().size());
    }

    //helpers

    public Restaurant setupRestaurant (){
        Restaurant restaurant = new Restaurant("Fish Witch", "214 NE Broadway", "97232", "503-402-9874", "http://fishwitch.com", "hellofishy@fishwitch.com");
        restaurantDao.add(restaurant);
        return restaurant;
    }

    public Restaurant setupAltRestaurant (){
        Restaurant restaurant = new Restaurant("Fish Witch", "214 NE Broadway", "97232", "503-402-9874");
        restaurantDao.add(restaurant);
        return restaurant;
    }
}