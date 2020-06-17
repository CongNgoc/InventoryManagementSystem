package inventory.dao;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import inventory.model.Paging;
import org.apache.log4j.Logger;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
@Repository
@Transactional(rollbackFor =Exception.class)
public class BaseDAOImpl<E> implements BaseDAO<E>{
    final static Logger log = Logger.getLogger(BaseDAOImpl.class);
    @Autowired
    SessionFactory sessionFactory;
    public List<E> findAll(String queryStr, Map<String, Object> mapParams, Paging paging) {
        log.info("find all record from db");
        StringBuilder queryString = new StringBuilder("");
        StringBuilder countQuery = new StringBuilder("");
        queryString.append(" from ").append(getGenericName()).append(" as model where model.activeFlag=1");
        countQuery.append(" SELECT COUNT(1) FROM ").append(getGenericName()).append(" as model where model.activeFlag=1");

        if(queryStr != null && !queryStr.isEmpty()) {
            //append queryStr from search form
            queryString.append(queryStr);
            countQuery.append(queryStr);
        }
//        queryString.append() append order by here
        Query<E> query = sessionFactory.getCurrentSession().createQuery(queryString.toString());
        Query<E> countQ = sessionFactory.getCurrentSession().createQuery(countQuery.toString());

        //Map value code & name of Category to Query
        if(mapParams != null && !mapParams.isEmpty()) {
            for(String key : mapParams.keySet()) {
                query.setParameter(key, mapParams.get(key));
                countQ.setParameter(key, mapParams.get(key));
            }
        }

        if(paging != null) {
            query.setFirstResult(paging.getOffset());
            query.setMaxResults(paging.getRecordPerPage());
            Long totalRecords = (Long) countQ.uniqueResult();
            paging.setTotalRows(totalRecords);
        }
        log.info("====================RUN AT HERE!");
        log.info( "Query find all ====>" +queryString.toString());
        return query.list();
    }

    public E findById(Class<E> e, Serializable id) {
        log.info("Find by ID " + id.getClass());
        return sessionFactory.getCurrentSession().get(e, id);
    }

    public List<E> findByProperty(String property, Object value) {
        log.info("Find by property");
        StringBuilder queryString = new StringBuilder();
        queryString.append(" from ").append(getGenericName()).append(" as model where model.activeFlag=1 and model.").append(property).append("=:").append(property);
        log.info(" query find by property ===>"+queryString.toString());
        Query<E> query = sessionFactory.getCurrentSession().createQuery(queryString.toString());
        query.setParameter(property, value);
        return query.getResultList();

    }

    public void save(E instance) {
        log.info(" save instance");
        sessionFactory.getCurrentSession().persist(instance);
    }

    public void update(E instance) {
        log.info("update");
        sessionFactory.getCurrentSession().merge(instance);
    }
    //
    public String getGenericName() {
        String s = getClass().getGenericSuperclass().toString();
        Pattern pattern = Pattern.compile("\\<(.*?)\\>");
        Matcher m = pattern.matcher(s);
        String generic="null";
        if(m.find()) {
            generic = m.group(1);
        }
        return generic;
    }

}
