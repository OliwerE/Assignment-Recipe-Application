package recipeApp;

import java.util.ArrayList;

public class FindRecipeByIngredientName implements SearchRecipes {
  public ArrayList<Recipe> findRecipes(ArrayList<Recipe> recipes, String searchQuery) {
    System.out.println("Searching recipes based on ingredient name: " + searchQuery + " ...");
    
    ArrayList<Recipe> foundRecipes = new ArrayList<Recipe>();
    
    for(int a = 0; a < recipes.size(); a++) { // Iterates all recipes
      ArrayList<Ingredient> ingredients = recipes.get(a).getIngredients();
      for (int b = 0; b < ingredients.size(); b++) { // Iterates all ingredients in the recipe
        String ingredientName = ingredients.get(b).getName();
        if (ingredientName.equals(searchQuery)) { // If an ingredient name is equal the search string (searchQuery)
          foundRecipes.add(recipes.get(a));
          break;
        }
      }
    }
    return foundRecipes;
  }
}
