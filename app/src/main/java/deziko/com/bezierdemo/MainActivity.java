package deziko.com.bezierdemo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private Button mQuadToBtn,mCubicToBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mQuadToBtn = (Button) findViewById(R.id.quadToBtn);
        mQuadToBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,QuadActivity.class);
                startActivity(intent);
            }
        });
        mCubicToBtn = (Button) findViewById(R.id.cubicToBtn);
        mCubicToBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,CubicActivity.class);
                startActivity(intent);
            }
        });
    }
}
