package dao;

import models.FoodType;

import java.util.List;

public interface FoodTypeDao {

    void add(FoodType foodType);

    //void addFoodtypeToRestaurant(Foodtype foodtype, Restaurant restaurant);

    List<FoodType> getAll();

    void deleteById(int id);

    void clearAll();
}
