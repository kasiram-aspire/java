package com.bloodBank.User_Service.Model;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;

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
public class Donors {
     @Id
     @GeneratedValue(strategy=GenerationType.IDENTITY)
     private Long id;
     private String donorName;
     private Integer age;
     private String bloodGroup;
     private String emailId;
     private Long phoneno;
     private LocalDate dateOfBirth;
     
     
     
}
