package com.example.faiz.quirkybytetask;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.transition.TransitionInflater;
import android.view.View;
import android.widget.Toast;
import com.example.faiz.quirkybytetask.fragment.DemoPostFragment;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if(isNetworkAvailable()) {
            showFragment(new DemoPostFragment(), "demopost");
        }else{
            Toast.makeText(this,"please check internet connectivity",Toast.LENGTH_SHORT).show();
        }

    }

    public void showFragment(Fragment name, String tag){
        FragmentManager fragmentManager = getSupportFragmentManager();
        // check if the fragment is in back stack
        boolean fragmentPopped = fragmentManager.popBackStackImmediate(tag, 0);
        if (fragmentPopped) {
            // fragment is pop from backStack
        } else {
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.fl_container, name, tag);
            fragmentTransaction.addToBackStack(tag);
            fragmentTransaction.commit();
        }
    }


    /**
     * function to show the fragment
     *
     * @param current current visible fragment
     * @param tag     fragment tag
     */
    public void showFragmentWithTransition(Fragment current, Fragment newFragment, String tag, View sharedView, String sharedElementName) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        // check if the fragment is in back stack
        boolean fragmentPopped = fragmentManager.popBackStackImmediate(tag, 0);
        if (fragmentPopped) {
            // fragment is pop from backStack
        } else {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                current.setSharedElementReturnTransition(TransitionInflater.from(this).inflateTransition(R.transition.default_transition));
                current.setExitTransition(TransitionInflater.from(this).inflateTransition(android.R.transition.no_transition));

                newFragment.setSharedElementEnterTransition(TransitionInflater.from(this).inflateTransition(R.transition.default_transition));
                newFragment.setEnterTransition(TransitionInflater.from(this).inflateTransition(android.R.transition.no_transition));
            }
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.fl_container, newFragment, tag);
            fragmentTransaction.addToBackStack(tag);
            fragmentTransaction.addSharedElement(sharedView, sharedElementName);
            fragmentTransaction.commit();
        }
    }

    /**
     * function to go back to previous fragment
     */
    private void oneStepBack() {
        FragmentTransaction fts = getSupportFragmentManager().beginTransaction();
        FragmentManager fragmentManager = getSupportFragmentManager();
        if (fragmentManager.getBackStackEntryCount() >= 2) {
            fragmentManager.popBackStackImmediate();
            fts.commit();
        } else {
            doubleClickToExit();
        }
    }

    // double back pressed function
    private static long back_pressed;

    private void doubleClickToExit() {
        if ((back_pressed + 2000) > System.currentTimeMillis())
            finish();
        else
            Toast.makeText(MainActivity.this, "Click again to exit", Toast.LENGTH_SHORT).show();
        back_pressed = System.currentTimeMillis();
    }

    @Override
    public void onBackPressed() {
        oneStepBack();
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = null;
        if (connectivityManager != null) {
            activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        }
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}
