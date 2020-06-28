package inventory.dao;

import inventory.model.Invoice;
import org.hibernate.query.Query;
import org.hibernate.type.StandardBasicTypes;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional(rollbackFor = Exception.class)
public class InvoiceDAOImpl extends BaseDAOImpl<Invoice> implements InvoiceDAO<Invoice>{

    @Override
    public Short getInvoiceSEQ() {
        Query query = sessionFactory.getCurrentSession().createNativeQuery("select INVOICE_SEQ.nextval as num from dual").
                addScalar("num", StandardBasicTypes.SHORT);;

        return ((Short) query.uniqueResult()).shortValue();
    }
}
