package model;

public class AvgCostHaving {;
    private String category;
    private float avgCost;

    public AvgCostHaving(String category, float avgCost) {
        this.category = category;
        this.avgCost = avgCost;
    }

    public String getCategory() {
        return category;
    }

    public float getAvgCost() {
        return avgCost;
    }
}
