package recipeApp;

import java.util.ArrayList;

public class Recipe {
  private String name;
  private int portions;
  private ArrayList<Ingredient> ingredients = new ArrayList<Ingredient>();
  private ArrayList<Integer> ingredientAmount = new ArrayList<Integer>();
  private String ingredientComment;
  private String instructions;

  public void setName(String n) {
    name = n;
  }

  public String getName() {
    return name;
  }

  public void setPortions(int p) {
    portions = p;
  }

  public int getPortions() {
    return portions;
  }

  public void setIngredients(ArrayList<Ingredient> i) {
    ingredients = i;
  }

  public ArrayList<Ingredient> getIngredients() {
    return new ArrayList<Ingredient>(ingredients);
  }

  public void setIngredientAmount(ArrayList<Integer> a) {
    ingredientAmount = a;
  }

  public ArrayList<Integer> getIngredientAmount() {
    return new ArrayList<Integer>(ingredientAmount);
  }

  public void setIngredientComment(String c) {
    ingredientComment = c;
  }

  public String getIngredientComment() {
    return ingredientComment;
  }

  public void setInstuctions(String i) {
    instructions = i;
  }

  public String getInstructions() {
    return instructions;
  }
}
