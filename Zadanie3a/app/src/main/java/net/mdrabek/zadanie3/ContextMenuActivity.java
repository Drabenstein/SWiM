package net.mdrabek.zadanie3;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

public class ContextMenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_context_menu);
        EditText editText = findViewById(R.id.textInputEditText);
        registerForContextMenu(editText);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {

        if (v.getId() == R.id.textInputEditText) {
            menu.add(1, 1, 1, "Copy");
            menu.add(1, 2, 2, "Paste");
            menu.add(1, 3, 3, "Clear");
        }
        super.onCreateContextMenu(menu, v, menuInfo);
    }

    public boolean onContextItemSelected(MenuItem item) {
        final EditText content = findViewById(R.id.textInputEditText);
        final ClipboardManager clipboard = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);

        switch (item.getItemId())
        {
            case 1:
                ClipData clip = ClipData.newPlainText("Zadanie3a", content.getText());
                clipboard.setPrimaryClip(clip);
                break;
            case 2:
                content.setText(clipboard.getText());
                break;
            case 3:
                content.setText("");
                break;
        }

        return super.onContextItemSelected(item);
    }
}
