package edu.temple.bitcoindashboard;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.SearchManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.SearchView;

public class MainActivity extends AppCompatActivity implements MainFragment.OnFragmentInteractionListener {


    boolean twoPanes;
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;
    AddressFragment addressFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        twoPanes = (findViewById(R.id.detail) != null);

        fragmentManager = getFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.main, new MainFragment());
        fragmentTransaction.commit();

        /*
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.main, new DetailFragment());
        //fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();

*/

        if(twoPanes) {
            fragmentManager = getFragmentManager();
            fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.main, new MainFragment());
            fragmentTransaction.commit();


            fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.add(R.id.detail, new DetailFragment());
            fragmentTransaction.commit();
        }


    }

    @Override
    public void onFragmentInteraction(int position) {

        if (position == 1){//USDButton

            if (twoPanes){
                fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.detail, new DetailFragment());
                fragmentTransaction.commit();
            } else {
                fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.main, new DetailFragment());
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }

        }//USDButton


        if (position == 2){//chartButton

            if (twoPanes){
                fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.detail, new ChartFragment());
                fragmentTransaction.commit();
            } else {
                fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.main, new ChartFragment());
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }

        }//chartButton


        if (position == 3){//blockInfoButton

            if (twoPanes){
                fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.detail, new BlockInfoFragment());
                fragmentTransaction.commit();
            } else {
                fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.main, new BlockInfoFragment());
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }

        }//blockInfoButton


        if (position == 4){//blockInfoButton

            if (twoPanes){
                fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.detail, new AddressFragment());
                fragmentTransaction.commit();
            } else {
                fragmentTransaction = fragmentManager.beginTransaction();
                addressFragment = new AddressFragment();
                fragmentTransaction.replace(R.id.main, addressFragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }

        }//blockInfoButton



    }


}
