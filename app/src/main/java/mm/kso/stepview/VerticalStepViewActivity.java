package mm.kso.stepview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;


import mm.kso.stepviewlib.VerticalStepView;

public class VerticalStepViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e("locale", App.getAppInstance().getPreferenceLocale().toString());
        App.getAppInstance().getLocaleAndSetLanguage(this);

        String[] steps = getResources().getStringArray(R.array.steps);
        setContentView(R.layout.activity_vertical_stepview);
        VerticalStepView veritcalStepView = (VerticalStepView) findViewById(R.id.vflow);
        Log.e("font path", App.getAppInstance().getFontPath());
        veritcalStepView.setCustomFont(this, App.getAppInstance().getFontPath());
        veritcalStepView.setProgress(3, 4, steps);
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
