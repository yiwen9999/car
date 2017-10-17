package com.hex.car.service;

import com.hex.car.domain.FavoritesProduct;

/**
 * User: hexuan
 * Date: 2017/10/9
 * Time: 上午10:47
 */
public interface FavoritesProductService {
    FavoritesProduct saveFavoritesProduct(FavoritesProduct favoritesProduct);

    void deleteFavoritesProduct(FavoritesProduct favoritesProduct);

    FavoritesProduct findFavoritesProductById(String id);
}
