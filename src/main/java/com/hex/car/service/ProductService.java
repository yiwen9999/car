package com.hex.car.service;

import com.hex.car.domain.Product;
import com.hex.car.domain.Shop;

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

    /**
     * 根据名称，状态，查询商品集合，按名称排序
     *
     * @param name  商品名称
     * @param state 商品状态
     * @return
     */
    List<Product> findProductsByNameLikeAndStateOrderByName(String name, Integer state);

    /**
     * 根据名称，状态，4s店查询商品集合，按名称排序
     *
     * @param name  商品名称
     * @param state 商品状态
     * @param shop  4s店
     * @return
     */
    List<Product> findProductsByNameLikeAndStateAndShopOrderByName(String name, Integer state, Shop shop);
}
