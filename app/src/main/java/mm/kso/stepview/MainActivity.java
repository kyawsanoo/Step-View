package mm.kso.stepview;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import mm.kso.stepviewlib.HorizontalStepView;

public class MainActivity extends AppCompatActivity {
    HorizontalStepView horizontalStepView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        horizontalStepView = (HorizontalStepView) findViewById(R.id.horizontalStepView);
        horizontalStepView.setProgress(3, 4, getResources().getStringArray(R.array.horizontalStepsArr));

        findViewById(R.id.btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, VerticalStepViewActivity.class));
            }
        });
    }

}
