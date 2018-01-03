package marxtseng.callerid;

import android.content.Context;
import android.content.SharedPreferences;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by marx on 03/01/2018.
 */

public class PhoneStateListener extends android.telephony.PhoneStateListener {

    String TAG = "PhoneStateListener";
    static PhoneStateListener instance;
    Context mContext;

    public static PhoneStateListener getInstance(Context context) {
        if (instance == null) {
            instance = new PhoneStateListener(context);
        }

        return instance;
    }

    public PhoneStateListener(Context context) {
        mContext = context;
    }

    @Override
    public void onCallStateChanged(int state, String incomingNumber) {
        super.onCallStateChanged(state, incomingNumber);

        if (state == TelephonyManager.CALL_STATE_RINGING) {
            if (incomingNumber.substring(0, 1).equals("0")) {
                incomingNumber = "886" + incomingNumber.substring(1, incomingNumber.length());
            }

            SharedPreferences pref = mContext.getSharedPreferences("Caller", 0);
            String strCaller = pref.getString("Caller", "");

            if (strCaller != null && !strCaller.equals("")) {
                try {
                    JSONObject callers = new JSONObject(strCaller);
                    JSONArray numbers = callers.getJSONArray("numbers");
                    JSONArray labels = callers.getJSONArray("labels");

                    for (int i = 0; i < numbers.length(); i++) {
                        String number = numbers.getString(i);
                        if (number.equals(incomingNumber)) {
                            Toast.makeText(mContext, labels.getString(i), Toast.LENGTH_SHORT).show();
                        }
                    }

                } catch(Exception e) {
                    Log.d(TAG, e.getLocalizedMessage());
                }
            }
        }
    }
}
