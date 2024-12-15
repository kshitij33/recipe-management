package org.example.recipe.mapper;

import org.example.recipe.dto.RecipeDTO;
import org.example.recipe.model.Recipe;
import org.springframework.stereotype.Component;

@Component
public class RecipeMapper {

    // Convert Recipe entity to RecipeDTO
    public RecipeDTO toDTO(Recipe recipe) {
        RecipeDTO dto = new RecipeDTO();
        dto.setId(recipe.getId());
        dto.setName(recipe.getName());
        dto.setDescription(recipe.getDescription());
        dto.setIngredients(recipe.getIngredients());
        dto.setInstructions(recipe.getInstructions());
        dto.setServings(recipe.getServings());
        return dto;
    }

    // Convert RecipeDTO to Recipe entity
    public Recipe toEntity(RecipeDTO dto) {
        Recipe recipe = new Recipe();
        recipe.setId(dto.getId());
        recipe.setName(dto.getName());
        recipe.setDescription(dto.getDescription());
        recipe.setIngredients(dto.getIngredients());
        recipe.setInstructions(dto.getInstructions());
        recipe.setServings(dto.getServings());
        return recipe;
    }
}
