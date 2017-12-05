package com.hex.car.repository;

import com.hex.car.domain.*;
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
                Double minDisplacement = (Double) condition.get("minDisplacement");
                Double maxDisplacement = (Double) condition.get("maxDisplacement");
                String[] brandIds = (String[]) condition.get("brandIds");
                String[] modelIds = (String[]) condition.get("modelIds");
                String[] carTypeIds = (String[]) condition.get("carTypeIds");
                String[] placeIds = (String[]) condition.get("placeIds");
                String[] engineTypeIds = (String[]) condition.get("engineTypeIds");
                String[] drivetrainIds = (String[]) condition.get("drivetrainIds");
                String[] transmissionIds = (String[]) condition.get("transmissionIds");
                String[] fuelTypeIds = (String[]) condition.get("fuelTypeIds");
                String[] bodyTypeIds = (String[]) condition.get("bodyTypeIds");
                String[] seatsIds = (String[]) condition.get("seatsIds");
                String[] shopIds = (String[]) condition.get("shopIds");
                Integer[] years = (Integer[]) condition.get("years");

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
                if (null != year) {
                    predicate.add(criteriaBuilder.equal(carJoin.get("year").as(Integer.class), year));
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
                if (null != minDisplacement) {
                    predicate.add(criteriaBuilder.greaterThanOrEqualTo(carJoin.get("displacement").as(Double.class), minDisplacement));
                }
                if (null != maxDisplacement) {
                    predicate.add(criteriaBuilder.lessThanOrEqualTo(carJoin.get("displacement").as(Double.class), maxDisplacement));
                }
                // TODO 下面方法还需要调，in的方法该如何写，还需要去查。
                if (null != brandIds && brandIds.length > 0) {
                    CriteriaBuilder.In<String> brandIn = criteriaBuilder.in(carJoin.get("brand").get("id").as(String.class));
                    for (String id : brandIds) {
                        brandIn.value(id);
                    }
                    predicate.add(brandIn);
                }
                if (null != modelIds && modelIds.length > 0) {
                    CriteriaBuilder.In<String> modelIn = criteriaBuilder.in(carJoin.get("model").get("id").as(String.class));
                    for (String id : modelIds) {
                        modelIn.value(id);
                    }
                    predicate.add(modelIn);
                }
                if (null != carTypeIds && carTypeIds.length > 0) {
                    CriteriaBuilder.In<String> carTypeIn = criteriaBuilder.in(carJoin.get("carType").get("id").as(String.class));
                    for (String id : carTypeIds) {
                        carTypeIn.value(id);
                    }
                    predicate.add(carTypeIn);
                }
                if (null != placeIds && placeIds.length > 0) {
                    CriteriaBuilder.In<String> placeIn = criteriaBuilder.in(shopJoin.get("place").get("id").as(String.class));
                    for (String id : placeIds) {
                        placeIn.value(id);
                    }
                    predicate.add(placeIn);
                }
                if (null != shopIds && shopIds.length > 0) {
                    CriteriaBuilder.In<String> shopIn = criteriaBuilder.in(shopJoin.get("id").as(String.class));
                    for (String id : shopIds) {
                        shopIn.value(id);
                    }
                    predicate.add(shopIn);
                }
                if (null != engineTypeIds && engineTypeIds.length > 0) {
                    CriteriaBuilder.In<String> engineTypeIn = criteriaBuilder.in(carJoin.get("engineType").get("id").as(String.class));
                    for (String id : engineTypeIds) {
                        engineTypeIn.value(id);
                    }
                    predicate.add(engineTypeIn);
                }
                if (null != drivetrainIds && drivetrainIds.length > 0) {
                    CriteriaBuilder.In<String> drivetrainIn = criteriaBuilder.in(carJoin.get("drivetrain").get("id").as(String.class));
                    for (String id : drivetrainIds) {
                        drivetrainIn.value(id);
                    }
                    predicate.add(drivetrainIn);
                }
                if (null != transmissionIds && transmissionIds.length > 0) {
                    CriteriaBuilder.In<String> transmissionIn = criteriaBuilder.in(carJoin.get("transmission").get("id").as(String.class));
                    for (String id : transmissionIds) {
                        transmissionIn.value(id);
                    }
                    predicate.add(transmissionIn);
                }
                if (null != fuelTypeIds && fuelTypeIds.length > 0) {
                    CriteriaBuilder.In<String> fuelTypeIn = criteriaBuilder.in(carJoin.get("fuelType").get("id").as(String.class));
                    for (String id : fuelTypeIds) {
                        fuelTypeIn.value(id);
                    }
                    predicate.add(fuelTypeIn);
                }
                if (null != bodyTypeIds && bodyTypeIds.length > 0) {
                    CriteriaBuilder.In<String> bodyTypeIn = criteriaBuilder.in(carJoin.get("bodyType").get("id").as(String.class));
                    for (String id : bodyTypeIds) {
                        bodyTypeIn.value(id);
                    }
                    predicate.add(bodyTypeIn);
                }
                if (null != seatsIds && seatsIds.length > 0) {
                    CriteriaBuilder.In<String> seatsIn = criteriaBuilder.in(carJoin.get("seats").get("id").as(String.class));
                    for (String id : seatsIds) {
                        seatsIn.value(id);
                    }
                    predicate.add(seatsIn);
                }
                if (null != years && years.length > 0) {
                    CriteriaBuilder.In<Integer> yearsIn = criteriaBuilder.in(carJoin.get("year").as(Integer.class));
                    for (Integer yearSingle : years) {
                        yearsIn.value(yearSingle);
                    }
                    predicate.add(yearsIn);
                }

                /**
                 * 查询所售车辆状态为【启用】且 4s店状态为【启用】的车辆
                 */
                predicate.add(criteriaBuilder.equal(root.get("state").as(Integer.class), new Integer(2)));
                predicate.add(criteriaBuilder.equal(shopJoin.get("state").as(Integer.class), new Integer(2)));

                Predicate[] pre = new Predicate[predicate.size()];
                criteriaQuery.distinct(true);
                return criteriaQuery.where(predicate.toArray(pre)).getRestriction();
            }
        };
    }

    public static Specification<FavoritesProduct> findFavoritesProducts(Map<String, Object> condition) {
        return new Specification<FavoritesProduct>() {
            @Override
            public Predicate toPredicate(Root<FavoritesProduct> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicate = new ArrayList<>();
                String userId = (String) condition.get("userId");

                if (!StringUtils.isEmpty(userId)) {
                    predicate.add(criteriaBuilder.equal(root.get("user").get("id").as(String.class), userId));
                }
                Predicate[] pre = new Predicate[predicate.size()];
                return criteriaQuery.where(predicate.toArray(pre)).getRestriction();
            }
        };
    }

}
