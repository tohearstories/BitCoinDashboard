package edu.temple.bitcoindashboard;

import android.annotation.TargetApi;
import android.app.Fragment;
import android.content.Context;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

@TargetApi(Build.VERSION_CODES.M)
public class AddressFragment extends Fragment {

    ArrayList<String> addresses;
    TextView balanceTextView;
    String addressToCheck;
    String urlRootString = "http://btc.blockr.io/api/v1/address/info/";
    String balanceFromAPI;
    ListView listView ;
    ArrayAdapter<String> arrayAdapter;
    String value;

    String internalFilename = "myfile";
    File file;


    public static AddressFragment newInstance() {
        return new AddressFragment();
    }
    public AddressFragment() {} // Required empty public constructor

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
           addresses = savedInstanceState.getStringArrayList("arraylistkey");
        }

        file = new File( getActivity().getFilesDir(), internalFilename);


        if (file.exists()) {
            addresses = new ArrayList<String>();
            try {
                BufferedReader br = new BufferedReader(new FileReader(file));
                String line;
                while ((line = br.readLine()) != null) {
                    addresses.add(line.toString());
                }
                br.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.activity_address_fragment, container, false);

        balanceTextView = (TextView) v.findViewById(R.id.balanceTextView);

        Button getBalanceButton = (Button) v.findViewById(R.id.balanceButton);
        getBalanceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Thread networkThread = new Thread() {
                    @Override
                    public void run() {
                        try {;
                            addressToCheck = ((EditText) getView().findViewById(R.id.editText)).getText().toString();
                            String urlString = urlRootString + addressToCheck.toString();
                            URL url = new URL(urlString);

                            BufferedReader reader = new BufferedReader(  new InputStreamReader(  url.openStream()));

                            String jsonString = reader.readLine();

                            JSONObject root = new JSONObject(jsonString);
                            Message msg = Message.obtain();
                            msg.obj = root;
                            messageHandler.sendMessage(msg);

                            Message msg2 = Message.obtain();
                            msg2.obj = addressToCheck;
                            messageHandler2.sendMessage(msg2);

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

        listView = (ListView) v.findViewById(R.id.listView);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            if (addresses == null){
                addresses = new ArrayList<String>();
            }

            arrayAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, addresses );
        }
        listView.setAdapter(arrayAdapter);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> adapter, View v, int position,
                                    long arg3)
            {
                value = (String)adapter.getItemAtPosition(position);


                Thread networkThread = new Thread() {
                    @Override
                    public void run() {
                        try {;
                            String urlString = urlRootString + value;
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
                balanceFromAPI = data.getString("balance");
            } catch (JSONException e) {
                e.printStackTrace();
            }

            balanceTextView.setText(balanceFromAPI);
        }
    };

    Handler messageHandler2 = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            String temp = (String) msg.obj;

            try {
                FileWriter writer = new FileWriter(file);

                for (int i=0; i < addresses.size(); i++){
                    writer.append( addresses.get(i)  );
                    writer.append('\n');
                }

                writer.append(temp);

                writer.close();

                addresses.add(temp);

            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    };






    @Override
    public void onSaveInstanceState(Bundle outState){

       outState.putStringArrayList("arraylistkey", addresses);
        super.onSaveInstanceState(outState);

    }

}
