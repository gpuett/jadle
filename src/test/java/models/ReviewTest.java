package models;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class ReviewTest {

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void getContent() {
        Review review = setupReview();
        assertEquals("Great service", review.getContent());
    }

    @Test
    public void setContent() {
        Review review = setupReview();
        review.setContent("Just good service");
        assertNotEquals("Great service", review.getContent());
    }

    @Test
    public void getWrittenBy() {
        Review review = setupReview();
        assertEquals("Kim", review.getWrittenBy());
    }

    @Test
    public void setWrittenBy() {
        Review review = setupReview();
        review.setWrittenBy("Mike");
        assertNotEquals("Kim", review.getWrittenBy());
    }

    @Test
    public void getRating() {
        Review review = setupReview();
        assertEquals(4, review.getRating());
    }

    @Test
    public void setRating() {
        Review review = setupReview();
        review.setRating(1);
        assertNotEquals(4, review.getRating());
    }

    @Test
    public void getRestaurantId() {
        Review review = setupReview();
        assertEquals(1, review.getRestaurantId());
    }

    @Test
    public void setRestaurantId() {
        Review review = setupReview();
        review.setRestaurantId(3);
        assertNotEquals(1, review.getRestaurantId());
    }

    @Test
    public void setId() {
        Review testReview = setupReview();
        testReview.setId(5);
        assertEquals(5, testReview.getId());
    }

    // helper
    public Review setupReview (){
        return new Review("Great service", "Kim", 4, 1);
    }
}