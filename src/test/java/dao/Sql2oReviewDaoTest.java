package dao;

import org.junit.*;
import org.sql2o.*;
import models.*;


import static org.junit.Assert.*;

public class Sql2oReviewDaoTest {
    private Connection conn;
    private Sql2oReviewDao reviewDao;
    private Sql2oRestaurantDao restaurantDao;

    @Before
    public void setUp() throws Exception {
        String connectionString = "jdbc:h2:mem:testing;INIT=RUNSCRIPT from 'classpath:db/create.sql'";
        Sql2o sql2o = new Sql2o(connectionString, "", "");
        reviewDao = new Sql2oReviewDao(sql2o);
        restaurantDao = new Sql2oRestaurantDao(sql2o);
        conn = sql2o.open();
    }

    @After
    public void tearDown() throws Exception {
        conn.close();
    }

    @Test
    public void addingReviewSetsId() {
        Review review = setupReview();
        assertEquals(1, review.getId());
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