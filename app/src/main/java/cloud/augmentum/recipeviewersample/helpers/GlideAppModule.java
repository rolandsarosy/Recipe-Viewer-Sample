package cloud.augmentum.recipeviewersample.helpers;

import com.bumptech.glide.annotation.GlideModule;
import com.bumptech.glide.module.AppGlideModule;

/**
 * The single purpose of this class to enable the usage of Glide's generated API. According to the documentation:
 *
 * Glide v4 uses an annotation processor to generate an API that allows applications to access
 * all options in RequestBuilder, RequestOptions and any included integration libraries in a single fluent API.
 */
@GlideModule
public class GlideAppModule extends AppGlideModule {
}
