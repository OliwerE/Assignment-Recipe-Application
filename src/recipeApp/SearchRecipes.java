package recipeApp;

import java.util.ArrayList;

public interface SearchRecipes {
  public ArrayList<Recipe> findRecipes(ArrayList<Recipe> recipes, String SearchQuery);
}
