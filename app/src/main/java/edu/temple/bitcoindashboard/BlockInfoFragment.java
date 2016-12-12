package edu.temple.bitcoindashboard;

import android.app.Fragment;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Timer;
import java.util.TimerTask;

public class BlockInfoFragment extends Fragment {
    String blockNumberString;
    String urlRootString = "http://btc.blockr.io/api/v1/block/info/";
    String feeString;
    String sizeString;
    String difficultyString;
    String daysDestroyedString;


    public static BlockInfoFragment newInstance() {
        return new BlockInfoFragment();
    }
    public BlockInfoFragment() {} // Required empty public constructor

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        setRetainInstance(true);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.activity_block_info_fragment, container, false);

        Button viewBlockInfoButton = (Button) v.findViewById(R.id.getBlockInfoButton);
        viewBlockInfoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Thread networkThread = new Thread() {
                    @Override
                    public void run() {
                        try {;
                            blockNumberString = ((EditText) getView().findViewById(R.id.blockNumberEditText)).getText().toString();
                            String urlString = urlRootString + blockNumberString.toString();
                            URL url = new URL(urlString);

                            BufferedReader reader = new BufferedReader(  new InputStreamReader(  url.openStream()));

                            String jsonString = reader.readLine();

                            JSONObject root = new JSONObject(jsonString);
                            Message msg = Message.obtain();
                            msg.obj = root;
                            messageHandler.sendMessage(msg);

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
        });



        Button previousButton = (Button) v.findViewById(R.id.previousButton);
        previousButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Thread networkThread = new Thread() {
                    @Override
                    public void run() {
                        try {
                            int i = Integer.parseInt(blockNumberString);
                            i = i - 1;
                            blockNumberString = String.valueOf(i);
                            String urlString = urlRootString + blockNumberString;
                            URL url = new URL(urlString);

                            BufferedReader reader = new BufferedReader(  new InputStreamReader(  url.openStream()));

                            String jsonString = reader.readLine();

                            JSONObject root = new JSONObject(jsonString);
                            Message msg = Message.obtain();
                            msg.obj = root;
                            messageHandler.sendMessage(msg);

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
        });



        Button nextButton = (Button) v.findViewById(R.id.nextButton);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Thread networkThread = new Thread() {
                    @Override
                    public void run() {
                        try {
                            int i = Integer.parseInt(blockNumberString);
                            i = i + 1;
                            blockNumberString = String.valueOf(i);
                            String urlString = urlRootString + blockNumberString;
                            URL url = new URL(urlString);

                            BufferedReader reader = new BufferedReader(  new InputStreamReader(  url.openStream()));

                            String jsonString = reader.readLine();

                            JSONObject root = new JSONObject(jsonString);
                            Message msg = Message.obtain();
                            msg.obj = root;
                            messageHandler.sendMessage(msg);

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
        });








        return v;

    }
    Handler messageHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {

            JSONObject root = (JSONObject) msg.obj;
            JSONObject data  = null;
            try {
                data = root.getJSONObject("data");

                feeString = data.getString("fee");
                sizeString = data.getString("size");
                difficultyString = data.getString("difficulty");
                daysDestroyedString = data.getString("days_destroyed");



            } catch (JSONException e) {
                e.printStackTrace();
            }

            ((TextView)getView().findViewById(R.id.feeTextView)).setText(" " + feeString);
            ((TextView)getView().findViewById(R.id.sizeTextView)).setText(" " + sizeString);
            ((TextView)getView().findViewById(R.id.difficultyTextView)).setText(" " + difficultyString);
            ((TextView)getView().findViewById(R.id.daysDestroyedTextView)).setText(" " + daysDestroyedString);
        }
    };



}
