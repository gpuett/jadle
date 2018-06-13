package dao;

import models.Review;

import java.util.List;

public interface ReviewDao {

    void add(Review review);

    List<Review> getAll();

    List<Review> getAllReviewsByRestaurantId(int restaurantId);

    Review findById(int id);

    void deleteById(int id);

    void clearAll();

    List<Review> getAllReviewsByRestaurantSortedNewestToOldest(int restaurantId);
}
