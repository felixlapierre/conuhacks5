package com.example.tunein;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;

import android.os.Looper;
import android.provider.Settings;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.CollationElementIterator;
import java.util.List;

import com.google.android.gms.location.*;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Url;

public class MainActivity extends AppCompatActivity {

    MediaPlayer player;
    FusedLocationProviderClient mFusedLocationClient;
//    TextView latitudeText, longitudeText;
    int PERMISSION_ID = 69;
    Button playBtn;
    TextView getSongResult;
    boolean musicIsPlaying = false;
    boolean musicIsPaused = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        playBtn = findViewById(R.id.playBtn);
        //player = MediaPlayer.create(MainActivity.this, R.raw.song);



//        latitudeText = findViewById(R.id.latitudeText);
//        longitudeText = findViewById(R.id.longitudeText);
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        getSongResult = findViewById(R.id.getSongResult);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://172.30.184.91:3000/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        TuneInApi tuneInApi = retrofit.create(TuneInApi.class);
        Call<List<Song>> call = tuneInApi.getSongs();
        call.enqueue(new Callback<List<Song>>() {
            @Override
            public void onResponse(Call<List<Song>> call, Response<List<Song>> response) {
                if(!response.isSuccessful()) {
                    getSongResult.setText("Code: " + response.code());
                    return;
                }

                List<Song> songs = response.body();
                for(Song song : songs) {
                    String content = "";
                    content += "message" + song.getMessage();
//                    content += "id: " + song.getId() + "\n";
//                    content += "title: " + song.getTitle();

                    getSongResult.append(content);
                }
                 // getSongResult.append(response.body());
            }

            @Override
            public void onFailure(Call<List<Song>> call, Throwable t) {
                getSongResult.setText("LIGMA BALLS" + t.getMessage());
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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

    public void playMusic(View view) {
        if(musicIsPlaying) {
            player.pause();
            musicIsPaused = true;
            playBtn.setText("Play");
            musicIsPlaying = false;

        } else if(musicIsPaused) {
            player.start();
            musicIsPaused = false;
            musicIsPlaying = true;
            playBtn.setText("Pause");
        } else {
            try {
                player = new MediaPlayer();
                player.setAudioStreamType(AudioManager.STREAM_MUSIC);
                player.setDataSource("https://cdn.apps.playnetwork.com/master/a5931c0db476ea9e4406dfd0a362e8839190e0ef38a4d52b397989480b85b7b4.ogg?Signature=dRMUViSp5AP91Lh1GUm49NDx9mhJ8745dJxUNg6jN1UKMLmEM-YEeQ4tykzAvACCVzslspO6yac3SbEvnBR--dQDgnqkM7YbxWSSdSFWxPDkIF8I-Qy2-XXZKV6e-595PAF4nScgEZsB7p224VME2p8vjqj9EaRjful6t1aKnPNyMPZum9ISZSyBe8~vQyGKa5Ho0kLQ4R6xEresXQKsS~WV7hD-b3rtQsHApvrqNXQvItfe1gBWBqgFzrqCdGE3vXkTYChOn9KXCZiC~3IAP8y2kQV3Gvj6GbAFMe6MEWpwnLbl8S87xm7C7fkSOBNTBHsmO7sZ14z284oyMZSmNg__&Key-Pair-Id=APKAJ4GOPJEICF5TREYA&Expires=1580026714");
                player.prepare();
                player.start();
                playBtn.setText("Pause");
                musicIsPlaying = true;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void stopMusic(View view) {
        if(musicIsPlaying) {
            playBtn.setText("Play");
        }
        
        player.stop();
        musicIsPaused = false;
        musicIsPlaying = false;
        //player = MediaPlayer.create(MainActivity.this, R.raw.song);
    }

    public void nextSong(View view) {
        // TODO
    }

    public void prevSong(View view) {
        // TODO
    }

    public void getLocationButtonClicked(View view){
        getLastLocation();
    }

    private void getLastLocation(){
        if (checkPermissions()) {
            if (isLocationEnabled()) {
                mFusedLocationClient.getLastLocation().addOnCompleteListener(
                        new OnCompleteListener<Location>() {
                            @Override
                            public void onComplete(@NonNull Task<Location> task) {
                                Location location = task.getResult();
                                if (location == null) {
                                    requestNewLocationData();
                                } else {
                                    Context context = getApplicationContext();
                                    Toast.makeText(context, "Latitude: " + location.getLatitude(), Toast.LENGTH_LONG).show();
                                    Toast.makeText(context, "Longitude: " + location.getLongitude(), Toast.LENGTH_LONG).show();
//                                    latitudeText.setText(location.getLatitude()+"");
//                                    longitudeText.setText(location.getLongitude()+"");
                                }
                            }
                        }
                );
            } else {
                Toast.makeText(this, "Turn on location", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(intent);
            }
        } else {
            requestPermissions();
        }
    }

    private void requestNewLocationData(){

        LocationRequest mLocationRequest = new LocationRequest();
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mLocationRequest.setInterval(0);
        mLocationRequest.setFastestInterval(0);
        //mLocationRequest.setNumUpdates(1);
        mLocationRequest.setInterval(10000);
        mLocationRequest.setFastestInterval(5000);

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        mFusedLocationClient.requestLocationUpdates(
                mLocationRequest, mLocationCallback,
                Looper.myLooper()
        );

    }

    private LocationCallback mLocationCallback = new LocationCallback() {
        @Override
        public void onLocationResult(LocationResult locationResult) {
            Location mLastLocation = locationResult.getLastLocation();
            Context context = getApplicationContext();
            Toast.makeText(context, "Latitude: " + mLastLocation.getLatitude(), Toast.LENGTH_LONG).show();
            Toast.makeText(context, "Longitude: " + mLastLocation.getLongitude(), Toast.LENGTH_LONG).show();
//            latitudeText.setText(mLastLocation.getLatitude()+"");
//            longitudeText.setText(mLastLocation.getLongitude()+"");
        }
    };

    private boolean checkPermissions() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            return true;
        }
        return false;
    }

    private void requestPermissions() {
        ActivityCompat.requestPermissions(
                this,
                new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION},
                PERMISSION_ID
        );
    }

    private boolean isLocationEnabled() {
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(
                LocationManager.NETWORK_PROVIDER
        );
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_ID) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getLastLocation();
            }
        }
    }

    @Override
    public void onResume(){
        super.onResume();
        if (checkPermissions()) {
            getLastLocation();
        }
    }
}
