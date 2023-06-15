package com.springbootApp.springbootapp.services;


import com.springbootApp.springbootapp.dao.FoodItemRepository;
import com.springbootApp.springbootapp.entity.FoodItem;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@Transactional
public class FoodItemService {
    private final FoodItemRepository foodItemRepository;

    public FoodItemService(FoodItemRepository foodItemRepository) {
        this.foodItemRepository = foodItemRepository;
    }

    public List<FoodItem> getAllFoodItems() {
        return foodItemRepository.findAll();
    }

    public FoodItem getFoodItemById(Long id) {
        return foodItemRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Food item not found"));
    }

    public FoodItem createFoodItem(FoodItem foodItem) {
        return foodItemRepository.save(foodItem);
    }

  //  public FoodItem updateFoodItem(Long id, FoodItem foodItem) {
//        return foodItemRepository.save(foodItem);
//    }
  public FoodItem updateFoodItem(Long id, FoodItem foodItem) {
      FoodItem existingFoodItem = foodItemRepository.findById(id)
              .orElseThrow(() -> new NoSuchElementException("Food item not found"));
      existingFoodItem.setName(foodItem.getName());
      return foodItemRepository.save(existingFoodItem);
  }


    public void deleteFoodItem(Long id) {
        foodItemRepository.deleteById(id);
    }

    // Other methods as needed
    // ...
}
