import com.google.gson.Gson;
import dao.Sql2oFoodTypeDao;
import dao.Sql2oRestaurantDao;
import dao.Sql2oReviewDao;
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
            review.setRestaurantId(restaurantId);
            reviewDao.add(review);
            response.status(201);
            return gson.toJson(review);
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

//           if(restaurantToFind == null) {
//               throw new ApiException(404, String.format("No restaurant with the id: \"%s\" exists", req.params("id")));
//           }

           return gson.toJson(restaurantToFind);
        });

        get("/foodtypes", "application/json", (request, response) -> {
           return gson.toJson(foodTypeDao.getAll());
        });

        get("/restaurants/:restaurantId/reviews", "application/json", (request, response) -> {
           int restaurantId = Integer.parseInt(request.params("restaurantId"));
           Restaurant restaurantToFind = restaurantDao.findById(restaurantId);

//            if (restaurantToFind == null){
//                throw new ApiException(404, String.format("No restaurant with the id: \"%s\" exists", req.params("id")));
//            }

            List<Review> allReviews = reviewDao.getAllReviewsByRestaurantId(restaurantId);
           return gson.toJson(allReviews);
        });


        //FILTERS
//        exception(ApiException.class, (exception, request, response) -> {
//            ApiException err = (ApiException) exception;
//            Map<String, Object> jsonMap = new HashMap<>();
//            jsonMap.put("status", err.getStatusCode());
//            jsonMap.put("errorMessage", err.getMessage());
//            response.type("application/json");
//            response.status(err.getStatusCode());
//            response.body(gson.toJson(jsonMap));
//        });


        after((request, response) -> {
           response.type("application/json");
        });
    }
}
