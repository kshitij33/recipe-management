package org.example.recipe.service;

import org.example.recipe.model.Recipe;
import org.example.recipe.repository.RecipeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RecipeService {

    @Autowired
    private RecipeRepository recipeRepository;

    // Get all recipes
    public List<Recipe> getAllRecipes() {
        return recipeRepository.findAll();
    }

    // Get recipe by ID
    public Optional<Recipe> getRecipeById(Long id) {
        return recipeRepository.findById(id);
    }

    // Create a new recipe
    public Recipe createRecipe(Recipe recipe) {
        if (recipe.getName() == null || recipe.getName().isEmpty()) {
            throw new IllegalArgumentException("Recipe name cannot be null or empty");
        }
        return recipeRepository.save(recipe);
    }


//     Update an existing recipe
    public Recipe updateRecipe(Long id, Recipe updatedRecipe) {
        Optional<Recipe> existingRecipeOpt = recipeRepository.findById(id);
        if (existingRecipeOpt.isPresent()) {
            Recipe existingRecipe = existingRecipeOpt.get();
            existingRecipe.setName(updatedRecipe.getName());
            existingRecipe.setDescription(updatedRecipe.getDescription());
            existingRecipe.setIngredients(updatedRecipe.getIngredients());
            existingRecipe.setInstructions(updatedRecipe.getInstructions());
            existingRecipe.setServings(updatedRecipe.getServings());

            return recipeRepository.save(existingRecipe);
        } else {
            throw new RuntimeException("Recipe not found with ID: " + id);
        }
    }

    // Delete a recipe
    public void deleteRecipe(Long id) {
        if (recipeRepository.existsById(id)) {
            recipeRepository.deleteById(id);
        } else {
            throw new RuntimeException("Recipe not found with ID: " + id);
        }
    }
}
