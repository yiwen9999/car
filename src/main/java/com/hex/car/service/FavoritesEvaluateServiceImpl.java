package com.hex.car.service;

import com.hex.car.domain.FavoritesEvaluate;
import com.hex.car.repository.FavoritesEvaluateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * User: hexuan
 * Date: 2017/10/9
 * Time: 上午10:45
 */
@Service
public class FavoritesEvaluateServiceImpl implements FavoritesEvaluateService {

    @Autowired
    private FavoritesEvaluateRepository favoritesEvaluateRepository;

    @Override
    public FavoritesEvaluate saveFavoritesEvaluate(FavoritesEvaluate favoritesEvaluate) {
        return favoritesEvaluateRepository.save(favoritesEvaluate);
    }

    @Override
    public void deleteFavoritesEvaluate(FavoritesEvaluate favoritesEvaluate) {
        favoritesEvaluateRepository.delete(favoritesEvaluate);
    }

    @Override
    public FavoritesEvaluate findFavoritesEvaluateById(String id) {
        return favoritesEvaluateRepository.findOne(id);
    }
}
