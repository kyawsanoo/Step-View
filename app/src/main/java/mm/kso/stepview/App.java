package mm.kso.stepview;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Typeface;
import java.util.Locale;

/**
 * Created by Kyawsan Oo on 4/6/2017.
 */

public class App extends Application {

    private static App appInstance;
    private String LOCALE_PREFERENCE_NAME = "LOCALE_PREFERENCE_NAME";
    String boldFontPath = "fonts/Roboto-Bold.ttf";
    String fontPath;
    String enFontPath = "fonts/Roboto-Regular.ttf";
    String zawgyiFontPath = "fonts/ZawgyiOne.ttf";
    String unicodeFontPath = "fonts/mm3-multi-os(16-08-2011).ttf";
    private String PREFERENCE_LOCALE_STRING = "PREFERENCE_LOCALE_STRING";
    String preferenceLocale;
    Typeface appTypeface, appBoldTypeface;

    String BURMA = "my", MYANMAR = "mn";


    @Override
    public void onCreate() {
        super.onCreate();
        appInstance = this;

    }

    public String getFontPath(){
        return fontPath;
    }


    public static App getAppInstance() {
        return appInstance;
    }

    public void savePreferenceLocale(String language){
        SharedPreferences shp = getSharedPreferences(
                LOCALE_PREFERENCE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = shp.edit();
        editor.putString(PREFERENCE_LOCALE_STRING, language);
        editor.commit();
    }

    public String getPreferenceLocale(){
        SharedPreferences shp = getSharedPreferences(
                LOCALE_PREFERENCE_NAME,Context.MODE_PRIVATE);
        String locale = shp.getString(PREFERENCE_LOCALE_STRING, "en");
        return locale;
    }

    public Typeface getEnglishBoldTypeface(Context context) {
        Typeface typeface = Typeface.createFromAsset(context.getAssets(), boldFontPath);
        return typeface;
    }

    public Typeface getEnglishTypeface(Context context) {
        Typeface typeface = Typeface.createFromAsset(context.getAssets(), enFontPath);
        return typeface;
    }

    public Typeface getZawGyiTypeface(Context context) {
        Typeface typeface = Typeface.createFromAsset(context.getAssets(), zawgyiFontPath);
        return typeface;
    }

    public Typeface getUnicodeTypeface(Context context) {
        Typeface typeface = Typeface.createFromAsset(context.getAssets(), unicodeFontPath);
        return typeface;
    }


    public void getLocaleAndSetLanguage(Context context){
        preferenceLocale = getPreferenceLocale();
            if(preferenceLocale.equals(MYANMAR)){
                appTypeface = getZawGyiTypeface(this);
                appBoldTypeface = getZawGyiTypeface(this);
                fontPath = zawgyiFontPath;
            }else if(preferenceLocale.equals(BURMA)){
                appTypeface = getUnicodeTypeface(this);
                appBoldTypeface = getUnicodeTypeface(this);
                fontPath = unicodeFontPath;
            }else{
                appTypeface = App.getAppInstance().getEnglishTypeface(this);
                appBoldTypeface = getEnglishBoldTypeface(this);
                fontPath = enFontPath;
            }

            changeLanguage(context, preferenceLocale);
    }

    public void changeLanguage(Context context, String languageCode){
        Locale locale = new Locale(languageCode);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        context.getApplicationContext().getResources().updateConfiguration(config, null);
    }

    public Typeface getAppTypeface(){
        return this.appTypeface;
    }

}
