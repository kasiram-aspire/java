package com.Blood.Inventory_Service.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Blood.Inventory_Service.Model.BloodCount;

@Repository
public interface BloodCountRepository extends JpaRepository<BloodCount,Long> {

	BloodCount findByBloodGroup(String bloodGroup);

}
