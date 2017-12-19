package com.hex.car.repository;

import com.hex.car.domain.BrowsingHistoryProduct;
import com.hex.car.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * User: hexuan
 * Date: 2017/9/18
 * Time: 下午4:18
 */
public interface BrowsingHistoryProductRepository extends JpaRepository<BrowsingHistoryProduct, String> {
    @Query("select p from BrowsingHistoryProduct bhp inner join bhp.product p group by p.id order by count (bhp) desc ")
    List<Product> findProductsByBrowsingHistoryCountDesc();
}
