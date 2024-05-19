package recipeApp;

import java.util.ArrayList;

public class Ingredients {
  private ArrayList<Ingredient> ingredients = new ArrayList<Ingredient>();

  private String replaceCharacter(String string, ArrayList<String> charRemove, String replaceWith) {
    String newString = string;
    for (int i = 0; i < charRemove.size(); i++) { // replaces each string from charRemove
      newString = newString.replace(charRemove.get(i), replaceWith);
    }
    return newString;
  }

  public Boolean uniqueNameCheck(String string) {
    ArrayList<String> charToRemove = new ArrayList<String>();
    charToRemove.add(":");
    charToRemove.add(";");
    string = replaceCharacter(string, charToRemove, "");
    if (string.length() <= 0) { // Avoids save errors with empty strings
      string = "#";
    }

    for (int i = 0; i < ingredients.size(); i++) { // Searches for recipes with the same name as string
      if (ingredients.get(i).getName().equals(string)) {
        return false;
      } 
    }
    return true;
  }

  public void createIngredient(String name, int unit, int price) {
    Ingredient newIngredient = new Ingredient();

    ArrayList<String> charToRemove = new ArrayList<String>();
    charToRemove.add(":");
    charToRemove.add(";");

    name = replaceCharacter(name, charToRemove, "");
    if (name.length() <= 0) { // Avoids save errors
      name = "#";
    }

    if (!uniqueNameCheck(name)) {
      System.out.println("Name not unique, Can not create ingredient!");
      return;
    } else {
      newIngredient.setName(name);

      String unitName;
      if (unit == 1) {
        unitName = "grams";
      } else if (unit == 2) {
        unitName = "litre";
      } else if (unit == 3) {
        unitName = "piece";
      } else {
        System.out.println(unit + " Is not a valid ingredient unit.");
        System.exit(1);
        return;
      }
      newIngredient.setUnit(unitName);
      newIngredient.setPrice(price);
      ingredients.add(newIngredient);
    }
  }

  public ArrayList<Ingredient> getIngredients() {
    return new ArrayList<Ingredient>(ingredients);
  }

  public Boolean deleteIngredient(Ingredient ingredient) {
    for (int i = 0; i < ingredients.size(); i++) {
      if (ingredients.get(i) == ingredient) { // Removes ingredient if the reference is equal
        ingredients.remove(i);
        return true;
      }
    }
    return false;
  }
}
