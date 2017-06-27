package com.example.manojpun.camc;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.manojpun.camc.data.Post;
import com.example.manojpun.camc.data.remote.APIService;
import com.example.manojpun.camc.data.remote.ApiUtils;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.ContentValues.TAG;
public class Screen extends Activity {

    private Button muploadBtn;
    private static final int CAMERA_REQUEST_CODE = 1;
    String API_KEY = "ajrOIHtndzmshMFNJYiST80u7uM6p1cMEpKjsnMgfdeEkFypYm";
    String albumKey = "b1ccb6caa8cefb7347d0cfb65146d5e3f84608f6ee55b1c90d37ed4ecca9b273", albumName = "photo_album";
    Button uploadB;
    private APIService mAPIService;

    EditText nameTxt, descTxt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_screen);


        muploadBtn = (Button) findViewById(R.id.upload);
        muploadBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//                startActivityForResult(intent, CAMERA_REQUEST_CODE);
                // declare all the layouts

                nameTxt = (EditText) findViewById(R.id.getname);
                descTxt = (EditText) findViewById(R.id.getskill);
                String tmpVari ="";

                Log.d("FIRST", "Attempting to open camera");

            //Chat Conversation End


                Intent imageIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
                File imagesFolder = new File(Environment.getExternalStorageDirectory(), "MyImages");
                imagesFolder.mkdirs();
                String filePath = "/storage/emulated/0/MyImages/QR_" + timeStamp + ".png" ;
                File image = new File(imagesFolder, "QR_" + timeStamp + ".png");

                Log.d("BUILD PATH", "MADE THE IMAGE AND PATH");


                Uri uriSavedImage = Uri.fromFile(image);

                imageIntent.putExtra(MediaStore.EXTRA_OUTPUT, uriSavedImage);

                startActivityForResult(imageIntent, CAMERA_REQUEST_CODE);
              //  Log.d("INTENT", filePath);


                mAPIService = ApiUtils.getAPIService();
                sendPost(1.0, "pred", "uid");
                //tmpVari = findPerson(filePath);
                Log.d("INTENT", filePath);
                tmpVari = tmpVari + "/describe";

                Log.d("DBPATH", tmpVari);

                Log.d("BEFORE", "Before firebase");
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                Log.d("AFTER", "After firebase");
                DatabaseReference myRef = database.getReference(tmpVari);
                Log.d("SUPERAFTER", "Database ref");

                myRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        // This method is called once with the initial value and again
                        // whenever data at this location is updated.
                        String value = dataSnapshot.getValue(String.class);
                        Log.d(TAG, "Value is: " + value);
                    }

                    @Override
                    public void onCancelled(DatabaseError error) {
                        // Failed to read value
                        Log.w(TAG, "Failed to read value.", error.toException());
                    }
                });

                nameTxt.setText("Elsie");
                descTxt.setText("Computer Science (Intermediate : Backend)");

            }
        });


        //findPerson("https://aos.iacpublishinglabs.com/question/aq/1400px-788px/pandas-live_64dff22c2fe56e9.jpg?domain=cx.aos.ask.com");
    }

    public void sendPost(Double confidence, String prediction, String uid) {
        mAPIService.savePost(confidence, prediction, uid).enqueue(new Callback<Post>() {
            @Override
            public void onResponse(Call<Post> call, Response<Post> response) {

                if(response.isSuccessful()) {
                    showResponse(response.body().toString());
                    Log.i("Success", "post submitted to API." + response.body().toString());
                }
            }

            @Override
            public void onFailure(Call<Post> call, Throwable t) {
                Log.e("Failure", "Unable to submit post to API.");
            }
        });
    }

    public void showResponse(String response) {
        nameTxt.setText(response);
    }


    @Override
    protected void onActivityResult(int requestcode, int resultcode, Intent data) {
        super.onActivityResult(requestcode, resultcode, data);

    }
    private void makeAlbum(){
        try {
            // Create Album
            HttpResponse<JsonNode> response = Unirest.post("https://lambda-face-recognition.p.mashape.com/album")
                    .header("X-Mashape-Key", API_KEY)
                    .header("Content-Type", "application/x-www-form-urlencoded")
                    .header("Accept", "application/json")
                    .field("album", albumName)
                    .asJson();
            descTxt.setText(response.getBody().toString());
        } catch (UnirestException e) {
            System.out.println("Error: " + e);
        }
    }
    private void inputImages(){
        try {
            // Create Album
            HttpResponse<JsonNode> response = Unirest.post("https://lambda-face-recognition.p.mashape.com/album")
                    .header("X-Mashape-Key", API_KEY)
                    .header("Content-Type", "application/x-www-form-urlencoded")
                    .header("Accept", "application/json")
                    .field("album", albumName)
                    .asJson();
            descTxt.setText(response.getBody().toString());

            // put in images on the album
            Unirest.post("https://lambda-face-recognition.p.mashape.com/album_train")
                    .header("X-Mashape-Key", API_KEY)
                    .field("album", albumName)
                    .field("albumkey",albumKey)
                    .field("urls", "https://s-media-cache-ak0.pinimg.com/originals/4c/49/b1/4c49b1cb43b74b7d721fd1faed6c6716.png")
                    .asJson();

            Unirest.post("https://lambda-face-recognition.p.mashape.com/album_train")
                    .header("X-Mashape-Key", API_KEY)
                    .field("album", albumName)
                    .field("albumkey",albumKey)
                    .field("entryid", "Elsie")
                    .field("urls", "https://scontent-iad3-1.xx.fbcdn.net/v/t1.0-9/1609788_10201867772960402_5849510141364386574_n.jpg?oh=2c45253aea65f94d4617653ab24a3783&oe=58FDD006")
                    .field("urls", "https://scontent-iad3-1.xx.fbcdn.net/v/t1.0-9/16388048_10208414557025912_2992000010828092739_n.jpg?oh=c2bbbe2bcdd021f42142588d0fd9a264&oe=592DA8E5")
                    .asJson();

            Unirest.post("https://lambda-face-recognition.p.mashape.com/album_train")
                    .header("X-Mashape-Key", API_KEY)
                    .field("album", albumName)
                    .field("albumkey",albumKey)
                    .field("entryid", "Manoj")
                    .field("urls", "https://scontent-iad3-1.xx.fbcdn.net/v/t1.0-9/15055742_1193379400729138_4575384512027996342_n.jpg?oh=b90835d1adf18a14057e73ce30177590&oe=592E07E5")
                    .field("urls", "https://scontent-iad3-1.xx.fbcdn.net/v/t1.0-9/600817_500013996732352_1963865461_n.jpg?oh=7415402df6fc76703bdaf92a00073daa&oe=593C27E7")
                    .asJson();

        } catch (UnirestException e) {
            System.out.println("Error: " + e);
        }
    }
    protected String findPerson(String file){
        String name = "";
        try{
            HttpResponse<JsonNode> response = Unirest.post("https://lambda-face-recognition.p.mashape.com/recognize")
                    .header("X-Mashape-Key", API_KEY)
                    .header("Accept", "application/json")
                    .field("album", albumName)
                    .field("albumkey", albumKey)
                    .field("files", new File(file))
                    .asJson();
            descTxt.setText(response.getBody().toString());
            name = response.toString();
        } catch (UnirestException e){
            System.out.println("Error" + e);
        }
        return name;
    }
}


