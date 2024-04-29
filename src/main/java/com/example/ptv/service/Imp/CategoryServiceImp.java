package com.example.ptv.service.Imp;

import com.example.ptv.dao.CategoryDao;
import com.example.ptv.entity.Category;
import com.example.ptv.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImp implements CategoryService {
    @Autowired
    private CategoryDao categoryDao;

    @Override
    public List<Category> getAllCategories() {
        return categoryDao.selectList(null);
    }


}
