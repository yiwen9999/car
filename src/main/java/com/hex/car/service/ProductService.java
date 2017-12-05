package com.hex.car.service;

import com.hex.car.domain.Brand;
import com.hex.car.domain.Model;
import com.hex.car.domain.Product;
import com.hex.car.domain.Shop;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.Date;
import java.util.List;
import java.util.Map;

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

    List<Product> findTop4ByStateOrderByCreateTimeDesc(Integer state);

    List<Product> findProductListByCreateTimeAndNameAndIdentity(Date beginTime, Date endTime, String name, Shop shop);

    List<Product> findTop10ProductsByNameLikeAndStateOrderByName(String name, Integer state);

    List<Product> findTop10ProductsByNameLikeAndStateAndShopOrderByName(String name, Integer state, Shop shop);

    Page<Product> findProductsByProductBrandModelCarTypeParameterPlaceShop(String name, Double minPrice, Double maxPrice, Integer year, String brandId, String modelId, String carTypeId, String placeId, String engineTypeId, String drivetrainId, String transmissionId, String fuelTypeId, String bodyTypeId, String seatsId, String shopId, PageRequest pageRequest);

    Page<Product> findProducts(Map<String, Object> condition, PageRequest pageRequest);

    List<Brand> findDistinctBrandByProduct(Integer state);

    List<Model> findDistinctModelByProduct(Integer state);

    List<Model> findDistinctModelByProductAndBrandId(Integer state, String brandId);

    List<Product> findProductsByIdIn(String[] ids);

    List<Product> findProductsByStateOrderByName(Integer state);

    Product findFirstByStateOrderByPriceAsc(Integer state);

    Product findFirstByStateOrderByPriceDesc(Integer state);
}
