package com.hex.car.service;

import com.hex.car.domain.Brand;
import com.hex.car.domain.Model;
import com.hex.car.domain.Product;
import com.hex.car.domain.Shop;
import com.hex.car.repository.MySpec;
import com.hex.car.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

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

    @Override
    public List<Product> findProductListByCreateTimeAndNameAndIdentity(Date beginTime, Date endTime, String name, Shop shop) {
        Sort sort = new Sort(Sort.Direction.DESC, "createTime");
        return productRepository.findAll(MySpec.findProductsByCreateTimeAndNameAndIdentity(beginTime, endTime, name, shop), sort);
    }

    @Override
    public List<Product> findTop10ProductsByNameLikeAndStateOrderByName(String name, Integer state) {
        return productRepository.findTop10ProductsByNameLikeAndStateOrderByName("%" + name + "%", state);
    }

    @Override
    public List<Product> findTop10ProductsByNameLikeAndStateAndShopOrderByName(String name, Integer state, Shop shop) {
        return productRepository.findTop10ProductsByNameLikeAndStateAndShopOrderByName("%" + name + "%", state, shop);
    }

    @Override
    public Page<Product> findProductsByProductBrandModelCarTypeParameterPlaceShop(String name, Double minPrice, Double maxPrice, Integer year, String brandId, String modelId, String carTypeId, String placeId, String engineTypeId, String drivetrainId, String transmissionId, String fuelTypeId, String bodyTypeId, String seatsId, String shopId, PageRequest pageRequest) {
        return productRepository.findAll(MySpec.findProductsByProductBrandModelCarTypeParameterPlaceShop(name, minPrice, maxPrice, year, brandId, modelId, carTypeId, placeId, engineTypeId, drivetrainId, transmissionId, fuelTypeId, bodyTypeId, seatsId, shopId), pageRequest);
    }

    @Override
    public Page<Product> findProducts(Map<String, Object> condition, PageRequest pageRequest) {
        return productRepository.findAll(MySpec.findProducts(condition), pageRequest);
    }

    @Override
    public List<Brand> findDistinctBrandByProduct(Integer state) {
        return productRepository.findDistinctBrandByProduct(state);
    }

    @Override
    public List<Model> findDistinctModelByProduct(Integer state) {
        return productRepository.findDistinctModelByProduct(state);
    }

    @Override
    public List<Product> findProductsByIdIn(String[] ids) {
        return productRepository.findProductsByIdIn(ids);
    }

    @Override
    public List<Product> findProductsByStateOrderByName(Integer state) {
        return productRepository.findProductsByStateOrderByName(new Integer(2));
    }
}
