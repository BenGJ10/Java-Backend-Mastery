package com.bengregory.app;

import com.bengregory.app.model.Product;
import com.bengregory.app.service.ProductService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import java.util.List;

@SpringBootApplication
public class SpringBootJDBC {

	public static void main(String[] args) {

		ApplicationContext context = SpringApplication.run(SpringBootJDBC.class, args);

		Product product = context.getBean(Product.class);
		product.setProductId(106);
		product.setName("Smart Watch");
		product.setBrand("Apple");
		product.setPrice(399.99);

		ProductService productService = context.getBean(ProductService.class);
		productService.addProduct(product);

		List<Product> products = productService.getProducts();
		System.out.println("Products in the database:");
		products.forEach(System.out::println);
	}

}
