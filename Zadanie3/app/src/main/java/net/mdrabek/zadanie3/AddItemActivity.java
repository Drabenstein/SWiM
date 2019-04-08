package net.mdrabek.zadanie3;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;

public class AddItemActivity extends Activity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);

        EditText editText = findViewById(R.id.newTodoItemEditText);

        editText.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3)
            {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3)
            {
            }

            @Override
            public void afterTextChanged(Editable editable)
            {
                if (editText.getLayout() != null && editText.getLayout().getLineCount() > 2)
                {
                    editText.getText().delete(editText.getText().length() - 1, editText.getText().length());
                }
            }
        });
    }

    public void onAddItemButtonClicked(View view)
    {
        Intent intent = getIntent();
        EditText editText = findViewById(R.id.newTodoItemEditText);
        intent.setData(Uri.parse(editText.getText().toString()));
        setResult(Activity.RESULT_OK, intent);
        finish();
    }
}
