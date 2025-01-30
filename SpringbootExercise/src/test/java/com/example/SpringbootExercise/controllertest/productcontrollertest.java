package com.example.SpringbootExercise.controllertest;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.hamcrest.Matchers.hasSize;
import java.util.Arrays;
import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.example.SpringbootExercise.controller.productController;
import com.example.SpringbootExercise.models.Product;
import com.example.SpringbootExercise.repository.ProductRepo;
import com.example.SpringbootExercise.services.ProductService;
import com.fasterxml.jackson.databind.ObjectMapper;

public class productcontrollertest {

    private MockMvc mockMvc;

    @InjectMocks
    private productController productController; // Inject controller

    @Mock
    private ProductRepo repo;  // Mock repository

    @Mock
    private ProductService productService;  // Mock service

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(productController).build();
    }

    @Test
    public void addProductShouldAddSuccessfully() throws Exception {
        // Given product details
        Product product = new Product(100, "iphone", 5000000);

        // When the service's addProductElement method is called, return the product
        when(productService.addProductElement(any(Product.class))).thenReturn(product);

        // Perform the POST request
        mockMvc.perform(post("/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(product)))
                .andExpect(status().isOk())  // Expect HTTP OK
                .andExpect(jsonPath("$.id").value(100))  // Verify product ID
                .andExpect(jsonPath("$.productname").value("iphone"))  // Verify product name
                .andExpect(jsonPath("$.price").value(5000000));  // Verify product price
    }
    @Test
    public void getAllProductsShouldReturnProducts() throws Exception {
        // Given a list of products
        Product product1 = new Product(100, "iphone", 5000000);
        Product product2 = new Product(101, "laptop", 7000000);
        List<Product> productList = Arrays.asList(product1, product2);
        // When the service's getProduct method is called, return the list of products
        when(productService.getProduct()).thenReturn(productList);

        // Perform the GET request
        mockMvc.perform(get("/product")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())  // Expect HTTP OK
                .andExpect(jsonPath("$", hasSize(2)))  // Verify the number of products returned is 2
                .andExpect(jsonPath("$[0].id").value(100))  // Verify first product's ID
                .andExpect(jsonPath("$[0].productname").value("iphone"))  // Verify first product's name
                .andExpect(jsonPath("$[0].price").value(5000000))  // Verify first product's price
                .andExpect(jsonPath("$[1].id").value(101))  // Verify second product's ID
                .andExpect(jsonPath("$[1].productname").value("laptop"))  // Verify second product's name
                .andExpect(jsonPath("$[1].price").value(7000000));  
    }
    //get value base on id
    @Test
    public void getTheProductBasedOnTheId() throws Exception
    {
    	int productId = 100;
        Product product = new Product(productId, "iphone", 5000000);
    	 when(productService.getElementById(productId)).thenReturn(product);
    	 mockMvc.perform(get("/product/{productId}", productId)
                 .contentType(MediaType.APPLICATION_JSON))
    	         .andExpect(status().isOk())
    	         .andExpect(jsonPath("$.id").value(100))
    	         .andExpect(jsonPath("$.productname").value("iphone"));
    	            
    }
    //update 
    @Test
    public void updateProductShouldReturnStatusOk() throws Exception {
        // Given a product to be updated
        Product product = new Product(100, "iphone", 5000000);
        when(productService.updateproduct(any(Product.class))).thenReturn(product);
        // Perform the PUT request with the product details in the request body
        mockMvc.perform(put("/product/updateproduct")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(product)))
                .andExpect(status().isOk()) // Expect HTTP status 200 OK
                .andExpect(jsonPath("$.id").value(100));
    }
}

