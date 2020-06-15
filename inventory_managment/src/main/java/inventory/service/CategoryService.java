package inventory.service;

import inventory.dao.CategoryDAO;
import inventory.model.Category;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Time;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CategoryService {
    private static final Logger log = Logger.getLogger(CategoryService.class);
    @Autowired
    private CategoryDAO<Category> categoryDAO;
    private Date utilDate = new Date();

    public void saveCategory(Category category) {
        log.info("Insert category" + category.toString());
        category.setActiveFlag(true);
        category.setCreateDate(new Time(utilDate.getTime()));
        category.setUpdateDate(new Time(utilDate.getTime()));
        category.setCategoryId(getCategorySEQ());
        categoryDAO.save(category);
    }
    public void updateCategory(Category category) {
        log.info("Update category" + category.toString());
        category.setUpdateDate(new Time(utilDate.getTime()));
        categoryDAO.update(category);
    }
    public void deleteCategory(Category category) {
        category.setActiveFlag(false);
        category.setUpdateDate((new Time(utilDate.getTime())));
        log.info("Delete category" + category.toString());
        categoryDAO.update(category);
    }
    public List<Category> findAll(String queryStr, Map<String, Object> mapParams) {
        log.info("Find all category start!");
        return categoryDAO.findAll(queryStr, mapParams);
    }
    public List<Category> findCategoryByProperty(String property, Object value) {
        log.info("=====Find by property category start====");
        log.info("property= "+property +" value"+ value.toString());
        return categoryDAO.findByProperty(property, value);
    }
    public Category findByIdCategory(short id) {
        log.info("find category by id ="+id);
        return categoryDAO.findById(Category.class, id);
    }

    public Short getCategorySEQ() {
        log.info("Get category sequence!");
        return categoryDAO.getCategorySEQ();
    }

    public List<Category> getAllCategory(Category category) {
        log.info("Get all category");
        StringBuilder queryString = new StringBuilder();
        Map<String, Object> mapParams = new HashMap<>();
        if(category != null) {
            if(category.getCode() != null) {
                queryString.append(" AND ( model.code = :code");
                mapParams.put("code", category.getCode());
                queryString.append(" OR model.name = :name )");
                mapParams.put("name", category.getCode());
            }
        }
        return categoryDAO.findAll(queryString.toString(), mapParams);
    }
}
