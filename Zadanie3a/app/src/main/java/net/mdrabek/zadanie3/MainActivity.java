package net.mdrabek.zadanie3;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onXMLBasedMenuButtonClick(View view)
    {
        final Intent xmlBasedMenuActivityIntent = new Intent(this, XMLBasedMenuActivity.class);
        startActivity(xmlBasedMenuActivityIntent);
    }

    public void onJavaBasedMenuButtonClick(View view)
    {
        final Intent javaBasedMenuActivityIntent = new Intent(this, JavaBasedMenuActivity.class);
        startActivity(javaBasedMenuActivityIntent);
    }

    public void onContextMenuButtonClick(View view)
    {
        final Intent contextMenuActivityIntent = new Intent(this, ContextMenuActivity.class);
        startActivity(contextMenuActivityIntent);
    }
}
