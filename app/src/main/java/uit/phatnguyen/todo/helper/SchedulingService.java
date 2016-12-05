package uit.phatnguyen.todo.helper;

import android.app.IntentService;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import uit.phatnguyen.todo.R;
import uit.phatnguyen.todo.activity.CreateItemsActivity;

public class SchedulingService extends IntentService {

    private NotificationManager mNotificationManager;
    public static final int NOTIFICATION_ID = 1;

    public SchedulingService(){
        super("SchedulingService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        sendNotification("");
        runRingtone();
        AlarmReceiver.completeWakefulIntent(intent);
    }
    private void sendNotification(String msg) {
        mNotificationManager = (NotificationManager)
                this.getSystemService(Context.NOTIFICATION_SERVICE);

        PendingIntent contentIntent = PendingIntent.getActivity(this, 0,
                new Intent(this, CreateItemsActivity.class), 0);

        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(R.drawable.todo)
                        .setContentTitle("")
                        .setStyle(new NotificationCompat.BigTextStyle()
                                .bigText(msg))
                        .setContentText(msg);

        mBuilder.setContentIntent(contentIntent);
        mNotificationManager.notify(NOTIFICATION_ID, mBuilder.build());
    }
    private void runRingtone() {

        Log.d("AlarmService", "Alarm Starting to ring");

        Uri alert = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
        Ringtone r = RingtoneManager.getRingtone(getBaseContext(), alert);

        if (r == null) {

            alert = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            r = RingtoneManager.getRingtone(getBaseContext(), alert);

            if (r == null) {
                alert = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE);
                r = RingtoneManager.getRingtone(getBaseContext(), alert);
            }
        }
        if (r != null)
            r.play();

    }
}
