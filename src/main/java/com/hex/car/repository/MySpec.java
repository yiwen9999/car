package com.hex.car.repository;

import com.hex.car.domain.Car;
import com.hex.car.domain.Evaluate;
import com.hex.car.domain.Product;
import com.hex.car.domain.Shop;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 复杂查询相关
 * <p>
 * User: hexuan
 * Date: 2017/9/1
 * Time: 上午10:32
 */
public class MySpec {

    public static Specification<Product> findProductsByCreateTimeAndNameAndIdentity(Date beginTime, Date endTime, String name, Shop shop) {
        return new Specification<Product>() {
            @Override
            public Predicate toPredicate(Root<Product> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicate = new ArrayList<>();

                if (!StringUtils.isEmpty(name)) {
                    predicate.add(criteriaBuilder.like(root.get("name").as(String.class), "%" + name + "%"));
                }

                if (null != shop) {
                    predicate.add(criteriaBuilder.equal(root.get("shop").as(Shop.class), shop));
                }

                if (null != beginTime) {
                    predicate.add(criteriaBuilder.greaterThan(root.get("createTime").as(Date.class), beginTime));
                }

                if (null != endTime) {
                    predicate.add(criteriaBuilder.lessThan(root.get("createTime").as(Date.class), endTime));
                }

                Predicate[] pre = new Predicate[predicate.size()];
                return criteriaQuery.where(predicate.toArray(pre)).getRestriction();
            }
        };
    }

    public static Specification<Evaluate> findEvaluatesByCreateTimeAndNameAndIdentity(Date beginTime, Date endTime, String name, Shop shop) {
        return new Specification<Evaluate>() {
            @Override
            public Predicate toPredicate(Root<Evaluate> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicate = new ArrayList<>();

                if (!StringUtils.isEmpty(name)) {
                    predicate.add(criteriaBuilder.like(root.get("name").as(String.class), "%" + name + "%"));
                }

                if (null != shop) {
                    predicate.add(criteriaBuilder.equal(root.get("shop").as(Shop.class), shop));
                }

                if (null != beginTime) {
                    predicate.add(criteriaBuilder.greaterThan(root.get("createTime").as(Date.class), beginTime));
                }

                if (null != endTime) {
                    predicate.add(criteriaBuilder.lessThan(root.get("createTime").as(Date.class), endTime));
                }

                Predicate[] pre = new Predicate[predicate.size()];
                return criteriaQuery.where(predicate.toArray(pre)).getRestriction();
            }
        };
    }

