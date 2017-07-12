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
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;


public class QuizQuestionsActivity extends ActionBarActivity {
    Button nextButton;
    RadioButton OptionAbutton,OptionBbutton,OptionCbutton;
    TextView Question;
    String [] Ques;
    String[] OpA;
    String[] OpB;
    String[] OpC;
    int finish;
    int i;
    String[] answers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_questions);
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        final Animation animShake = AnimationUtils.loadAnimation(this, R.anim.anim_shake);
        Ques=getResources().getStringArray(R.array.Questions);
        OpA=getResources().getStringArray(R.array.OpA);
        OpB=getResources().getStringArray(R.array.OpB);
        OpC=getResources().getStringArray(R.array.OpC);
        answers=new String[Ques.length];
        if (savedInstanceState != null) {
            this.i = savedInstanceState.getInt("index");
            this.answers=savedInstanceState.getStringArray("ans");

        }
        else {
            i = 0;
        }
        nextButton=(Button)findViewById(R.id.nextButton);
        OptionAbutton=(RadioButton)findViewById(R.id.OptionAradioButton);
        OptionBbutton=(RadioButton)findViewById(R.id.OptionBradioButton);
        OptionCbutton=(RadioButton)findViewById(R.id.OptionCradioButton);

        Question=(TextView)findViewById(R.id.QuestiontextView);
        Question.setText(Ques[i]);
        OptionAbutton.setText(OpA[i]);
        OptionBbutton.setText(OpB[i]);
        OptionCbutton.setText(OpC[i]);



        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.startAnimation(animShake);
                boolean check=true;
                if(OptionAbutton.isChecked()){
                    answers[i]=OpA[i];
                    check=false;
                }
                if(OptionBbutton.isChecked()){
                    if(answers[i]==null) {
                        answers[i] = OpB[i];
                        check=false;
                    }
                    else
                    check=true;
                }
                if(OptionCbutton.isChecked()){
                    if(answers[i]==null) {
                        answers[i] = OpC[i];
                        check=false;
                    }
                    else
                        check=true;
                }
                if(check){
                    Toast.makeText(QuizQuestionsActivity.this, "Select One", Toast.LENGTH_LONG).show();
                    Question.setText(Ques[i]);
                    OptionAbutton.setText(OpA[i]);
                    OptionBbutton.setText(OpB[i]);
                    OptionCbutton.setText(OpC[i]);
                    OptionAbutton.setChecked(false);
                    OptionBbutton.setChecked(false);
                    OptionCbutton.setChecked(false);
                    answers[i]=null;


                }
                else{
                   i=i+1;
                    if(i<Ques.length){
                        Question.setText(Ques[i]);
                        OptionAbutton.setText(OpA[i]);
                        OptionBbutton.setText(OpB[i]);
                        OptionCbutton.setText(OpC[i]);

                        OptionAbutton.setChecked(false);
                        OptionBbutton.setChecked(false);
                        OptionCbutton.setChecked(false);

                    }
                    else{
                        Bundle b = new Bundle();
                        b.putStringArray("key", answers);
                       finish=1;
                        Intent secondActivityIntent = new Intent(QuizQuestionsActivity.this, QuizResultActivity.class);
                        secondActivityIntent.putExtras(b);

                        startActivityForResult(secondActivityIntent, 0);
                    }
                }


            }


        });

    }

    @Override
    protected void onResume(){
        super.onResume();
        if(finish==1) {
             finish();
        }
    }
    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);

        savedInstanceState.putInt("index", this.i);
        savedInstanceState.putStringArray("ans",answers);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_quiz_questions, menu);
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
