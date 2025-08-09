package com.guilhermeduarte.projectecommerce.repositories;

import com.guilhermeduarte.projectecommerce.entities.ProductCategory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;

@Repository
public class ProductCategoryRepository {
    JdbcTemplate jdbcTemplate;

    public ProductCategoryRepository(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public Integer create(Long idProduto, Long idCategory){
        return this.jdbcTemplate.update("INSERT INTO product_category(product_id, category_id) VALUES(?,?)", String.valueOf(idProduto), String.valueOf(idCategory));
    }
}
