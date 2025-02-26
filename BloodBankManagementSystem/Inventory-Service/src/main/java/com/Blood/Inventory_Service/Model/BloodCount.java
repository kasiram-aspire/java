package com.Blood.Inventory_Service.Model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class BloodCount {
     @Id
     @GeneratedValue(strategy=GenerationType.IDENTITY)
     private Long Id;
     private String bloodGroup;
     private Long units;
}
