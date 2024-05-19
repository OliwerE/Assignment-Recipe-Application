package recipeApp;

import java.util.ArrayList;

public class FindRecipesByMaxPrice implements SearchRecipes{
  public ArrayList<Recipe> findRecipes(ArrayList<Recipe> recipes, String searchQuery) {
    System.out.println("Searching recipes by max price: " + searchQuery + " ...");
    ArrayList<Recipe> foundRecipes = new ArrayList<Recipe>();

    int maxPrice = Integer.parseInt(searchQuery);

    for (int a = 0; a < recipes.size(); a++) { // Iterates all recipes
      Recipe recipe = recipes.get(a);
      int recipePrice = 0;
      for (int b = 0; b < recipe.getIngredients().size(); b++) { // Iterates all ingredients in the recipe
        int amount = recipe.getIngredientAmount().get(b);
        recipePrice += (amount * recipe.getIngredients().get(b).getPrice()); // Calculates recipe price
      }
      if (recipePrice <= maxPrice) { // Adds recipe to response if the recipe price is equal or less than the max price
        foundRecipes.add(recipe);
      }
    }

    return foundRecipes;
  }
}
