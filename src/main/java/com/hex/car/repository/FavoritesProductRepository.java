package com.hex.car.repository;

import com.hex.car.domain.FavoritesProduct;
import com.hex.car.domain.Product;
import com.hex.car.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

/**
 * User: hexuan
 * Date: 2017/9/18
 * Time: 下午4:22
 */
public interface FavoritesProductRepository extends JpaRepository<FavoritesProduct, String>, JpaSpecificationExecutor {
    FavoritesProduct findFirstByUserAndProduct(User user, Product product);

    List<FavoritesProduct> findFavoritesProductsByProduct(Product product);
}
