package com.Blood.Inventory_Service.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Blood.Inventory_Service.Model.BloodCount;
import com.Blood.Inventory_Service.Model.Inventory;

@Repository
public interface InventroyRepo extends JpaRepository<Inventory,Long>{

	List<Inventory> findByBloodGroup(String bloodGroup);

	Inventory findByDonorname(String donorname);
   
}
