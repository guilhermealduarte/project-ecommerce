package com.guilhermeduarte.projectecommerce.mappers;

import com.guilhermeduarte.projectecommerce.entities.Category;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class CategoryMapper implements RowMapper<Category> {
    @Override
    public Category mapRow(ResultSet rs, int rowNum) throws SQLException {

        Category category = new Category();

        category.setId(rs.getLong("id"));
        category.setUuid(UUID.fromString(rs.getString("uuid")));
        category.setName(rs.getString("name"));

        return category;
    }
}
