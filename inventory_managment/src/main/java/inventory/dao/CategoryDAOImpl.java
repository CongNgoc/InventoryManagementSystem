package inventory.dao;

import inventory.model.Category;
import org.hibernate.query.Query;
import org.hibernate.type.StandardBasicTypes;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional(rollbackFor = Exception.class)
public class CategoryDAOImpl extends BaseDAOImpl<Category> implements CategoryDAO<Category>{

    @Override
    public short getCategorySEQ() {
        Query query = sessionFactory.getCurrentSession().createNativeQuery("select CATEGORY_SEQ.nextval as num from dual").
                addScalar("num", StandardBasicTypes.SHORT);;

        return ((Short) query.uniqueResult()).shortValue();
    }
}
