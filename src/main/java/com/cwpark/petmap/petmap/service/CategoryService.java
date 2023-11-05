package com.cwpark.petmap.petmap.service;

import com.cwpark.petmap.petmap.data.dao.CategoryDao;
import com.cwpark.petmap.petmap.data.domain.Category;
import com.cwpark.petmap.petmap.data.dto.CategoryDto;
import com.cwpark.petmap.petmap.util.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;


@Service
public class CategoryService {
    private final CategoryDao categoryDao;

    @Autowired
    public CategoryService(CategoryDao categoryDao) {
        this.categoryDao = categoryDao;
    }

    public void saveCategory(CategoryDto categoryDto,  MultipartFile file) throws IOException {
        String saveFileName = categoryDto.getCategoryImg();

        if(file != null)  {
            saveFileName = FileUtils.singleFileUpload(file, "category");
        }

        categoryDao.saveCategory(Category.builder()
                .categoryParent(categoryDto.getCategoryParent())
                .categoryDepth(categoryDto.getCategoryDepth())
                .categoryName(categoryDto.getCategoryName())
                .categoryImg(saveFileName)
                .categoryId(categoryDto.getCategoryId())
                .build());
    }

    public List<CategoryDto> getCategory(Long parent, int depth) {
        if(parent == 0) {
            return categoryDao.getCategory();
        } else {
            return categoryDao.getCategory(parent, depth);
        }
    }

    public void deleteCategory(CategoryDto categoryDto) {
        categoryDao.deleteCategory(Category.builder().categoryId(categoryDto.getCategoryId()).build());
    }

    public String[] itemCategory(Long categoryId) {
        return categoryDao.itemCategory(categoryId).split("c");
    }
}
