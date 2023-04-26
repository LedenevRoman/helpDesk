package com.training.rledenev.dao.h2;

import com.training.rledenev.dao.CategoryDao;
import com.training.rledenev.model.Category;
import org.springframework.stereotype.Repository;

@Repository
public class CategoryDaoH2 extends CrudDaoH2<Category> implements CategoryDao {

    public CategoryDaoH2() {
        setClazz(Category.class);
    }
}
