package net.mdrabek.zadanie6;

import android.app.Activity;
import android.app.AlertDialog;
import android.media.MediaPlayer;
import android.os.Environment;
import android.os.Bundle;
import android.text.InputType;
import android.util.SparseBooleanArray;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static android.view.ActionMode.TYPE_PRIMARY;

public class RecordListingActivity extends Activity implements AdapterView.OnItemClickListener
{
    public final static String AUDIO_RECORDINGS_ARG = "audio-folder";
    private final static String DEFAULT_AUDIO_PATH = Environment.getExternalStorageDirectory().getAbsolutePath() +
            "/AudioGrabber";

    private File audioFolder;
    private List<File> recordings;
    private List<String> recordingNames;
    private ArrayAdapter<String> adapter;
    private MediaPlayer player;
    private ListView recordsListView;

    private ActionMode.Callback actionModeCallback;
    private ActionMode actionMode;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record_listing);

        String folderPath = getIntent().getStringExtra(AUDIO_RECORDINGS_ARG);

        if(folderPath == null)
        {
            folderPath = DEFAULT_AUDIO_PATH;
        }

        audioFolder = new File(folderPath);
        if(!audioFolder.exists())
        {
            Toast.makeText(this, "Audio recording folder not found", Toast.LENGTH_SHORT).show();
        }

        recordsListView = findViewById(R.id.recordsListView);
        recordings = getAudioRecordingsFileNames(audioFolder);
        recordingNames = recordings.stream().map(f -> f.getName().substring(0, f.getName().length() - 4)).collect(Collectors.toList());
        adapter = new ArrayAdapter<>(this, R.layout.recording_list_item, R.id.recordingNameTextView, recordingNames);
        recordsListView.setAdapter(adapter);

        initActionMode();

        recordsListView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        recordsListView.setOnItemClickListener(this);
        recordsListView.setOnItemLongClickListener((parent, view, position, id) -> {
            if(actionMode != null)
            {
                return false;
            }

            recordsListView.setItemChecked(position, true);
            recordsListView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
            actionMode = startActionMode(actionModeCallback, TYPE_PRIMARY);
            return true;
        });
    }

    private void initActionMode()
    {
        actionModeCallback = new ActionMode.Callback() {
            @Override
            public boolean onCreateActionMode(ActionMode mode, Menu menu) {
                mode.getMenuInflater().inflate(R.menu.record_listing_menu, menu);
                return true;
            }

            @Override
            public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
                return false;
            }

            @Override
            public boolean onActionItemClicked(ActionMode mode, MenuItem item) {

                switch (item.getItemId())
                {
                    case R.id.deleteMenuItem:
                        deleteSelectedRecordings();
                        break;
                    case R.id.renameMenuItem:
                        renameRecording();
                        break;
                }

                mode.finish();
                return true;
            }

            @Override
            public void onDestroyActionMode(ActionMode mode) {
                actionMode = null;
                recordsListView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
                recordsListView.setItemChecked(-1, true);
            }
        };
    }

    private void renameRecording()
    {
        SparseBooleanArray checkedItems = recordsListView.getCheckedItemPositions();

        if(checkedItems.size() > 0)
        {
            final int selectedRecordingIndex = checkedItems.keyAt(0);
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Podaj nową nazwę");
            final EditText input = new EditText(this);
            input.setInputType(InputType.TYPE_CLASS_TEXT);
            builder.setView(input);
            builder.setPositiveButton("Potwierdź", (dialog, which) ->
            {
                String newFilename = input.getText().toString();
                File recording = recordings.get(selectedRecordingIndex);
                File destFile = new File(recording.getParent(), newFilename  + ".wav");
                recording.renameTo(destFile);
                recordingNames.set(selectedRecordingIndex, newFilename);
                adapter.notifyDataSetChanged();
            });
            builder.setNegativeButton("Anuluj", (dialog, which) -> dialog.cancel());
            builder.show();
        }
    }

    private void deleteSelectedRecordings()
    {
        SparseBooleanArray checkedItems = recordsListView.getCheckedItemPositions();

        for(int i = 0; i < checkedItems.size(); i++)
        {
            recordings.get(checkedItems.keyAt(i)).delete();
        }

        for(int i = 0; i < checkedItems.size(); i++)
        {
            recordings.remove(checkedItems.keyAt(i) - i * 1);
            recordingNames.remove(checkedItems.keyAt(i) - i * 1);
        }

        ArrayAdapter<String> adapter = (ArrayAdapter<String>)recordsListView.getAdapter();
        adapter.notifyDataSetChanged();
    }

    private ArrayList<File> getAudioRecordingsFileNames(File parentDir)
    {
        ArrayList<File> results = new ArrayList<>();
        File[] files = parentDir.listFiles();
        for (File file : files) {
            if (file.isDirectory()) {
                results.addAll(getAudioRecordingsFileNames(file));
            } else {
                if(file.getName().endsWith(".wav")){
                    results.add(file);
                }
            }
        }
        return results;
    }

    @Override
    protected void onPause()
    {
        if (player != null)
        {
            player.release();
            player = null;
        }
        super.onPause();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id)
    {
        recordsListView.setItemChecked(position, true);

        if(player != null)
        {
            player.stop();
            player.release();
            player = null;
        }

        player = new MediaPlayer();
        try
        {
            player.setDataSource(recordings.get(position).getAbsolutePath());
            player.prepare();
            player.setLooping(false);
            player.setOnCompletionListener(mediaPlayer -> {
                mediaPlayer.release();
                player = null;
            });
            player.start();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}
