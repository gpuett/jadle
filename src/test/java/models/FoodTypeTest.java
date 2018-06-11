package models;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class FoodTypeTest {

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void getName() {
        FoodType foodType = setupFoodType();
        assertEquals("dessert", foodType.getName());
    }

    @Test
    public void setName() {
        FoodType foodType = setupFoodType();
        foodType.setName("breakfast");
        assertNotEquals("dessert", foodType.getName());
    }

    @Test
    public void setId() {
        FoodType foodType = setupFoodType();
        foodType.setId(5);
        assertEquals(5, foodType.getId());
    }

    // helper
    public FoodType setupFoodType(){
        return new FoodType("dessert");
    }
}