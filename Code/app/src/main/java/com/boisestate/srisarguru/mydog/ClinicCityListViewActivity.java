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


public class ClinicCityListViewActivity extends ActionBarActivity {
    String[] cities;
    ListView cityList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clinic_city_list_view);

        cities=getResources().getStringArray(R.array.Cities);
        this.cityList=(ListView)findViewById(R.id.cityList);
        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this,R.layout.breed_listview, R.id.ListView_textView,cities);
        cityList.setAdapter(adapter2);
        registerClickCallBack();

    }
    private void registerClickCallBack() {

        cityList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View viewClicked,int position, long id) {
                Intent i = new Intent(viewClicked.getContext(), ClinicsDisplayActivity.class);
                i.putExtra("Number", position);
                startActivity(i);
            }
        });
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_clinic_city_list_view, menu);
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
