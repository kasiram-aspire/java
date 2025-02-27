package com.blood.Request_Service.Model;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import jakarta.persistence.Id;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Request {
	 @Id
     @GeneratedValue(strategy=GenerationType.IDENTITY)
     private Long Id;
	 private String HospitolName;
	 private String bloodGroup;
	 private String Message;
	 private LocalDate datelimit;
}
