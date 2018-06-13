import com.google.gson.Gson;
import dao.Sql2oFoodTypeDao;
import dao.Sql2oRestaurantDao;
import dao.Sql2oReviewDao;
import exceptions.ApiException;
import models.FoodType;
import models.Restaurant;
import models.Review;
import org.sql2o.Connection;
import org.sql2o.Sql2o;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static spark.Spark.*;

public class App {
    public static void main(String[] args) {
        Sql2oFoodTypeDao foodTypeDao;
        Sql2oRestaurantDao restaurantDao;
        Sql2oReviewDao reviewDao;
        Connection conn;
        Gson gson = new Gson();
        String connectionString = "jdbc:h2:~/jadle.db;INIT=RUNSCRIPT from 'classpath:db/create.sql'";
        Sql2o sql2o = new Sql2o(connectionString, "", "");
        restaurantDao = new Sql2oRestaurantDao(sql2o);
        foodTypeDao = new Sql2oFoodTypeDao(sql2o);
        reviewDao = new Sql2oReviewDao(sql2o);
        conn = sql2o.open();


        // CREATE
        post("/restaurants/new", "application/json", (request, response) -> {
            Restaurant restaurant = gson.fromJson(request.body(), Restaurant.class);
            restaurantDao.add(restaurant);
            response.status(201);
            return gson.toJson(restaurant);
        });

        post("/foodtypes/new", "application/json", (request, response) -> {
            FoodType foodType = gson.fromJson(request.body(), FoodType.class);
            foodTypeDao.add(foodType);
            response.status(201);
            return gson.toJson(foodType);
        });

        post("/restaurants/:restaurantId/reviews/new", "application/json", (request, response) -> {
            int restaurantId = Integer.parseInt(request.params("restaurantId"));
            Review review = gson.fromJson(request.body(), Review.class);
            review.setCreatedAt();
            review.setFormattedCreatedAt();
            review.setRestaurantId(restaurantId);
            reviewDao.add(review);
            response.status(201);
            return gson.toJson(review);
        });

        post("/restaurants/:restaurantId/foodtype/:foodtypeId", "application/json", (request, response) -> {
           int restaurantId = Integer.parseInt(request.params("restaurantId"));
           int foodtypeId = Integer.parseInt(request.params("foodtypeId"));
           Restaurant restaurant = restaurantDao.findById(restaurantId);
           FoodType foodType = foodTypeDao.findById(foodtypeId);

           if (restaurant != null && foodType != null) {
               foodTypeDao.addFoodtypeToRestaurant(foodType, restaurant);
               response.status(201);
               return gson.toJson(String.format("Restaurant '%s' and Foodtype '%s' have been associated", foodType.getName(), restaurant.getName()));
           } else {
               throw new ApiException(404, String.format("Restaurant or Foodtype does not exist"));
           }
        });

        //READ
        get("/restaurants", "application/json", (request, response) -> {
            System.out.println(restaurantDao.getAll());

            if(restaurantDao.getAll().size() > 0) {
                return gson.toJson(restaurantDao.getAll());
            } else {
                return "{\"message\":\"I'm sorry, but no restaurants are currently listed in the database.\"}";
            }

        });

        get("/restaurants/:id", "application/json", (request, response) -> {
           int restaurantId = Integer.parseInt(request.params("id"));

           Restaurant restaurantToFind = restaurantDao.findById(restaurantId);

           if(restaurantToFind == null) {
               throw new ApiException(404, String.format("No restaurant with the id: '%s' exists", request.params("id")));
           }

           return gson.toJson(restaurantToFind);
        });

        get("/foodtypes", "application/json", (request, response) -> {
           return gson.toJson(foodTypeDao.getAll());
        });

        get("/restaurants/:restaurantId/reviews", "application/json", (request, response) -> {
           int restaurantId = Integer.parseInt(request.params("restaurantId"));
           Restaurant restaurantToFind = restaurantDao.findById(restaurantId);

            if (restaurantToFind == null){
                throw new ApiException(404, String.format("No restaurant with the id: '%s' exists", request.params("id")));
            }

            List<Review> allReviews = reviewDao.getAllReviewsByRestaurantId(restaurantId);
           return gson.toJson(allReviews);
        });

        get("/restaurants/:id/foodtypes", "application/json", (request, response) -> {
           int restaurantId = Integer.parseInt(request.params("id"));
           Restaurant restaurantToFind = restaurantDao.findById(restaurantId);
           if (restaurantToFind == null) {
               throw new ApiException(404, String.format("No restaurant with the id: '%s' exists", request.params("id")));
           } else if (restaurantDao.getAllFoodtypesByRestaurant(restaurantId).size() == 0) {
               return "{\"message\":\"I'm sorry, but no foodtypes are listed for this restaurant.\"}";
           } else {
               return gson.toJson(restaurantDao.getAllFoodtypesByRestaurant(restaurantId));
           }
        });

        get("foodtypes/:id/restaurants", "application/json", (request, response) -> {
           int foodtypeId = Integer.parseInt(request.params("id"));
           FoodType foodTypeToFind = foodTypeDao.findById(foodtypeId);
           if (foodTypeToFind == null) {
               throw new ApiException(404, String.format("No foodtype with the id: '%s' exists", request.params("id")));
           } else if (foodTypeDao.getAllRestaurantsForAFoodtype(foodtypeId).size() == 0) {
               return "{\"message\":\"I'm sorry, but no restaurants are listed for this foodtype.\"}";
           } else {
               return gson.toJson(foodTypeDao.getAllRestaurantsForAFoodtype(foodtypeId));
           }
        });

        get("restaurants/:id/sortedReviews", "application/json", (request, response) -> {
           int restaurantId = Integer.parseInt(request.params("id"));
           Restaurant restaurantToFind = restaurantDao.findById(restaurantId);
           List<Review> allReviews;
           if (restaurantToFind == null) {
               throw new ApiException(404, String.format("No restaurant with the id: '%s' exists", request.params("id")));
           }
           allReviews = reviewDao.getAllReviewsByRestaurantSortedNewestToOldest(restaurantId);
           return gson.toJson(allReviews);
        });


        //FILTERS
        exception(ApiException.class, (exception, request, response) -> {
            ApiException err = (ApiException) exception;
            Map<String, Object> jsonMap = new HashMap<>();
            jsonMap.put("status", err.getStatusCode());
            jsonMap.put("errorMessage", err.getMessage());
            response.type("application/json");
            response.status(err.getStatusCode());
            response.body(gson.toJson(jsonMap));
        });


        after((request, response) -> {
           response.type("application/json");
        });
    }
}
