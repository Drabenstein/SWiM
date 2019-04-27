package net.mdrabek.zadanie3;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

public class CheckableContextMenuActivity extends AppCompatActivity {

    private boolean[] checkedOptions = new boolean[6];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkable_context_menu);
        registerForContextMenu(findViewById(R.id.textContentTextView));
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        if (v.getId() == R.id.textContentTextView) {
            MenuInflater menuInflater = getMenuInflater();
            menuInflater.inflate(R.menu.checkable_context_menu, menu);

            for (int i = 0; i < menu.size(); ++i) {
                MenuItem mi = menu.getItem(i);
                if (checkedOptions[i]) {
                    mi.setChecked(true);
                }
            }
        }
        super.onCreateContextMenu(menu, v, menuInfo);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        final TextView content = findViewById(R.id.textContentTextView);

        switch (item.getItemId()) {
            case R.id.redText:
                content.setTextAppearance(R.style.redFont);
                checkedOptions[0] = true;
                checkedOptions[1] = checkedOptions[2] = false;
                break;
            case R.id.limeText:
                content.setTextAppearance(R.style.limeFont);
                checkedOptions[1] = true;
                checkedOptions[0] = checkedOptions[2] = false;
                break;
            case R.id.whiteText:
                content.setTextAppearance(R.style.whiteFont);
                checkedOptions[2] = true;
                checkedOptions[0] = checkedOptions[1] = false;
                break;
            case R.id.smallFont:
                content.setTextAppearance(R.style.smallFont);
                checkedOptions[3] = true;
                checkedOptions[4] = checkedOptions[5] = false;
                break;
            case R.id.normalFont:
                content.setTextAppearance(R.style.normalFont);
                checkedOptions[4] = true;
                checkedOptions[3] = checkedOptions[5] = false;
                break;
            case R.id.largeFont:
                content.setTextAppearance(R.style.largeFont);
                checkedOptions[5] = true;
                checkedOptions[3] = checkedOptions[4] = false;
                break;
            default:
                return super.onContextItemSelected(item);
        }

        return true;
    }
}
