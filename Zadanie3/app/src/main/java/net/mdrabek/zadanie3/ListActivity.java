package net.mdrabek.zadanie3;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

public class ListActivity extends Activity
{
    public static final String listItemSourceFileName = "todos";
    private static final int AddItem = 1;
    private List<String> todoItems;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        if(fileExist(listItemSourceFileName))
        {
            try
            {
                readFromFile(listItemSourceFileName);
            }
            catch (IOException ex)
            {
                Toast t = Toast.makeText(this, "Błąd podczas odczytu listy z pliku", Toast.LENGTH_SHORT);
                t.setGravity(Gravity.TOP, 0,0);
                t.show();
            }
        }
        else
        {
            initDefaultList();
        }

        updateAdapter();
    }

    public boolean fileExist(String fname){
        File file = getBaseContext().getFileStreamPath(fname);
        return file.exists();
    }

    private void updateAdapter()
    {
        ListView listView = findViewById(R.id.normalListView);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_checked, todoItems);
        listView.setAdapter(adapter);
    }

    public void onAddItemButtonClicked(View view)
    {
        view.setEnabled(false);
        final Intent addItemIntent = new Intent(Intent.ACTION_INSERT);
        startActivityForResult(addItemIntent, AddItem);
    }

    public void onClearListButtonClicked(View view)
    {
        view.setEnabled(false);
        findViewById(R.id.addItemButton).setEnabled(false);
        todoItems.clear();
        updateAdapter();
        findViewById(R.id.addItemButton).setEnabled(true);
        view.setEnabled(true);
    }

    private void initDefaultList()
    {
        todoItems = new ArrayList<>();
        todoItems.add("Element 1");
        todoItems.add("Element 2");
        todoItems.add("Element 3");
    }

    private void saveToFile(String filename) throws IOException
    {
        FileOutputStream fos = openFileOutput(filename, MODE_PRIVATE);
        OutputStreamWriter osw = new OutputStreamWriter(fos);
        BufferedWriter bw = new BufferedWriter(osw);

        for (String item : todoItems)
        {
            bw.write(item + System.lineSeparator());
        }

        bw.close();
    }

    private void readFromFile(String filename) throws IOException
    {
        FileInputStream fis = openFileInput(filename);
        InputStreamReader isr = new InputStreamReader(fis);
        BufferedReader reader = new BufferedReader(isr);

        String readString = reader.readLine();
        List<String> results = new ArrayList<>();
        while (readString != null)
        {
            results.add(readString);
            readString = reader.readLine();
        }

        reader.close();

        todoItems = results;
    }

    @Override
    protected void onPause()
    {
        try
        {
            saveToFile(listItemSourceFileName);
        }
        catch (IOException e)
        {
            Toast t = Toast.makeText(this, "Błąd podczas zapisywania listy do pliku", Toast.LENGTH_SHORT);
            t.setGravity(Gravity.TOP, 0,0);
            t.show();
        }

        super.onPause();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if (requestCode == AddItem && resultCode == Activity.RESULT_OK)
        {
            String entry = data.getDataString();
            if (entry != null && !entry.isEmpty())
            {
                todoItems.add(entry);
                updateAdapter();
            }
            else
            {
                Toast t = Toast.makeText(this, "Próbowano dodać pusty element listy", Toast.LENGTH_SHORT);
                t.setGravity(Gravity.CENTER, 0, 0);
                t.show();
            }
        }

        findViewById(R.id.addItemButton).setEnabled(true);
        super.onActivityResult(requestCode, resultCode, data);
    }
}