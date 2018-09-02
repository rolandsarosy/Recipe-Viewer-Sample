package cloud.augmentum.recipeviewersample.api.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * The purpose of using this service generator is that we only open 1 web socket at all times by
 * ensuring that this class is used and every single object is static so that one instance is created
 */
public class ServiceGenerator {

    private static final String BASE_REQUEST_URL = "http://94.177.216.191:7072/";

    private static Gson gson = new GsonBuilder()
            .setLenient()
            .create();

    private static OkHttpClient httpClient = new OkHttpClient();

    private static Retrofit.Builder builder =
            new Retrofit.Builder()
                    .baseUrl(BASE_REQUEST_URL)
                    .client(httpClient)
                    .addConverterFactory(GsonConverterFactory.create(gson));

    private static Retrofit retrofit = builder.build();

    private static OkHttpClient.Builder httpClientBuilder = new OkHttpClient.Builder();

    public static <S> S createService(Class<S> serviceClass) {
        return retrofit.create(serviceClass);
    }
}
