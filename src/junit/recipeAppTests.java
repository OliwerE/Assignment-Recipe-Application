package junit;
import recipeApp.*;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

public class recipeAppTests {
  @Test
  public void searchRecipeTest_1() { // Search recipes by ingredient name
    Recipes recipes = new recipeApp.Recipes();

    // Ingredient 1
    Ingredient ing1 = new recipeApp.Ingredient();
    ing1.setName("tomato");
    ing1.setUnit("grams");
    ing1.setPrice(20);

    // Ingredient 2
    Ingredient ing2 = new recipeApp.Ingredient();
    ing2.setName("egg");
    ing2.setUnit("piece");
    ing2.setPrice(5);

    // Ingredient 3
    Ingredient ing3 = new recipeApp.Ingredient();
    ing3.setName("orange");
    ing3.setUnit("piece");
    ing3.setPrice(10);

    // Recipe 1
    ArrayList<Ingredient> test1Ingredients = new ArrayList<Ingredient>();
    test1Ingredients.add(ing1);
    test1Ingredients.add(ing2);
    ArrayList<Integer> test1Amounts = new ArrayList<Integer>();
    test1Amounts.add(5);
    test1Amounts.add(3);
    recipes.createRecipe("test1", 2, test1Ingredients, test1Amounts, "test 1 comment", "test 1 instructions");

    // Recipe 2
    ArrayList<Ingredient> test2Ingredients = new ArrayList<Ingredient>();
    test2Ingredients.add(ing3);
    test2Ingredients.add(ing2);
    ArrayList<Integer> test2Amounts = new ArrayList<Integer>();
    test2Amounts.add(2);
    test2Amounts.add(2);
    recipes.createRecipe("test2", 3, test2Ingredients, test2Amounts, "test 2 comment", "test 2 instructions");

    // Recipe 3
    ArrayList<Ingredient> test3Ingredients = new ArrayList<Ingredient>();
    test3Ingredients.add(ing1);
    test3Ingredients.add(ing2);
    test3Ingredients.add(ing3);
    ArrayList<Integer> test3Amounts = new ArrayList<Integer>();
    test3Amounts.add(200);
    test3Amounts.add(3);
    test3Amounts.add(5);
    recipes.createRecipe("test3", 3, test3Ingredients, test3Amounts, "test 3 comment", "test 3 instructions");

    // Expected response from "tomato"
    ArrayList<Recipe> expectedResultTomato = new ArrayList<Recipe>();
    expectedResultTomato.add(recipes.getRecipes().get(0)); // recipe 1
    expectedResultTomato.add(recipes.getRecipes().get(2)); // recipe 3

    // Expected response from "egg"
    ArrayList<Recipe> expectedResultEgg = new ArrayList<Recipe>();
    expectedResultEgg.add(recipes.getRecipes().get(0)); // recipe 1
    expectedResultEgg.add(recipes.getRecipes().get(1)); // recipe 2
    expectedResultEgg.add(recipes.getRecipes().get(2)); // recipe 3

    // Expected response from "Orange"
    ArrayList<Recipe> expectedResultOrange = new ArrayList<Recipe>();
    expectedResultOrange.add(recipes.getRecipes().get(1)); // recipe 2
    expectedResultOrange.add(recipes.getRecipes().get(2)); // recipe 3

    // Expected response from "doesnotexist"
    ArrayList<Recipe> expectedResultDoesNotExist = new ArrayList<Recipe>();

    assertEquals("SearchRecipes did not return correct arrayList using query 'tomato'", expectedResultTomato, recipes.searchRecipes("tomato", 1));
    assertEquals("SearchRecipes did not return correct arrayList using query 'egg'", expectedResultEgg, recipes.searchRecipes("egg", 1));
    assertEquals("SearchRecipes did not return correct arrayList using query 'orange'", expectedResultOrange, recipes.searchRecipes("orange", 1));
    assertEquals("SearchRecipes did not return correct arrayList using query 'doesnotexist'", expectedResultDoesNotExist, recipes.searchRecipes("doesnotexist", 1));
  }

