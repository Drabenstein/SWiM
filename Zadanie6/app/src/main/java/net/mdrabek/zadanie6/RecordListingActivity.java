package net.mdrabek.zadanie6;

import android.app.ListActivity;
import android.media.MediaPlayer;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.io.File;
import java.io.FileDescriptor;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class RecordListingActivity extends ListActivity
{
    public final static String AUDIO_RECORDINGS_ARG = "audio-folder";
    private final static String DEFAULT_AUDIO_PATH = Environment.getExternalStorageDirectory().getAbsolutePath() +
            "/AudioGrabber";

    private File audioFolder;
    private List<File> recordings;
    private MediaPlayer player;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
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

        recordings = getAudioRecordingsFileNames(audioFolder);
        List<String> recordingsNames = recordings.stream().map(f -> f.getName().substring(0, f.getName().length() - 4)).collect(Collectors.toList());
        ArrayAdapter<String> listAdapter = new ArrayAdapter<>(this, R.layout.recording_list_item, R.id.recordingNameTextView, recordingsNames);
        setListAdapter(listAdapter);
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
    protected void onListItemClick(ListView l, View v, int position, long id)
    {
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
        super.onListItemClick(l, v, position, id);
    }
}
