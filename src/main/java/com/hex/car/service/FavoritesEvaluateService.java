package com.hex.car.service;

import com.hex.car.domain.FavoritesEvaluate;

/**
 * User: hexuan
 * Date: 2017/10/9
 * Time: 上午10:44
 */
public interface FavoritesEvaluateService {
    FavoritesEvaluate saveFavoritesEvaluate(FavoritesEvaluate favoritesEvaluate);

    void deleteFavoritesEvaluate(FavoritesEvaluate favoritesEvaluate);

    FavoritesEvaluate findFavoritesEvaluateById(String id);
}
