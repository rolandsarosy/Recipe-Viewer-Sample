package cloud.augmentum.recipeviewersample.ui;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.BulletSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

import cloud.augmentum.recipeviewersample.R;
import cloud.augmentum.recipeviewersample.api.model.Ingredient;
import cloud.augmentum.recipeviewersample.api.model.Recipe;
import cloud.augmentum.recipeviewersample.helpers.GlideApp;

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.RecipeViewHolder> {

    private static final String LOG_TAG = RecipeAdapter.class.getSimpleName();

    // The mDataset is the List of Recipe objects returned by the network call
    private List<Recipe> mDataset;
    private Context mContext;

    /**
     * Establishing the ViewHolder pattern for this RecyclerView
     */
    public static class RecipeViewHolder extends RecyclerView.ViewHolder {
        public ImageView recipeImage;
        public TextView recipeName;
        public RelativeLayout parentLayout;

        public RecipeViewHolder(View itemView) {
            super(itemView);
            recipeImage = itemView.findViewById(R.id.list_item_recipe_image);
            recipeName = itemView.findViewById(R.id.list_item_recipe_name);
            parentLayout = itemView.findViewById(R.id.list_item_parent);
        }
    }

    /**
     * Public constructor for the adapter
     *
     * @param dataset is the mDataset passed to the constructor. Should contain a List of Recipe objects
     */
    public RecipeAdapter(Context context, List<Recipe> dataset) {
        this.mDataset = dataset;
        this.mContext = context;
    }

    @NonNull
    @Override
    public RecipeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item, parent, false);

        return new RecipeViewHolder(itemView);
    }

    /**
     * Binding the data to each of the list items.
     *
     * @param holder   is an instance of the ViewHolder pattern
     * @param position is the position of the single item in the list
     */
    @Override
    public void onBindViewHolder(@NonNull RecipeViewHolder holder, final int position) {
        // Get the current Recipe object
        Recipe recipe = mDataset.get(position);

        // Get the image URL and set it for this Recipe with Glide
        String imageUrl = recipe.getImgUrl();
        GlideApp
                .with(mContext)
                .load(imageUrl)
                .centerCrop()
                .into(holder.recipeImage);

        // Set the text for this Recipe
        holder.recipeName.setText(recipe.getName());

        // TODO: Tech debt. Needs improvements upon this by creating and passing a parcelable instead of primitive data types.
        holder.parentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(LOG_TAG, "Clicked this recipe: " + mDataset.get(position).getName());
                // Initialize the data we'll pass to the intent
                String passedImgUrl = mDataset.get(position).getImgUrl();
                String passedName = mDataset.get(position).getName();
                String passedNumberOfIngredients = Integer.toString(mDataset.get(position).getIngredients().size()) + " " + mContext.getString(R.string.detail_view_label_number_of_ingredients);
                String passedIngredients = "";
                String passedSteps = "";

                // We need to format the individual ingredients into one, passable String
                // TODO: Update this with SpannableString and SpannableStringBuilder for a better bulleted list
                List<Ingredient> ingredientsList = mDataset.get(position).getIngredients();
                for (int i = 0; i < ingredientsList.size(); i++) {
                    passedIngredients = passedIngredients
                            + "- "
                            + ingredientsList.get(i).getName()
                            + ", "
                            + ingredientsList.get(i).getQuantity()
                            + "\n";
                }

                // We need to get the individual steps into one, passable String
                List<String> stepsList = mDataset.get(position).getSteps();
                for (int i = 0; i < stepsList.size(); i++) {
                    passedSteps = passedSteps + stepsList.get(i) + " ";
                }

                Intent intent = new Intent(mContext, DetailsActivity.class);
                intent.putExtra("recipe_image_url", passedImgUrl);
                intent.putExtra("recipe_name", passedName);
                intent.putExtra("recipe_number_of_ingredients", passedNumberOfIngredients);
                intent.putExtra("recipe_ingredients", passedIngredients);
                intent.putExtra("recipe_steps", passedSteps);

                mContext.startActivity(intent);
            }
        });
    }

    /**
     * @return the size of the mDataset
     */
    @Override
    public int getItemCount() {
        return mDataset.size();
    }
}