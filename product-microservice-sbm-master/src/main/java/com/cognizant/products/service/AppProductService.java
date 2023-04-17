package com.cognizant.products.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.cognizant.products.DTO.AppProductDTO;
import com.cognizant.products.exception.InvalidUserAccessException;
import com.cognizant.products.exception.ProductNotFoundException;
import com.cognizant.products.model.AppProduct;

@Service
public interface AppProductService {

	List<AppProduct> getAllProducts(String token) throws InvalidUserAccessException;

	AppProduct getProductById(String token, long id) throws InvalidUserAccessException, ProductNotFoundException;

	AppProduct updateProduct(String token, long id, AppProductDTO dto)
			throws ProductNotFoundException, InvalidUserAccessException;

	AppProduct addProduct(String token, AppProductDTO dto) throws InvalidUserAccessException;

	AppProduct deleteProduct(String token, long id) throws InvalidUserAccessException, ProductNotFoundException;

	List<AppProduct> getMyProduct(String token) throws InvalidUserAccessException;

	
}
