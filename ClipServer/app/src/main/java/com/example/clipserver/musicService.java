package com.example.clipserver;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.IBinder;
import android.os.RemoteException;

import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;

import java.io.IOException;


public class musicService extends Service {

    // media player
    MediaPlayer mediaPlayer = new MediaPlayer();
    // notification
    Notification notification;
    // channel id and notification id for notification channel
    private static final String CHANNEL_ID      = "MediaPlaybackService";
    private static final int    NOTIFICATION_ID = 1;
    // length of the music when stopped
    int length = 0;
    // url for getting the music from google drive
    String[] geturl = {"https://drive.google.com/uc?export=download&id=14i1hR7HxbzJblSdaoDpJ5AGmv8WSXvir",
            "https://drive.google.com/uc?export=download&id=1-uq7sD5prpZxDZ2r9jeV57UaQ_DsLWbP",
            "https://drive.google.com/uc?export=download&id=1GGri6zLihO4Crohvf9V_lVYf1ErB9GeJ",
            "https://drive.google.com/uc?export=download&id=11GV6a_wamEm0LD8iWoDJnH9_qZB8kK-7",
            "https://drive.google.com/uc?export=download&id=1s04IzzzRB_qrkWLyTOiccTJkaFF4fnWs"
    };

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onCreate() {
        super.onCreate();

        // UB: Starting in Oreo notifications have a notification channel
        //     The channel defines basic properties of
        this.createNotificationChannel();

        final Intent notificationIntent = new Intent();

        notificationIntent.setComponent(new ComponentName("package com.example.audioclient",
                "package com.example.audioclient.MainActivity"));

        final PendingIntent pendingIntent = PendingIntent.getActivity(this, 0,
                notificationIntent, 0) ;

        notification =
                new NotificationCompat.Builder(getApplicationContext(), CHANNEL_ID)
                        .setSmallIcon(android.R.drawable.ic_media_play)
                        .setOngoing(true).setContentTitle("Music Playing")
                        .setContentText("Click to Access Music Player")
                        .setTicker("Music is playing!")
                        .setFullScreenIntent(pendingIntent, false)
                        .build();

        // Put this Service in a foreground state, so it won't
        // readily be killed by the system
        startForeground(NOTIFICATION_ID, notification);

    }

    // UB 11-12-2018:  Now Oreo wants communication channels...
    private void createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "Music player notification";
            String description = "The channel for music player notifications";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    private final musicAIDL.Stub mbinder = new musicAIDL.Stub() {
    // sends the url of google drive for downloading the song
        public String[] getURL(){
            return new String[] {"https://drive.google.com/uc?export=download&id=14i1hR7HxbzJblSdaoDpJ5AGmv8WSXvir",
                                 "https://drive.google.com/uc?export=download&id=1-uq7sD5prpZxDZ2r9jeV57UaQ_DsLWbP",
                                 "https://drive.google.com/uc?export=download&id=1GGri6zLihO4Crohvf9V_lVYf1ErB9GeJ",
                                 "https://drive.google.com/uc?export=download&id=11GV6a_wamEm0LD8iWoDJnH9_qZB8kK-7",
                                 "https://drive.google.com/uc?export=download&id=1s04IzzzRB_qrkWLyTOiccTJkaFF4fnWs"
            };
        }

        // method to play the song, works on the index received
        @Override
        public boolean playMusic(int song) throws RemoteException {
            try{
                mediaPlayer.reset();
                try {
                    // retrives the song
                    mediaPlayer.setDataSource(geturl[song]);

                } catch (IOException e) {
                    e.printStackTrace();
                }
                try {
                    // prepares the music, takes longer for buffering
                    mediaPlayer.prepare();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                // starts the music
                mediaPlayer.start();
                return true;
            }catch(Exception ex){
                ex.printStackTrace();
            }
            return false;
        }

        @Override
        public boolean pauseMusic(int song) throws RemoteException {
            try{
                // pause the music and get the current position
                mediaPlayer.pause();
                length = mediaPlayer.getCurrentPosition();
                return true;
            } catch (IllegalStateException e) {
                e.printStackTrace();
            }
            return false;
        }

        @Override
        public boolean resumeMusic(int song) throws RemoteException {
            try{
                // resumes the music form the point it was stopped
                mediaPlayer.seekTo(length);
                mediaPlayer.start();
                return true;
            }catch(Exception ex){
                ex.printStackTrace();
            }
            return false;
        }

        @Override
        public boolean stopMusic(int song) throws RemoteException {
            try{
                // stops the music
                mediaPlayer.stop();
                mediaPlayer = null;
                return true;
            }catch(Exception ex){
                ex.printStackTrace();
            }
            return false;
        }

    };

    // binds the service
    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        return mbinder;
    }
    // unbinds the service
    @Override
    public boolean onUnbind(Intent intent) {
        return super.onUnbind(intent);
    }
}
