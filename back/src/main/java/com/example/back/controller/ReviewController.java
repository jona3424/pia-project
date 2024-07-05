package com.example.back.controller;

import com.example.back.entities.Reviews;
import com.example.back.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/reviews")
public class ReviewController {

    @Autowired
    private ReviewService reviewService;

    @GetMapping
    public List<Reviews> getAllReviews() {
        return reviewService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Reviews> getReviewById(@PathVariable Integer id) {
        Optional<Reviews> Reviews = reviewService.findById(id);
        return Reviews.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public Reviews createReview(@RequestBody Reviews Reviews) {
        return reviewService.save(Reviews);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Reviews> updateReview(@PathVariable Integer id, @RequestBody Reviews reviewDetails) {
        Optional<Reviews> Reviews = reviewService.findById(id);
        if (Reviews.isPresent()) {
            Reviews updatedReview = Reviews.get();
            updatedReview.setUserId(reviewDetails.getUserId());
            updatedReview.setRestaurantId(reviewDetails.getRestaurantId());
            updatedReview.setReservationId(reviewDetails.getReservationId());
            updatedReview.setRating(reviewDetails.getRating());
            updatedReview.setComment(reviewDetails.getComment());
            return ResponseEntity.ok(reviewService.save(updatedReview));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReview(@PathVariable Integer id) {
        reviewService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/average-rating/{restaurantId}")
    public ResponseEntity<?> getAverageRating(@PathVariable Integer restaurantId) {
        return ResponseEntity.ok(reviewService.getAverageRating(restaurantId));
    }
}

