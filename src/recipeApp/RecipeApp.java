package recipeApp;

import java.util.ArrayList;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class RecipeApp {
  private UserInterface userInterface = new UserInterface();
  private Ingredients ingredients = new Ingredients();
  private Recipes recipes = new Recipes();

  private String loadStringFromFile(String fileLocation) {
    try {
    File file = new File(fileLocation);
    Scanner fileReader = new Scanner(file);
    
    if (fileReader.hasNext()) {
      String dataFromFile = fileReader.nextLine();
      fileReader.close();
      return dataFromFile;
    } else { // If file is empty
      fileReader.close();
      return "";
    }
    } catch(FileNotFoundException e){
      System.out.println(fileLocation + " not found, starting application without file.");
      return "";
    } catch (java.util.InputMismatchException e) {
      System.out.println("Error Read ingredients");
      e.printStackTrace();
      System.exit(1);
      return "";
    }
  }

  private void loadIngredientsFromFile() {
    System.out.println("Loading Ingredients...");
    String data = loadStringFromFile("ingredients.txt");

    if (data.length() > 0) { // if file has content
      String[] splitIngredients = data.split(";");

      for (int i = 0; i < splitIngredients.length; i++) {
        String[] ingredientArray = splitIngredients[i].split(":");

        int ingredientType;
        if (ingredientArray[1].equals("grams")) {
          ingredientType = 1;
        } else if (ingredientArray[1].equals("litre")) {
          ingredientType = 2;
        } else if (ingredientArray[1].equals("piece")) {
          ingredientType = 3;
        } else {
          System.out.println(ingredientArray[0] + " has an unknown unit, check ingredients.txt and restart the application.");
          System.exit(1);
          return;
        }
        ingredients.createIngredient(ingredientArray[0], ingredientType, Integer.parseInt(ingredientArray[2]));
      }
    } else {
      System.out.println("Couldn't find any stored ingredients!");
    }
  }

  private void loadRecipesFromFile() {
    System.out.println("Loading Recipes...");
    String dataFromFile = loadStringFromFile("recipes.txt");

    if (dataFromFile.length() > 0) { // If file has content
      String[] splitRecipes = dataFromFile.split(";;;");
      for (int i = 0; i < splitRecipes.length; i++) { // Each recipe
        String recipeString = splitRecipes[i];
        String[] splitRecipeString = recipeString.split(";;"); // Splits recipe in parts

        String name = splitRecipeString[0];
        int portions = Integer.parseInt(splitRecipeString[1]);

        ArrayList<Ingredient> fileRecipeIngredients = new ArrayList<Ingredient>(); // All ingredients for the recipe
        ArrayList<Integer> ingredientAmount = new ArrayList<Integer>();
        String[] splitIngredients = splitRecipeString[2].split(":");
 
        for (int a = 0; a < splitIngredients.length; a++) { // Each ingredient for recipe from file
          String[] ingredientFromFileName = splitIngredients[a].split(";");

          // Add ingredient object link
          ArrayList<Ingredient> storedIngredients = ingredients.getIngredients();

          for (int b = 0; b < storedIngredients.size(); b++) { // Each ingredient stored in the application
            String storedIngredientName = storedIngredients.get(b).getName();

            // Adds ingredient reference to recreated recipe
            if (storedIngredientName.equals(ingredientFromFileName[0])) {
              fileRecipeIngredients.add(storedIngredients.get(b));
              break;
            } else {
              continue;
            }
          }

          ingredientAmount.add(Integer.parseInt(ingredientFromFileName[1]));
        }
        String comments = splitRecipeString[3];
        String instructions = splitRecipeString[4];

        recipes.createRecipe(name, portions, fileRecipeIngredients, ingredientAmount, comments, instructions);                                                                                     // recipe
      }
    } else {
      System.out.println("Couldn't find any stored recipes!");
    }
  }

  private void loadMainMenu() {
    int returnNumber = userInterface.mainMenu();
  
    if (returnNumber == 1) {
      loadIngredientMenu();
    } else if (returnNumber == 2) {
      loadRecipeMenu();
    } else if (returnNumber == 3) {
      closeApp();
    } else {
      System.out.println("\nError: Choose an alternative between one and three.");
      loadMainMenu();
    }
  }

  private void loadIngredientMenu() {
    ArrayList<Ingredient> allStoredIngredients = ingredients.getIngredients();
    int ingredientReturnInt = userInterface.ingredientMenu(allStoredIngredients);

    if (ingredientReturnInt == 1) {
      loadMainMenu();
    } else if (ingredientReturnInt == 2) {
      System.out.println("Error: Ingredient details did not open!");
      loadIngredientMenu();
    } else if (ingredientReturnInt == 3) {
      loadCreateIngredient();
    } else if (ingredientReturnInt == 4) {
      loadRemoveIngredient();
    } else {
      System.out.println("\nError ingredient menu did not return an integer between one and four");
      loadIngredientMenu();
    }
  }

  private void loadCreateIngredient() {
    userInterface.createIngredient(ingredients);
    loadIngredientMenu(); // Opens ingredient menu after ingredient has been created.
  }

  private void loadRemoveIngredient() {
    userInterface.deleteIngredient(ingredients, recipes);
    loadIngredientMenu();
  }

  private void loadRecipeMenu() {
    ArrayList<Recipe> allRecipes = recipes.getRecipes();
    int recipeMenuReturnInt = userInterface.recipeMenu(allRecipes);

    if (recipeMenuReturnInt == 1) {
      loadMainMenu();
    } else if (recipeMenuReturnInt == 3) {
      loadCreateRecipe();
    } else if (recipeMenuReturnInt == 4) {
      loadRemoveRecipe();
    } else if (recipeMenuReturnInt == 5) {
      loadSearchRecipes();
    } else if (recipeMenuReturnInt == 0) {
      loadRecipeMenu();
      return;
    } else {
      System.out.println("\nError recipe menu did not return an integer between one and five");
      loadRecipeMenu();
    }
  }

  private void loadSearchRecipes() {
    userInterface.searchRecipes(recipes);
    loadRecipeMenu();
  }

  private void loadRemoveRecipe() {
    userInterface.deleteRecipe(recipes);
    loadRecipeMenu();
  }

  private void loadCreateRecipe() {
    userInterface.createRecipe(recipes, ingredients.getIngredients());
    loadRecipeMenu();
  }

  private File createFile(String src) {
     try{
      File f = new File(src);
      f.createNewFile();
      return f;
    } catch (IOException e) {
      System.out.println("Error: Create file");
      e.printStackTrace();
      return new File("");
    }
  }

  private void writeToFile(String string, String src) {
    try {
      FileWriter ingredientWriter = new FileWriter(src);
      ingredientWriter.write(string);
      ingredientWriter.close();
    } catch (IOException e) {
      System.out.println("Error: Write file");
      e.printStackTrace();
    }
  }

  private void saveIngredientsToFile() {
      File ingredientsFile = createFile("ingredients.txt");
      String saveString = "";

      ArrayList<Ingredient> ingredientsToSave = ingredients.getIngredients();
      if (ingredientsToSave.size() < 1) { // Removes file if ingredientsToSave is empty
        ingredientsFile.delete();
      } else {
        for (int i = 0; i < ingredientsToSave.size(); i++) {
          String iString = ingredientsToSave.get(i).getName() + ":" + ingredientsToSave.get(i).getUnit() + ":"
              + ingredientsToSave.get(i).getPrice();
          saveString += iString + ";";
        }

        writeToFile(saveString, "ingredients.txt"); // Writes ingredients to the file.
      }
  }
 
  private void saveRecipesToFile() {
    File recipesFile = createFile("recipes.txt");
    String saveString = "";

    ArrayList<Recipe> recipesToSave = recipes.getRecipes();

    if (recipesToSave.size() < 1) { // Removes file if recipesToSave is empty
      recipesFile.delete();
    } else {
      for (int i = 0; i < recipesToSave.size(); i++) { // creates string for each recipe
        String recipeString = recipesToSave.get(i).getName() + ";;" + recipesToSave.get(i).getPortions() + ";;";

        for (int a = 0; a < recipesToSave.get(i).getIngredients().size(); a++) { // Creates ingredient strings
          recipeString += recipesToSave.get(i).getIngredients().get(a).getName() + ";"
              + recipesToSave.get(i).getIngredientAmount().get(a) + ":";

        }
        recipeString += ";;" + recipesToSave.get(i).getIngredientComment() + ";;" + recipesToSave.get(i).getInstructions();

        saveString += recipeString + ";;;";
      }

      writeToFile(saveString, "recipes.txt"); // Writes ingredients to the file.
    }
  }

  private void closeApp() {
    System.out.println("Closing scanner...");
    userInterface.closeScanner();

    System.out.println("saves ingredients...");
    saveIngredientsToFile();
    

    System.out.println("saves recipes...");
    saveRecipesToFile();

    System.out.println("Closing the application...");
    System.exit(0);
  }

  public void startApp() {
    loadIngredientsFromFile();
    loadRecipesFromFile();
    loadMainMenu();
  }
}
