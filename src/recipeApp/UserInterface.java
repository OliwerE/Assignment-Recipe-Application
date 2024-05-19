package recipeApp;

import java.util.ArrayList;
import java.util.Scanner;

public class UserInterface {
  private Scanner inputScanner = new Scanner(System.in);

  public void closeScanner() {
    inputScanner.close();
  }

  private int intScanner() {
    int number;
    while(true) {
      try {
        number = inputScanner.nextInt();
        inputScanner.nextLine(); // Resets scanner
        break;
      } catch(java.util.InputMismatchException e) {
        inputScanner.nextLine(); // Resets scanner
        System.out.print("Error: " + e + "\nEnter a valid alternative: ");
        continue;
      }
    }
    return number;
  }

  private String stringScanner() { 
    String string;
    while (true) {
      try {
        string = inputScanner.nextLine();
        break;
      } catch (java.util.InputMismatchException e) {
        System.out.print("Error: " + e + "\nEnter a valid string: ");
        continue;
      }
    }
    return string;
  }

  private void waitToContinue(String returnTo) {
    while (true) {
      System.out.print("Back to " + returnTo + " (1): ");
      int alt = intScanner();

      if (alt == 1) {
        break;
      } else if (alt != 1) {
        System.out.println(alt + " is not an alternative.");
        continue;
      } else {
        System.out.println("waitToContinue: something went wrong!");
        System.exit(1);
      }
    }
  }

  public int mainMenu() {
    System.out.print("----------------Main Menu----------------\nIngredient menu(1), Recipe menu(2), Close application(3)\nChoose an alternative: ");
    int userAlt = intScanner();
    return userAlt;
  }

  private void printIngredients(ArrayList<Ingredient> ingredients) {
    for (int i = 0; i < ingredients.size(); i++) {
      System.out.println((i + 1) + ". " + ingredients.get(i).getName());
    }
  }

  public int ingredientMenu(ArrayList<Ingredient> ingredients) {
    while(true) {
      System.out.println("-------------Ingredient Menu-------------\nIngredienser:");
      if (ingredients.size() == 0) {
        System.out.println("There are no ingredients. Add an ingredient to find it here!");
      } else if (ingredients.size() > 0) {
        printIngredients(ingredients);
        System.out.println("--------------------");
      } else {
        System.out.println("There are less than zero ingredients. Something went wrong!");
        System.exit(1);
      }

      System.out.print("Open main menu(1), ingredient details(2), Create Ingredient(3), Delete ingredient(4)\nChoose an alternative: ");
      int userAlt = intScanner();

      if (userAlt == 2) { // Opens ingredient details
        while(true) {
          System.out.print("Choose ingredient number (Back = 0): ");
          int selectedIngredient = intScanner() - 1;
          if (selectedIngredient >= 0 && selectedIngredient < ingredients.size()) {
            Ingredient ingredient = ingredients.get(selectedIngredient);
            ingredientDetails(ingredient);
            break;
          } else if ((selectedIngredient + 1) == 0) {
            break;
          } else {
            System.out.println("Ingredient with number " + (selectedIngredient + 1) + " does not exist.");
            continue;
          }
        }

      } else {
        return userAlt;
      }
    }

  }

  public void ingredientDetails(Ingredient ingredients) {
    System.out.println("-----Ingredient Details-----\nName: " + ingredients.getName() + "\nUnit: " + ingredients.getUnit() + "\nPrice: " + ingredients.getPrice() + "\n----------------------------");
    waitToContinue("ingredient menu");
  }

  public void createIngredient(Ingredients ingredients) {
    String getNameTerminalText = "Create ingredient:\nWarning: Colon and semicolon (: and ;) will be removed.\nname: ";
    String name;
    while(true) {
      System.out.print(getNameTerminalText);
      name = stringScanner();

      Boolean isUnique = ingredients.uniqueNameCheck(name);

      if (isUnique) {
        break;
      } else {
        System.out.println("Ingredient name is not unique!");
        continue;
      }
    }

    int unit;
    while(true) {
      System.out.print("unit(1 = grams, 2 = litre, 3 = piece): ");
      unit = intScanner();

      if (unit > 0 && unit < 4) {
        break;
      } else {
        System.out.println(unit + " is not an alternative!");
      }
    }

    while(true) {
      System.out.print("price: ");
      int price = intScanner();

      if (price > 0) {
        ingredients.createIngredient(name, unit, price); // Saves ingredient in Ingredients object
        break;
      } else {
        System.out.println("Price must be above zero.");
        continue;
      }
    }
  }

