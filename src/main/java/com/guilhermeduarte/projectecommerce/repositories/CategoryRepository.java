package com.guilhermeduarte.projectecommerce.repositories;

import com.guilhermeduarte.projectecommerce.entities.Category;
import com.guilhermeduarte.projectecommerce.mappers.CategoryMapper;
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
public class CategoryRepository {

    JdbcTemplate jdbcTemplate;

    public CategoryRepository(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public boolean existsById(String uuid){
        boolean result = false;
        int count = this.jdbcTemplate.queryForObject("SELECT COUNT(1) AS id FROM categories WHERE uuid = ?", new Object[] { uuid }, Integer.class);

        if (count > 0) {
            result = true;
        }

        return result;
    }

    public List<Category> findAll(){
        return this.jdbcTemplate.query("SELECT id, uuid, name FROM categories", new CategoryMapper());
    }

    public Long findIdByUuid(String uuid){
        return this.jdbcTemplate.queryForObject("SELECT id FROM categories WHERE uuid = ?", new Object[]{uuid}, Long.class);
    }

    public Optional<Category> findByUuid(String uuid){
        return Optional.ofNullable(this.jdbcTemplate.queryForObject("SELECT id, uuid, name FROM categories WHERE uuid = ?", new Object[]{uuid}, new CategoryMapper()));
    }

    public Long create(Category category){
        GeneratedKeyHolder holder = new GeneratedKeyHolder();
        this.jdbcTemplate.update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                PreparedStatement statement = con.prepareStatement("INSERT INTO categories(uuid, name) VALUES(?,?)", Statement.RETURN_GENERATED_KEYS);
                statement.setString(1, String.valueOf(category.getUuid()));
                statement.setString(2, category.getName());
                return statement;
            }
        }, holder);

        return Objects.requireNonNull(holder.getKey()).longValue();
    }

    public void update(Category category){
        this.jdbcTemplate.update("UPDATE categories SET name = ? WHERE uuid = ?", category.getName(), category.getUuid());
    }

    public void delete(String uuid){
        this.jdbcTemplate.update("DELETE FROM categories WHERE uuid = ?", uuid);
    }

//
//    private final JdbcClient jdbcClient;
//
//    public CategoryRepository(JdbcClient jdbcClient) {
//        this.jdbcClient = jdbcClient;
//    }
//
//    public Integer existsById(Long id){
//        return this.jdbcClient.sql("SELECT COUNT(1) AS id FROM categories WHERE id = :id").param("id", id).query(Integer.class).single();
//    }
//
//    public List<Category> findAll(){
//        return this.jdbcClient.sql("SELECT id, name FROM categories").query(Category.class).list();
//    }
//
//    public Optional<Category> findById(Long id){
//        return jdbcClient.sql("SELECT id, name FROM categories WHERE id = :id").param("id", id).query(Category.class).optional();
//    }
//
//    public void create(Category category){
//        jdbcClient.sql("INSERT INTO categories(uuid, name) VALUES(:uuid, :name)").param("uuid", UUID.randomUUID()).param("name", category.getName()).update();
//    }
//
//    public void update(Category category){
//        jdbcClient.sql("UPDATE categories SET name = :name WHERE id = :id").param("name", category.getName()).param("id", category.getId()).update();
//    }
//
//    public Integer delete(Long id){
//        return jdbcClient.sql("DELETE FROM categories WHERE id = :id").param("id", id).update();
//    }
}
