package dao;

import org.junit.*;
import models.*;
import org.sql2o.*;

import static org.junit.Assert.*;

public class Sql2oFoodTypeDaoTest {
    private Sql2oFoodTypeDao foodTypeDao;
    private Sql2oRestaurantDao restaurantDao;
    private Connection conn;

    @Before
    public void setUp() throws Exception {
        String connectionString = "jdbc:h2:mem:testing;INIT=RUNSCRIPT from 'classpath:db/create.sql'";
        Sql2o sql2o = new Sql2o(connectionString, "", "");
        restaurantDao = new Sql2oRestaurantDao(sql2o);
        foodTypeDao = new Sql2oFoodTypeDao(sql2o);
        conn = sql2o.open();
    }

    @After
    public void tearDown() throws Exception {
        conn.close();
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

    // helpers

    public FoodType setupNewFoodtype(){
        return new FoodType("Sushi");
    }
}