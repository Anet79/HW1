package com.example.hw01.utils;

import android.content.Context;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.VibrationEffect;
import android.os.Vibrator;

public class GameUtils {
    private static GameUtils instance;
    private Context appContext;

    public static GameUtils getInstance() {
        return instance;
    }

    private GameUtils(Context context) {
        appContext = context;
    }

    // Singleton
    public static GameUtils initHelper(Context context) {
        if (instance == null)
            instance = new GameUtils(context);
        return instance;
    }


    public void Vibrate(int millisecond) {
        Vibrator v = (Vibrator) appContext.getSystemService(Context.VIBRATOR_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            v.vibrate(VibrationEffect.createOneShot(millisecond, VibrationEffect.DEFAULT_AMPLITUDE));
        } else {

            v.vibrate(millisecond);
        }
    }


    public void playSound(int audio) {
        final MediaPlayer mp = MediaPlayer.create(appContext, audio);
        mp.start();
        mp.setOnCompletionListener(MediaPlayer::pause);

    }
}
