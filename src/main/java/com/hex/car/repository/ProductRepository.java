package com.hex.car.repository;

import com.hex.car.domain.Product;
import com.hex.car.domain.Shop;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * User: hexuan
 * Date: 2017/9/18
 * Time: 下午4:29
 */
public interface ProductRepository extends JpaRepository<Product, String> {
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
}
