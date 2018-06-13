package dao;

import org.junit.*;
import org.sql2o.*;
import models.*;


import static org.junit.Assert.*;

public class Sql2oReviewDaoTest {
    private static Connection conn;
    private static Sql2oReviewDao reviewDao;
    private static Sql2oRestaurantDao restaurantDao;

    @BeforeClass
    public static void setUp() throws Exception {
        String connectionString = "jdbc:postgresql://localhost:5432/jadle_test";
        Sql2o sql2o = new Sql2o(connectionString, null, null);
//        String connectionString = "jdbc:postgresql://ec2-50-16-241-91.compute-1.amazonaws.com:5432/d4ktfn9loh8rmh";
//        Sql2o sql2o = new Sql2o(connectionString, "fawtgwyejuwger", "d5f9f9f6f95b06b5e0e19490a459f35a93358fe932d442899ff134e6aa3349d1" );
        reviewDao = new Sql2oReviewDao(sql2o);
        restaurantDao = new Sql2oRestaurantDao(sql2o);
        conn = sql2o.open();
    }

    @After
    public void tearDown() throws Exception {
        System.out.println("clearing database");
        reviewDao.clearAll();
        restaurantDao.clearAll();
    }

    @AfterClass
    public static void shutDown() throws Exception {
        conn.close();
        System.out.println("connection closed");
    }

    @Test
    public void addingReviewSetsId() {
        Review review = new Review("great", "joe", 4, 1);
        reviewDao.add(review);
        int originalId = review.getId();
        int foundId = reviewDao.findById(review.getId()).getId();
        assertEquals(foundId, originalId);
    }

    @Test
    public void getAll() {
        Review review1 = setupReview();
        Review review2 = setupReview();
        assertEquals(2, reviewDao.getAll().size());
    }

    @Test
    public void getAllReviewsByRestaurantId() {
        Restaurant restaurant1 = setupRestaurant();
        Restaurant restaurant2 = setupRestaurant();
        restaurant2.setId(444);
        Review review1 = setupReviewForRestaurant(restaurant1);
        Review review2 = setupReviewForRestaurant(restaurant1);
        Review review3 = setupReviewForRestaurant(restaurant2);
        assertEquals(2, reviewDao.getAllReviewsByRestaurantId(restaurant1.getId()).size());

    }

    @Test
    public void deleteById() {
        Review review1 = setupReview();
        Review review2 = setupReview();
        assertEquals(2, reviewDao.getAll().size());
        reviewDao.deleteById(review1.getId());
        assertEquals(1, reviewDao.getAll().size());
    }

    @Test
    public void clearAll() {
        Review review1 = setupReview();
        Review review2 = setupReview();
        reviewDao.clearAll();
        assertEquals(0, reviewDao.getAll().size());
    }

    @Test
    public void timeStampIsReturnedCorrectly() throws Exception {
        Restaurant restaurant = setupRestaurant();
        Review review = new Review("foodcoma!", "Captain Kirk", 3,  restaurant.getId());
        reviewDao.add(review);
        long creationTime = review.getCreatedAt();
        long savedTime = reviewDao.getAll().get(0).getCreatedAt();
        String formattedCreationTime = review.getFormattedCreatedAt();
        String formattedDSavedime = reviewDao.getAll().get(0).getFormattedCreatedAt();
        assertEquals(formattedCreationTime, formattedDSavedime);
        assertEquals(creationTime, savedTime);
    }

    @Test
    public void reviewsAreReturnedInCorrectOrder() throws Exception {
        Restaurant restaurant = setupRestaurant();
        Review review1 = new Review("foodcoma!", "Captain Kirk", 3,  restaurant.getId());
        reviewDao.add(review1);
        try {
            Thread.sleep(2000);
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }

        Review review2 = new Review("passable", "Mr Spock", 1, restaurant.getId());
        reviewDao.add(review2);
        try {
            Thread.sleep(2000);
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
        Review review3 = new Review("bloody good grub", "Scotty", 4, restaurant.getId());
        reviewDao.add(review3);
        try {
            Thread.sleep(2000);
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
        Review review4 = new Review("I prefer home cooking", "Mr Sulu", 2, restaurant.getId());
        reviewDao.add(review4);
        assertEquals(4, reviewDao.getAllReviewsByRestaurantId(restaurant.getId()).size());
        assertEquals("I prefer home cooking", reviewDao.getAllReviewsByRestaurantSortedNewestToOldest(restaurant.getId()).get(0).getContent());
    }

    //helpers

    public Review setupReview() {
        Review review = new Review("great", "Kim", 4, 555);
        reviewDao.add(review);
        return review;
    }

    public Review setupReviewForRestaurant(Restaurant restaurant) {
        Review review = new Review("great", "Kim", 4, restaurant.getId());
        reviewDao.add(review);
        return review;
    }

    public Restaurant setupRestaurant() {
        Restaurant restaurant = new Restaurant("Fish Witch", "214 NE Broadway", "97232", "503-402-9874", "http://fishwitch.com", "hellofishy@fishwitch.com");
        restaurantDao.add(restaurant);
        return restaurant;
    }
}