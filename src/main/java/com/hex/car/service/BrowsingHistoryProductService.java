package com.hex.car.service;

import com.hex.car.domain.BrowsingHistoryProduct;
import com.hex.car.domain.Product;

import java.util.List;

/**
 * User: hexuan
 * Date: 2017/10/9
 * Time: 上午10:11
 */
public interface BrowsingHistoryProductService {
    BrowsingHistoryProduct saveBrowsingHistoryProduct(BrowsingHistoryProduct browsingHistoryProduct);

    void deleteBrowsingHistoryProduct(BrowsingHistoryProduct browsingHistoryProduct);

    BrowsingHistoryProduct findBrowsingHistoryProductById(String id);

    List<Product> findProductsByBrowsingHistoryCountDesc();

    void deleteBrowsingHistoryProductByProduct(Product product);
}
