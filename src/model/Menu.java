package model;

public class Menu {
    private String name;
    private float cost;
    private String category;

    public Menu(String name, String category) {
        this.name = name;
        this.category = category;
        cost = 0.0f;
    }

    public Menu(String name, float cost, String category) {
        this.name = name;
        this.cost = cost;
        this.category = category;
    }

    public String getName() {
        return name;
    }

    public float getCost() {
        return cost;
    }

    public String getCategory() {
        return category;
    }
}