  public void deleteIngredient(Ingredients ingredients, Recipes recipes) {
    ArrayList<Ingredient> allIngredients = ingredients.getIngredients();
    
    int ingredientIndex;
    while(true) {
      System.out.print("Select ingredient to remove (Back = 0): ");
      ingredientIndex = intScanner() - 1;
      if ((ingredientIndex + 1) == 0) {
        return;
      } else if ((ingredientIndex + 1) > 0 && (ingredientIndex) < (allIngredients.size())) {
        break;
      } else {
        System.out.println("Ingredient does not exist, try again.");
        continue;
      }
    }

    String searchQuery = allIngredients.get(ingredientIndex).getName();
    ArrayList<Recipe> affectedRecipes = recipes.searchRecipes(searchQuery, 1); // Searches recipes with ingredient

    System.out.println("These recipes will get removed by removing ingredient " + searchQuery + ":");
    
    if (affectedRecipes.size() > 0) { // Lists all recipes including the ingredient.
      for (int i = 0; i < affectedRecipes.size(); i++) {
        String name = affectedRecipes.get(i).getName();
        System.out.println((i + 1) + ". " + name);
      }
    } else if (affectedRecipes.size() == 0) {
      System.out.println("Did not find any recipes.");
    } else {
      System.out.println("Length of array list is less than zero.");
      System.exit(1);
    }
    
    while(true) {
      System.out.print("Confirm (1 = yes, 2 = no): ");
      int answer = intScanner();

      if(answer == 1) {
        System.out.println("Removing...");
        for(int i = 0; i < affectedRecipes.size(); i++) { // Removes all recipes with the ingredient
          recipes.deleteRecipe(affectedRecipes.get(i));
        }
        break;
      } else if (answer == 2) {
        System.out.println("Remove ingredient has been canceled");
        return;
      } else {
        System.out.println(answer + " is not an alternative, try again.");
      }
    }
    Ingredient ingredient = allIngredients.get(ingredientIndex);
    Boolean delete = ingredients.deleteIngredient(ingredient);
    if (delete) {
      System.out.println("Ingredient and affected recipes has been removed.");
    } else {
      System.out.println("Ingredient has not been removed.");
    }
  }

  public int recipeMenu(ArrayList<Recipe> recipes) {
    System.out.println("----------Recipe Menu----------");
    while(true) {
      if (recipes.size() == 0) {
        System.out.println("There are no Recipes. Add a recipe to find it here!");
      } else if (recipes.size() > 0) {
        for (int i = 0; i < recipes.size(); i++) { // Lists all recipes
          System.out.println((i + 1) + ". " + recipes.get(i).getName());
        }
      } else {
        System.out.println("There are less than zero recipes. Something went wrong!");
        System.exit(1);
      }

      System.out.print("--------------------\nOpen main menu (1), Open recipe(2), Create recipe(3), Delete recipe(4), Search recipe(5)\nChoose an alternative: ");
      int userAlt = intScanner();

      if (userAlt == 2) {
        while(true) {
          System.out.print("Choose recipe number (back = 0): ");
          int selectedRecipe = intScanner() - 1;
          
          if((selectedRecipe + 1) <= recipes.size() && (selectedRecipe + 1) > 0) {
            Recipe recipe = recipes.get(selectedRecipe);
            renderRecipe(recipe);
            return 0;
          } else if ((selectedRecipe + 1) == 0) {
            return 0;
          } else {
            System.out.println("Recipe does not exist, try again.");
          }
        }
      } else {
        return userAlt;
      }
    }
  }

  public void renderRecipe(Recipe recipe) {
    int amountOfPortions;
    System.out.print("Enter number of portions: ");
    amountOfPortions = intScanner();
    System.out.println("----------Recipe " + recipe.getName() + " ----------\nName: " + recipe.getName() + "\nPortions: " + amountOfPortions);

    ArrayList<Ingredient> ingredients = recipe.getIngredients();
    ArrayList<Integer> ingredientsAmount = recipe.getIngredientAmount();
    int totalPrice = 0;
    double recipePortions = recipe.getPortions();
    double wantedPortions = amountOfPortions;

    System.out.println("Ingredients:");
    for (int i = 0; i < ingredients.size(); i++) { // Lists each ingredient
      double recipeAmount = ingredientsAmount.get(i);
      double amount;

      // Add ingredient to total price
      if (ingredients.get(i).getUnit().equals("grams") || ingredients.get(i).getUnit().equals("litre")) {
        amount = (recipeAmount / recipePortions) * wantedPortions;
      } else if (ingredients.get(i).getUnit().equals("piece")) {
        amount = Math.ceil((recipeAmount / recipePortions) * wantedPortions);
      } else {
        System.out.println("Unknown ingredient type closing application...");
        System.exit(1);
        return;
      }

      int intAmount = (int) Math.round(amount);

      totalPrice += ingredients.get(i).getPrice() * intAmount;

      System.out.println(ingredients.get(i).getName() + ": " + intAmount + " " + ingredients.get(i).getUnit());
    }
    System.out.println("Ingredient comments: " + recipe.getIngredientComment() + "\nInstructions: " + recipe.getInstructions() + "\nPrice: " + totalPrice + " kr");
    waitToContinue("main menu");
  }

