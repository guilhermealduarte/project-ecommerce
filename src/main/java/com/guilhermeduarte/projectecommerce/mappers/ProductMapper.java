package com.guilhermeduarte.projectecommerce.mappers;

import com.guilhermeduarte.projectecommerce.entities.Product;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class ProductMapper implements RowMapper<Product> {
    @Override
    public Product mapRow(ResultSet rs, int rowNum) throws SQLException {

        Product product = new Product();

        product.setId(rs.getLong("id"));
        product.setUuid(UUID.fromString(rs.getString("uuid")));
        product.setName(rs.getString("name"));
        product.setDescription(rs.getString("description"));
        product.setPrice(rs.getDouble("price"));
        product.setImageUrl(rs.getString("image_url"));

        return product;
    }
}
