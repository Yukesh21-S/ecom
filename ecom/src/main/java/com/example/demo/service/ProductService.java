package com.example.demo.service;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.model.Product;
import com.example.demo.repository.ProductRepository;

@Service
public class ProductService {

	@Autowired
	private ProductRepository repo;
	public List<Product> getAllProducts() {
	
		return repo.findAll();
				
	}
	
//	public Product getPoductById(int id)
//	{
//		return repo.findById(id).get();
//		
//	}
	public Product addProduct(Product product,MultipartFile imageFile) throws IOException {
		// TODO Auto-generated method stub
	product.setImageName(imageFile.getOriginalFilename());
	product.setImageType(imageFile.getContentType());
	product.setImageDate(imageFile.getBytes());
		return repo.save(product);
		
	}
	public void deleteproduct(int id)
	{
		repo.deleteById(id);
	}

	public Product getPoductById(int id) {
		// TODO Auto-generated method stub
		return repo.findById(id).get();
	}

	public Product updateProduct(int id, Product product, MultipartFile imageFile) throws IOException {
		// TODO Auto-generated method stub
		product.setImageDate(imageFile.getBytes());
		return repo.save(product);
	}


public List<Product> searchProduct(String keyword) {
	// TODO Auto-generated method stub
	return repo.searchProducts(keyword);
}
	
}
