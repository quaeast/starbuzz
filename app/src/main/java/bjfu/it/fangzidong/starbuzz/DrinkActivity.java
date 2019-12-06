package bjfu.it.fangzidong.starbuzz;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.media.Image;
import android.media.session.PlaybackState;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class DrinkActivity extends AppCompatActivity {

    public static final String EXTRA_DRINKID = "drinkid";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drink);

        int drinkid = getIntent().getIntExtra(EXTRA_DRINKID, 0);
        StarbuzzDatabaseHelper starbuzzDatabaseHelper = new StarbuzzDatabaseHelper(this);

        try (SQLiteDatabase db = starbuzzDatabaseHelper.getReadableDatabase()) {
            Cursor cursor = db.query(
                    "DRINK",
                    null,
                    null,
                    null,
                    null, null,null);
            //
            cursor.moveToFirst();
            cursor.move(drinkid);
            TextView name = findViewById(R.id.name);
            name.setText(cursor.getString(1));

            ImageView photo = findViewById(R.id.photo);
            photo.setImageResource(cursor.getInt(3));
            photo.setContentDescription(cursor.getString(1));

            TextView description = findViewById(R.id.description);
            description.setText(cursor.getString(2));

            Log.d("hahaha", String.valueOf(cursor.isFirst()));
            Log.d("hahaha", cursor.getInt(0)+"");
            Log.d("hahaha", cursor.getString(1));
        } catch (SQLException e) {
            Log.e("sqlite", e.getMessage());
            Toast toast = Toast.makeText(this, "Database unavailable", Toast.LENGTH_SHORT);
            toast.show();
        }


        /*Drink drink = Drink.drinks[drinkid];

        ImageView photo = findViewById(R.id.photo);
        photo.setImageResource(drink.getImageResourceId());
        photo.setContentDescription(drink.getName());


        TextView description = findViewById(R.id.description);
        description.setText(drink.getDescription());

         */
    }
}
