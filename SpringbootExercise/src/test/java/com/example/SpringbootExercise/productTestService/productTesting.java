package com.example.SpringbootExercise.productTestService;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.SpringbootExercise.models.Product;
import com.example.SpringbootExercise.repository.ProductRepo;
import com.example.SpringbootExercise.services.ProductService;
@ExtendWith(MockitoExtension.class)
public class productTesting {
	@Mock
	 ProductRepo repo;
	@InjectMocks
	ProductService productservice;
	@Test  //checking the product added succesfully and product is not null
	public void addProductshouldAddSuccessfully()
	{
		Product product=new Product();
		product.setId(100);
		product.setProductname("iphone");
		product.setPrice(50000);
		Mockito.when(repo.save(product)).thenReturn(product);
		Product savedproduct=productservice.addProductElement(product);
		assertNotNull(savedproduct);
		assertEquals(product.getId(),savedproduct.getId());    //  test prouct is == matched product
	}
	
	@Test
	public void addedProductEqualsToCreatedProducts()
	{   List<Product> addedproduct = new ArrayList<>();
		Product product=new Product(100,"iphone",5000000);
		Mockito.when(repo.save(product)).thenReturn(product);
		addedproduct.add(productservice.addProductElement(product));
		product=new Product(101,"fridge",40000);
		addedproduct.add(productservice.addProductElement(product));
		product=new Product(102,"phone",40000);
		addedproduct.add(productservice.addProductElement(product));
		assertEquals(3,addedproduct.size()); //              no.of.products added == no of products present in the db
		assertEquals(101,addedproduct.get(1).getId());
	}

}
