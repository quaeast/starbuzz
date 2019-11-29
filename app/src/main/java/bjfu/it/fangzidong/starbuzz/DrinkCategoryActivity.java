package bjfu.it.fangzidong.starbuzz;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;


public class DrinkCategoryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drink_category);

        ArrayAdapter<Drink> listAdapter = new ArrayAdapter<>(
                this, android.R.layout.simple_list_item_1, Drink.drinks
        );

        ListView listView = findViewById(R.id.list_drinks);
        listView.setAdapter(listAdapter);

        AdapterView.OnItemClickListener itemClickListener =
                new OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        if(position == 0){
                            Intent intent = new Intent(DrinkCategoryActivity.this,
                                    DrinkActivity.class);
                            intent.putExtra(DrinkActivity.EXTRA_DRINKID, (int) id);
                            startActivity(intent);
                        }
                    }
                };
    }
}
