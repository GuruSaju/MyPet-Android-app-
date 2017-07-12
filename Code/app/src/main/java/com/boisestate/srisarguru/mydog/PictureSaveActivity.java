package com.boisestate.srisarguru.mydog;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;


public class PictureSaveActivity extends ActionBarActivity {
    Bitmap photoDetail;
    ImageView pic;
    Button cancel,save;
    EditText filename;
    String fileNAme;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_picture_save);
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        save=(Button)findViewById(R.id.savebutton);
        cancel=(Button)findViewById(R.id.cancelbutton);
        filename=(EditText)findViewById(R.id.editText);

        pic=(ImageView)findViewById(R.id.imageView);
        Bundle b=this.getIntent().getExtras();
        byte[] byteArray;
        this.photoDetail = null;

        byteArray = b.getByteArray("picture");
        if (byteArray != null) {
            this.photoDetail = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
        }
        pic.setImageBitmap(photoDetail);
        save.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                fileNAme=filename.getText().toString();
                SaveImage(photoDetail,filename.getText().toString());

            }

        });
        cancel.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
               finish();

            }

        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_picture_save, menu);
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
    private void SaveImage(Bitmap finalBitmap,String filename) {
        Boolean isSDPresent = android.os.Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED);

        String root2 = getFilesDir().toString();

        File myDir2 = new File(root2 + "/MyDog");
       boolean loop=true;
       if(myDir2.exists()){
           loop=false;
           String fname = filename +".png";
           File file = new File(myDir2, fname);
           if (file.exists ()) file.delete ();
           try {
               FileOutputStream out = new FileOutputStream(file);
               finalBitmap.compress(Bitmap.CompressFormat.PNG, 90, out);

               out.flush();
               out.close();

           } catch (Exception e) {
               e.printStackTrace();
           }
           Toast.makeText(PictureSaveActivity.this,
                   "Saved", Toast.LENGTH_SHORT).show();


           Intent breedListActivityIntent = new Intent(PictureSaveActivity.this, PicturesPageActivity.class);
           startActivityForResult(breedListActivityIntent, 0);
       }


       else if(isSDPresent) {

            String root = Environment.getExternalStorageDirectory().toString();
            File myDir = new File(root + "/MyDog");
            if (myDir.exists()) {
                loop=false;
                String fname = filename +".png";
                File file = new File(myDir, fname);
                if (file.exists ()) file.delete ();
                try {
                    FileOutputStream out = new FileOutputStream(file);
                    finalBitmap.compress(Bitmap.CompressFormat.PNG, 90, out);

                    out.flush();
                    out.close();

                } catch (Exception e) {
                    e.printStackTrace();
                }
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT)
               {
                    Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
                    File f = new File("file://"+ Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES));
                    Uri contentUri = Uri.fromFile(f);
                    mediaScanIntent.setData(contentUri);
                    this.sendBroadcast(mediaScanIntent);
                }
                else
                {
                    sendBroadcast(new Intent(Intent.ACTION_MEDIA_MOUNTED, Uri.parse("file://" + Environment.getExternalStorageDirectory())));
                }
                Toast.makeText(PictureSaveActivity.this,
                        "Saved", Toast.LENGTH_SHORT).show();


                Intent savedBackActivityIntent = new Intent(PictureSaveActivity.this, PicturesPageActivity.class);
                startActivityForResult(savedBackActivityIntent, 0);
            }
        }
        if(loop)
        {
           buildAlertMessageStorage();
        }
    }
    private void buildAlertMessageStorage() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Where do you want to save your files?")
                .setCancelable(false)
                .setPositiveButton("Internal         Storage", new DialogInterface.OnClickListener() {
                    public void onClick(@SuppressWarnings("unused") final DialogInterface dialog, @SuppressWarnings("unused") final int id) {

                        String root2 = getFilesDir().toString();

                        File myDir2 = new File(root2 + "/MyDog");
                        myDir2.mkdir();
                        SaveImage(photoDetail,fileNAme);
                        dialog.cancel();
                    }
                })
                .setNegativeButton("External Storage (Recommended)", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, @SuppressWarnings("unused") final int id) {

                        if(isExternalStorageWritable())
                        {
                            String root = Environment.getExternalStorageDirectory().toString();
                            File myDir = new File(root + "/MyDog");
                            myDir.mkdir();
                            SaveImage(photoDetail,fileNAme);
                            dialog.cancel();
                        }
                        else
                        {
                            Toast.makeText(PictureSaveActivity.this,
                                    "No External Memory", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
        final AlertDialog alert = builder.create();
        alert.show();
    }
    public boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            return true;
        }
        return false;
    }
}
