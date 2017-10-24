package com.hex.car.service;

import com.hex.car.domain.Product;

import java.util.List;

/**
 * User: hexuan
 * Date: 2017/8/24
 * Time: 上午11:22
 */
public interface ProductService {
    Product saveProduct(Product product);

    void deleteProduct(Product product);

    Product findProductById(String id);

    List<Product> findAllProduct();
}
