package group.lsg.resultinvestmentapp.Class;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.DisplayMetrics;

import com.bumptech.glide.util.LruCache;

public class LruBitmapCache  extends LruCache<String, Bitmap>
        implements ImageCache  {
    /**
     * Constructor for LruCache.
     *
     * @param maxSize The maximum size of the cache, the units must match the units used in {@link
     *             #getSize(Object)}.
     */
    public LruBitmapCache(long maxSize) {
        super(maxSize);
    }

    public LruBitmapCache(Context ctx) {
        this(getCacheSize(ctx));
    }


    protected int sizeOf(String key, Bitmap value) {
        return value.getRowBytes() * value.getHeight();
    }

    public Bitmap getBitmap(String url) {
        return get(url);
    }

    public void putBitmap(String url, Bitmap bitmap) {
        put(url, bitmap);
    }

    // Returns a cache size equal to approximately three screens worthof images.
    public static int getCacheSize(Context ctx) {
        final DisplayMetrics displayMetrics = ctx.getResources().
                getDisplayMetrics();
        final int screenWidth = displayMetrics.widthPixels;
        final int screenHeight = displayMetrics.heightPixels;
        // 4 bytes per pixel
        final int screenBytes = screenWidth * screenHeight * 4;

        return screenBytes * 3;
    }
}
