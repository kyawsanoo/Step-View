package mm.kso.stepviewlib;

/**
 * Created by Kyawsan Oo on 6/29/2017.
 */

import android.content.Context;

/**
 * Created by paike on 2017/2/10.
 * xyz@163.com
 */

class Utils {
    /**
     * dip to pixel
     */
    static int dip2px(Context context, float px) {
        final float scale = getScreenDensity(context);
        return (int) (px * scale + 0.5);
    }
    /**
     * get screen density
     */
    private static float getScreenDensity(Context context) {
        return context.getResources().getDisplayMetrics().density;
    }

}
