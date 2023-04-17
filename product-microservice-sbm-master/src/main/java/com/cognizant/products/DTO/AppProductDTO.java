package com.cognizant.products.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

public @Data @AllArgsConstructor @NoArgsConstructor class AppProductDTO {
	
	private String name;
	private String make;
	private String model;
	private double cost;	
	private String productImageUrl;
}
