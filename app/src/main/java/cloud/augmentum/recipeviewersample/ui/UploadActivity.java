package cloud.augmentum.recipeviewersample.ui;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.cloudinary.android.MediaManager;

import java.util.ArrayList;
import java.util.List;

import cloud.augmentum.recipeviewersample.R;
import cloud.augmentum.recipeviewersample.api.model.Ingredient;
import cloud.augmentum.recipeviewersample.api.model.Recipe;
import cloud.augmentum.recipeviewersample.api.service.RecipeClient;
import cloud.augmentum.recipeviewersample.api.service.ServiceGenerator;
import okhttp3.ResponseBody;
import retrofit2.Callback;
import retrofit2.Response;

// TODO: Tech debt. The method flow of this class can be vastly improved.
public class UploadActivity extends AppCompatActivity {

    private static final String LOG_TAG = UploadActivity.class.getSimpleName();

    private List<Ingredient> mRecipeIngredients;
    private List<String> mRecipeSteps;
    private CoordinatorLayout mCoordinatorLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload);
        mCoordinatorLayout = findViewById(R.id.input_coordinator_layout);

        // Create new but empty instances of the Ingredients and Steps
        mRecipeSteps = new ArrayList<>();
        mRecipeIngredients = new ArrayList<>();

        // Set up the buttons with the onClickListeners and helper methods
        setupClickListeners();

        // Initialize the Cloudinary MediaManager
        MediaManager.init(this);

    }

    /**
     * Helper method that puts the current content of the Step EditText into the global variable List
     * and updates the TextView displaying the list of steps.
     */
    private void addStep() {
        // Find reference to the EditText and TextView fields of the 'Add step' section
        EditText stepEditText = findViewById(R.id.input_field_steps);
        TextView stepListTextView = findViewById(R.id.input_list_steps);

        // Get the current step from the EditText with validation that it is not empty
        if (TextUtils.isEmpty(stepEditText.getText())) {
            stepEditText.setError(getString(R.string.upload_view_hint_error_empty_field));
        } else {
            String currentStep = stepEditText.getText().toString().trim();

            // Append the TextView with this content and formatting
            stepListTextView.append(currentStep + "\n");

            // Make the TextView visible if its not already
            if (stepListTextView.getVisibility() != View.VISIBLE) {
                stepListTextView.setVisibility(View.VISIBLE);
            }

            // Add to the List of steps
            mRecipeSteps.add(currentStep);

            // Clear up the EditText
            stepEditText.setText("");
        }
    }

    /**
     * Helper method that puts the current content of the 3 Ingredient EditText fields into
     * the global variable list with validation and updates the TextView displaying the current list of ingredients.
     */
    private void addIngredient() {
        // Find reference to the Views we'll use
        EditText ingredientNameEditText = findViewById(R.id.input_field_ingredients_name);
        EditText ingredientQuantityEditText = findViewById(R.id.input_field_ingredients_quantity);
        EditText ingredientTypeEditText = findViewById(R.id.input_field_ingredients_type);
        TextView ingredientsListTextView = findViewById(R.id.input_list_ingredients);

        // Get the current ingredients with validation
        if (TextUtils.isEmpty(ingredientNameEditText.getText())) {
            ingredientNameEditText.setError(getString(R.string.upload_view_hint_error_empty_field));
        } else if (TextUtils.isEmpty(ingredientQuantityEditText.getText())) {
            ingredientQuantityEditText.setError(getString(R.string.upload_view_hint_error_empty_field));
        } else if (TextUtils.isEmpty(ingredientTypeEditText.getText())) {
            ingredientTypeEditText.setError(getString(R.string.upload_view_hint_error_empty_field));
        } else {

            String currentIngredientName = ingredientNameEditText.getText().toString().trim();
            String currentIngredientQuantity = ingredientQuantityEditText.getText().toString().trim();
            String currentIngredientType = ingredientTypeEditText.getText().toString().trim();

            // Append the TextView with this content and formatting
            ingredientsListTextView.append(currentIngredientName + ", " + currentIngredientQuantity + "\n");

            // Make the TextView visible if its not already
            if (ingredientsListTextView.getVisibility() != View.VISIBLE) {
                ingredientsListTextView.setVisibility(View.VISIBLE);
            }

            // Create an Ingredient object and add this to the List
            Ingredient currentIngredient = new Ingredient(currentIngredientName, currentIngredientQuantity, currentIngredientType);
            mRecipeIngredients.add(currentIngredient);

            // Clear up all 3 EditTexts
            ingredientNameEditText.setText("");
            ingredientQuantityEditText.setText("");
            ingredientTypeEditText.setText("");
        }
    }

    /**
     * Helper method to set up the click listeners on each button
     */
    private void setupClickListeners() {
        // Find reference to the Add Ingredient button and set the listener
        Button addIngredientButton = findViewById(R.id.input_btn_add_ingredient);
        addIngredientButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addIngredient();
            }
        });

        // Find reference to the Add Step button and set the listener
        Button addStepButton = findViewById(R.id.input_btn_add_step);
        addStepButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addStep();
            }
        });

        // Find reference to the Submit button and set the listener
        Button submitButton = findViewById(R.id.input_btn_submit);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendPostRequest();
            }
        });
    }

    /**
     * Helper method that creates the custom object from the available data and
     * prepares the network call, then makes the POST request.
     */
    private void sendPostRequest() {
        // Find reference to the views we'll use and set variables
        EditText nameEditText = findViewById(R.id.input_field_name);
        EditText imageUrlEditText = findViewById(R.id.input_field_image);
        String recipeName;
        String recipeImageUrl;

        // Get the data from the Name and ImageUrl fields and validate the remaining data
        if (TextUtils.isEmpty(nameEditText.getText())) {
            nameEditText.setError(getString(R.string.upload_view_hint_error_empty_field));
            createValidationErrorSnackBar();
            return;
        } else {
            recipeName = nameEditText.getText().toString().trim();
        }

        if (TextUtils.isEmpty(imageUrlEditText.getText())) {
            imageUrlEditText.setError(getString(R.string.upload_view_hint_error_empty_field));
            createValidationErrorSnackBar();
            return;
        } else {
            recipeImageUrl = imageUrlEditText.getText().toString().trim();
        }

        if (mRecipeIngredients.size() == 0 | mRecipeSteps.size() == 0) {
            createValidationErrorSnackBar();
            return;
        }

        // Create the Recipe object (this point can only be reached with valid data)
        Recipe preparedRecipe = new Recipe(recipeName, mRecipeIngredients, mRecipeSteps, recipeImageUrl);

        // Prepare the network call
        RecipeClient client = ServiceGenerator.createService(RecipeClient.class);
        retrofit2.Call<ResponseBody> call = client.submitRecipe(preparedRecipe);

        // Make the POST request asynchronously
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(retrofit2.Call<ResponseBody> call, Response<ResponseBody> response) {
                mCoordinatorLayout = findViewById(R.id.input_coordinator_layout);
                Snackbar
                        .make(mCoordinatorLayout, R.string.upload_view_hint_upload_successful, Snackbar.LENGTH_LONG)
                        .setAction(getString(R.string.upload_view_hint_leave), new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                finish();
                            }
                        })
                        .show();
            }

            @Override
            public void onFailure(retrofit2.Call<ResponseBody> call, Throwable t) {
                mCoordinatorLayout = findViewById(R.id.input_coordinator_layout);
                Snackbar
                        .make(mCoordinatorLayout, R.string.upload_view_hint_upload_unsuccessful, Snackbar.LENGTH_LONG)
                        .setAction(getString(R.string.upload_view_hint_retry), new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                sendPostRequest();
                            }
                        })
                        .show();

                Log.d(LOG_TAG, t.toString());
            }
        });
    }

    /**
     * Helper method to create a SnackBar to notify the user that some fields are empty
     */
    private void createValidationErrorSnackBar() {
        Snackbar
                .make(mCoordinatorLayout, R.string.upload_view_hint_empty_field, Snackbar.LENGTH_SHORT)
                .show();
    }

    /**
     * This overriding makes sure that we can lose focus and hide the software keyboard
     */
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        View view = getCurrentFocus();
        if (view != null && (ev.getAction() == MotionEvent.ACTION_UP || ev.getAction() == MotionEvent.ACTION_MOVE) && view instanceof EditText && !view.getClass().getName().startsWith("android.webkit.")) {
            int scrcoords[] = new int[2];
            view.getLocationOnScreen(scrcoords);
            float x = ev.getRawX() + view.getLeft() - scrcoords[0];
            float y = ev.getRawY() + view.getTop() - scrcoords[1];
            if (x < view.getLeft() || x > view.getRight() || y < view.getTop() || y > view.getBottom())
                ((InputMethodManager) this.getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow((this.getWindow().getDecorView().getApplicationWindowToken()), 0);
        }
        return super.dispatchTouchEvent(ev);
    }
}