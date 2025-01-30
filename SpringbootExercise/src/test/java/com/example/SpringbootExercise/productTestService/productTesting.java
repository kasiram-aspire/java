package com.example.SpringbootExercise.productTestService;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.example.SpringbootExercise.controller.productController;
import com.example.SpringbootExercise.models.Product;
import com.example.SpringbootExercise.repository.ProductRepo;
import com.example.SpringbootExercise.services.ProductService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

@ExtendWith(MockitoExtension.class)
public class productTesting {

    private MockMvc mockMvc;

    private Product product = new Product(100, "iphone", 5000000);

    @Mock
    private ProductRepo repo;

    @InjectMocks
    private ProductService productservice;

    @Test  // checking the product is added successfully and product is not null
    public void addProductShouldAddSuccessfully() {
        Product product = new Product(100, "iphone", 50000);

        when(repo.save(product)).thenReturn(product);

        Product savedProduct = productservice.addProductElement(product);

        assertNotNull(savedProduct);
        assertEquals(product.getId(), savedProduct.getId());  // test if product ID matches
    }

    @Test
    public void addedProductEqualsToCreatedProducts() {
        List<Product> addedProducts = new ArrayList<>();
        Product product = new Product(100, "iphone", 5000000);

        when(repo.save(product)).thenReturn(product);
        addedProducts.add(productservice.addProductElement(product));

        product = new Product(101, "fridge", 40000);
        addedProducts.add(productservice.addProductElement(product));

        product = new Product(102, "phone", 40000);
        addedProducts.add(productservice.addProductElement(product));

        assertEquals(3, addedProducts.size());  // Verify the number of products added
        assertEquals(101, addedProducts.get(1).getId());
    }
    // get element by id
    @Test
    public void getProductBasedOnId()
    {
    	 Product product = new Product(100, "iphone", 5000000);
    	 when(repo.findById(100)).thenReturn(Optional.of(product));
      	Product savedproduct= productservice.getElementById(100);
      	assertNotNull(savedproduct);
    	assertEquals("iphone",savedproduct.getProductname());
    }
    // get element by id
    @Test
    public void update()
    {
    	 Product product = new Product(100, "iphone", 5000000);
    	 when(repo.findById(100)).thenReturn(Optional.of(product));
      	Product savedproduct= productservice.getElementById(100);
      	assertNotNull(savedproduct);
    	assertEquals("iphone",savedproduct.getProductname());
    }
    
    
}
    

