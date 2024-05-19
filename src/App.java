import recipeApp.RecipeApp;

public class App {
  public static void main(String[] args) throws Exception {
    try {
      RecipeApp application = new RecipeApp();
      System.out.println("Application starts...");
      application.startApp();
    } catch (Exception e) {
      System.out.println("An error has occured: " + e);
      System.exit(1);
    } 
  }
}
