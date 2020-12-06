package com.example.audioclient;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.ResolveInfo;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import com.example.clipserver.musicAIDL;

import java.io.IOException;


public class MainActivity extends AppCompatActivity {

    private musicAIDL mMusic; // AIDL object for using the methods
    String[] url = null; // stores url from google drive
    String finalUrl; // stores url for the selected music
    MediaPlayer mediaPlayer = new MediaPlayer(); // media player object
    Button bind; // button for binding the service
//    Button pause; // pause the song
//    Button Stop; // stop the song
//    int length = 0 ; // stores at what time the music was stopped
    Button song1; // button to play song 1
//    Button song2; // button to play song 2
//    Button song3; // button to play song 3
//    Button song4; // button to play song 4
//    Button song5; // button to play song 5
//    Button resume; // button to resume the song
//    Button unbind; // button to stop the service
    Intent i; // intent to bind service
    int ifSong = 0; // checks if song is being played or not
    int ifBound = 0; // checks if the service is connected or not

    private final ServiceConnection mConnection = new ServiceConnection() {

        public void onServiceConnected(ComponentName className, IBinder iservice) {
            mMusic = musicAIDL.Stub.asInterface(iservice);

            ifBound = 1;
            // Call KeyGenerator and get a new ID
            if (ifBound == 1) {

                try {
                    url = mMusic.getURL();
                    ifBound = 1;
                    Toast.makeText(MainActivity.this, "Service started",Toast.LENGTH_SHORT).show();
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }


        }

        public void onServiceDisconnected(ComponentName className) {

            mMusic = null;
            ifBound = 0;
            mediaPlayer.reset();
            Toast.makeText(MainActivity.this, "Service disconnected",Toast.LENGTH_SHORT).show();
        }
    };


    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);

        setContentView(R.layout.activity_main);
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        bind = findViewById(R.id.buttonBind);
//        pause =  findViewById(R.id.buttonPause);
//        Stop =  findViewById(R.id.buttonStop);
        song1 = findViewById(R.id.buttonSong1);
//        song2 = findViewById(R.id.buttonSong2);
//        song3 = findViewById(R.id.buttonSong3);
//        song4 = findViewById(R.id.buttonSong4);
//        song5 = findViewById(R.id.buttonSong5);
//        resume = findViewById(R.id.buttonResume);
//        unbind = findViewById(R.id.buttonUnbind);


        song1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // plays the first song
                if(ifBound == 1) {
                    Toast.makeText(MainActivity.this, "Music will start in a moment",Toast.LENGTH_SHORT).show();

                    mediaPlayer.reset();
                    ifSong = 1;
                    finalUrl = url[0];
                    try {
                        mediaPlayer.setDataSource(finalUrl);

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    try {
                        mediaPlayer.prepare(); // might take long! (for buffering, etc)
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    mediaPlayer.start();
//                    pause.setEnabled(true);
//                    resume.setEnabled(false);
                }
                else
                {
                    Toast.makeText(MainActivity.this, "Service isn't running",Toast.LENGTH_SHORT).show();
                }
            }

        });
