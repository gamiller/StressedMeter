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

    //start the vibration and sound to play until photo is chosen
    public void startSound(Context context){
        //ringtone/vibrtion

        try {
            //get the uri of the ringtone to be played
            notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
            //create the media player, set the audio stream
            mediaPlayer = new MediaPlayer();
            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mediaPlayer.setDataSource(context, notification);
            mediaPlayer.prepare();
            //start the media player
            mediaPlayer.start();
        } catch (Exception e) {
            e.printStackTrace();

        }

        // Get instance of Vibrator from current Context
        vibrator= (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);

        //create the pattern of vibration
        // Start without a delay, Vibrate for 100 millisecond, Sleep for 1000 milliseconds
        long[] pattern = {0, 100, 1000};

        //repeat the pattern from the beginning
        vibrator.vibrate(pattern, 0);

    }

}
