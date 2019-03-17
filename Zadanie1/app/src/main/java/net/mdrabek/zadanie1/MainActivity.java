package net.mdrabek.zadanie1;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v4.widget.ImageViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final Intent secondActivityIntent = new Intent(this, SecondActivity.class);
        Button btn1 = (Button) findViewById(R.id.activity2btn);
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(secondActivityIntent);
            }
        });

        Switch langSwitch = findViewById(R.id.languageSwitch);
        langSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Button activity2btn = findViewById(R.id.activity2btn);
                Button activity3btn = findViewById(R.id.activity3btn);

                if(isChecked)
                {
                    activity2btn.setText(R.string.activity2en);
                    activity3btn.setText(R.string.activity3en);
                }
                else
                {
                    activity2btn.setText(R.string.activity2pl);
                    activity3btn.setText(R.string.activity3pl);
                }
            }
        });

        final ImageView imgView = findViewById(R.id.img);
        imgView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if(imgView.getAlpha() < 1.0f)
                {
                    imgView.setAlpha(1.0f);
                }
                else
                {
                    imgView.setAlpha(0.3f);
                }
                return true;
            }
        });
    }

//    public void startSecondActivity(View view)
//    {
//        final Intent secondActivityIntent = new Intent(this, SecondActivity.class);
//        startActivity(secondActivityIntent);
//    }

    public void startThirdActivity(View view)
    {
        final Intent thirdActivityIntent = new Intent(this, ThirdActivity.class);
        startActivity(thirdActivityIntent);
    }
}
