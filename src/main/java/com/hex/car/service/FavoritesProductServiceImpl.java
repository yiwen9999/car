package com.hex.car.service;

import com.hex.car.domain.FavoritesProduct;
import com.hex.car.domain.Product;
import com.hex.car.domain.User;
import com.hex.car.repository.FavoritesProductRepository;
import com.hex.car.repository.MySpec;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * User: hexuan
 * Date: 2017/10/9
 * Time: 下午3:21
 */
@Service
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

    @Override
    public Page<FavoritesProduct> findFavoritesProducts(Map<String, Object> condition, PageRequest pageRequest) {
        return favoritesProductRepository.findAll(MySpec.findFavoritesProducts(condition), pageRequest);
    }

    @Override
    public FavoritesProduct findFirstByUserAndProduct(User user, Product product) {
        return favoritesProductRepository.findFirstByUserAndProduct(user, product);
    }
}
