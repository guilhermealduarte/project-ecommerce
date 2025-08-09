package com.guilhermeduarte.projectecommerce.repositories;

import com.guilhermeduarte.projectecommerce.entities.Category;
import com.guilhermeduarte.projectecommerce.mappers.ProductMapper;

import com.guilhermeduarte.projectecommerce.entities.Product;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Repository
public class ProductRepository {

	JdbcTemplate jdbcTemplate;

	public ProductRepository(DataSource dataSource) {

		jdbcTemplate = new JdbcTemplate(dataSource);
	}

	public boolean existsById(String uuid){
		boolean result = false;
		int count = this.jdbcTemplate.queryForObject("SELECT COUNT(1) AS id FROM products WHERE uuid = ?", new Object[] { uuid }, Integer.class);

		if (count > 0) {
			result = true;
		}

		return result;
	}

	public List<Product> findAll(){
		return this.jdbcTemplate.query("SELECT id, uuid, name, description, price, image_url FROM products", new ProductMapper());
	}

	public Optional<Product> findByUuid(String uuid){
		return Optional.ofNullable(this.jdbcTemplate.queryForObject("SELECT id, uuid, name, description, price, image_url FROM products WHERE uuid = ?", new Object[]{uuid}, new ProductMapper()));
	}

	public Long create(Product product){
		GeneratedKeyHolder holder = new GeneratedKeyHolder();
		this.jdbcTemplate.update(new PreparedStatementCreator() {
			@Override
			public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
				PreparedStatement statement = con.prepareStatement("INSERT INTO products(uuid, name, description, price, image_url) VALUES(?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
				statement.setString(1, String.valueOf(product.getUuid()));
				statement.setString(2, product.getName());
				statement.setString(3, product.getDescription());
				statement.setString(4, String.valueOf(product.getPrice()));
				statement.setString(5, product.getImageUrl());
				return statement;
			}
		}, holder);

		return Objects.requireNonNull(holder.getKey()).longValue();
	}

	public void update(Product product){
		this.jdbcTemplate.update("UPDATE products SET name = ?, description = ?, price = ?, image_url = ? WHERE uuid = ?", product.getName(), product.getDescription(), product.getPrice(), product.getImageUrl(), product.getUuid());
	}

	public void delete(String uuid){
		this.jdbcTemplate.update("DELETE FROM products WHERE uuid = ?", uuid);
	}
	
//	@Query("SELECT obj FROM Product obj JOIN FETCH obj.categories WHERE UPPER(obj.name) LIKE UPPER(CONCAT('%', :name, '%'))")
//	Page<Product> searchByName(String name, Pageable pageable);
}
