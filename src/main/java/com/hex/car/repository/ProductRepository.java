package com.hex.car.repository;

import com.hex.car.domain.Brand;
import com.hex.car.domain.Model;
import com.hex.car.domain.Product;
import com.hex.car.domain.Shop;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * User: hexuan
 * Date: 2017/9/18
 * Time: 下午4:29
 */
public interface ProductRepository extends JpaRepository<Product, String>, JpaSpecificationExecutor {
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

    List<Product> findTop10ProductsByNameLikeAndStateOrderByName(String name, Integer state);

    List<Product> findTop10ProductsByNameLikeAndStateAndShopOrderByName(String name, Integer state, Shop shop);

    List<Product> findTop4ByStateOrderByCreateTimeDesc(Integer state);

    @Query("select distinct car.brand from Product p inner join p.car car where p.state = :state")
    List<Brand> findDistinctBrandByProduct(@Param("state")Integer state);

    @Query("select distinct car.model from Product p inner join p.car car where p.state = :state")
    List<Model> findDistinctModelByProduct(@Param("state")Integer state);
}
