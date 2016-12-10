package edu.temple.bitcoindashboard;

import android.app.Fragment;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Timer;
import java.util.TimerTask;

public class ChartFragment extends Fragment {

    WebView derp;

    public static ChartFragment newInstance() {
        return new ChartFragment();
    }
    public ChartFragment() {} // Required empty public constructor

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.activity_chart_fragment, container, false);

        derp = (WebView) v.findViewById(R.id.webView);
        derp.loadUrl("https://chart.yahoo.com/z?s=BTCUSD=X&t=1d");

        new Timer().schedule(new TimerTask(){
            @Override
            public void run() {

                Message emptyMessage = Message.obtain();
                messageHandler.sendMessage(emptyMessage);

            }}, 15000, 15000);


        Button oneDay = (Button) v.findViewById(R.id.oneDayButton);
        Button fiveDay = (Button) v.findViewById(R.id.fiveDayButton);

        oneDay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                derp.loadUrl("https://chart.yahoo.com/z?s=BTCUSD=X&t=1d");
            }
        });

        fiveDay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                derp.loadUrl("https://chart.yahoo.com/z?s=BTCUSD=X&t=5d");
            }
        });

        return v;

    }
    Handler messageHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
                derp.reload();
        }
    };


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        if (menu != null) {

            menu.findItem(R.id.searchButton).setVisible(false);
        }
    }
}
