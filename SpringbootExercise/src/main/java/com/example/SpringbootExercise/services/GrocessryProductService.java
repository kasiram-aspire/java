//package com.example.SpringbootExercise.services;
//
//import java.util.List;
//import java.util.Optional;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import com.example.SpringbootExercise.models.GrocessryProduct;
//import com.example.SpringbootExercise.repository.GrocessryProductRepo;
//
//@Service
//public class GrocessryProductService {
//	@Autowired
//	GrocessryProductRepo grocessryrepo;
//	public void addproduct(GrocessryProduct product)
//	{
//		grocessryrepo.save(product);
//	}
//	public List<GrocessryProduct> getAllproducts() {
//		// TODO Auto-generated method stub
//		return grocessryrepo.findAll();
//	}
//	public GrocessryProduct getUsersByproductId(Long productId) {
//		// TODO Auto-generated method stub
//		 return grocessryrepo.findById(productId).orElse(null);
//	}
//}
