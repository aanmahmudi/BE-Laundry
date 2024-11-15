package com.laundry.BE_Laundry.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.laundry.BE_Laundry.Entity.Product;
import com.laundry.BE_Laundry.Repository.ProductRepository;

@Service
public class ProductService {
	
	@Autowired
	private ProductRepository productRepository;
	
	public Product createProduct(Product product) {
		return productRepository.save(product);
	}
	
	public List <Product> getAllProducts(){
		return productRepository.findAll();
	}
	
	public Product updateProduct(Long id, Product productDetails){
		Product product = productRepository.findById(id).orElseThrow();
		product.setName(productDetails.getName());
		product.setPrice(productDetails.getPrice());
		product.setDescription(productDetails.getDescription());
		return productDetails;
		
	}
	
	public void deleteProduct(Long id) {
		productRepository.deleteById(id);
	}
	

}
