package cloud.augmentum.recipeviewersample.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.List;

import cloud.augmentum.recipeviewersample.R;
import cloud.augmentum.recipeviewersample.api.model.Recipe;
import cloud.augmentum.recipeviewersample.api.service.RecipeClient;
import cloud.augmentum.recipeviewersample.api.service.ServiceGenerator;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CatalogActivity extends AppCompatActivity {

    private static final String LOG_TAG = CatalogActivity.class.getSimpleName();

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private List<Recipe> mDataset;
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_catalog);
        mContext = this;

        // Find reference to the RecyclerView and set it to fixed size for performance improvements
        mRecyclerView = findViewById(R.id.recipe_recycler_view);
        mRecyclerView.setHasFixedSize(true);

        // Create and set the LayoutManager and ItemAnimators
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());

        // Setup the Floating Action Button
        setupFab();

        // Make the initial network call
        makeNetworkCall();
    }

    /**
     * Helper method two switch the visibility of the ProgressBar depending on the input
     *
     * @param makeVisible marks whether the view should be turned TO visible or not
     */
    private void switchProgressBarVisibility(boolean makeVisible) {
        ProgressBar progressBar = findViewById(R.id.progress_bar);
        if (makeVisible) {
            progressBar.setVisibility(View.VISIBLE);
        } else {
            progressBar.setVisibility(View.GONE);
        }
    }

    /**
     * Helper method to switch the visibility of the EmptyTextView depending on the input
     *
     * @param makeVisible marks whether the view should be turned TO visible or not
     */
    private void switchEmptyTextViewVisibility(boolean makeVisible) {
        TextView textView = findViewById(R.id.empty_text_view);
        if (makeVisible) {
            textView.setVisibility(View.VISIBLE);
        } else {
            textView.setVisibility(View.GONE);
        }
    }

    /**
     * Helper method to add onClickListener to the F.A.B.
     */
    private void setupFab() {
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, UploadActivity.class);
                startActivity(intent);
            }
        });
    }

    private void makeNetworkCall() {
        // Create a Retrofit client with the ServiceGenerator class and prepare the network call
        RecipeClient client = ServiceGenerator.createService(RecipeClient.class);
        Call<List<Recipe>> call = client.getRecipes();

        // Make the network call on a background thread
        call.enqueue(new Callback<List<Recipe>>() {
            @Override
            public void onResponse(Call<List<Recipe>> call, Response<List<Recipe>> response) {
                // Parse the data and set the adapter
                mDataset = response.body();
                mAdapter = new RecipeAdapter(mContext, mDataset);
                mRecyclerView.setAdapter(mAdapter);
                switchProgressBarVisibility(false);
            }

            @Override
            public void onFailure(Call<List<Recipe>> call, Throwable t) {
                // Create a SnackBar message and show it to the user
                CoordinatorLayout coordinatorLayout = findViewById(R.id.coordinator_layout);
                Snackbar snackbar = Snackbar
                        .make(coordinatorLayout, R.string.catalog_view_hint_network_error, Snackbar.LENGTH_INDEFINITE)
                        // Set up the Retry action to make the call again
                        .setAction(R.string.catalog_view_hint_retry, new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                switchEmptyTextViewVisibility(false);
                                switchProgressBarVisibility(true);
                                makeNetworkCall();
                            }
                        });
                snackbar.show();
                switchProgressBarVisibility(false);
                switchEmptyTextViewVisibility(true);
            }
        });
    }
}