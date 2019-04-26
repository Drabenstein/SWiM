package net.mdrabek.zadanie3;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;

public class JavaBasedMenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_java_based_menu);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuItem car = menu.add(R.string.menuItemCar);
        MenuItem train = menu.add(R.string.menuItemTrain);
        MenuItem airplane = menu.add(R.string.menuItemAirplane);

        final ImageView vehicleImageView = findViewById(R.id.vehicleImageView);

        car.setOnMenuItemClickListener(v ->
        {
            vehicleImageView.setImageResource(R.drawable.ic_directions_car_light_green_a400_24dp);
            return true;
        });

        train.setOnMenuItemClickListener(v ->
        {
            vehicleImageView.setImageResource(R.drawable.ic_train_yellow_500_24dp);
            return true;
        });

        airplane.setOnMenuItemClickListener(v ->
        {
            vehicleImageView.setImageResource(R.drawable.ic_airplanemode_active_light_green_a200_48dp);
            return true;
        });

        return super.onCreateOptionsMenu(menu);
    }
}
