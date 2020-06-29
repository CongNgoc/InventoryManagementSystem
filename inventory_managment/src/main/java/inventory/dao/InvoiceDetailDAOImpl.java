package inventory.dao;

import inventory.model.InvoiceDetail;
import org.hibernate.query.Query;
import org.hibernate.type.StandardBasicTypes;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional(rollbackFor = Exception.class)
public class InvoiceDetailDAOImpl extends BaseDAOImpl<InvoiceDetail> implements InvoiceDetailDAO<InvoiceDetail>{

    @Override
    public Short getInvoiceDetailSEQ() {
        Query query = sessionFactory.getCurrentSession().createNativeQuery("select IN_DE_SEQ.nextval as num from dual").
                addScalar("num", StandardBasicTypes.SHORT);;

        return ((Short) query.uniqueResult()).shortValue();
    }
}
