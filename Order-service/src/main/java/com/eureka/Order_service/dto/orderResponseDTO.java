package com.eureka.Order_service.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class orderResponseDTO {
	private Long orderId;
	private Long productId;
	private int quantity;
	private double totalPrice;
	
	//product details
	
	private String productName;
	private double productPrice;
//	public orderResponseDTO() {
		
//	}
//	public orderResponseDTO(Long orderId, Long productId, int quantity, double totalPrice, String productName,
//			double productPrice) {
//		this.orderId = orderId;
//		this.productId = productId;
//		this.quantity = quantity;
//		this.totalPrice = totalPrice;
//		this.productName = productName;
//		this.productPrice = productPrice;
//	}
//	public Long getOrderId() {
//		return orderId;
//	}
//	public void setOrderId(Long orderId) {
//		this.orderId = orderId;
//	}
//	public Long getProductId() {
//		return productId;
//	}
//	public void setProductId(Long productId) {
//		this.productId = productId;
//	}
//	public int getQuantity() {
//		return quantity;
//	}
//	public void setQuantity(int quantity) {
//		this.quantity = quantity;
//	}
//	public double getTotalPrice() {
//		return totalPrice;
//	}
//	public void setTotalPrice(double totalPrice) {
//		this.totalPrice = totalPrice;
//	}
//	public String getProductName() {
//		return productName;
//	}
//	public void setProductName(String productName) {
//		this.productName = productName;
//	}
//	public double getProductPrice() {
//		return productPrice;
//	}
//	public void setProductPrice(double productPrice) {
//		this.productPrice = productPrice;
//	}
//	
//	

}