//        song2.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                // plays second song
//                if(ifBound == 1) {
//                    Toast.makeText(MainActivity.this, "Music will start in a moment",Toast.LENGTH_SHORT).show();
//                    mediaPlayer.reset();
//                    ifSong = 1;
//                    finalUrl = url[1];
//                    try {
//                        mediaPlayer.setDataSource(finalUrl);
//
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                    try {
//                        mediaPlayer.prepare(); // might take long! (for buffering, etc)
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                    mediaPlayer.start();
//                    pause.setEnabled(true);
//                    resume.setEnabled(false);
//                }
//                else
//                {
//                    Toast.makeText(MainActivity.this, "Service isn't running",Toast.LENGTH_SHORT).show();
//                }
//            }
//
//        });
//
//        song3.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                // plays third song
//                if(ifBound == 1) {
//                    Toast.makeText(MainActivity.this, "Music will start in a moment",Toast.LENGTH_SHORT).show();
//                    mediaPlayer.reset();
//                    ifSong = 1;
//                    finalUrl = url[2];
//                    try {
//                        mediaPlayer.setDataSource(finalUrl);
//
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                    try {
//                        mediaPlayer.prepare(); // might take long! (for buffering, etc)
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                    mediaPlayer.start();
//                    pause.setEnabled(true);
//                    resume.setEnabled(false);
//                }
//                else
//                {
//                    Toast.makeText(MainActivity.this, "Service isn't running",Toast.LENGTH_SHORT).show();
//                }
//            }
//
//        });
//        song4.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                // plays fourth song
//                if(ifBound == 1) {
//                    Toast.makeText(MainActivity.this, "Music will start in a moment",Toast.LENGTH_SHORT).show();
//
//                    mediaPlayer.reset();
//                    ifSong = 1;
//                    finalUrl = url[3];
//                    try {
//                        mediaPlayer.setDataSource(finalUrl);
//
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                    try {
//                        mediaPlayer.prepare(); // might take long! (for buffering, etc)
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                    mediaPlayer.start();
//                    pause.setEnabled(true);
//                    resume.setEnabled(false);
//                }
//                else
//                {
//                    Toast.makeText(MainActivity.this, "Service isn't running",Toast.LENGTH_SHORT).show();
//                }
//            }
//
//        });
//
//        song5.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                // plays fifth song
//                if(ifBound == 1) {
//                    Toast.makeText(MainActivity.this, "Music will start in a moment",Toast.LENGTH_SHORT).show();
//                    mediaPlayer.reset();
//                    ifSong = 1;
//                    finalUrl = url[4];
//                    try {
//                        mediaPlayer.setDataSource(finalUrl);
//
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                    try {
//                        mediaPlayer.prepare(); // might take long! (for buffering, etc)
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                    mediaPlayer.start();
//                    pause.setEnabled(true);
//                    resume.setEnabled(false);
//                }
//                else
//                {
//                    Toast.makeText(MainActivity.this, "Service isn't running",Toast.LENGTH_SHORT).show();
//                }
//            }
//
//        });


//        pause.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if(ifBound == 1) {
//                    if(ifSong == 1) {
//                        mediaPlayer.pause();
//                        length = mediaPlayer.getCurrentPosition();
//                        resume.setEnabled(true);
//                        pause.setEnabled(false);
//                        ifSong = 0;
//                        Toast.makeText(MainActivity.this, "Music pause",Toast.LENGTH_SHORT).show();
//                    }
//                    else
//                    {
//                        Toast.makeText(MainActivity.this, "Song is not being played",Toast.LENGTH_SHORT).show();
//                    }
//                }
//                else
//                {
//                    Toast.makeText(MainActivity.this, "Service isn't running",Toast.LENGTH_SHORT).show();
//                }
//            }
//        });
//
//        resume.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if(ifBound == 1) {
//                    ifSong = 1;
//                    mediaPlayer.seekTo(length);
//                    mediaPlayer.start();
//                    pause.setEnabled(true);
//                    resume.setEnabled(false);
//                    Toast.makeText(MainActivity.this, "Music resumed",Toast.LENGTH_SHORT).show();
//                }
//                else
//                {
//                    Toast.makeText(MainActivity.this, "Service isn't running",Toast.LENGTH_SHORT).show();
//                }
//            }
//        });
//
//        Stop.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if(ifBound == 1) {
//                    ifSong = 0;
//                    mediaPlayer.stop();
//                    pause.setEnabled(true);
//                    resume.setEnabled(false);
//                    Toast.makeText(MainActivity.this, "Music stopped",Toast.LENGTH_SHORT).show();
//                }
//                else
//                {
//                    Toast.makeText(MainActivity.this, "Service isn't running",Toast.LENGTH_SHORT).show();
//                }
//            }
//        });
        bind.setOnClickListener(new View.OnClickListener() {

            @RequiresApi(api = Build.VERSION_CODES.O)
            public void onClick(View v) {

                if (ifBound == 0) {

                    i = new Intent(musicAIDL.class.getName());
                    ResolveInfo info = getPackageManager().resolveService(i, 0);
                    i.setComponent(new ComponentName(info.serviceInfo.packageName, info.serviceInfo.name));
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        startForegroundService(i);
                    }
                    bindService(i, mConnection, Context.BIND_AUTO_CREATE);
//                    resume.setEnabled(false);
                }
                else
                {
                    Toast.makeText(MainActivity.this, "Service is already running",Toast.LENGTH_SHORT).show();
                }

            }
        });

//        unbind.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if(ifBound == 1) {
//                    unbindService(mConnection);
//                    stopService(i);
//                    ifBound = 0;
//                    ifSong = 0;
//                    mediaPlayer.reset();
//                    bind.setEnabled(true);
//                    Toast.makeText(MainActivity.this, "Service Stopped",Toast.LENGTH_SHORT).show();
//                }
//                else
//                {
//                    Toast.makeText(MainActivity.this, "Service isn't running",Toast.LENGTH_SHORT).show();
//                }
//            }
//        });
    }
}

