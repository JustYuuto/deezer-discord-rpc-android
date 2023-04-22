package dev.yuuto.deezerdiscordrpc.services;

import android.app.Notification;
import android.os.Bundle;
import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;
import android.widget.Toast;

import androidx.annotation.NonNull;

public class DeezerNotificationsListenerService extends NotificationListenerService {
    public DeezerNotificationsListenerService() {
    }

    @Override
    public void onNotificationPosted(@NonNull StatusBarNotification sbn) {
        // Retrieve the notification data
        Notification notification = sbn.getNotification();

        Bundle extras = notification.extras;
        String title = extras.getString(Notification.EXTRA_TITLE);
        String text = extras.getString(Notification.EXTRA_TEXT);
        // Perform any necessary actions with the notification data

        Toast.makeText(this, "TITLE: " + title, Toast.LENGTH_SHORT).show();
        Toast.makeText(this, "TEXT: " + text, Toast.LENGTH_SHORT).show();
    }
}