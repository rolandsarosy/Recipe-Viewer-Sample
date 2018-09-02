package cloud.augmentum.recipeviewersample.api.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Ingredient {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("quantity")
    @Expose
    private String quantity;
    @SerializedName("type")
    @Expose
    private String type;

    /**
     * No args constructor for use in serialization
     */
    public Ingredient() {
    }

    /**
     * @param id       is the ID of single step for the recipe. It is a continuation of the ID of the recipe
     *                 and is incremented for each step
     * @param name     is the ingredient's name
     * @param quantity is the required quantity of the ingredient
     * @param type     is the ingredient's category, such as 'condiment', 'diary', 'baking', 'produce'.
     */
    public Ingredient(Integer id, String name, String quantity, String type) {
        super();
        this.id = id;
        this.name = name;
        this.quantity = quantity;
        this.type = type;
    }

    /**
     * Overloading the constructor for creating this object (as an ID will not be assigned)
     */
    public Ingredient(String name, String quantity, String type) {
        super();
        this.name = name;
        this.quantity = quantity;
        this.type = type;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Ingredient withId(Integer id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Ingredient withName(String name) {
        this.name = name;
        return this;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public Ingredient withQuantity(String quantity) {
        this.quantity = quantity;
        return this;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Ingredient withType(String type) {
        this.type = type;
        return this;
    }
}