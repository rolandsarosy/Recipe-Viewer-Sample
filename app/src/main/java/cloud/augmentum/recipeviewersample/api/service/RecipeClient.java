package cloud.augmentum.recipeviewersample.api.service;

import java.util.List;

import cloud.augmentum.recipeviewersample.api.model.Recipe;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface RecipeClient {

    @GET("getRecipes")
    Call<List<Recipe>> getRecipes();

    @POST("uploadRecipe")
    Call<ResponseBody> submitRecipe(@Body Recipe recipe);
}