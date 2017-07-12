package com.boisestate.srisarguru.mydog;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class BudgetMainActivity extends ActionBarActivity {
    Button submitButton;
    EditText minRange,maxRange;
    String max,min;
    String[] range;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_budget_main);
        submitButton=(Button)findViewById(R.id.submitBudgetbutton);
        minRange=(EditText)findViewById(R.id.minRangeeditText);
        maxRange=(EditText)findViewById(R.id.MaxRangeeditText);
        final Animation animShake = AnimationUtils.loadAnimation(this, R.anim.anim_shake);
        findViewById(R.id.submitBudgetbutton).startAnimation(animShake);
        range=new String[2];
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.startAnimation(animShake);
                min=minRange.getText().toString();
                max=maxRange.getText().toString();
                System.out.println(";"+min + " "+max+";");
                range[0]=min;
                range[1]=max;
                System.out.println(range[0]+ " "+range[1]);
                if(minRange.getText().length()!=0 && maxRange.getText().length()!=0){
                       if(Integer.parseInt(min)>Integer.parseInt(max)){
                           Toast.makeText(BudgetMainActivity.this, "Ending range should be larger than starting range.", Toast.LENGTH_LONG).show();
                       }
                    else {
                           Bundle b = new Bundle();
                           b.putStringArray("keybu", range);

                           Intent BudgetActivityIntent = new Intent(BudgetMainActivity.this, BudgetResultActivity.class);
                           BudgetActivityIntent.putExtras(b);
                           startActivityForResult(BudgetActivityIntent, 0);
                       }
                }
                else{
                    if(minRange.getText().length()==0 && maxRange.getText().length()==0)
                    Toast.makeText(BudgetMainActivity.this, "Enter the range", Toast.LENGTH_LONG).show();
                    else if(minRange.getText().length()==0)
                        Toast.makeText(BudgetMainActivity.this, "Enter start range", Toast.LENGTH_LONG).show();
                   else  if(maxRange.getText().length()==0)
                        Toast.makeText(BudgetMainActivity.this, "Enter end range", Toast.LENGTH_LONG).show();
                }

            }


        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_budget_main, menu);
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
