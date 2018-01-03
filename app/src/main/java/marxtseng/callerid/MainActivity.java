package marxtseng.callerid;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button load = findViewById(R.id.load);
        load.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                load();
            }
        });
    }

    // 下載顯示清單
    private void load() {
        String url = "https://gist.githubusercontent.com/marxtseng/b7cf8b849f13d07e1e158ad9d621186f/raw/eb2bf4c73e1fbaebed7c747001d01afd1f967aa4/Callers.js";
        JsonRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d(TAG, response.toString());

                SharedPreferences pref = getSharedPreferences("Caller", 0);
                SharedPreferences.Editor edit = pref.edit();

                edit.putString("Caller", response.toString());
                edit.commit();

                Toast.makeText(MainActivity.this, "下載完成!", Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(TAG, error.getLocalizedMessage());
            }
        });

        RequestQueue quene = Volley.newRequestQueue(this);
        quene.add(request);
    }
}
