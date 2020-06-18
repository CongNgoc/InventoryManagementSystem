package inventory.service;

import inventory.dao.ProductInfoDAO;
import inventory.model.Category;
import inventory.model.Paging;
import inventory.model.ProductInfo;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Date;
import java.util.Map;

@Service
public class ProductInfoService {
    private static final Logger log = Logger.getLogger(ProductInfo.class);
    private Date sysDate = new Date();
    @Autowired
    private ProductInfoDAO<ProductInfo> productInfoDAO;

    public void saveProductInfo(ProductInfo productInfo) throws Exception{
        log.info("Save Product Info!" + productInfo.toString());
        productInfo.setCreateDate(sysDate);
        productInfo.setUpdateDate(sysDate);
        productInfo.setImgUrl("/upload/" + productInfo.getMultipartFile().getOriginalFilename());
        log.info("===GetOriginalFilename" + productInfo.getMultipartFile().getOriginalFilename());
        productInfoDAO.save(productInfo);
    }
    public void updateProductInfo(ProductInfo productInfo) throws Exception{
        log.info("Update Product info!" + productInfo.toString());
        productInfo.setUpdateDate(sysDate);
        productInfoDAO.update(productInfo);
    }
    public void deleteProductInfo(ProductInfo productInfo) throws Exception {
        log.info("Delete Product Info!" + productInfo.toString());
        productInfo.setActiveFlag(false);
        log.info("=======productInfo.getActiveFlag!" + productInfo.isActiveFlag());
        productInfo.setUpdateDate(sysDate);
        productInfoDAO.update(productInfo);
    }
    public List<ProductInfo> findByProperty(String property, Object value) {
        log.info("Find ProductInfo by Property!");
        log.info("property ="+property +" value"+ value.toString());
        return productInfoDAO.findByProperty(property, value);
    }
    public ProductInfo findProductInfoById(short id) {
        log.info("Find Product Info by id " + id);
        return productInfoDAO.findById(ProductInfo.class, id);
    }

    //Get all ProductInfo and Paging list ProductInfo
    public List<ProductInfo> getAllProductInfo(ProductInfo productInfo, Paging paging) {
        log.info("Get all Product Info" + productInfo.getCode());
        StringBuilder queryString = new StringBuilder();
        Map<String, Object> mapParams = new HashMap<>();
        if(productInfo != null) {
            if(productInfo.getCode() != null) {
                queryString.append(" AND ( model.code LIKE concat('%',:code,'%')");
                mapParams.put("code", productInfo.getCode());
                queryString.append(" OR model.name LIKE concat('%',:name,'%') )");
                mapParams.put("name", productInfo.getCode());
            }
        }
        return productInfoDAO.findAll(queryString.toString(), mapParams, paging);
    }
}
