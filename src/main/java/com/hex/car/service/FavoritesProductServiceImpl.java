package com.hex.car.service;

import com.hex.car.domain.FavoritesProduct;
import com.hex.car.repository.FavoritesProductRepository;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * User: hexuan
 * Date: 2017/10/9
 * Time: 下午3:21
 */
public class FavoritesProductServiceImpl implements FavoritesProductService {

    @Autowired
    private FavoritesProductRepository favoritesProductRepository;

    @Override
    public FavoritesProduct saveFavoritesProduct(FavoritesProduct favoritesProduct) {
        return favoritesProductRepository.save(favoritesProduct);
    }

    @Override
    public void deleteFavoritesProduct(FavoritesProduct favoritesProduct) {
        favoritesProductRepository.delete(favoritesProduct);
    }

    @Override
    public FavoritesProduct findFavoritesProductById(String id) {
        return favoritesProductRepository.findOne(id);
    }
}
