package com.cognizant.products.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cognizant.products.DTO.AppProductDTO;
import com.cognizant.products.exception.InvalidUserAccessException;
import com.cognizant.products.exception.ProductNotFoundException;
import com.cognizant.products.model.AppProduct;
import com.cognizant.products.model.Message;
import com.cognizant.products.service.AppProductService;

import feign.FeignException.FeignClientException;

@RestController
@RequestMapping("/product")
@CrossOrigin(origins = "http://localhost:5000")
public class ProductController {

	@Autowired
	AppProductService service;

	@GetMapping
	public ResponseEntity<?> getAllProducts(@RequestHeader(name = "Authorization") String token) {
		try {
			List<AppProduct> allProducts = service.getAllProducts(token);
			if(allProducts.isEmpty()) {				
				return new ResponseEntity<>(new Message(200, "NO_DATA_PRESENT", null), HttpStatus.OK);
			}
			return new ResponseEntity<>(new Message(200, "FOUND_DATA", allProducts), HttpStatus.OK);
		} catch (InvalidUserAccessException e) {
			return new ResponseEntity<>(new Message(401, "UNAUTHORIZED_ACCESS", null), HttpStatus.UNAUTHORIZED);
		} catch (FeignClientException e) {
			String[] message = e.getMessage().split(" ");
			int errCode = Integer.parseInt(message[0].split("")[1] + message[0].split("")[2] + message[0].split("")[3]);
			return new ResponseEntity<>(new Message(errCode, "AUTHORIZATION_ERROR", message[5]),
					HttpStatus.valueOf(errCode));
		}
	}
	
	@GetMapping("/my-products")
	public ResponseEntity<?> getMyProducts(@RequestHeader(name = "Authorization")String token){
		try {
			List<AppProduct> result = service.getMyProduct(token);
			if(result.isEmpty()) {				
				return new ResponseEntity<>(new Message(200, "NO_DATA_FOUND", null), HttpStatus.OK);
			}
			return new ResponseEntity<>(new Message(200, "DATA_FOUND", result),HttpStatus.OK);
		}  catch (InvalidUserAccessException e) {
			return new ResponseEntity<>(new Message(401, "UNAUTHORIZED_ACCESS", null), HttpStatus.UNAUTHORIZED);
		} catch (FeignClientException e) {
			String[] message = e.getMessage().split(" ");
			int errCode = Integer.parseInt(message[0].split("")[1] + message[0].split("")[2] + message[0].split("")[3]);
			return new ResponseEntity<>(new Message(errCode, "AUTHORIZATION_ERROR", message[5]),
					HttpStatus.valueOf(errCode));
		}
	}

	@GetMapping("/{id}")
	public ResponseEntity<?> getProductById(@RequestHeader(name = "Authorization") String token,
			@PathVariable Long id) {
		try {
			AppProduct result = service.getProductById(token, id);
			return new ResponseEntity<>(new Message(200, "PRODUCT_FOUND", result), HttpStatus.OK);
		} catch (InvalidUserAccessException e) {
			return new ResponseEntity<>(new Message(401, "UNAUTHORIZED_DATA_ACCESS", null), HttpStatus.UNAUTHORIZED);
		} catch (ProductNotFoundException e) {
			return new ResponseEntity<>(new Message(404, "NO_PRODUCT_FOUND_WITH_ID_" + id, null), HttpStatus.NOT_FOUND);
		} catch (FeignClientException e) {
			String[] message = e.getMessage().split(" ");
			int errCode = Integer.parseInt(message[0].split("")[1] + message[0].split("")[2] + message[0].split("")[3]);
			return new ResponseEntity<>(new Message(errCode, "AUTHORIZATION_ERROR", message[5]),
					HttpStatus.valueOf(errCode));
		}
	}

	@PutMapping("/{id}")
	public ResponseEntity<?> updateProduct(@RequestHeader(name = "Authorization") String token, @PathVariable long id,
			@RequestBody AppProductDTO dto) {
		try {
			AppProduct result = service.updateProduct(token, id, dto);
			return new ResponseEntity<>(new Message(200, "Product updated", result), HttpStatus.OK);
		} catch (ProductNotFoundException e) {
			return new ResponseEntity<>(new Message(404, "NO_PRODUCT_FOUND_WITH_ID_" + id, null), HttpStatus.NOT_FOUND);
		} catch (InvalidUserAccessException e) {
			return new ResponseEntity<>(new Message(401, "UNAUTHORIZED_DATA_ACCESS", null), HttpStatus.UNAUTHORIZED);
		} catch (FeignClientException e) {
			String[] message = e.getMessage().split(" ");
			int errCode = Integer.parseInt(message[0].split("")[1] + message[0].split("")[2] + message[0].split("")[3]);
			return new ResponseEntity<>(new Message(errCode, "AUTHORIZATION_ERROR", message[5]),
					HttpStatus.valueOf(errCode));
		}
	}

	@PostMapping
	public ResponseEntity<?> addProduct(@RequestHeader(name = "Authorization") String token, @RequestBody AppProductDTO dto) {
		try {
			AppProduct result = service.addProduct(token, dto);
			return new ResponseEntity<>(new Message(200, "Product added successfully with id: "+result.getId(), result), HttpStatus.OK);
		} catch (InvalidUserAccessException e) {
			return new ResponseEntity<>(new Message(401, "UNAUTHORIZED_DATA_ACCESS", null), HttpStatus.UNAUTHORIZED);
		} catch (FeignClientException e) {
			String[] message = e.getMessage().split(" ");
			int errCode = Integer.parseInt(message[0].split("")[1] + message[0].split("")[2] + message[0].split("")[3]);
			return new ResponseEntity<>(new Message(errCode, "AUTHORIZATION_ERROR", message[5]),
					HttpStatus.valueOf(errCode));
		}
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteProduct(@RequestHeader(name = "Authorization") String token, @PathVariable long id) {
		try {
			AppProduct product = service.deleteProduct(token, id);
			return new ResponseEntity<>(new Message(200, "PRODUCT DELETED SUCCESSFULLY", product), HttpStatus.OK);
		} catch (ProductNotFoundException e) {
			return new ResponseEntity<>(new Message(404, "NO_PRODUCT_FOUND_WITH_ID_" + id, null), HttpStatus.NOT_FOUND);
		} catch (InvalidUserAccessException e) {
			return new ResponseEntity<>(new Message(401, "UNAUTHORIZED_DATA_ACCESS", null), HttpStatus.UNAUTHORIZED);
		} catch (FeignClientException e) {
			String[] message = e.getMessage().split(" ");
			int errCode = Integer.parseInt(message[0].split("")[1] + message[0].split("")[2] + message[0].split("")[3]);
			return new ResponseEntity<>(new Message(errCode, "AUTHORIZATION_ERROR", message[5]),
					HttpStatus.valueOf(errCode));
		}
	}

}
