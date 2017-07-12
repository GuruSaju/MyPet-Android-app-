package com.boisestate.srisarguru.mydog;

import android.annotation.TargetApi;
import android.app.ActionBar;
import android.content.Intent;
import android.os.Build;
import android.provider.CalendarContract;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;




public class MainActivity extends ActionBarActivity {
    Button breedsButton,quizButton,budgetButton,findClinicsButton,alertButton,picturesButton;
    int backButtonCount=0;
      @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
          setContentView(R.layout.activity_main);
          android.support.v7.app.ActionBar actionBar = getSupportActionBar();
          actionBar.hide();
          final Animation animTranslate = AnimationUtils.loadAnimation(this, R.anim.anim_translate);
          final Animation animAlpha = AnimationUtils.loadAnimation(this, R.anim.anin_alpha);
          final Animation animRotate = AnimationUtils.loadAnimation(this, R.anim.anim_rotate);
        breedsButton =(Button)findViewById(R.id.breedbutton);
        quizButton =(Button)findViewById(R.id.Quizbutton);
        budgetButton =(Button)findViewById(R.id.budgetbutton);
        findClinicsButton=(Button)findViewById(R.id.hospitalbutton);
        alertButton=(Button)findViewById(R.id.alertButton);
        picturesButton=(Button)findViewById(R.id.picturesButton);
          findViewById(R.id.breedbutton).startAnimation(animAlpha);
          findViewById(R.id.Quizbutton).startAnimation(animAlpha);
          findViewById(R.id.budgetbutton).startAnimation(animAlpha);
          findViewById(R.id.hospitalbutton).startAnimation(animAlpha);
          findViewById(R.id.alertButton).startAnimation(animAlpha);
          findViewById(R.id.picturesButton).startAnimation(animAlpha);


        breedsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.startAnimation(animTranslate);
                Intent breedListActivityIntent = new Intent(MainActivity.this, BreedList.class);
                startActivityForResult(breedListActivityIntent, 0);

            }


        });
        budgetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.startAnimation(animTranslate);
                Intent budgetActivityIntent = new Intent(MainActivity.this, BudgetMainActivity.class);
                startActivityForResult(budgetActivityIntent, 0);

            }


        });
        quizButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.startAnimation(animRotate);
                Intent QuizActivityIntent = new Intent(MainActivity.this, QuizActivity.class);
                startActivityForResult(QuizActivityIntent, 0);

            }


        });
        findClinicsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.startAnimation(animRotate);
                Intent findClinicsActivityIntent = new Intent(MainActivity.this, ClinicCityListViewActivity.class);
                startActivityForResult(findClinicsActivityIntent, 0);

            }
        });
        alertButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.startAnimation(animTranslate);
                Intent calIntent = new Intent(Intent.ACTION_INSERT);
               calIntent.setData(CalendarContract.Events.CONTENT_URI);
               startActivity(calIntent);
            }


        });
        picturesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.startAnimation(animTranslate);
                Intent galleryActivityIntent = new Intent(MainActivity.this, PicturesPageActivity.class);
                startActivityForResult(galleryActivityIntent, 0);

            }


        });
    }

    @Override
public void onBackPressed()
{
    if(backButtonCount >= 1)
    {
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
    else
    {
        Toast.makeText(this, "Press the back button once again to close the application.", Toast.LENGTH_SHORT).show();
        backButtonCount++;
    }
}
}
