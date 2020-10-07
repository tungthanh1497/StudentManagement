package com.ttc.tungtt.sm;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.ttc.tungtt.sm.commons.Constants;
import com.ttc.tungtt.sm.ui.main.MainFragment;

public class MainActivity extends AppCompatActivity {

    private FragmentManager mFragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        mFragmentManager = getSupportFragmentManager();
        if (savedInstanceState == null) {
            addScreen(MainFragment.newInstance(), Constants.SCREEN_TAG.MAIN);
        }
    }

    /**
     * open a new Fragment and add to backstack
     *
     * @param fragment: destination fragment
     * @param name:     fragment's TAG
     * @param bundle:   bundle to transfer data (if needed)
     */
    public void addScreen(Fragment fragment, String name, Bundle bundle) {
        if (bundle != null) {
            fragment.setArguments(bundle);
        }
        mFragmentManager.beginTransaction()
                .add(R.id.container, fragment)
                .addToBackStack(name)
                .commit();
    }

    public void addScreen(Fragment fragment, String name) {
        addScreen(fragment, name, null);
    }

    public void onBack() {
        mFragmentManager.popBackStack();
    }
}