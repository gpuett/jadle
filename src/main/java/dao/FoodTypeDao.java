package dao;

import models.FoodType;
import models.Restaurant;

import java.util.List;

public interface FoodTypeDao {

    void add(FoodType foodType);

    void addFoodtypeToRestaurant(FoodType foodType, Restaurant restaurant);

    List<FoodType> getAll();

    List<Restaurant> getAllRestaurantsForAFoodtype(int foodtypeId);

    void deleteById(int id);

    void clearAll();
}
