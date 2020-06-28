package inventory.dao;

import inventory.model.ProductInfo;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional(rollbackFor = Exception.class)
public class ProductInfoImpl extends BaseDAOImpl<ProductInfo> implements ProductInfoDAO<ProductInfo>{
    @Override
    public List<ProductInfo> getAllProduct() {
        StringBuilder queryString = new StringBuilder("");
        queryString.append(" from ProductInfo as model where model.activeFlag=1");
        log.info( "Query find all ====>" +queryString.toString());
        return sessionFactory.getCurrentSession().createQuery(queryString.toString()).list();
    }
}
