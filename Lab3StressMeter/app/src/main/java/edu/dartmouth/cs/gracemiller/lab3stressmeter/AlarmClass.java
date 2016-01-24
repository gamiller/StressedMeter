package edu.dartmouth.cs.gracemiller.lab3stressmeter;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Vibrator;

/**
 * Created by gracemiller on 1/24/16.
 */
public class AlarmClass {
    Uri notification;
    static MediaPlayer mediaPlayer;
    static Vibrator vibrator;

    public void startSound(Context context){
        //ringtone/vibrtion

        try {
            notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
            mediaPlayer = new MediaPlayer();
            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mediaPlayer.setDataSource(context, notification);
            mediaPlayer.prepare();
            mediaPlayer.start();
        } catch (Exception e) {
            e.printStackTrace();

        }

        // Get instance of Vibrator from current Context
        vibrator= (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);

        // Start without a delay
        // Vibrate for 100 milliseconds
        // Sleep for 1000 milliseconds
        long[] pattern = {0, 100, 1000};

        // The '0' here means to repeat indefinitely
        // '0' is actually the index at which the pattern keeps repeating from (the start)
        // To repeat the pattern from any other point, you could increase the index, e.g. '1'
        vibrator.vibrate(pattern, 0);

    }
//    public void stopSound(){
//        mediaPlayer.stop();
//        vibrator.cancel();
//    }
}
