package net.mdrabek.zadanie3;

import android.graphics.Rect;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
//import android.support.v7.view.ActionMode;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import static android.view.ActionMode.TYPE_FLOATING;

public class JavaBasedMenuActivity extends AppCompatActivity {

    private ActionMode.Callback2 actionModeCallback;
    private ActionMode actionMode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_java_based_menu);
        initActionMode();
        ImageView imageView = findViewById(R.id.vehicleImageView);
        imageView.setOnLongClickListener(v -> {
            if (actionMode != null) {
                return false;
            }

            actionMode = startActionMode(actionModeCallback, TYPE_FLOATING);
            return true;
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuItem car = menu.add(R.string.menuItemCar);
        MenuItem train = menu.add(R.string.menuItemTrain);
        MenuItem airplane = menu.add(R.string.menuItemAirplane);

        final ImageView vehicleImageView = findViewById(R.id.vehicleImageView);
        final TextView vehicleTextView = findViewById(R.id.vehicleTextView);

        car.setOnMenuItemClickListener(v ->
        {
            vehicleTextView.setText("Car");
            vehicleImageView.setImageResource(R.drawable.ic_directions_car_lime_100_48dp);
            return true;
        });

        train.setOnMenuItemClickListener(v ->
        {
            vehicleTextView.setText("Train");
            vehicleImageView.setImageResource(R.drawable.ic_train_yellow_500_48dp);
            return true;
        });

        airplane.setOnMenuItemClickListener(v ->
        {
            vehicleTextView.setText("Airplane");
            vehicleImageView.setImageResource(R.drawable.ic_airplanemode_active_light_green_a200_48dp);
            return true;
        });

        return super.onCreateOptionsMenu(menu);
    }

    private void initActionMode() {

        final ConstraintLayout content = findViewById(R.id.contentConstraintLayout);

        actionModeCallback = new ActionMode.Callback2() {
            @Override
            public boolean onCreateActionMode(ActionMode mode, Menu menu) {
                menu.add(1, 1, 1, "Car");
                menu.add(1, 2, 2, "Train");
                menu.add(1, 3, 3, "Airplane");
                return true;
            }

            @Override
            public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
                return false;
            }

            @Override
            public boolean onActionItemClicked(ActionMode mode, MenuItem item) {

                final ImageView vehicleImageView = findViewById(R.id.vehicleImageView);
                final TextView vehicleTextView = findViewById(R.id.vehicleTextView);

                switch (item.getItemId()) {
                    case 1:
                        vehicleTextView.setText("Car");
                        vehicleImageView.setImageResource(R.drawable.ic_directions_car_lime_100_48dp);
                        break;
                    case 2:
                        vehicleTextView.setText("Train");
                        vehicleImageView.setImageResource(R.drawable.ic_train_yellow_500_48dp);
                        break;
                    case 3:
                        vehicleTextView.setText("Airplane");
                        vehicleImageView.setImageResource(R.drawable.ic_airplanemode_active_light_green_a200_48dp);
                        break;
                }

                mode.finish();
                return true;
            }

            @Override
            public void onDestroyActionMode(ActionMode mode) {
                actionMode = null;
            }

            @Override
            public void onGetContentRect(ActionMode mode, View view, Rect outRect) {
                if (view != null) {
                    int x = content.getLeft();
                    int y = content.getBottom() - 60;
                    outRect.set(x, y, content.getRight(), y);
                } else {
                    super.onGetContentRect(mode, view, outRect);
                }
            }
        };
    }
}
