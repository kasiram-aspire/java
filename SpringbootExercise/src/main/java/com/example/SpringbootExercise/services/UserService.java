package com.example.SpringbootExercise.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.SpringbootExercise.models.GroceryProduct;
import com.example.SpringbootExercise.models.User;
import com.example.SpringbootExercise.repository.GroceryProductRepository;
import com.example.SpringbootExercise.repository.UserRepository;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private GroceryProductRepository groceryProductRepository;

    // Save a User along with Grocery Products
    public User saveUserWithProducts(User user) {
        for (GroceryProduct product : user.getGroceryProducts()) {
            product.setUser(user);  // Assign user to each product
        }
        return userRepository.save(user);
    }

    // Get all Users with Grocery Products
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

	public User getById(Long id) {
		
		return userRepository.findById(id).orElse(null);
	}
}