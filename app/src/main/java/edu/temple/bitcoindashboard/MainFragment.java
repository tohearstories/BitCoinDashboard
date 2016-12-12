package edu.temple.bitcoindashboard;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class MainFragment extends Fragment {

    public static MainFragment newInstance() {
        return new MainFragment();
    }
    public MainFragment() {} // Required empty public constructor

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setHasOptionsMenu(true);
       // setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.activity_main_fragment, container, false);

        Button usdButton = (Button) v.findViewById(R.id.priceButton);//usdButton will be 1
        usdButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                (  (OnFragmentInteractionListener)  getActivity()  ).onFragmentInteraction(1);
            }
        });

        Button chartButton = (Button) v.findViewById(R.id.chartButton);//chartButton will be 2
        chartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                (  (OnFragmentInteractionListener)  getActivity()  ).onFragmentInteraction(2);
            }
        });

        Button blockInfoButton = (Button) v.findViewById(R.id.blockInfoButton);//chartButton will be 3
        blockInfoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                (  (OnFragmentInteractionListener)  getActivity()  ).onFragmentInteraction(3);
            }
        });

        Button balanceButton = (Button) v.findViewById(R.id.balanceButton);//chartButton will be 4
        balanceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                (  (OnFragmentInteractionListener)  getActivity()  ).onFragmentInteraction(4);
            }
        });

        return v;

    }



    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(int button);
    }
}
