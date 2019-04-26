package net.mdrabek.zadanie3;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

public class XMLBasedMenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xmlbased_menu);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.my_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.reportBug:
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse("http://mdrabek.net/bugs"));
                startActivity(i);
                break;
            case R.id.colorRed:
                TextView output = findViewById(R.id.outputTextView);
                output.setTextColor(Color.RED);
                break;
            case R.id.changeFont:
                TextView outputTextView = findViewById(R.id.outputTextView);
                outputTextView.setTextAppearance(this, R.style.changedFont);
                break;
        }

        return super.onOptionsItemSelected(item);
    }
}
