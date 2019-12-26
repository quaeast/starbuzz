package bjfu.it.fangzidong.starbuzz;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.media.Image;
import android.media.session.PlaybackState;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.Checkable;
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
                    "_id = ?",
                    new String[]{Integer.toString(drinkid)},
                    null, null, null);
            //
            cursor.moveToFirst();
            TextView name = findViewById(R.id.name);
            name.setText(cursor.getString(1));

            ImageView photo = findViewById(R.id.photo);
            photo.setImageResource(cursor.getInt(3));
            photo.setContentDescription(cursor.getString(1));

            TextView description = findViewById(R.id.description);
            description.setText(cursor.getString(2));

            CheckBox favourite  = findViewById(R.id.favourite_checkbok);
            favourite.setChecked(cursor.getInt(4)==1);

            Log.d("hahaha", String.valueOf(cursor.isFirst()));
            Log.d("hahaha", cursor.getInt(0) + "");
            Log.d("hahaha", cursor.getString(1));
        } catch (SQLException e) {
            Log.e("sqlite", e.getMessage());
            Toast toast = Toast.makeText(this, "Database unavailable", Toast.LENGTH_SHORT);
            toast.show();
        }

    }

    public void onFavoriteClicked(View view){
        Checkable favorite = (CheckBox) view;
        ContentValues drinkValues = new ContentValues();
        drinkValues.put("FAVORITE", favorite.isChecked());

        int drinkid = getIntent().getIntExtra(EXTRA_DRINKID, 0);
        SQLiteOpenHelper starbuzzDatabaseHelper  = new StarbuzzDatabaseHelper(this);

        try (SQLiteDatabase db = starbuzzDatabaseHelper.getWritableDatabase()){
            int row = db.update("DRINK", drinkValues,
                    "_id=?", new String[]{Integer.toString(drinkid)});
            Log.d("sqlite", "update row"+row);
        }catch (SQLiteException e){
            Log.e("sqlite", e.getMessage());
            Toast toast = Toast.makeText(this, "Datebase unwitable", Toast.LENGTH_SHORT);
            toast.show();
        }
    }
}
