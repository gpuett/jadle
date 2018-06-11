package dao;

import models.Restaurant;

import java.util.List;

public interface RestaurantDao {

    void add (Restaurant restaurant);

    List<Restaurant> getAll();

    Restaurant findById(int id);

    void update(int id, String name, String address, String zipcode, String phone, String website, String email);

    void deleteById(int id);

    void clearAll();
}
