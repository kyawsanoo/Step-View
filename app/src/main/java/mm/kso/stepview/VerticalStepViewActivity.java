package mm.kso.stepview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;


import mm.kso.stepviewlib.VerticalStepView;

public class VerticalStepViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vertical_stepview);
        VerticalStepView verticalStepView = (VerticalStepView) findViewById(R.id.verticalStepView);
        verticalStepView.setProgress(3, 4, getResources().getStringArray(R.array.verticalStepsArr));
    }
}
