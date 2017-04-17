package com.test.sampletest;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.SeekBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity
{

    SeekBar mSeekBar;
    TextView lblMin;
    static int inc;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mSeekBar = (SeekBar)findViewById(R.id.seekBarDistance);
        lblMin = (TextView)findViewById(R.id.lblMin);

        mSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener()
        {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser)
            {
                getProgress(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar)
            {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar)
            {
            }
        });
    }

    private void getProgress(int progress)
    {
        String p = "<";
        if(progress<=25)
        {
            //+25
            inc = 25;
        }else if(progress<=50)
        {
            //+25
            inc = 50;
        }
        else if(progress<=75)
        {
            //+25
            inc = 75;
        }
        else if(progress<=100)
        {
            //+25
            inc = 100;
        }
        else if(progress<=150)
        {
            //+25
            inc = 150;
        }
        else if(progress<=200)
        {
            //+25
            inc = 200;
        }
        else if(progress<=300)
        {
            //+25
            inc = 300;
        }
        else if(progress<=400)
        {
            //+25
            inc = 400;
        }
        else if(progress<=500)
        {
            //+25
            inc = 500;
        }
        else if(progress<=750)
        {
            //+25
            inc = 750;
        }
        else if(progress<=1000)
        {
            //+25
            inc = 1000;
        }
        else if(progress<=1500)
        {
            //+25
            inc = 1500;
        }
        else if(progress<=2000)
        {
            //+25
            inc = 2000;
        }
        else if(progress<2500)
        {
            //+25
            inc = 2500;
        }else{
            p = ">";
            inc = 2500;
        }
        p = p + inc;
        lblMin.setText(p);
        Log.d("SEEK", "getProgress: " + p);
    }
}
