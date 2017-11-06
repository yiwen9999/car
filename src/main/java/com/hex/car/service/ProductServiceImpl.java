package com.hex.car.service;

import com.hex.car.domain.Product;
import com.hex.car.domain.Shop;
import com.hex.car.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * User: hexuan
 * Date: 2017/8/24
 * Time: 上午11:36
 */
@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Override
    public Product saveProduct(Product product) {
        return productRepository.save(product);
    }

    @Override
    public void deleteProduct(Product product) {
        productRepository.delete(product);
    }

    @Override
    public Product findProductById(String id) {
        return productRepository.findOne(id);
    }

    @Override
    public List<Product> findAllProduct() {
        return productRepository.findAll();
    }

    @Override
    public List<Product> findProductsByNameLikeAndStateOrderByName(String name, Integer state) {
        return productRepository.findProductsByNameLikeAndStateOrderByName(name, state);
    }

    @Override
    public List<Product> findProductsByNameLikeAndStateAndShopOrderByName(String name, Integer state, Shop shop) {
        return productRepository.findProductsByNameLikeAndStateAndShopOrderByName(name, state, shop);
    }

    @Override
    public List<Product> findTop4ByStateOrderByCreateTimeDesc(Integer state) {
        return productRepository.findTop4ByStateOrderByCreateTimeDesc(state);
    }
}
