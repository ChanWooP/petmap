package com.cwpark.petmap.petmap.data.dao;

import com.cwpark.petmap.petmap.data.domain.Category;
import com.cwpark.petmap.petmap.data.domain.Coupon;
import com.cwpark.petmap.petmap.data.dto.CategoryDto;
import com.cwpark.petmap.petmap.data.dto.CouponDto;
import com.cwpark.petmap.petmap.data.persistence.CategoryRepository;
import com.cwpark.petmap.petmap.data.persistence.CouponRepository;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

@Repository
public class CategoryDao {
    private final CategoryRepository categoryRepository;
    private final JPAQueryFactory queryFactory;

    @Autowired
    public CategoryDao(CategoryRepository categoryRepository, JPAQueryFactory queryFactory) {
        this.categoryRepository = categoryRepository;
        this.queryFactory = queryFactory;
    }

    public void saveCategory(Category category) {
        categoryRepository.save(category);
    }

    public List<CategoryDto> getCategory() {
        List<Category> list = categoryRepository.findByCategoryDepthOrderByCategoryId(0);
        List<CategoryDto> rtnList = null;

        if(list.isEmpty()) {
            throw new EmptyResultDataAccessException(1);
        } else {
            rtnList = list.stream().map(c -> CategoryDto.builder()
                    .categoryId(c.getCategoryId())
                    .categoryParent(c.getCategoryParent())
                    .categoryDepth(c.getCategoryDepth())
                    .categoryName(c.getCategoryName())
                    .categoryImg(c.getCategoryImg())
                    .build()).collect(Collectors.toList());
        }

        return rtnList;
    }

    public List<CategoryDto> getCategory(Long parent, int depth) {
        List<Category> list = categoryRepository.findByCategoryParentAndCategoryDepthOrderByCategoryId(parent, depth);
        List<CategoryDto> rtnList = null;

        if(list.isEmpty()) {
            throw new EmptyResultDataAccessException(1);
        } else {
            rtnList = list.stream().map(c -> CategoryDto.builder()
                    .categoryId(c.getCategoryId())
                    .categoryParent(c.getCategoryParent())
                    .categoryDepth(c.getCategoryDepth())
                    .categoryName(c.getCategoryName())
                    .categoryImg(c.getCategoryImg())
                    .build()).collect(Collectors.toList());
        }

        return rtnList;
    }

    public void deleteCategory(Category category) {
        categoryRepository.deleteCategory(category.getCategoryId());
    }

    public String itemCategory(Long categoryId) {
        return categoryRepository.findCategory(categoryId);
    }

}
