package net.mdrabek.zadanie1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final Intent secondActivityIntent = new Intent(this, SecondActivity.class);
        Button btn1 = (Button) findViewById(R.id.button1);
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(secondActivityIntent);
            }
        });
    }

    public void startSecondActivity(View view)
    {
        final Intent secondActivityIntent = new Intent(this, SecondActivity.class);
        startActivity(secondActivityIntent);
    }

    public void startThirdActivity(View view)
    {
        final Intent thirdActivityIntent = new Intent(this, ThirdActivity.class);
        startActivity(thirdActivityIntent);
    }
}
