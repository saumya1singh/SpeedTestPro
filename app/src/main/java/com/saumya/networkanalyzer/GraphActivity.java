package com.saumya.networkanalyzer;

import android.content.Context;
import android.content.Intent;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.github.anastr.speedviewlib.ImageLinearGauge;
import com.github.anastr.speedviewlib.LinearGauge;
import com.github.anastr.speedviewlib.ProgressiveGauge;

import java.util.Locale;

public class GraphActivity extends AppCompatActivity {

    ImageLinearGauge imageLinearGauge;
    SeekBar seekBar;
    Button start;
    TextView textSpeed;
    ImageView back;
    ProgressiveGauge progressiveGauge;
    private AlphaAnimation buttonClick = new AlphaAnimation(1F, 0.2F);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph);

        findallViews();


        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.startAnimation(buttonClick);
                imageLinearGauge.speedTo(getSpeed(), 20);
                progressiveGauge.speedTo(getSpeed(), 3000);

            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.startAnimation(buttonClick);
                startActivity(new Intent(getBaseContext(), MainActivity.class));
            }
        });

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

    private void findallViews() {
        imageLinearGauge = (ImageLinearGauge) findViewById(R.id.gauge);
        progressiveGauge=(ProgressiveGauge)findViewById(R.id.progressiveGauge);
        seekBar = (SeekBar) findViewById(R.id.seekBar);
        start = (Button) findViewById(R.id.ok);
        textSpeed = (TextView) findViewById(R.id.textSpeed);
        back=(ImageView)findViewById(R.id.graphback);
    }
    private Integer getSpeed(){
        //----------------This method gives the Normal Network Speed only if network is Speed-----------------
        WifiManager wifiManager =(WifiManager) getBaseContext().getApplicationContext().getSystemService(Context.WIFI_SERVICE) ;
        WifiInfo wifiInfo = wifiManager.getConnectionInfo();
        int speedMbps = wifiInfo.getLinkSpeed();
        return speedMbps;
    }


}

