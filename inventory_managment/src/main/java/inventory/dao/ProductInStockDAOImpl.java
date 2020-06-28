package inventory.dao;

import inventory.model.Paging;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import inventory.model.Category;
import inventory.model.ProductInStock;

import java.util.List;
import java.util.Map;

@Repository
@Transactional(rollbackFor=Exception.class)
public class ProductInStockDAOImpl extends BaseDAOImpl<ProductInStock> implements ProductInStockDAO<ProductInStock>{

}
