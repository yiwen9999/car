package com.hex.car.service;

import com.hex.car.domain.FavoritesProduct;
import com.hex.car.domain.Product;
import com.hex.car.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.Map;

/**
 * User: hexuan
 * Date: 2017/10/9
 * Time: 上午10:47
 */
public interface FavoritesProductService {
    FavoritesProduct saveFavoritesProduct(FavoritesProduct favoritesProduct);

    void deleteFavoritesProduct(FavoritesProduct favoritesProduct);

    FavoritesProduct findFavoritesProductById(String id);

    Page<FavoritesProduct> findFavoritesProducts(Map<String, Object> condition, PageRequest pageRequest);

    FavoritesProduct findFirstByUserAndProduct(User user, Product product);
}
