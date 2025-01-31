package com.example.SpringbootExercise.models;

import jakarta.persistence.Id;
import org.springframework.stereotype.Component;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;


@Entity
@Table(name = "user")
public class User {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long userId;
	private String userName;
	@ManyToOne
	 @JoinColumn(name = "Id", nullable = false)
	private GrocessryProduct grocessryproduct;

	public User()
	{
		
	}
	public User(Long userId, String userName) {
		this.userId = userId;
		this.userName = userName;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public GrocessryProduct getGrocessryproduct() {
		return grocessryproduct;
	}
	public void setGrocessryproduct(GrocessryProduct grocessryproduct) {
		this.grocessryproduct = grocessryproduct;
	}
}
