package com.hex.car.service;

import com.hex.car.domain.BrowsingHistoryProduct;
import com.hex.car.domain.Product;
import com.hex.car.repository.BrowsingHistoryProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * User: hexuan
 * Date: 2017/10/9
 * Time: 上午10:23
 */
@Service
public class BrowsingHistoryProductServiceImpl implements BrowsingHistoryProductService {

    @Autowired
    private BrowsingHistoryProductRepository browsingHistoryProductRepository;

    @Override
    public BrowsingHistoryProduct saveBrowsingHistoryProduct(BrowsingHistoryProduct browsingHistoryProduct) {
        return browsingHistoryProductRepository.save(browsingHistoryProduct);
    }

    @Override
    public void deleteBrowsingHistoryProduct(BrowsingHistoryProduct browsingHistoryProduct) {
        browsingHistoryProductRepository.delete(browsingHistoryProduct);
    }

    @Override
    public BrowsingHistoryProduct findBrowsingHistoryProductById(String id) {
        return browsingHistoryProductRepository.findOne(id);
    }

    @Override
    public List<Product> findProductsByBrowsingHistoryCountDesc() {
        return browsingHistoryProductRepository.findProductsByBrowsingHistoryCountDesc();
    }
}
