package com.example.SpringbootExercise.services;

import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import com.example.SpringbootExercise.models.Product;
import jakarta.annotation.PostConstruct;
@Service
public class ProductService {
     public List<Product> productList=new ArrayList<>();
     public void addProduct()      //add default products into the list only once .when the list is empty
     {    
    	 if (productList.isEmpty())
     {
          productList.add(new Product(101,"iphone",5000));
          productList.add(new Product(102,"laptop",5000));
          productList.add(new Product(103,"fridge",5000));
          productList.add(new Product(104,"fan",5000));
          productList.add(new Product(105,"car",5000));
     }
     }
     public List<Product> getProduct()    //get all the product 
     {
    	 return productList;
     }
	public Product getElementById(int productId) {
		return productList.stream()
				          .filter(n->productId==n.getId())
				          .findFirst()
				          .orElse(null);
	}
	public void addProductElement(Product product)
	{   
		productList.add(product);
	}
	public void updateproduct(Product product) {   // update the product based on product id
		int index=0;
		for(int i=0;i<productList.size();i++)
		{
			if(productList.get(i).getId()==product.getId())
			{
				index=i;
				break;
			}
		}
		productList.set(index, product);
		
	}
	public void deleteproduct(int productId) {   //delete the product based on the product id
		int index=0;
		for(int i=0;i<productList.size();i++)
		{
			if(productList.get(i).getId()==productId)
			{
				index=i;
				break;
			}
		}
		productList.remove(index);
		
	}
}
