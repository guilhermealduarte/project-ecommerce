package com.guilhermeduarte.projectecommerce.entities;

import jakarta.persistence.Table;

@Table(name = "product_category")
public class ProductCategory {

    private Long idProduct;

    private Long idCategory;

    public Long getIdProduct() {
        return idProduct;
    }

    public void setIdProduct(Long idProduct) {
        this.idProduct = idProduct;
    }

    public Long getIdCategory() {
        return idCategory;
    }

    public void setIdCategory(Long idCategory) {
        this.idCategory = idCategory;
    }
}
