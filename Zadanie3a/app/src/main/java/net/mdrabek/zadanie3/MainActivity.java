package net.mdrabek.zadanie3;

import android.content.Intent;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView.OnNavigationItemSelectedListener onNavigationItemSelectedListener
            = item -> {
                switch (item.getItemId()) {
                    case R.id.navXmlMenu:
                        findViewById(R.id.xmlBasedMenuButton).performClick();
                        return true;
                    case R.id.navJavaMenu:
                        findViewById(R.id.javaBasedMenuButton).performClick();
                        return true;
                    case R.id.navContextMenu:
                        findViewById(R.id.contextMenuButton).performClick();
                        return true;
                    case R.id.navCheckableContextMenu:
                        findViewById(R.id.checkableContextMenuButton).performClick();
                        return true;
                }
                return false;
            };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView navigation = findViewById(R.id.bottomNavigation);
        navigation.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener);
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

    public void onCheckableContextMenuButtonClick(View view)
    {
        final Intent checkableContextMenuActivityIntent = new Intent(this, CheckableContextMenuActivity.class);
        startActivity(checkableContextMenuActivityIntent);
    }
}
