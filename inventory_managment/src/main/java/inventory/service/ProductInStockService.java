package inventory.service;

import inventory.dao.ProductInStockDAO;
import inventory.dao.ProductInfoDAO;
import inventory.model.Paging;
import inventory.model.ProductInStock;
import inventory.model.ProductInfo;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
public class ProductInStockService {
    private Logger logger = Logger.getLogger(ProductInStockService.class);
    @Autowired
    ProductInStockDAO<ProductInStock> productInStockDAO;
    @Autowired
    ProductInfoService productInfoService;

    public List<ProductInStock> getAllProductInStock(ProductInStock productInStock, Paging paging) {
        logger.info("get all product in stock!");
        StringBuilder queryStr = new StringBuilder();
        Map<String, Object> mapParams = new HashMap<>();
        //productInStock == null => productInfo is null
        ProductInfo productInfo = productInfoService.findProductInfoById(productInStock.getProductId());
        productInStock.setProductInfo(productInfo);

        if(productInStock!=null && productInStock.getProductInfo()!=null) {
            if(!StringUtils.isEmpty(productInStock.getProductInfo().getCategory().getName())) {
                queryStr.append(" and model.productInfo.category.name like :code");
                mapParams.put("code","%"+productInStock.getProductInfo().getCategory().getName()+"%");
            }
            if(productInStock.getProductInfo().getCode()!=null && !StringUtils.isEmpty(productInStock.getProductInfo().getCode())) {
                queryStr.append(" and model.productInfo.code like :code");
                mapParams.put("code","%"+ productInStock.getProductInfo().getCode()+"%");
            }
            if(productInStock.getProductInfo().getName()!=null && !StringUtils.isEmpty(productInStock.getProductInfo().getName()) ) {
                queryStr.append(" and model.productInfo.name like :code");
                mapParams.put("code", "%"+productInStock.getProductInfo().getName()+"%");
            }
        }
        return productInStockDAO.findAll(queryStr.toString(), mapParams,paging);

    }
    


}