    public static Specification<Product> findProductsByProductBrandModelCarTypeParameterPlaceShop(String name, Double minPrice, Double maxPrice, Integer year,
                                                                                                  String brandId, String modelId, String carTypeId, String placeId,
                                                                                                  String engineTypeId, String drivetrainId, String transmissionId,
                                                                                                  String fuelTypeId, String bodyTypeId, String seatsId,
                                                                                                  String shopId) {
        return new Specification<Product>() {
            @Override
            public Predicate toPredicate(Root<Product> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicate = new ArrayList<>();
                Join<Product, Car> carJoin = root.join("car", JoinType.INNER);
                Join<Product, Shop> shopJoin = root.join("shop", JoinType.INNER);

                if (!StringUtils.isEmpty(name)) {
                    predicate.add(criteriaBuilder.like(root.get("name").as(String.class), "%" + name + "%"));
                }
                if (null != minPrice) {
                    predicate.add(criteriaBuilder.greaterThanOrEqualTo(root.get("price").as(Double.class), minPrice));
                }
                if (null != maxPrice) {
                    predicate.add(criteriaBuilder.lessThanOrEqualTo(root.get("price").as(Double.class), maxPrice));
                }
                if (!StringUtils.isEmpty(year)) {
                    predicate.add(criteriaBuilder.equal(carJoin.get("year").as(String.class), year));
                }
                if (!StringUtils.isEmpty(brandId)) {
                    predicate.add(criteriaBuilder.equal(carJoin.get("brand").get("id").as(String.class), brandId));
                }
                if (!StringUtils.isEmpty(modelId)) {
                    predicate.add(criteriaBuilder.equal(carJoin.get("model").get("id").as(String.class), modelId));
                }
                if (!StringUtils.isEmpty(carTypeId)) {
                    predicate.add(criteriaBuilder.equal(carJoin.get("carType").get("id").as(String.class), carTypeId));
                }
                if (!StringUtils.isEmpty(placeId)) {
                    predicate.add(criteriaBuilder.equal(shopJoin.get("place").get("id").as(String.class), placeId));
                }
                if (!StringUtils.isEmpty(shopId)) {
                    predicate.add(criteriaBuilder.equal(shopJoin.get("id").as(String.class), shopId));
                }
                if (!StringUtils.isEmpty(engineTypeId)) {
                    predicate.add(criteriaBuilder.equal(carJoin.get("engineType").get("id").as(String.class), engineTypeId));
                }
                if (!StringUtils.isEmpty(drivetrainId)) {
                    predicate.add(criteriaBuilder.equal(carJoin.get("drivetrain").get("id").as(String.class), drivetrainId));
                }
                if (!StringUtils.isEmpty(transmissionId)) {
                    predicate.add(criteriaBuilder.equal(carJoin.get("transmission").get("id").as(String.class), transmissionId));
                }
                if (!StringUtils.isEmpty(fuelTypeId)) {
                    predicate.add(criteriaBuilder.equal(carJoin.get("fuelType").get("id").as(String.class), fuelTypeId));
                }
                if (!StringUtils.isEmpty(bodyTypeId)) {
                    predicate.add(criteriaBuilder.equal(carJoin.get("bodyType").get("id").as(String.class), bodyTypeId));
                }
                if (!StringUtils.isEmpty(seatsId)) {
                    predicate.add(criteriaBuilder.equal(carJoin.get("seats").get("id").as(String.class), seatsId));
                }

                predicate.add(criteriaBuilder.equal(root.get("state").as(Integer.class), new Integer(2)));

                Predicate[] pre = new Predicate[predicate.size()];
                criteriaQuery.distinct(true);
                return criteriaQuery.where(predicate.toArray(pre)).getRestriction();
            }
        };
    }

    public static Specification<Evaluate> findEvaluates(Map<String, Object> condition) {
        return new Specification<Evaluate>() {
            @Override
            public Predicate toPredicate(Root<Evaluate> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicate = new ArrayList<>();
                Integer state = (Integer) condition.get("state");
                String shopId = (String) condition.get("shopId");

                if (null != state) {
                    predicate.add(criteriaBuilder.equal(root.get("state").as(Integer.class), state));
                }
                if (!StringUtils.isEmpty(shopId)) {
                    Join<Evaluate, Product> productJoin = root.join("products", JoinType.INNER);
                    predicate.add(criteriaBuilder.equal(productJoin.get("shop").get("id").as(String.class), shopId));
                }

                Predicate[] pre = new Predicate[predicate.size()];
                criteriaQuery.distinct(true);
                return criteriaQuery.where(predicate.toArray(pre)).getRestriction();
            }
        };
    }

    public static Specification<Shop> findShops(Map<String, Object> condition) {
        return new Specification<Shop>() {
            @Override
            public Predicate toPredicate(Root<Shop> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicate = new ArrayList<>();
                Integer state = (Integer) condition.get("state");

                if (null != state) {
                    predicate.add(criteriaBuilder.equal(root.get("state").as(Integer.class), state));
                }

                Predicate[] pre = new Predicate[predicate.size()];
                return criteriaQuery.where(predicate.toArray(pre)).getRestriction();
            }
        };
    }

