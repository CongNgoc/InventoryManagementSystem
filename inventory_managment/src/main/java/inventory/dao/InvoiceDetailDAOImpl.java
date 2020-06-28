package inventory.dao;

import inventory.model.InvoiceDetail;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional(rollbackFor = Exception.class)
public class InvoiceDetailDAOImpl extends BaseDAOImpl<InvoiceDetail> implements InvoiceDetailDAO<InvoiceDetail>{
}
