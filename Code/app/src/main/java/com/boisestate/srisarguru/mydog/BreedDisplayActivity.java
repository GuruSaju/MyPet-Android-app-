package com.boisestate.srisarguru.mydog;

        import android.support.v7.app.ActionBarActivity;
        import android.os.Bundle;
        import android.widget.ImageView;
        import android.widget.ScrollView;
        import android.widget.TextView;

public class BreedDisplayActivity extends ActionBarActivity {
    private int[] Breedpics ;
    int position;
    ImageView dogImage;
    ScrollView dogInfo;
    String[] breedsInfo,breeds;
    TextView doggyInfo;
    String fromClass;
    String data;
    String breed;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_breed_display);
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        this.fromClass =getIntent().getStringExtra("this");
        breeds=getResources().getStringArray(R.array.Breeds);
        if(this.fromClass.equals("breed")){
            this.position=getIntent().getIntExtra("Number",0);

        }
        else if(this.fromClass.equals("budget")){
            this.data=getIntent().getStringExtra("Info");
            String[] split=data.split(" ");

            for(int i=0;i<split.length;i++){
                if(split[i].equals("costs")) {
                    break;
                }
                else{
                    if(breed==null){
                        breed=split[i];
                    }
                    else{
                        breed=breed+" "+split[i];
                    }
                }
            }
            System.out.println(":"+split[0]+":");
            for(int i=0;i<breeds.length;i++){
                if(breed.equals(breeds[i])){
                    this.position=i;
                }
            }
        }

        DogPictures dogpic=new DogPictures();
        Breedpics=dogpic.getPictures();
        breedsInfo =getResources().getStringArray(R.array.Breedsinfo);
        dogImage =(ImageView)findViewById(R.id.DogimageView);
        dogInfo =(ScrollView)findViewById(R.id.doginfoscrollView);
        doggyInfo =(TextView)findViewById(R.id.doginfotextview);
        doggyInfo.setText(breedsInfo[position]);
        dogImage.setImageResource(Breedpics[position]);

    }
}
