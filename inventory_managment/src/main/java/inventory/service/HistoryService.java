package inventory.service;

import inventory.dao.HistoryDAO;
import inventory.model.History;
import inventory.model.InvoiceDetail;
import inventory.model.Paging;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class HistoryService {
    private Logger logger = Logger.getLogger(HistoryService.class);
    @Autowired
    private HistoryDAO<History> historyDAO;
    private Date sys_date = new Date();

    public List<History> getAllHistoty(History history, Paging paging) {
        StringBuilder queryStr = new StringBuilder();
        Map<String, Object> mapParams = new HashMap<>();
        if(history!=null) {
            if(history.getProductInfo()!=null) {
                if(!StringUtils.isEmpty(history.getProductInfo().getCategory().getName()) ) {
                    queryStr.append(" and model.productInfo.category.name like :cateName");
                    mapParams.put("cateName","%"+history.getProductInfo().getCategory().getName()+"%");
                }
                if(!StringUtils.isEmpty(history.getProductInfo().getCode())) {
                    queryStr.append(" and model.productInfo.code like :code");
                    mapParams.put("code", "%"+history.getProductInfo().getCode()+"%");
                }
                if( !StringUtils.isEmpty(history.getProductInfo().getName()) ) {
                    queryStr.append(" and model.productInfo.name like :name");
                    mapParams.put("name", "%"+history.getProductInfo().getName()+"%");
                }
            }
            if( !StringUtils.isEmpty(history.getActionName()) ) {
                queryStr.append(" and model.actionName like :actionName");
                mapParams.put("actionName", "%"+history.getActionName()+"%");
            }
            if(history.isType()) {
                queryStr.append(" and model.type = :type");
                mapParams.put("type", history.isType());
            }
        }
        return historyDAO.findAll(queryStr.toString(), mapParams,paging);
    }

    public void save(InvoiceDetail invoiceDetail, String action, boolean type) {
        History history = new History();
        history.setProductInfo(invoiceDetail.getProductInfo());
        history.setProductId(invoiceDetail.getProductId());
        history.setActionName(action);
        history.setPrice(invoiceDetail.getProductInfo().getPrice());
        history.setType(type);
        history.setQty(invoiceDetail.getQuanity());
        history.setCreateDate(sys_date);
        history.setUpdateDate(sys_date);
        historyDAO.save(history);
    }
}
