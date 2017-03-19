package deziko.com.bezierdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import deziko.com.bezierdemo.customview.ElasticBall;

public class CubicActivity extends AppCompatActivity {

    private Button mMoveBtn;
    private ElasticBall mElasticBall;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cubic);
        mElasticBall = (ElasticBall) findViewById(R.id.elasticall);
        mMoveBtn = (Button) findViewById(R.id.moveBtn);
        mMoveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mElasticBall.startAnimation();
            }
        });
    }

}
