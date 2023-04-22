package dev.yuuto.deezerdiscordrpc;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.app.NotificationManager;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.widget.Button;

import dev.yuuto.deezerdiscordrpc.services.DeezerNotificationsListenerService;

public class PermissionsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_permissions);

        Button requestPermissionButton = findViewById(R.id.request_permission_button);

        requestPermissionButton.setOnClickListener(view -> {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && !Settings.canDrawOverlays(this)) {
                // Ask the user for permission to access notifications
                Intent intent = new Intent(Settings.ACTION_NOTIFICATION_LISTENER_SETTINGS);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (checkNotificationPermission()) {
            Intent activity = new Intent(this, MainActivity.class);
            startActivity(activity);
            finishActivity(0);
        }
    }

    private boolean checkNotificationPermission() {
        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O_MR1) {
            // For Android Oreo and above versions, we need to check if the app has the Notification access permission.
            if (notificationManager != null && !notificationManager.isNotificationListenerAccessGranted(new ComponentName(this, DeezerNotificationsListenerService.class))) {
                return false;
            }
        } else {
            // For versions below Android Oreo, we need to check if the app has the READ_NOTIFICATIONS permission.
            int notificationPermission = ContextCompat.checkSelfPermission(this, "READ_NOTIFICATIONS");
            return notificationPermission == PackageManager.PERMISSION_GRANTED;
        }
        return true;
    }

}