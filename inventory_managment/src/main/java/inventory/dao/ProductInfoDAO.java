package inventory.dao;
import inventory.model.ProductInfo;

import java.util.List;

public interface ProductInfoDAO<E> extends BaseDAO<E>{
    public List<ProductInfo> getAllProduct();
}
