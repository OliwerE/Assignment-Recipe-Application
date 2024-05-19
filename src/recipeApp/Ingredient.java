package recipeApp;

public class Ingredient {
  private String name;
  private String unit;
  private int price;

  public String getName() {
    return name;
  }
  
  public void setName(String n) {
    name = n;
  }

  public String getUnit() {
    return unit;
  }

  public void setUnit(String u) {
    unit = u;
  }

  public int getPrice() {
    return price;
  }

  public void setPrice(int p) {
    if (p > 0) {
      price = p;
    } else {
      System.out.println(name + ": Price not set: " + p + " is negative!");
    }
  }
}
