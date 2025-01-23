package com.example.SpringbootExercise.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;

import com.example.SpringbootExercise.models.Product;
@Service
public class ProductService {
     public List<Product> productList=new ArrayList<>();
     public void addProduct()
     {
          productList.add(new Product(101,"iphone",5000));
          productList.add(new Product(102,"laptop",5000));
          productList.add(new Product(103,"fridge",5000));
          productList.add(new Product(104,"fan",5000));
          productList.add(new Product(105,"car",5000));
     }
     public List<Product> getProduct()
     {
    	 return productList;
     }
}
