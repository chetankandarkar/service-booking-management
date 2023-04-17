package com.cognizant.products.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cognizant.products.DTO.AppProductDTO;
import com.cognizant.products.DTO.ValidatingDTO;
import com.cognizant.products.clients.AuthClient;
import com.cognizant.products.exception.InvalidUserAccessException;
import com.cognizant.products.exception.ProductNotFoundException;
import com.cognizant.products.model.AppProduct;
import com.cognizant.products.repository.ProductRepository;
import com.cognizant.products.service.AppProductService;

@Service
public class AppProductServiceImpl implements AppProductService {

	@Autowired
	ProductRepository repository;

	@Autowired
	AuthClient authClient;

	@Override
	@Transactional
	public List<AppProduct> getAllProducts(String token) throws InvalidUserAccessException {
		if (authClient.validatingToken(token).isValidStatus()) {
			return repository.findAll();
		}
		throw new InvalidUserAccessException("INVALID_USER_EXCEPTION");
	}

	@Override
	@Transactional
	public AppProduct getProductById(String token, long id)
			throws InvalidUserAccessException, ProductNotFoundException {
		if (authClient.validatingToken(token).isValidStatus()) {
			Optional<AppProduct> product = repository.findById(id);
			if (product.isPresent()) {
				return product.get();
			}
			throw new ProductNotFoundException("PRODUCT WITH THE PROVIDED ID NOT FOUND");
		}
		throw new InvalidUserAccessException("INVALID_USER_EXCEPTION");
	}

	@Override
	@Transactional
	public AppProduct updateProduct(String token, long id, AppProductDTO dto)
			throws ProductNotFoundException, InvalidUserAccessException {
		ValidatingDTO validator = authClient.validatingToken(token);
		Optional<AppProduct> findById = repository.findById(id);
		if (findById.isPresent()) {
			if (validator.isValidStatus() && (validator.getUserRole().contains("ROLE_ADMIN")
					|| validator.getEmail().equalsIgnoreCase(findById.get().getCreatedBy()))) {
				AppProduct appProduct = findById.get();
				appProduct.setName(dto.getName());
				appProduct.setMake(dto.getMake());
				appProduct.setCost(dto.getCost());
				appProduct.setModel(dto.getModel());
				AppProduct result = repository.save(appProduct);
				return result;
			}
			throw new InvalidUserAccessException("UNAUTHORIZED_DATA_ACCESS");
		}
		throw new ProductNotFoundException("PRODUCT NOT FOUND");
	}

	@Override
	@Transactional
	public AppProduct addProduct(String token, AppProductDTO dto) throws InvalidUserAccessException {
		if (authClient.validatingToken(token).isValidStatus()) {
			AppProduct result = repository.save(new AppProduct(dto.getName(), dto.getMake(), dto.getModel(),
					dto.getCost(), new Date(), authClient.validatingToken(token).getEmail(),dto.getProductImageUrl()));
			return result;
		}
		throw new InvalidUserAccessException("UNAUTHORIZED_DATA_ACCESS");
	}

	@Override
	@Transactional
	public AppProduct deleteProduct(String token, long id) throws InvalidUserAccessException, ProductNotFoundException {
		ValidatingDTO validator = authClient.validatingToken(token);
		Optional<AppProduct> product = repository.findById(id);
		if (product.isPresent()) {
			if (validator.isValidStatus() && (validator.getUserRole().contains("ROLE_ADMIN")
					|| validator.getEmail().equalsIgnoreCase(product.get().getCreatedBy()))) {
				repository.delete(product.get());
				return product.get();
			}
			throw new InvalidUserAccessException("Invalid User Access");
		}
		throw new ProductNotFoundException("Product Not found Exception");
	}
	
	@Override
	@Transactional
	public List<AppProduct> getMyProduct(String token) throws InvalidUserAccessException{
		ValidatingDTO validator = authClient.validatingToken(token);
		if(validator.isValidStatus()) {
			return repository.findByCreatedBy(validator.getEmail());			
		}
		throw new InvalidUserAccessException("Unauthorized Access");
	}
	
}
