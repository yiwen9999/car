package com.hex.car.repository;

import com.hex.car.domain.FavoritesProduct;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * User: hexuan
 * Date: 2017/9/18
 * Time: 下午4:22
 */
public interface FavoritesProductRepository extends JpaRepository<FavoritesProduct,String> {
}
