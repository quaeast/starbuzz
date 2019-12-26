package bjfu.it.fangzidong.starbuzz;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class StarbuzzDatabaseHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "starbuzz.db";
    private static final int DB_VERSION = 2;

    public StarbuzzDatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    private static void insertDrink(SQLiteDatabase db, String name, String description, int resourceId){
        ContentValues drinkValues = new ContentValues();
        drinkValues.put("NAME", name);
        drinkValues.put("DESCRIPTION", description);
        drinkValues.put("IMAGE_RESOURCE_ID", resourceId);
        long result = db.insert("DRINK", null, drinkValues);
        Log.d("sqlite", "insert"+name+",_id: "+result);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE DRINK(_id INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "NAME TEXT,"
                + "DESCRIPTION TEXT,"
                + "IMAGE_RESOURCE_ID INTEGER);"
        );
//        v2
        db.execSQL("ALTER TABLE DRINK ADD COLUMN FAVORITE NUMERIC;");
        insertDrink(db,"Latte", "Espresso and steamed milk", R.drawable.latte);
        insertDrink(db,"Cappuccino", "Espresso, hot milk and steamed-milk foam", R.drawable.cappuccino);
        insertDrink(db,"Filter", "Our best drip coffee", R.drawable.filter);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.d("haha", "old version"+oldVersion);
        if(oldVersion<=1){
            db.execSQL("ALTER TABLE DRINK ADD COLUMN FAVORITE NUMERIC;");
        }else if(oldVersion<=2){
            ContentValues latteDesc = new ContentValues();
            latteDesc.put("DESCRIPTION", "Tasty");
            db.update("DRINK",latteDesc,"NAME = ?", new String[]{"latte"});
        }
    }
}
