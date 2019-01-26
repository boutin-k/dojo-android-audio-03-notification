package fr.wildcodeschool.mediaplayer;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.os.Handler;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.SeekBar;

import fr.wildcodeschool.mediaplayer.notification.MediaNotification;
import fr.wildcodeschool.mediaplayer.player.WildOnPlayerListener;
import fr.wildcodeschool.mediaplayer.player.WildPlayer;
import fr.wildcodeschool.mediaplayer.notification.NotificationReceiver;

import static fr.wildcodeschool.mediaplayer.notification.MediaNotification.*;

public class MainActivity extends AppCompatActivity implements SeekBar.OnSeekBarChangeListener {
  // Audio player
  private WildPlayer mPlayer = null;
  // SeekBar
  private SeekBar mSeekBar = null;
  // SeekBar update delay
  private static final int SEEKBAR_DELAY = 1000;
  // Thread used to update the SeekBar position
  private final Handler mSeekBarHandler = new Handler();
  private Runnable mSeekBarThread;

  private MediaNotification mNotification = null;
  private BroadcastReceiver mBroadcastReceiver = null;

  /**
   * Application context accessor
   * https://possiblemobile.com/2013/06/context/
   */
  private static Context appContext;
  public  static Context getAppContext() {
    return appContext;
  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    // Initialization of the application context
    MainActivity.appContext = getApplicationContext();

    // Initialization of the wild audio player
    mPlayer = new WildPlayer(this);
    mPlayer.init(R.string.song, new WildOnPlayerListener() {
      @Override
      public void onPrepared(MediaPlayer mp) {
        mSeekBar.setMax(mp.getDuration());
      }

      @Override
      public void onCompletion(MediaPlayer mp) {
        mSeekBarHandler.removeCallbacks(mSeekBarThread);
        mSeekBar.setProgress(0);
      }
    });

    // Initialization of the SeekBar
    mSeekBar = findViewById(R.id.seekBar);
    mSeekBar.setOnSeekBarChangeListener(this);

    // Thread used to update the SeekBar position according to the audio player
    mSeekBarThread = new Runnable() {
      @Override
      public void run() {
        // Widget should only be manipulated in UI thread
        mSeekBar.post(() -> mSeekBar.setProgress(mPlayer.getCurrentPosition()));
        // Launch a new request
        mSeekBarHandler.postDelayed(this, SEEKBAR_DELAY);
      }
    };

    // Create the notification
    mNotification =
      new MediaNotification.Builder(getApplicationContext())
        .addActions(NotificationReceiver.class)
        .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.greenday))
        .setContentTitle(getString(R.string.song_title))
        .setContentText(getString(R.string.song_description))
        .buildNotification();
  }

  @Override
  protected void onStart() {
    super.onStart();

    if (null != mNotification)
      mNotification.register();

    mBroadcastReceiver = new BroadcastReceiver() {
      @Override
      public void onReceive(Context context, Intent intent) {
        if (null != intent && null != intent.getAction()) {
          switch (intent.getAction()) {
            case ACTION_PLAY:
              playMedia(null);
              break;
            case ACTION_PAUSE:
              pauseMedia(null);
              break;
            case ACTION_STOP:
              stopMedia(null);
              break;
          }
        }
      }
    };

    LocalBroadcastManager mgr = LocalBroadcastManager.getInstance(this);
    mgr.registerReceiver(mBroadcastReceiver, new IntentFilter(ACTION_PLAY));
    mgr.registerReceiver(mBroadcastReceiver, new IntentFilter(ACTION_PAUSE));
    mgr.registerReceiver(mBroadcastReceiver, new IntentFilter(ACTION_STOP));
  }

  @Override
  protected void onStop() {
    super.onStop();
    if (null != mNotification)
      mNotification.unregister();

    LocalBroadcastManager mgr = LocalBroadcastManager.getInstance(this);
    mgr.unregisterReceiver(mBroadcastReceiver);
  }

  @Override
  protected void onDestroy() {
    super.onDestroy();
    mPlayer.release();
    mSeekBarHandler.removeCallbacks(mSeekBarThread);
  }


  // --------------------------------------------------------------------------
  // SeekBar interface
  // --------------------------------------------------------------------------

  /**
   * OnSeekBarChangeListener interface method implementation
   * @param seekBar Widget related to the event
   * @param progress Current position on the SeekBar
   * @param fromUser Define if it is a user action or a programmatic seekTo
   */
  @Override
  public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
      if (fromUser) {
        mPlayer.seekTo(progress);
      }
  }

  /**
   * OnSeekBarChangeListener interface method implementation
   * @param seekBar Widget related to the event
   */
  @Override
  public void onStartTrackingTouch(SeekBar seekBar) {
    // Stop seekBarUpdate here
    mSeekBarHandler.removeCallbacks(mSeekBarThread);
  }

  /**
   * OnSeekBarChangeListener interface method implementation
   * @param seekBar Widget related to the event
   */
  @Override
  public void onStopTrackingTouch(SeekBar seekBar) {
    // Restart seekBarUpdate here
    if (null != mPlayer && mPlayer.isPlaying()) {
      mSeekBarHandler.postDelayed(mSeekBarThread, SEEKBAR_DELAY);
    }
  }


  // --------------------------------------------------------------------------
  // Buttons onClick
  // --------------------------------------------------------------------------

  /**
   * On play button click
   * Launch the playback of the media
   */
  public void playMedia(View v) {
    if (null != mPlayer && mPlayer.play()) {
      mSeekBarHandler.postDelayed(mSeekBarThread, SEEKBAR_DELAY);
    }
  }

  /**
   * On pause button click
   * Pause the playback of the media
   */
  public void pauseMedia(View v) {
    if (null != mPlayer && mPlayer.pause()) {
      mSeekBarHandler.removeCallbacks(mSeekBarThread);
    }
  }

  /**
   * On reset button click
   * Stop the playback of the media
   */
  public void stopMedia(View v) {
    if (null != mPlayer && mPlayer.stop()) {
      mSeekBar.setProgress(0);
    }
  }
}
