package com.hex.car.repository;

import com.hex.car.domain.Evaluate;
import com.hex.car.domain.Shop;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * User: hexuan
 * Date: 2017/9/18
 * Time: 下午4:21
 */
public interface EvaluateRepository extends JpaRepository<Evaluate, String> {
    /**
     * 根据4s店获取文章集合
     *
     * @return
     */
    List<Evaluate> findEvaluatesByProductShop(Shop shop);
}
