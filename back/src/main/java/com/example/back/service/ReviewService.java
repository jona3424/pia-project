package com.example.back.service;

import com.example.back.entities.Restaurants;
import com.example.back.entities.Reviews;
import com.example.back.repository.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ReviewService {

    @Autowired
    private ReviewRepository reviewRepository;

    public List<Reviews> findAll() {
        return reviewRepository.findAll();
    }

    public Optional<Reviews> findById(Integer id) {
        return reviewRepository.findById(id);
    }

    public Reviews save(Reviews review) {
        return reviewRepository.save(review);
    }

    public void deleteById(Integer id) {
        reviewRepository.deleteById(id);
    }

    public Double getAverageRating(Integer restaurantId) {
        List<Reviews> byRestaurantId = reviewRepository.findByRestaurantId(new Restaurants(restaurantId));
        if (byRestaurantId.isEmpty()) {
            return 0.0;
        }
        double sum = 0;
        for (Reviews review : byRestaurantId) {
            sum += review.getRating();
        }
        return sum / byRestaurantId.size();
    }

}

