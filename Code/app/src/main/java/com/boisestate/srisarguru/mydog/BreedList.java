package com.boisestate.srisarguru.mydog;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;


public class BreedList extends ActionBarActivity{
    String[] breeds;
    ListView breedList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_breed_list);
        breeds=getResources().getStringArray(R.array.Breeds);
       this.breedList =(ListView)findViewById(R.id.breedlist);
        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this,R.layout.breed_listview, R.id.ListView_textView,breeds);
        breedList.setAdapter(adapter2);
        registerClickCallBack();

    }
    private void registerClickCallBack() {
        breedList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View viewClicked, int position, long id) {

                Intent i = new Intent(viewClicked.getContext(), BreedDisplayActivity.class);
                i.putExtra("this", "breed");
                i.putExtra("Number", position);
                startActivity(i);
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_breed_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
