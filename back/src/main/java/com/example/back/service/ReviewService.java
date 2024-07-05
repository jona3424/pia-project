package com.example.back.service;

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
}