  public void createRecipe(Recipes recipes, ArrayList<Ingredient> ingredients) {
    System.out.println("----------Create Recipe----------\nWarning: Colon and semicolon (: and ;) will be removed.");

    System.out.print("Enter recipe name: ");
    String recipeName = stringScanner();

    System.out.print("Enter number of portions: ");
    int numberOfPortions = intScanner();

    System.out.println("Your ingredients:");
    printIngredients(ingredients);
    System.out.println("---------------");

    ArrayList<Ingredient> ingredientsForRecipe = new ArrayList<Ingredient>();
    ArrayList<Integer> ingredientAmountsForRecipe = new ArrayList<Integer>();
    while(true) { // Adds ingredients until newRecipeIngredient equals zero
      System.out.print("Enter ingredient you want to add (enter 0 to save recipe): ");
      int newRecipeIngredient = intScanner() - 1;
      if ((newRecipeIngredient + 1) == 0) {
        break;
      }
      ingredientsForRecipe.add(ingredients.get(newRecipeIngredient));

      System.out.println("Enter amount needed for " + numberOfPortions + " portion(s) (" + ingredients.get(newRecipeIngredient).getUnit() + "): ");
      int newRecipeIngredientAmount = intScanner();
      ingredientAmountsForRecipe.add(newRecipeIngredientAmount);
    }

    System.out.print("Add ingredient comments: ");
    String ingredientComments = stringScanner();

    System.out.print("Add instructions: ");
    String recipeInstructions = stringScanner();

    recipes.createRecipe(recipeName, numberOfPortions, ingredientsForRecipe, ingredientAmountsForRecipe, ingredientComments, recipeInstructions);
  }

  public void deleteRecipe(Recipes recipes) {
    ArrayList<Recipe> allRecipes = recipes.getRecipes();

    while (true) {
      System.out.print("Select recipe to remove (Back = 0): ");
      int recipeNumber = intScanner() - 1;

      if ((recipeNumber + 1) == 0) { // Go back
        break;
      } else if ((recipeNumber + 1) <= allRecipes.size() && (recipeNumber + 1) > 0) {// Remove recipe
        Recipe recipe = allRecipes.get(recipeNumber);
        Boolean delete = recipes.deleteRecipe(recipe);
        if (delete) {
          System.out.println("Recipe has been removed.");
        } else {
          System.out.println("Something went wrong: recipe has not been removed.");
        }
        break;
      } else {
        System.out.println("Recipe does not exist, try again.");
      }
    }
  }

  public void searchRecipes(Recipes recipes) {
    while(true) {
      System.out.print("Enter search method (1 = by ingredient name, 2 = by max price): ");
      int searchMethod = intScanner();

      if (searchMethod == 1 || searchMethod == 2) {
        if (searchMethod == 1) {
          System.out.print("Enter ingredient name: ");
        } else if (searchMethod == 2) {
          System.out.print("Enter max price: ");
        } else {
          System.out.print("Something went wrong, searchMethod is not 1 or 2");
          System.exit(1);
          return;
        }
        
        String searchQuery = stringScanner();
        ArrayList<Recipe> searchResult = recipes.searchRecipes(searchQuery, searchMethod);

        System.out.println("Search Results:");

        if (searchResult.size() > 0) { // Lists search result
          for (int i = 0; i < searchResult.size(); i++) {
            System.out.println((i + 1) + ". " + searchResult.get(i).getName());
          }
        } else {
          System.out.println("Did not find any recipes.");
        }
        waitToContinue("Recipe Menu");
        break;
      } else {
        System.out.println("Search method: " + searchMethod + " is not a valid alternative, try again!");
        continue;
      }
    }
  }
}
