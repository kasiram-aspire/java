//package com.example.SpringbootExercise.controller;
//
//import java.util.List;
//import java.util.Optional;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RestController;
//
//import com.example.SpringbootExercise.models.GrocessryProduct;
//import com.example.SpringbootExercise.models.User;
//import com.example.SpringbootExercise.services.GrocessryProductService;
//
//@RestController
//public class GrocessryProductController {
//	@Autowired
//	GrocessryProductService grocessryproductservice;
//	
//	@PostMapping("/product/add")
//	public String add(@RequestBody GrocessryProduct product)
//	{
//		grocessryproductservice.addproduct(product);
//		 return "added";
//	}
//    @GetMapping("/product/showall")
//	 public List<GrocessryProduct> getAllMovies() {
//	        return grocessryproductservice.getAllproducts();
//	    }
//    @GetMapping("/Product/{productId}")
//    public GrocessryProduct getUsersByMovieId(@PathVariable Long productId) {
//        return grocessryproductservice.getUsersByproductId(productId);
//    }
//}
