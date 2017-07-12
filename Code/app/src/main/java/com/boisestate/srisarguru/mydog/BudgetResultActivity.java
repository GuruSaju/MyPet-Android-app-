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


public class BudgetResultActivity extends ActionBarActivity {
    String[] budgetRange =new String[2];
    String range;
    String[] rangeBreeds, finalBreedList,breeds;
    int[] breedCosts;
    int size;
    ListView budgetBreedView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_budget_result);
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        breeds=getResources().getStringArray(R.array.Breeds);
        budgetBreedView =(ListView)findViewById(R.id.BreedrangelistView);
        rangeBreeds =getResources().getStringArray(R.array.Breedscostrange);
        breedCosts =getResources().getIntArray(R.array.Costs);
        Bundle b=this.getIntent().getExtras();
        if(b!=null)
        this.budgetRange =b.getStringArray("keybu");
        for(int i=0;i< budgetRange.length;i++){
            range=range+" "+ budgetRange[i];
        }
        size=0;
        for(int i=0;i<breeds.length;i++){
            if( Integer.parseInt(budgetRange[0])<= breedCosts[i]&& Integer.parseInt(budgetRange[1])>= breedCosts[i]){
                size++;
            }
        }
        finalBreedList =new String[size];
        int count=0;
        int nullcount=0;
        for(int i=0;i<breeds.length;i++){
            if( Integer.parseInt(budgetRange[0])<= breedCosts[i]&& Integer.parseInt(budgetRange[1])>= breedCosts[i]){
                finalBreedList[count]=breeds[i] + " costs around " + rangeBreeds[i];
                count++;
                nullcount=nullcount+1;
            }
        }
        if(nullcount==0){
            finalBreedList =new String[1];
            finalBreedList[0]="No breed is available within that cost";
            ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this,R.layout.breed_listview, R.id.ListView_textView, finalBreedList);
            budgetBreedView.setAdapter(adapter2);
        }
        else {
            ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this, R.layout.breed_listview, R.id.ListView_textView, finalBreedList);
            budgetBreedView.setAdapter(adapter2);
            registerClickCallBack();
        }
    }
    private void registerClickCallBack() {
        budgetBreedView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View viewClicked, int position, long id) {
                String data = (String) budgetBreedView.getItemAtPosition(position);
                Intent i = new Intent(viewClicked.getContext(), BreedDisplayActivity.class);
                i.putExtra("this", "budget");
                i.putExtra("Info", data);
                startActivity(i);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_budget_result, menu);
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
