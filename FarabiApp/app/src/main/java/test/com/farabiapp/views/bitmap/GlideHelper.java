package test.com.farabiapp.views.bitmap;

import java.io.IOException;
import java.io.InputStream;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.data.DataFetcher;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.model.stream.StreamModelLoader;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.widget.ImageView;

/**
 * Created by sathya on 25/08/16.
 */
public class GlideHelper {

    /**
     * Fill an ImageView with a picture from the resources using Glide.
     *
     * @param context
     *            the Context for where to load
     * @param imageView
     *            the ImageView to fill with an image
     * @param resDrawableId
     *            the resource drawable id
     */
    public static void resDrawableToImageView(Context context, ImageView imageView, int resDrawableId) {
        if (context == null || imageView == null) {
            return;
        }
        Glide.with(context).load(resDrawableId).into(imageView);
    }

    /**
     * Fill an ImageView with a picture from an http link using Glide.
     *
     * @param context
     *            the Context for where to load
     * @param imageView
     *            the ImageView to fill with an image
     * @param imageUrl
     *            the image url from which Glide should download and cache the
     *            image
     */
    public static void urlToImageView(Context context, ImageView imageView, @NonNull String imageUrl) {
        urlToImageView(context, imageView, imageUrl, false);
    }

    /**
     * Fill an ImageView with a picture from an http link using Glide, else load
     * placeholder.
     *
     * @param context
     *            the context
     * @param imageView
     *            the image view
     * @param imageUrl
     *            the image url
     * @param useCacheOnly
     *            the use cache only
     * @param placeholder
     *            the placeholder
     */
    public static void urlToImageView(Context context, ImageView imageView, @NonNull String imageUrl, boolean useCacheOnly,
            Drawable placeholder) {

        if (context == null || imageView == null) {
            return;
        }
        if (useCacheOnly) {
            Glide.with(context).using(cacheOnlyStreamLoader).load(imageUrl).fitCenter().override(350, 200).placeholder(placeholder)
                    .into(imageView);
            return;
        }
        Glide.with(context).load(imageUrl).placeholder(placeholder).diskCacheStrategy(DiskCacheStrategy.ALL).into(imageView);

    }

    /**
     * Fill an ImageView with a picture from an Http link using Glide.
     *
     * @param context
     *            the Context for where to load
     * @param imageView
     *            the ImageView to fill with an image
     * @param imageUrl
     *            the image url from which Glide should download and cache the
     *            image
     * @param useCacheOnly
     *            whether to only use the cache to load the pictures or allow
     *            downloading the picture if the picture is not found in the
     *            cache.
     */
    public static void urlToImageView(Context context, ImageView imageView, @NonNull String imageUrl, boolean useCacheOnly) {
        if (context == null || imageView == null) {
            return;
        }
        if (useCacheOnly) {
            Glide.with(context).using(cacheOnlyStreamLoader).load(imageUrl).into(imageView);
            return;
        }
        Glide.with(context).load(imageUrl).diskCacheStrategy(DiskCacheStrategy.ALL).into(imageView);
    }

    private static final StreamModelLoader<String> cacheOnlyStreamLoader = new StreamModelLoader<String>() {
        @Override
        public DataFetcher<InputStream> getResourceFetcher(final String model, int i, int i1) {
            return new DataFetcher<InputStream>() {
                @Override
                public InputStream loadData(Priority priority) throws Exception {
                    throw new IOException();
                }

                @Override
                public void cleanup() {

                }

                @Override
                public String getId() {
                    return model;
                }

                @Override
                public void cancel() {

                }
            };
        }
    };
}