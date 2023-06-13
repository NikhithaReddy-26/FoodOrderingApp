package com.springbootApp.springbootapp.dao;

import com.springbootApp.springbootapp.entity.FoodItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface FoodItemRepository extends JpaRepository<FoodItem, Long> {
}