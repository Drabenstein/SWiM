package net.mdrabek.zadanie2;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

public class Lista3 extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista3);

        MySecondAdapter mySecondAdapter = new MySecondAdapter(this);
        ListView list = findViewById(R.id.list3View);
        list.setAdapter(mySecondAdapter);

        SharedPreferences sp = getSharedPreferences("dane_apki", MODE_PRIVATE);
        SharedPreferences.Editor spe = sp.edit();
        int nru = sp.getInt("nr_uruchomienia", 0);
        nru++;
        Toast.makeText(getApplicationContext(), "Uruchomienie nr " + nru, Toast.LENGTH_SHORT).show();
        spe.putInt("nr_uruchomienia", nru);
        spe.commit();
    }
}
