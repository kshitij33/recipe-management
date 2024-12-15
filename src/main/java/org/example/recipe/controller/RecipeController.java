package org.example.recipe.controller;

import org.example.recipe.dto.RecipeDTO;
import org.example.recipe.mapper.RecipeMapper;
import org.example.recipe.model.Recipe;
import org.example.recipe.service.RecipeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/recipes")
public class RecipeController {

    @Autowired
    private RecipeService recipeService;

    @Autowired
    private RecipeMapper recipeMapper;

    // Get all recipes
    @GetMapping
    public ResponseEntity<List<RecipeDTO>> getAllRecipes() {
        List<RecipeDTO> recipes = recipeService.getAllRecipes()
                .stream()
                .map(recipeMapper::toDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(recipes);
    }

    // Get recipe by ID
// Get recipe by ID
    @GetMapping("/{id}")
    public ResponseEntity<RecipeDTO> getRecipeById(@PathVariable Long id) {
        return recipeService.getRecipeById(id)
                .map(recipe -> ResponseEntity.ok(recipeMapper.toDTO(recipe)))
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(null));  // Return null or an empty RecipeDTO in case of NOT_FOUND
    }


    // Create a new recipe
    @PostMapping
    public ResponseEntity<?> createRecipe(@RequestBody RecipeDTO recipeDTO) {
        try {
            Recipe recipe = recipeMapper.toEntity(recipeDTO);
            Recipe createdRecipe = recipeService.createRecipe(recipe);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(recipeMapper.toDTO(createdRecipe));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    // Update an existing recipe
    @PutMapping("/{id}")
    public ResponseEntity<?> updateRecipe(@PathVariable Long id, @RequestBody RecipeDTO recipeDTO) {
        try {
            Recipe recipe = recipeMapper.toEntity(recipeDTO);
            Recipe updatedRecipe = recipeService.updateRecipe(id, recipe);
            return ResponseEntity.ok(recipeMapper.toDTO(updatedRecipe));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    // Delete a recipe
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteRecipe(@PathVariable Long id) {
        try {
            recipeService.deleteRecipe(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}
