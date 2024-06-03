package com.example.ptv.service;

import com.example.ptv.dao.CategoryDao;
import com.example.ptv.entity.Category;
import com.example.ptv.service.CategoryService;
import com.example.ptv.service.Imp.CategoryServiceImp;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CategoryServiceImpTest {

    @InjectMocks
    private CategoryServiceImp categoryService;

    @Mock
    private CategoryDao categoryDao;

    private Category category;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        category = new Category();
        category.setCategoryId(1);
        category.setCategoryName("Electronics");
    }

    @Test
    void testGetAllCategories() {
        when(categoryDao.selectList(null)).thenReturn(Arrays.asList(category));

        List<Category> result = categoryService.getAllCategories();
        assertEquals(1, result.size());
        assertEquals(category, result.get(0));
    }
}
