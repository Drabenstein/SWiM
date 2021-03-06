package net.mdrabek.zadanie2;

import android.app.Activity;
import android.os.Bundle;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class Lista2 extends Activity implements AdapterView.OnItemClickListener {

    private String[] items = new String[] {
            "Styczeń", "Luty", "Marzec", "Kwiecień", "Maj", "Czerwiec",
            "Lipiec", "Sierpień", "Wrzesień", "Październik", "Listopad", "Grudzień"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista2);

        ListView lista2 = findViewById(R.id.multichoiceListView);
        lista2.setOnItemClickListener(this);
        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_multiple_choice, items);
        lista2.setAdapter(adapter2);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        ListView list = findViewById(R.id.multichoiceListView);

        String text = "";
        SparseBooleanArray checked = list.getCheckedItemPositions();
        for(int i = 0; i < checked.size(); i++)
        {
            if(checked.valueAt(i))
            {
                int index = checked.keyAt(i);
                text += (" " + String.valueOf(index + 1));
            }
        }

        Toast.makeText(this, "Wybrałeś: " + text, Toast.LENGTH_SHORT).show();
    }
}
