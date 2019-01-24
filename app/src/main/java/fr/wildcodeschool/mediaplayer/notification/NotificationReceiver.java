package fr.wildcodeschool.mediaplayer.notification;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;

public class NotificationReceiver extends BroadcastReceiver {
  @Override
  public void onReceive(Context ctx, Intent intent)
  {
    LocalBroadcastManager.getInstance(ctx).sendBroadcast(intent);
  }
}
