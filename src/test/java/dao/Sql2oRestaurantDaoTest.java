package dao;

import models.*;
import org.junit.*;
import org.sql2o.*;

import java.util.Arrays;

import static org.junit.Assert.*;

public class Sql2oRestaurantDaoTest {
    private static Connection conn;
    private static Sql2oRestaurantDao restaurantDao;
    private static Sql2oFoodTypeDao foodTypeDao;
    private static Sql2oReviewDao reviewDao;

    @BeforeClass
    public static void setUp() throws Exception {
        String connectionString = "jdbc:postgresql://localhost:5432/jadle_test";
        Sql2o sql2o = new Sql2o(connectionString, null, null);
//        String connectionString = "jdbc:postgresql://ec2-50-16-241-91.compute-1.amazonaws.com:5432/d4ktfn9loh8rmh";
//        Sql2o sql2o = new Sql2o(connectionString, "fawtgwyejuwger", "d5f9f9f6f95b06b5e0e19490a459f35a93358fe932d442899ff134e6aa3349d1" );
        restaurantDao = new Sql2oRestaurantDao(sql2o);
        foodTypeDao = new Sql2oFoodTypeDao(sql2o);
        reviewDao = new Sql2oReviewDao(sql2o);
        conn = sql2o.open();
    }

    @After
    public void tearDown() throws Exception {
        System.out.println("clearing database");
        restaurantDao.clearAll();
        foodTypeDao.clearAll();
        reviewDao.clearAll();
    }

    @AfterClass
    public static void shutDown() throws Exception {
        conn.close();
        System.out.println("connection closed");
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

    @Test
    public void restaurantReturnsFoodTypesCorrectly() {
        FoodType testFoodType = new FoodType("Seafood");
        foodTypeDao.add(testFoodType);
        FoodType otherFoodType = new FoodType("Bar Food");
        foodTypeDao.add(otherFoodType);
        Restaurant testRestaurant = setupRestaurant();
        restaurantDao.addRestaurantToFoodtype(testRestaurant, testFoodType);
        restaurantDao.addRestaurantToFoodtype(testRestaurant, otherFoodType);

        FoodType[] foodTypes = {testFoodType, otherFoodType};

        assertEquals(Arrays.asList(foodTypes), restaurantDao.getAllFoodtypesByRestaurant(testRestaurant.getId()));
    }

    @Test
    public void deletingRestaurantAlsoUpdatesJoinTable() {
        FoodType testFoodtype = new FoodType("Seafood");
        foodTypeDao.add(testFoodtype);

        Restaurant testRestaurant = setupRestaurant();
        Restaurant altRestaurant = setupAltRestaurant();
        restaurantDao.addRestaurantToFoodtype(testRestaurant, testFoodtype);
        restaurantDao.addRestaurantToFoodtype(altRestaurant, testFoodtype);

        restaurantDao.deleteById(testRestaurant.getId());
        assertEquals(0, restaurantDao.getAllFoodtypesByRestaurant(testRestaurant.getId()).size());
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