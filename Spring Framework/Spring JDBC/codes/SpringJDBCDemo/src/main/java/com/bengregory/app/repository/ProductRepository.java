package com.bengregory.app.repository;

import com.bengregory.app.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public class ProductRepository {

    private JdbcTemplate jdbc;

    public JdbcTemplate getJdbc() {
        return jdbc;
    }

    @Autowired
    public void setJdbc(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    public void save(Product product){
        String saveQuery = "INSERT INTO products (product_id, name, brand, price) VALUES (?,?,?,?)";
        int rows = jdbc.update(saveQuery, product.getProductId(), product.getName(), product.getBrand(), product.getPrice());

        if(rows > 0) System.out.println("Successfully added " + product.getName() + " to the database!");
        else System.out.println("Failed to add " + product.getName() + " to the database!");
        System.out.println(rows + " rows affected.");
    }

    public List<Product> findAll() {

        String fetchQuery = "SELECT * FROM products";

        // Use RowMapper to map each row of the result set to a Product object
        RowMapper<Product> mapper = (rs, rowNum) -> {
                Product product = new Product();
                product.setProductId(rs.getInt("product_id"));
                product.setName(rs.getString("name"));
                product.setBrand(rs.getString("brand"));
                product.setPrice(rs.getInt("price"));
                return product;
        };

        return jdbc.query(fetchQuery, mapper);
    }
}
