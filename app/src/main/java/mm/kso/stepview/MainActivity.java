package mm.kso.stepview;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.Locale;

import mm.kso.stepviewlib.HorizontalStepView;

public class MainActivity extends AppCompatActivity {
    HorizontalStepView horizontalStepView;
    Typeface appTypeface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e("locale", App.getAppInstance().getPreferenceLocale().toString());
        App.getAppInstance().getLocaleAndSetLanguage(this);
        appTypeface = App.getAppInstance().getAppTypeface();

        String[] str = getResources().getStringArray(R.array.steps);
        setContentView(R.layout.activity_main);
        horizontalStepView = (HorizontalStepView) findViewById(R.id.horizontalStepView);
        Log.e("font path", App.getAppInstance().getFontPath());
        horizontalStepView.setCustomFont(this, App.getAppInstance().getFontPath());
        horizontalStepView.setProgress(2, 4, str);

        Button toVerticalStepView = (Button)(findViewById(R.id.btn));
        toVerticalStepView.setTypeface(appTypeface);
        toVerticalStepView.setText(R.string.see_verticle);
        toVerticalStepView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, VerticalStepViewActivity.class));
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.en:
                App.getAppInstance().savePreferenceLocale("en");
                break;
            case R.id.zawgyi:
                App.getAppInstance().savePreferenceLocale("mn");
                break;
            case R.id.mm3:
                App.getAppInstance().savePreferenceLocale("my");
                break;
        }
        finish();
        startActivity(getIntent());

        return super.onOptionsItemSelected(item);
    }
}
