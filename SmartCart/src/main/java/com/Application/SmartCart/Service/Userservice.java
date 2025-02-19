package com.Application.SmartCart.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Application.SmartCart.Model.Product;
import com.Application.SmartCart.Model.User;
import com.Application.SmartCart.Repository.ProductRepository;
import com.Application.SmartCart.Repository.UserRepository;

@Service
public class Userservice {
    @Autowired
    private UserRepository userrepo;

    @Autowired
    private ProductRepository productRepo;

    public String adduser(User user) {
        String name = user.getName();
        User userobj = userrepo.findByName(name);

        if (userobj == null) {
            userobj = new User(name);
        }

        Set<Product> products = new HashSet<>();
        Set<Product> productobj = user.getProducts();
        List<String> productNames = new ArrayList<>();

        for (Product names : productobj) {
            productNames.add(names.getProductName());
        }

        for (String productName : productNames) {
            Set<Product> existingProducts = productRepo.findByProductName(productName);

            Product product;
            if (existingProducts.isEmpty()) {
                product = new Product(productName);
                product = productRepo.save(product); // Save the new product before using it
            } else {
                product = existingProducts.iterator().next(); // Use an existing product
            }

            products.add(product);
        }

        // Associate products with the user and save
        userobj.getProducts().addAll(products);
        userrepo.save(userobj);
        
        return "added";
    }
}

