package recipeApp;

import java.util.ArrayList;

public class Recipes {
  private ArrayList<Recipe> recipes = new ArrayList<Recipe>();

  private String replaceCharacter(String string, ArrayList<String> charRemove, String replaceWith) {
    String newString = string;
    for (int i = 0; i < charRemove.size(); i++) { // replaces each string from charRemove
      newString = newString.replace(charRemove.get(i), replaceWith);
    }
    return newString;
  }

  private String replaceIfEmptyString(String s) {
    if (s.length() <= 0) { // Avoids save errors with empty strings
      return "#";
    } else {
      return s;
    }
  }

  public void createRecipe(String name, int portions, ArrayList<Ingredient> ingredients, ArrayList<Integer> ingredientAmount, String comments, String instructions) {
    Recipe newRecipe = new Recipe();

    ArrayList<String> charToRemove = new ArrayList<String>();
    charToRemove.add(":");
    charToRemove.add(";");

    name = replaceCharacter(name, charToRemove, "");
    name = replaceIfEmptyString(name);

    newRecipe.setName(name);

    newRecipe.setPortions(portions);
    newRecipe.setIngredients(ingredients);
    newRecipe.setIngredientAmount(ingredientAmount);

    comments = replaceCharacter(comments, charToRemove, "");
    comments = replaceIfEmptyString(comments);

    newRecipe.setIngredientComment(comments);

    instructions = replaceCharacter(instructions, charToRemove, "");
    instructions = replaceIfEmptyString(instructions);

    newRecipe.setInstuctions(instructions);

    recipes.add(newRecipe);
  }

  public ArrayList<Recipe> getRecipes() {
    return new ArrayList<Recipe>(recipes);
  }

  public boolean deleteRecipe(Recipe recipe) {
    for(int i = 0;i<recipes.size();i++) {
      if (recipes.get(i) == recipe) { // Removes recipe if the reference is equal
        System.out.println("Found recipe! Removing...");
        recipes.remove(i);
        return true;
      }
    }
  return false;
  }

  public ArrayList<Recipe> searchRecipes(String searchQuery, int searchMethod) {
    SearchContext searchContext;
    if (searchMethod == 1) { // Search by ingredient name
      searchContext = new SearchContext(new FindRecipeByIngredientName());
      ArrayList<Recipe> foundRecipes = searchContext.executeSearchRecipes(recipes, searchQuery);
      return foundRecipes;
    } else if (searchMethod == 2) { // Search by max price
      searchContext = new SearchContext(new FindRecipesByMaxPrice());
      ArrayList<Recipe> foundRecipes = searchContext.executeSearchRecipes(recipes, searchQuery);
      return foundRecipes;
    } else {
      System.out.println("Search method is not 1 or 2, something went wrong!");
      System.exit(1);
      return new ArrayList<Recipe>();
    }
  }
}
