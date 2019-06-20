package com.saumya.networkanalyzer;

import android.content.Context;
import android.content.Intent;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.github.anastr.speedviewlib.AwesomeSpeedometer;

import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class SpeedActivity extends AppCompatActivity {

    AwesomeSpeedometer awesomeSpeedometer;
    SeekBar seekBar;
    ImageView BackButton;
    Button downLoadSpeed, stop, uploadSpeed;
    TextView textSpeed;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_speed);

        findViewsById();

        //-------------------Manage DownloadButton---------------------------------
        final long downloadTime = TimeUnit.MILLISECONDS.toSeconds(60000);
        downLoadSpeed.setOnClickListener(
                new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                awesomeSpeedometer.speedTo(getSpeed(),downloadTime);
            }
        });


        //------------------Manage UploadTime----------------------------------
        final long uploadTime = TimeUnit.MILLISECONDS.toSeconds(90000);
        uploadSpeed.setOnClickListener(new View.OnClickListener() {
                                           @Override
                                           public void onClick(View v) {
                                               awesomeSpeedometer.speedTo(getSpeed(),uploadTime);
                                           }
                                       });

        //----------------stop Button-------------------------------
        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                awesomeSpeedometer.stop();
            }
        });

        BackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getBaseContext(), MainActivity.class));
            }
        });

        //--------------------------manage SeekBar--------------------------------
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                textSpeed.setText(String.format(Locale.getDefault(), "%d", progress));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });
    }

    private void findViewsById() {
        awesomeSpeedometer = findViewById(R.id.awesomeSpeedometer);
        seekBar = findViewById(R.id.seekBar);
        downLoadSpeed = findViewById(R.id.download);
        uploadSpeed=findViewById(R.id.upload);
        stop = findViewById(R.id.stop);
        BackButton=findViewById(R.id.back);
        textSpeed = findViewById(R.id.textSpeed);
    }

    private Integer getSpeed(){
        //----------------This method gives the Normal Network Speed only if network is Speed-----------------
       WifiManager wifiManager =(WifiManager) getBaseContext().getApplicationContext().getSystemService(Context.WIFI_SERVICE) ;
        WifiInfo wifiInfo = wifiManager.getConnectionInfo();
        int speedMbps = wifiInfo.getLinkSpeed();
        return speedMbps;
    }
    //------------------------Will handle the network for Mobile data later---------------------------------
    }

