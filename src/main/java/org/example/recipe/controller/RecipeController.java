package org.example.recipe.controller;

import org.example.recipe.model.Recipe;
import org.example.recipe.service.RecipeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/recipes")
public class RecipeController {
    
    @Autowired
    private RecipeService recipeService;

    // Get all recipes
    @GetMapping
    public ResponseEntity<List<Recipe>> getAllRecipes() {
        List<Recipe> recipes = recipeService.getAllRecipes();
        return ResponseEntity.ok(recipes); // HTTP 200 OK
    }

    // Get recipe by ID
    @GetMapping("/{id}")
    public ResponseEntity<?> getRecipeById(@PathVariable Long id) {
        Optional<Recipe> recipe = recipeService.getRecipeById(id);
        if (recipe.isPresent()) {
            return ResponseEntity.ok(recipe.get()); // HTTP 200 OK
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Recipe not found with ID: " + id); // HTTP 404 Not Found with custom message
        }
    }

    // Create a new recipe
    @PostMapping
    public ResponseEntity<?> createRecipe(@RequestBody Recipe recipe) {
        try {
            Recipe createdRecipe = recipeService.createRecipe(recipe);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(createdRecipe); // HTTP 201 Created
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(e.getMessage()); // HTTP 400 Bad Request with error message
        }
    }

    // Update an existing recipe
    @PutMapping("/{id}")
    public ResponseEntity<?> updateRecipe(@PathVariable Long id, @RequestBody Recipe recipe) {
        try {
            Recipe updatedRecipe = recipeService.updateRecipe(id, recipe);
            return ResponseEntity.ok(updatedRecipe); // HTTP 200 OK
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(e.getMessage()); // HTTP 404 Not Found with custom message
        }
    }

    // Delete a recipe
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteRecipe(@PathVariable Long id) {
        try {
            recipeService.deleteRecipe(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build(); // HTTP 204 No Content
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(e.getMessage()); // HTTP 404 Not Found with custom message
        }
    }
}
