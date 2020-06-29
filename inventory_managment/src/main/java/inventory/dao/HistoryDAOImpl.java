package inventory.dao;

import inventory.model.History;
import org.hibernate.query.Query;
import org.hibernate.type.StandardBasicTypes;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional(rollbackFor = Exception.class)
public class HistoryDAOImpl extends BaseDAOImpl<History> implements HistoryDAO<History>{
    @Override
    public Short getHistorySEQ() {
        Query query = sessionFactory.getCurrentSession().createNativeQuery("select HISTORY_SEQ.nextval as num from dual").
                addScalar("num", StandardBasicTypes.SHORT);;

        return ((Short) query.uniqueResult()).shortValue();
    }
}
