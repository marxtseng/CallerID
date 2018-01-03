package marxtseng.callerid;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.TelephonyManager;

/**
 * Created by marx on 03/01/2018.
 */

public class PhoneReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        if (intent.getAction().equals(Intent.ACTION_NEW_OUTGOING_CALL)) {

        } else {
            PhoneStateListener listener = PhoneStateListener.getInstance(context);
            TelephonyManager tm = (TelephonyManager) context.getSystemService(Service.TELEPHONY_SERVICE);
            tm.listen(null, PhoneStateListener.LISTEN_NONE);
            tm.listen(listener, PhoneStateListener.LISTEN_CALL_STATE);
        }
    }
}
