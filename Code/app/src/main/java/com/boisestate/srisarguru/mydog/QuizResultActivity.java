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
import android.widget.ImageView;
import android.widget.TextView;


public class QuizResultActivity extends ActionBarActivity {
    String[] answerkey;
    TextView ansView,breedname;
    String ans;
    int anscount;
    String[] breedsQ1,breedsQ2,breedsQ3,breedsQ4,breedsQ5,breeds;
    int[] results;
    ImageView breedimage;
    private int[] Breedpics;
    Button goToBreeds;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_result);
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        Bundle b=this.getIntent().getExtras();
        this.answerkey=b.getStringArray("key");
        final Animation animShake = AnimationUtils.loadAnimation(this, R.anim.anim_shake);
        final Animation animScale = AnimationUtils.loadAnimation(this, R.anim.anim_scale);
        findViewById(R.id.gotobreedsbutton).startAnimation(animShake);
        breeds=getResources().getStringArray(R.array.Breeds);
        breedsQ1=getResources().getStringArray(R.array.BreedsQ1);
        breedsQ2=getResources().getStringArray(R.array.BreedsQ2);
        breedsQ3=getResources().getStringArray(R.array.BreedsQ3);
        breedsQ4=getResources().getStringArray(R.array.BreedsQ4);
        breedsQ5=getResources().getStringArray(R.array.BreedsQ5);
       breedimage=(ImageView)findViewById(R.id.BreedresultimageView);
        ansView=(TextView)findViewById(R.id.AnswertextView);
        breedname=(TextView)findViewById(R.id.BreednametextView);
        goToBreeds=(Button)findViewById(R.id.gotobreedsbutton);
        DogPictures dogpic=new DogPictures();
        Breedpics=dogpic.getPictures();
        results=new int[breeds.length];
        for(int i=0;i<results.length;i++){
            results[i]=0;
        }
        anscount=0;
        for(int i=0;i<5;i++){
            for(int j=0;j<breedsQ1.length;j++){
                if(anscount==0){
                    if(breedsQ1[j].equals(answerkey[0])){
                        results[j]=results[j]+1;
                    }
                }
                else if(anscount==1){
                    if(breedsQ2[j].equals(answerkey[1])){
                        results[j]=results[j]+1;
                    }
                }
                else if(anscount==2){
                    if(breedsQ3[j].equals(answerkey[2])){
                        results[j]=results[j]+1;
                    }
                }
                else if(anscount==3){
                    if(breedsQ4[j].equals(answerkey[3])){
                        results[j]=results[j]+1;
                    }
                }
                else{
                    if(breedsQ5[j].equals(answerkey[4])){
                        results[j]=results[j]+1;
                    }

                }

            }
            anscount++;
        }
         int largest=0,largestindex=0;
        for(int i=0;i<results.length;i++){
            if(results[i]>largest && breedsQ1[i].equals(answerkey[0])){
                largest=results[i];
                largestindex=i;
            }
            System.out.println(results[i]);
        }
        for(int i=0;i<answerkey.length;i++){
            if(ans==null){
                ans=answerkey[i]+" ";
            }
            else
            ans=ans+" "+answerkey[i];
        }
        ans=ans+" "+breeds[largestindex];
        breedname.setText(breeds[largestindex]);
        breedimage.setImageResource(Breedpics[largestindex]);
        findViewById(R.id.BreedresultimageView).startAnimation(animScale);
        goToBreeds.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent QuizActivityIntent = new Intent(QuizResultActivity.this, BreedList.class);
                startActivityForResult(QuizActivityIntent, 0);

            }


        });
    }

    @Override
    public void onBackPressed()
    {
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_quiz_result, menu);
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
