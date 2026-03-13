package org.alt.model;

public class Transaction {
    private int id;
    private String type;
    private int amount;
    private String category;

    public Transaction(int id, String type, int amount, String category) {
        this.id = id;
        this.type = type;
        this.amount = amount;
        this.category = category;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Object getProperty(String property) {
        return switch (property) {
            case "id" -> id;
            case "type" -> type;
            case "amount" -> amount;
            case "category" -> category;
            default -> null;
        };
    }
}