    public static Specification<Product> findProducts(Map<String, Object> condition) {
        return new Specification<Product>() {
            @Override
            public Predicate toPredicate(Root<Product> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                String name = (String) condition.get("name");
                Double minPrice = (Double) condition.get("minPrice");
                Double maxPrice = (Double) condition.get("maxPrice");
                Integer year = (Integer) condition.get("year");
                String brandId = (String) condition.get("brandId");
                String modelId = (String) condition.get("modelId");
                String carTypeId = (String) condition.get("carTypeId");
                String placeId = (String) condition.get("placeId");
                String engineTypeId = (String) condition.get("engineTypeId");
                String drivetrainId = (String) condition.get("drivetrainId");
                String transmissionId = (String) condition.get("transmissionId");
                String fuelTypeId = (String) condition.get("fuelTypeId");
                String bodyTypeId = (String) condition.get("bodyTypeId");
                String seatsId = (String) condition.get("seatsId");
                String shopId = (String) condition.get("shopId");

                List<Predicate> predicate = new ArrayList<>();
                Join<Product, Car> carJoin = root.join("car", JoinType.INNER);
                Join<Product, Shop> shopJoin = root.join("shop", JoinType.INNER);

                if (!StringUtils.isEmpty(name)) {
                    predicate.add(criteriaBuilder.like(root.get("name").as(String.class), "%" + name + "%"));
                }
                if (null != minPrice) {
                    predicate.add(criteriaBuilder.greaterThanOrEqualTo(root.get("price").as(Double.class), minPrice));
                }
                if (null != maxPrice) {
                    predicate.add(criteriaBuilder.lessThanOrEqualTo(root.get("price").as(Double.class), maxPrice));
                }
                if (!StringUtils.isEmpty(year)) {
                    predicate.add(criteriaBuilder.equal(carJoin.get("year").as(String.class), year));
                }
                if (!StringUtils.isEmpty(brandId)) {
                    predicate.add(criteriaBuilder.equal(carJoin.get("brand").get("id").as(String.class), brandId));
                }
                if (!StringUtils.isEmpty(modelId)) {
                    predicate.add(criteriaBuilder.equal(carJoin.get("model").get("id").as(String.class), modelId));
                }
                if (!StringUtils.isEmpty(carTypeId)) {
                    predicate.add(criteriaBuilder.equal(carJoin.get("carType").get("id").as(String.class), carTypeId));
                }
                if (!StringUtils.isEmpty(placeId)) {
                    predicate.add(criteriaBuilder.equal(shopJoin.get("place").get("id").as(String.class), placeId));
                }
                if (!StringUtils.isEmpty(shopId)) {
                    predicate.add(criteriaBuilder.equal(shopJoin.get("id").as(String.class), shopId));
                }
                if (!StringUtils.isEmpty(engineTypeId)) {
                    predicate.add(criteriaBuilder.equal(carJoin.get("engineType").get("id").as(String.class), engineTypeId));
                }
                if (!StringUtils.isEmpty(drivetrainId)) {
                    predicate.add(criteriaBuilder.equal(carJoin.get("drivetrain").get("id").as(String.class), drivetrainId));
                }
                if (!StringUtils.isEmpty(transmissionId)) {
                    predicate.add(criteriaBuilder.equal(carJoin.get("transmission").get("id").as(String.class), transmissionId));
                }
                if (!StringUtils.isEmpty(fuelTypeId)) {
                    predicate.add(criteriaBuilder.equal(carJoin.get("fuelType").get("id").as(String.class), fuelTypeId));
                }
                if (!StringUtils.isEmpty(bodyTypeId)) {
                    predicate.add(criteriaBuilder.equal(carJoin.get("bodyType").get("id").as(String.class), bodyTypeId));
                }
                if (!StringUtils.isEmpty(seatsId)) {
                    predicate.add(criteriaBuilder.equal(carJoin.get("seats").get("id").as(String.class), seatsId));
                }

                predicate.add(criteriaBuilder.equal(root.get("state").as(Integer.class), new Integer(2)));

                Predicate[] pre = new Predicate[predicate.size()];
                criteriaQuery.distinct(true);
                return criteriaQuery.where(predicate.toArray(pre)).getRestriction();
            }
        };
    }
}
