package net.mdrabek.zadanie1;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class ThirdActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third);
    }

    public void returnToMain(View view)
    {
        onBackPressed();
    }

    @Override
    protected void onStop()
    {
        super.onStop();
        Toast.makeText(getApplicationContext(), "Aktywność 3 - Zatrzymana", Toast.LENGTH_SHORT).show();
    }
}
