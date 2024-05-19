package recipeApp;

import java.util.ArrayList;

public class SearchContext {
  private SearchRecipes searchRecipes;

  public SearchContext(SearchRecipes context) {
    searchRecipes = context; // changes search context
  }

  public ArrayList<Recipe> executeSearchRecipes(ArrayList<Recipe> recipes, String searchQuery) {
    return searchRecipes.findRecipes(recipes, searchQuery); // Returns found recipes
  }
}
