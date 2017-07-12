package com.boisestate.srisarguru.mydog;

import android.content.Intent;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import java.util.ArrayList;


public class ClinicsDisplayActivity extends ActionBarActivity {
    int cityPosition;
    String[] clinics,cities, tempClinics;
    ListView clinicsList;
    String city;
    ArrayList<ClinicDetails> clinicsArraylist = new ArrayList<ClinicDetails>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clinics_display);
        this.cityPosition =getIntent().getIntExtra("Number",0);
        cities=getResources().getStringArray(R.array.Cities);
        Resources res = getResources();
        TypedArray ta = res.obtainTypedArray(R.array.Clinics);
        int n = ta.length();

          String[][] array = new String[n][];
        for (int i = 0; i < n; ++i) {
            int id = ta.getResourceId(i, 0);
            if (id > 0) {
                array[i] = res.getStringArray(id);
            }
        }
        ta.recycle();
        clinics=array[cityPosition];
        if(cityPosition <2) {
            tempClinics = new String[clinics.length];
            for (int i = 0; i < clinics.length; i++) {
                String[] split = clinics[i].split("/");
                ClinicDetails cl = new ClinicDetails(split[0], split[1], split[2], split[3], split[4]);
                clinicsArraylist.add(i, cl);
                tempClinics[i] = split[0] + "\r\n"+ split[1] + "\r\n" + split[4];

            }

            System.out.println(clinics[0]);
            System.out.println(clinics[1]);
        }
        if(clinics!=null && cityPosition <2)  {
            this.clinicsList = (ListView) findViewById(R.id.ClinicslistView);
            ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this, R.layout.clinics_listview, R.id.ClinicstextView, tempClinics);
            clinicsList.setAdapter(adapter2);
            registerClickCallBack();
        }
        else
        {
            Toast.makeText(ClinicsDisplayActivity.this,
                    "Really? Where is your patriotism towards Boise?", Toast.LENGTH_SHORT).show();
            System.out.println("clinics is null");
        }

    }
    private void registerClickCallBack() {
        clinicsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View viewClicked,int position, long id) {

                Intent i = new Intent(viewClicked.getContext(), ClinicMapsActivity.class);
                i.putExtra("Number", position);
                i.putExtra("citypos", cityPosition);
                startActivity(i);
            }
        });
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_clinics_display, menu);
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
