package inventory.dao;

import inventory.model.ProductInfo;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional(rollbackFor = Exception.class)
public class ProductInfoImpl extends BaseDAOImpl<ProductInfo> implements ProductInfoDAO<ProductInfo>{

}
