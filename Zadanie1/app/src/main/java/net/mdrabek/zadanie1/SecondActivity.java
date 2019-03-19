package net.mdrabek.zadanie1;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class SecondActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
    }

    public void SubmitButtonClicked(View view)
    {
        TextView resultView = findViewById(R.id.resultView);
        EditText nameView = findViewById(R.id.nameEdit);
        EditText surnameView = findViewById(R.id.surnameEdit);
        RadioGroup sexRadioGroup = findViewById(R.id.radioSex);
        CheckBox javaCheckbox = findViewById(R.id.javaCheckBox);
        CheckBox kotlinCheckbox = findViewById(R.id.kotlinCheckBox);
        CheckBox csharpCheckbox = findViewById(R.id.csharpCheckBox);

        StringBuilder result = new StringBuilder();
        result.append(nameView.getText());
        result.append(" ");
        result.append(surnameView.getText());
        result.append("\n");
        int selectedSexRadioBtnId = sexRadioGroup.getCheckedRadioButtonId();
        if(selectedSexRadioBtnId != -1) {
            RadioButton sexRadioBtn = findViewById(selectedSexRadioBtnId);
            result.append(sexRadioBtn.getText());
            result.append("\n");
        }
        result.append("Znane języki: \n");

        if(javaCheckbox.isChecked())
        {
            result.append(javaCheckbox.getText());
            result.append("\n");
        }

        if(kotlinCheckbox.isChecked())
        {
            result.append(kotlinCheckbox.getText());
            result.append("\n");
        }

        if(csharpCheckbox.isChecked())
        {
            result.append(csharpCheckbox.getText());
            result.append("\n");
        }

        resultView.setText(result.toString());
    }

    public void BackButtonClicked(View view)
    {
        onBackPressed();
    }
}