  @Test
  public void searchRecipeTest_2() { // Searches recipes by max price
    Recipes recipes = new recipeApp.Recipes();

    // Ingredient 1
    Ingredient ing1 = new recipeApp.Ingredient();
    ing1.setName("tomato");
    ing1.setUnit("grams");
    ing1.setPrice(2);

    // Ingredient 2
    Ingredient ing2 = new recipeApp.Ingredient();
    ing2.setName("egg");
    ing2.setUnit("piece");
    ing2.setPrice(5);

    // Ingredient 3
    Ingredient ing3 = new recipeApp.Ingredient();
    ing3.setName("Milk");
    ing3.setUnit("litre");
    ing3.setPrice(10);

    // Recipe 1
    ArrayList<Ingredient> test1Ingredients = new ArrayList<Ingredient>();
    test1Ingredients.add(ing2);
    test1Ingredients.add(ing3);
    ArrayList<Integer> test1Amounts = new ArrayList<Integer>();
    test1Amounts.add(5);
    test1Amounts.add(2);
    recipes.createRecipe("test1", 2, test1Ingredients, test1Amounts, "test 1 comment", "test 1 instructions"); // price = 45

    // Recipe 2
    ArrayList<Ingredient> test2Ingredients = new ArrayList<Ingredient>();
    test2Ingredients.add(ing1);
    test2Ingredients.add(ing2);
    test2Ingredients.add(ing3);
    ArrayList<Integer> test2Amounts = new ArrayList<Integer>();
    test2Amounts.add(50);
    test2Amounts.add(2);
    test2Amounts.add(1);
    recipes.createRecipe("test2", 3, test2Ingredients, test2Amounts, "test 2 comment", "test 2 instructions"); // price = 120

    // Recipe 3
    ArrayList<Ingredient> test3Ingredients = new ArrayList<Ingredient>();
    test3Ingredients.add(ing3);
    ArrayList<Integer> test3Amounts = new ArrayList<Integer>();
    test3Amounts.add(5);
    recipes.createRecipe("test3", 3, test3Ingredients, test3Amounts, "test 3 comment", "test 3 instructions"); // price 50

    // Expected response max price: 200
    ArrayList<Recipe> expectedResult200 = new ArrayList<Recipe>();
    expectedResult200.add(recipes.getRecipes().get(0)); // recipe 1
    expectedResult200.add(recipes.getRecipes().get(1)); // recipe 2
    expectedResult200.add(recipes.getRecipes().get(2)); // recipe 3

    // Expected response max price: 100
    ArrayList<Recipe> expectedResult100 = new ArrayList<Recipe>();
    expectedResult100.add(recipes.getRecipes().get(0)); // recipe 1
    expectedResult100.add(recipes.getRecipes().get(2)); // recipe 3

    // Expected response max price: 51
    ArrayList<Recipe> expectedResult51 = new ArrayList<Recipe>();
    expectedResult51.add(recipes.getRecipes().get(0)); // recipe 1
    expectedResult51.add(recipes.getRecipes().get(2)); // recipe 3

    // Expected response max price: 50
    ArrayList<Recipe> expectedResult50 = new ArrayList<Recipe>();
    expectedResult50.add(recipes.getRecipes().get(0)); // recipe 1
    expectedResult50.add(recipes.getRecipes().get(2)); // recipe 3

    // Expected response max price: 49
    ArrayList<Recipe> expectedResult49 = new ArrayList<Recipe>();
    expectedResult49.add(recipes.getRecipes().get(0)); // recipe 1

    // Expected response max price: 0
    ArrayList<Recipe> expectedResult0 = new ArrayList<Recipe>();

    // Expected response max price: -1
    ArrayList<Recipe> expectedResultMinusOne = new ArrayList<Recipe>();

    assertEquals("SearchRecipes did not return correct arrayList using max price 200", expectedResult200, recipes.searchRecipes("200", 2));
    assertEquals("SearchRecipes did not return correct arrayList using max price 100", expectedResult100, recipes.searchRecipes("100", 2));
    assertEquals("SearchRecipes did not return correct arrayList using max price 51", expectedResult51, recipes.searchRecipes("51", 2));
    assertEquals("SearchRecipes did not return correct arrayList using max price 50", expectedResult50, recipes.searchRecipes("50", 2));
    assertEquals("SearchRecipes did not return correct arrayList using max price 49", expectedResult49, recipes.searchRecipes("49", 2));
    assertEquals("SearchRecipes did not return correct arrayList using max price 0", expectedResult0, recipes.searchRecipes("0", 2));
    assertEquals("SearchRecipes did not return correct arrayList using max price -1", expectedResultMinusOne, recipes.searchRecipes("-1", 2));
  
  }
}
