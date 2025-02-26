package com.Blood.Inventory_Service.dto;

import java.time.LocalDate;

import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Component
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Donordto {
	 private Long id;
     private String donorName;
     private Integer age;
     private String bloodGroup;
     private String emailId;
     private Long phoneno;
     private LocalDate dateOfBirth;
}
