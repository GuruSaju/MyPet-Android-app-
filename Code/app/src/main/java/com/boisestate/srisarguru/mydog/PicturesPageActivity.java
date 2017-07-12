package com.boisestate.srisarguru.mydog;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.ArrayList;


public class PicturesPageActivity extends Activity {
    private static final int CAMERA_REQUEST = 1888;
    TextView cal;
    String TITLE="DOG";
    Button cameraButton;
    Bitmap photoloc;
    private int count;
    private Bitmap[] thumbnails;
    private boolean[] thumbnailsselection;
    private String[] arrPath;
    private ImageAdapter imageAdapter;
    ArrayList<String> f = new ArrayList<String>();
    ArrayList<String> fn = new ArrayList<String>();// list of file paths// list of file paths
    File[] listFile;
    GridView imagegrid;
    ImageView defaultImg;
    TextView defaultText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pictures_main);
        final Animation animScale = AnimationUtils.loadAnimation(this, R.anim.anin_alpha);
        cameraButton=(Button)findViewById(R.id.Camerabutton);
        getFromSdcard();
        imagegrid = (GridView) findViewById(R.id.PhoneImageGrid);
        imageAdapter = new ImageAdapter();
        imagegrid.setAdapter(imageAdapter);


        cameraButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.startAnimation(animScale);
                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, CAMERA_REQUEST);
            }
        });

    }

    public void getFromSdcard() {
        Boolean isSDPresent = android.os.Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED);
        if (isSDPresent) {
            File file = new File(android.os.Environment.getExternalStorageDirectory(), "MyDog");

            if (file.isDirectory()) {
                listFile = file.listFiles();


                for (int i = 0; i < listFile.length; i++) {

                    f.add(listFile[i].getAbsolutePath());
                    fn.add(listFile[i].getName());

                }
            }
        }

            File file = new File(getFilesDir(), "MyDog");

            if (file.isDirectory()) {
                listFile = file.listFiles();


                for (int i = 0; i < listFile.length; i++) {

                    f.add(listFile[i].getAbsolutePath());
                    fn.add(listFile[i].getName());

                }
            }
          if(f.isEmpty()){
              defaultImg=(ImageView)findViewById(R.id.imageView2);
              defaultText=(TextView)findViewById(R.id.textView);
              defaultImg.setImageResource(R.drawable.defaultdog);
              defaultText.setText("Your filename.png");
          }
        }


    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CAMERA_REQUEST && resultCode == RESULT_OK) {
            Bitmap photo = (Bitmap) data.getExtras().get("data");
            this.photoloc = photo;
            Intent returnIntent = new Intent(PicturesPageActivity.this, PictureSaveActivity.class);
            Bundle b = new Bundle();

            if (photoloc != null) {
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                photoloc.compress(Bitmap.CompressFormat.PNG, 100, stream);
                byte[] byteArray = stream.toByteArray();
                returnIntent.putExtra("picture", byteArray);

            }
            returnIntent.putExtras(b);
            startActivityForResult(returnIntent,0);
           // image.setImageBitmap(photo);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_calendar_main, menu);
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
    public class ImageAdapter extends BaseAdapter {
        private LayoutInflater mInflater;

        public ImageAdapter() {
            mInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        public int getCount() {
            return f.size();
        }

        public Object getItem(int position) {
            return position;
        }

        public long getItemId(int position) {
            return position;
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if (convertView == null) {
                holder = new ViewHolder();
                convertView = mInflater.inflate(
                        R.layout.gridview, null);
                holder.imageview = (ImageView) convertView.findViewById(R.id.gridpicimageview);
                holder.textview=(TextView)convertView.findViewById(R.id.gridpictextview);
                convertView.setTag(holder);
            }
            else {
                holder = (ViewHolder) convertView.getTag();
            }
         //  if(position!=0) {
//                System.out.println("Emptylist");
                Bitmap myBitmap = BitmapFactory.decodeFile(f.get(position));
                holder.imageview.setImageBitmap(myBitmap);
                holder.textview.setText(fn.get(position));
           /// }
//           else{
//                System.out.println("Empty");
//                holder.imageview.setImageResource(R.drawable.defaultdog);
//                holder.textview.setText("Your filename.png");
//           }
            return convertView;
        }
    }
    class ViewHolder {
        ImageView imageview;
        TextView textview;
    }
    @Override
    public void onBackPressed()
    {

        Intent backPressActivityIntent = new Intent(PicturesPageActivity.this, MainActivity.class);
        startActivityForResult(backPressActivityIntent, 0);

    }
}
