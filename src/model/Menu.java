package model;

public class Menu {
    private String name;
    private float cost;
    private String category;
    
    public Menu(String name, float cost, String category) {
        this.name = name;
        this.category = category;
        this.cost = cost;
    }

    public String getName(){
        return name;
    }
    public float getCost() { return cost;}
    public String getCategory() {return category;}
}
