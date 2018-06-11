package dao;

import models.FoodType;
import models.Restaurant;

import java.util.List;

public interface RestaurantDao {

    void add (Restaurant restaurant);

    void addRestaurantToFoodtype(Restaurant restaurant, FoodType foodType);

    List<Restaurant> getAll();

    List<FoodType> getAllFoodtypesByRestaurant(int restaurantId);

    Restaurant findById(int id);

    void update(int id, String name, String address, String zipcode, String phone, String website, String email);

    void deleteById(int id);

    void clearAll();
}
