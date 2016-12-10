package edu.temple.bitcoindashboard;

import android.app.Fragment;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

public class DetailFragment extends Fragment {

    TextView USDTextView;
    String usdString;

    public static DetailFragment newInstance() {
        return new DetailFragment();
    }
    public DetailFragment() {} // Required empty public constructor

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        Thread networkThread = new Thread() {
            @Override
            public void run() {
                try {
                    URL url = new URL("http://api.coindesk.com/v1/bpi/currentprice.json");

                    BufferedReader reader = new BufferedReader(  new InputStreamReader(  url.openStream()));

                    String jsonString = reader.readLine();

                    JSONObject root = new JSONObject(jsonString);
                    JSONObject bpi  = root.getJSONObject("bpi");
                    JSONObject usd  = bpi.getJSONObject("USD");
                    String threadusdString = usd.getString("rate_float");

                    Message usdMessage = Message.obtain();
                    usdMessage.obj = threadusdString;

                    messageHandler.sendMessage(usdMessage);

                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };
        networkThread.start();
}
    Handler messageHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {

            usdString = (String) msg.obj;
            USDTextView =  (TextView) getView().findViewById(R.id.usdTextView);
            USDTextView.setText(usdString);

        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.activity_detail_fragment, container, false);
        return v;

    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        if (menu != null) {

            menu.findItem(R.id.searchButton).setVisible(false);
        }
    }
}
