package com.example.demo.controller;

import java.io.IOException;
import java.security.PublicKey;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.model.Product;
import com.example.demo.service.ProductService;

@RestController
@CrossOrigin
@RequestMapping("/api")
public class ProductController {

	@Autowired
	private ProductService service;
	
	@GetMapping("/products")
	public ResponseEntity<List<Product>> getAllProducts()
	{
		return new ResponseEntity<>(service.getAllProducts(),HttpStatus.OK);	}
	
	
	@GetMapping("/product/{id}")
    public ResponseEntity<Product> getProduct(@PathVariable int id) {
        Product product = service.getPoductById(id);
        if (product != null) {
            return new ResponseEntity<>(product, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    
	@PostMapping("/product")
	public ResponseEntity<?> addProduct(@RequestPart Product product, @RequestPart MultipartFile imageFile) {
	    try {
	        Product createdProduct = service.addProduct(product, imageFile);
	        return new ResponseEntity<>(createdProduct, HttpStatus.CREATED);
	    } catch (Exception e) {
	        return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
	    }
	}
	
	@GetMapping("product/{productId}/image")
	public ResponseEntity<byte[]> getImageByProductId(@PathVariable int productId)
	{
		Product product=service.getPoductById(productId);
		byte[] imageFile=product.getImageDate();
		 
		return ResponseEntity.ok().contentType(MediaType.valueOf(product.getImageType()))
				.body(imageFile);
		
	}


	  @DeleteMapping("/product/{id}")
	  public ResponseEntity<String> deleteproduct(@PathVariable int id )
	  {
		  Product product= service.getPoductById(id);
		  if(product!=null)
		  {
			  service.deleteproduct(id);
			  return new ResponseEntity<>("Deleted",HttpStatus.OK);
		  }
		  else {
			return new ResponseEntity<>("Prduct not found",HttpStatus.NOT_FOUND);
		}}
		  
		  @PutMapping("/product/{id}")
		  public ResponseEntity<String> updateProduct(@PathVariable int id,@RequestPart Product product,@RequestPart MultipartFile imageFile)
		  {
			  Product product1=null;
			  try {
				  product1=service.updateProduct(id,product,imageFile);
				  
			  }catch(IOException e)
			  {
				  return new ResponseEntity<>("Failed to update",HttpStatus.BAD_REQUEST);
			  }
			  if(product1!=null)
			  {
				  return new ResponseEntity<>("Updated",HttpStatus.OK);
			  }
			  else {
				return new ResponseEntity<>("Failde to update",HttpStatus.BAD_REQUEST);
			}
		  }
		  @GetMapping("products/search")
		  public ResponseEntity<List<Product>> searchProduct(@RequestParam String keyword)
		  {
			List<Product> product=  service.searchProduct(keyword);
			System.out.println(keyword);
			return new ResponseEntity<>(product,HttpStatus.OK);
		  }
	  
}

