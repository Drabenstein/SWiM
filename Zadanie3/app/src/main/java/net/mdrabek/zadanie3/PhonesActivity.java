package net.mdrabek.zadanie3;

import android.app.Activity;
import android.app.SearchManager;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.zip.Inflater;

public class PhonesActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phones);
        SharedPreferences sp = getSharedPreferences("phones", MODE_PRIVATE);

        boolean isEmpty = sp.getBoolean("list_empty", false);
        ToggleButton syncToggleButton = findViewById(R.id.syncToggleButton);

        if (isEmpty) {
            syncToggleButton.setChecked(true);
            updateAdapterData(new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
        } else {
            String titlesString = sp.getString("titles", null);
            String descriptionString = sp.getString("descriptions", null);
            String imagesIdsString = sp.getString("imagesIds", null);

            if (titlesString != null && descriptionString != null && imagesIdsString != null) {
                String[] titles = titlesString.split(";;");
                String[] descriptions = descriptionString.split(";;");
                String[] imagesIdsText = imagesIdsString.split(";");
                Integer[] imagesIds = new Integer[imagesIdsText.length];
                for (int i = 0; i < imagesIdsText.length; i++) {
                    imagesIds[i] = Integer.parseInt(imagesIdsText[i]);
                }

                ArrayList<String> titlesList = new ArrayList<>(Arrays.asList(titles));
                ArrayList<String> descriptionList = new ArrayList<>(Arrays.asList(descriptions));
                ArrayList<Integer> imagesIdsList = new ArrayList<>(Arrays.asList(imagesIds));

                syncToggleButton.setChecked(true);
                updateAdapterData(titlesList, descriptionList, imagesIdsList);
            } else {
                syncToggleButton.setChecked(false);
                resetAdapterData();
            }
        }
    }

    private void updateAdapterData(List<String> titles, List<String> description, List<Integer> imagesIds) {
        CustomAdapter adapter = new CustomAdapter(titles, description, imagesIds);
        ListView listView = findViewById(R.id.phonesListView);
        listView.setAdapter(adapter);
    }

    private void resetAdapterData() {
        String[] titles = getResources().getStringArray(R.array.phonesTitles);
        String[] descriptions = getResources().getStringArray(R.array.phonesDescriptions);
        Integer[] imagesIds = new Integer[]
                {
                        R.drawable.nokia_5_1_plus, R.drawable.nokia_8, R.drawable.xperia_10,
                        R.drawable.p10_lite, R.drawable.p8_lite, R.drawable.galaxy_s_10_plus
                };

        updateAdapterData(new ArrayList<>(Arrays.asList(titles)),
                new ArrayList<>(Arrays.asList(descriptions)),
                new ArrayList<>(Arrays.asList(imagesIds)));
    }

    @Override
    protected void onPause() {
        ToggleButton spToggleBtn = findViewById(R.id.syncToggleButton);

        if (spToggleBtn.isChecked()) {
            SharedPreferences sp = getSharedPreferences("phones", MODE_PRIVATE);
            SharedPreferences.Editor spe = sp.edit();
            spe.clear();
            ListView listView = findViewById(R.id.phonesListView);
            CustomAdapter adapter = (CustomAdapter) listView.getAdapter();

            if (adapter.titles.size() == 0) {
                spe.putBoolean("list_empty", true);
            } else {
                StringBuilder titlesString = new StringBuilder();

                for (String title : adapter.titles) {
                    titlesString.append(title);
                    titlesString.append(";;");
                }

                titlesString.deleteCharAt(titlesString.length() - 1);
                titlesString.deleteCharAt(titlesString.length() - 1);

                StringBuilder descriptionString = new StringBuilder();

                for (String description : adapter.descriptions) {
                    descriptionString.append(description);
                    descriptionString.append(";;");
                }

                descriptionString.deleteCharAt(descriptionString.length() - 1);
                descriptionString.deleteCharAt(descriptionString.length() - 1);

                spe.putString("titles", titlesString.toString());
                spe.putString("descriptions", descriptionString.toString());

                StringBuilder sb = new StringBuilder();

                for (Integer i : adapter.imagesIds) {
                    sb.append(i + ";");
                }

                sb.deleteCharAt(sb.length() - 1);

                spe.putString("imagesIds", sb.toString());
            }

            spe.commit();
        }

        super.onPause();
    }

    public void onResetButttonClicked(View view) {
        resetAdapterData();
    }

    private class CustomAdapter extends BaseAdapter {
        List<String> titles;
        List<String> descriptions;
        List<Integer> imagesIds;
        private LayoutInflater inflater;

        public CustomAdapter(List<String> titles, List<String> descriptions, List<Integer> imagesIds) {
            this.titles = titles;
            this.descriptions = descriptions;
            this.imagesIds = imagesIds;
            inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        @Override
        public int getCount() {
            return titles.size();
        }

        @Override
        public Object getItem(int position) {
            return position;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LVItemHolder lvItem;

            if (convertView == null) {
                convertView = inflater.inflate(R.layout.custom_phone_list_item, null);
                lvItem = new LVItemHolder();
                lvItem.listItemTitle = convertView.findViewById(R.id.listItemTitle);
                lvItem.listItemDescription = convertView.findViewById(R.id.listItemDescription);
                lvItem.listItemImage = convertView.findViewById(R.id.listItemImgView);
                final View child = convertView;
                final List<String> titles = this.titles;
                final List<String> descriptions = this.descriptions;
                final List<Integer> imagesIds = this.imagesIds;
                convertView.findViewById(R.id.removeImg).setOnClickListener(v -> {
                    titles.remove(position);
                    descriptions.remove(position);
                    imagesIds.remove(position);
                    notifyDataSetChanged();
                });
                convertView.setTag(lvItem);
            } else {
                lvItem = (LVItemHolder) convertView.getTag();
            }

            lvItem.listItemTitle.setText(titles.get(position));
            lvItem.listItemDescription.setText(descriptions.get(position));
            lvItem.listItemImage.setImageResource(imagesIds.get(position));

            convertView.setOnClickListener((View view) ->
            {
                CharSequence query = lvItem.listItemTitle.getText();
                try {
                    Intent intent = new Intent(Intent.ACTION_WEB_SEARCH);
                    intent.putExtra(SearchManager.QUERY, query); // query contains search string
                    startActivity(intent);
                } catch (ActivityNotFoundException ex) {
                    Toast toast = Toast.makeText(getApplicationContext(), ex.getLocalizedMessage(), Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.TOP, 0, 0);
                    toast.show();
                }
            });

            return convertView;
        }

        private class LVItemHolder {
            ImageView listItemImage;
            TextView listItemTitle;
            TextView listItemDescription;
        }
    }
}
