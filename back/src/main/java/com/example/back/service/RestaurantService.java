package com.example.back.service;

import com.example.back.entities.RestaurantWorker;
import com.example.back.entities.Restaurants;
import com.example.back.entities.Users;
import com.example.back.repository.RestaurantRepository;
import com.example.back.repository.RestaurantWorkerRepository;
import com.example.back.repository.UserRepository;
import com.example.back.response.RestaurantsWithWorkers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class RestaurantService {

    @Autowired
    private RestaurantRepository restaurantRepository;
    @Autowired
    private RestaurantWorkerRepository restaurantWorkerRepository;

    @Autowired
    private UserRepository usersRepository;

    public List<Restaurants> findAll() {
        return restaurantRepository.findAll();
    }

    public Optional<Restaurants> findById(Integer id) {
        return restaurantRepository.findById(id);
    }

    public Restaurants save(Restaurants restaurant) {
        return restaurantRepository.save(restaurant);
    }

    public void deleteById(Integer id) {
        restaurantRepository.deleteById(id);
    }

    public int getNumberOfRestaurants() {
        return restaurantRepository.findAll().size();
    }

    public List<RestaurantsWithWorkers> getAllRestaurantsWithWorkers() {
        List<RestaurantsWithWorkers> restaurantsWithWorkersList = new ArrayList<>();
        restaurantRepository.findAll().forEach(restaurant -> {
            RestaurantsWithWorkers restaurantsWithWorkers= new RestaurantsWithWorkers();
            restaurantsWithWorkers.setRestaurantAddress(restaurant.getAddress());
            restaurantsWithWorkers.setRestaurantName(restaurant.getName());
            restaurantsWithWorkers.setTypeOfRestaurant(restaurant.getType());
            restaurantWorkerRepository.findUsersByRestaurantId(restaurant).forEach(worker -> {
                restaurantsWithWorkers.getWorkerNames().add(worker.getFirstName() + " " + worker.getLastName());
            });
            restaurantsWithWorkersList.add(restaurantsWithWorkers);
        });
        return restaurantsWithWorkersList;
    }

    public boolean assingWorkerToRestaurant(Integer restaurantId, Integer workerId) {
        // Provera da li restoran postoji
        Optional<Restaurants> restaurant = restaurantRepository.findById(restaurantId);
        if (!restaurant.isPresent()) {
            throw new IllegalArgumentException("Restaurant with ID " + restaurantId + " does not exist.");
        }

        // Provera da li korisnik postoji
        Optional<Users> user = usersRepository.findById(workerId);
        if (!user.isPresent()) {
            throw new IllegalArgumentException("User with ID " + workerId + " does not exist.");
        }

        // Provera da li je korisnik već dodeljen restoranu
        RestaurantWorker existingWorker = restaurantWorkerRepository.findByUserIdAndRestaurantId(user.get(), restaurant.get());
        if (existingWorker != null) {
            return false;
        }

        // Dodeljivanje korisnika restoranu
        RestaurantWorker restaurantWorker = new RestaurantWorker();
        restaurantWorker.setRestaurantId(restaurant.get());
        restaurantWorker.setUserId(user.get());
        restaurantWorkerRepository.saveAndFlush(restaurantWorker);
        return true;

    }
}

