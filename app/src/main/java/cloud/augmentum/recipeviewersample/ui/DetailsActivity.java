package cloud.augmentum.recipeviewersample.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import cloud.augmentum.recipeviewersample.R;
import cloud.augmentum.recipeviewersample.helpers.GlideApp;

public class DetailsActivity extends AppCompatActivity {

    private String recipeImageUrl;
    private String recipeName;
    private String recipeNumberOfIngredients;
    private String recipeIngredients;
    private String recipeSteps;
    private Context mContext;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        mContext = this;

        // Get the values from the activity's starter intent
        getStarterIntentValues();

        // Setup the Views from the global variables
        setupViews();
    }

    /**
     * Helper method to get the starter intent's values
     */
    private void getStarterIntentValues() {
        // Get the starter intent into an Intent object
        Intent starterIntent = getIntent();
        // Get the data from said intent
        recipeImageUrl = starterIntent.getStringExtra("recipe_image_url");
        recipeName = starterIntent.getStringExtra("recipe_name");
        recipeNumberOfIngredients = starterIntent.getStringExtra("recipe_number_of_ingredients");
        recipeIngredients = starterIntent.getStringExtra("recipe_ingredients");
        recipeSteps = starterIntent.getStringExtra("recipe_steps");
    }

    /**
     * Helper method to setup the views from the global variables created
     */
    private void setupViews() {
        // Find reference to every View we'll populate
        ImageView recipeImageView = findViewById(R.id.detail_view_recipe_image);
        TextView recipeNameTextView = findViewById(R.id.detail_view_recipe_name);
        TextView recipeNumberOfIngredientsTextView = findViewById(R.id.detail_view_information_ingredients);
        TextView recipeIngredientsTextView = findViewById(R.id.detail_view_ingredients_list);
        TextView recipeStepsTextView = findViewById(R.id.detail_view_steps_list);

        // Load and scale the image with Glide
        GlideApp
                .with(mContext)
                .load(recipeImageUrl)
                .centerCrop()
                .into(recipeImageView);

        // Set the rest of the Views normally
        recipeNameTextView.setText(recipeName);
        recipeNumberOfIngredientsTextView.setText(recipeNumberOfIngredients);
        recipeIngredientsTextView.setText(recipeIngredients);
        recipeStepsTextView.setText(recipeSteps);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle the user click in the back button properly
        switch (item.getItemId()) {
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(DetailsActivity.this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
