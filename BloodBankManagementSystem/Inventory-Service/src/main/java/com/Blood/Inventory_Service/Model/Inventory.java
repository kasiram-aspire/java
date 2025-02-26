package com.Blood.Inventory_Service.Model;

import java.time.LocalDate;
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
public class Inventory {
   @Id
   @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    private String donorname;
    private String bloodGroup;
    private Integer Age;
    private Long UnitOfBlood;
    private LocalDate BloodGivenDate;
}
