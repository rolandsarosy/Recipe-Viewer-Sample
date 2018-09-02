package cloud.augmentum.recipeviewersample.api.model;

import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Recipe {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("ingredients")
    @Expose
    private List<Ingredient> ingredients = null;
    @SerializedName("steps")
    @Expose
    private List<String> steps = null;
    @SerializedName("imgUrl")
    @Expose
    private String imgUrl;

    /**
     * No args constructor for use in serialization
     */
    public Recipe() {
    }

    /**
     * @param imgUrl      is the web URL for the image of the recipe
     * @param ingredients is the JSONArray (converted to a List) that contains the individual ingredients
     *                    for the recipe
     * @param id          is the ID given by  the endpoint to a single recipe, which is a continuation
     *                    from the previous recipe's last ingredient ID
     * @param name        is the name of the recipe
     * @param steps       is the JSONArray (converted to a List) that contains the steps to create the recipe
     */
    public Recipe(Integer id, String name, List<Ingredient> ingredients, List<String> steps, String imgUrl) {
        super();
        this.id = id;
        this.name = name;
        this.ingredients = ingredients;
        this.steps = steps;
        this.imgUrl = imgUrl;
    }

    /**
     *  Overloading the constructor for creating this object (as an ID will not be assigned)
     */
    public Recipe(String name, List<Ingredient> ingredients, List<String> steps, String imgUrl){
        super();
        this.name = name;
        this.ingredients = ingredients;
        this.steps = steps;
        this.imgUrl = imgUrl;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Recipe withId(Integer id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Recipe withName(String name) {
        this.name = name;
        return this;
    }

    public List<Ingredient> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }

    public Recipe withIngredients(List<Ingredient> ingredients) {
        this.ingredients = ingredients;
        return this;
    }

    public List<String> getSteps() {
        return steps;
    }

    public void setSteps(List<String> steps) {
        this.steps = steps;
    }

    public Recipe withSteps(List<String> steps) {
        this.steps = steps;
        return this;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public Recipe withImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
        return this;
    }
}