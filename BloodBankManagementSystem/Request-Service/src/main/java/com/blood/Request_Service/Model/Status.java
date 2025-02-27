package com.blood.Request_Service.Model;

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
public class Status {
	 @Id
     @GeneratedValue(strategy=GenerationType.IDENTITY)
     private Long Id;
	 private String Hospitolname;
	 private String donorName;
	 private String status;
	 private Integer age;
	 private String bloodGroup;
	 
}
