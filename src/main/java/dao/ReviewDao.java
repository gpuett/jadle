package dao;

import models.Review;

import java.util.List;

public interface ReviewDao {

    void add(Review review);

    List<Review> getAll();

    List<Review> getAllReviewsByRestaurantId(int restaurantId);

    void deleteById(int id);

    void clearAll();
}
