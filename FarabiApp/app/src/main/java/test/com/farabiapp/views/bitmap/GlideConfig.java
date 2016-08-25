package test.com.farabiapp.views.bitmap;

import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.module.GlideModule;

import android.content.Context;
import android.support.annotation.NonNull;

/**
 * Created by sathya on 25/08/16.
 */
public class GlideConfig implements GlideModule {
    @Override
    public void applyOptions(Context context, @NonNull GlideBuilder builder) {
        builder.setDecodeFormat(DecodeFormat.PREFER_ARGB_8888);

    }

    @Override
    public void registerComponents(Context context, Glide glide) {

    }
}
