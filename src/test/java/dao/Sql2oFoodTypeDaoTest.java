package dao;

import org.junit.*;
import models.*;
import org.sql2o.*;

import static org.junit.Assert.*;

public class Sql2oFoodTypeDaoTest {
    private static Sql2oFoodTypeDao foodTypeDao;
    private static Sql2oRestaurantDao restaurantDao;
    private static Connection conn;

    @BeforeClass
    public static void setUp() throws Exception {
        String connectionString = "jdbc:postgresql://localhost:5432/jadle_test";
        Sql2o sql2o = new Sql2o(connectionString, null, null);
        restaurantDao = new Sql2oRestaurantDao(sql2o);
        foodTypeDao = new Sql2oFoodTypeDao(sql2o);
        conn = sql2o.open();
    }

    @After
    public void tearDown() throws Exception {
        System.out.println("clearing database");
        restaurantDao.clearAll();
        foodTypeDao.clearAll();
    }

    @AfterClass
    public static void shutDown() throws Exception {
        conn.close();
        System.out.println("connection closed");
    }

    @Test
    public void add() {
        FoodType foodType = setupNewFoodtype();
        int originalFoodTypeId = foodType.getId();
        foodTypeDao.add(foodType);
        assertNotEquals(originalFoodTypeId, foodType.getId());
    }

    @Test
    public void getAll() {
        FoodType foodType = setupNewFoodtype();
        foodTypeDao.add(foodType);
        assertEquals(1, foodTypeDao.getAll().size());
    }

    @Test
    public void getAllWithNoneAdded() {
        assertEquals(0, foodTypeDao.getAll().size());
    }

    @Test
    public void deleteById() {
        FoodType foodType = setupNewFoodtype();
        foodTypeDao.add(foodType);
        foodTypeDao.deleteById(foodType.getId());
        assertEquals(0, foodTypeDao.getAll().size());
    }

    @Test
    public void clearAll() {
        FoodType foodType = setupNewFoodtype();
        foodTypeDao.add(foodType);
        foodTypeDao.clearAll();
        assertEquals(0, foodTypeDao.getAll().size());
    }

    @Test
    public void addFoodTypeToRestaurantAddTypeCorrectly() throws Exception {
        Restaurant testRestaurant = setupRestaurant();
        Restaurant altRestaurant = setupAltRestaurant();
        FoodType testFoodType = setupNewFoodtype();
        foodTypeDao.add(testFoodType);
        foodTypeDao.addFoodtypeToRestaurant(testFoodType, testRestaurant);
        foodTypeDao.addFoodtypeToRestaurant(testFoodType, altRestaurant);
        assertEquals(2, foodTypeDao.getAllRestaurantsForAFoodtype(testFoodType.getId()).size());
    }

    @Test
    public void findFoodTypeById() {
        FoodType foodType = setupNewFoodtype();
        foodTypeDao.add(foodType);
        assertEquals(foodType, foodTypeDao.findById(foodType.getId()));
    }

    @Test
    public void deletingFoodTypeAlsoUpdatesJoinTable() {
        FoodType testFoodType = new FoodType("Seafood");
        foodTypeDao.add(testFoodType);
        FoodType otherFoodType = new FoodType("Bar Food");
        foodTypeDao.add(otherFoodType);
        Restaurant testRestaurant = setupRestaurant();
        restaurantDao.addRestaurantToFoodtype(testRestaurant, testFoodType);
        restaurantDao.addRestaurantToFoodtype(testRestaurant, otherFoodType);

        foodTypeDao.deleteById(testFoodType.getId());
        assertEquals(1, restaurantDao.getAllFoodtypesByRestaurant(testRestaurant.getId()).size());
    }

    // helpers

    public FoodType setupNewFoodtype(){
        return new FoodType("Sushi");
    }

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