package tawseel.com.tajertawseel.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import tawseel.com.tajertawseel.fragments.pickSetHome1fragment;
import tawseel.com.tajertawseel.fragments.pickSetHome2fragment;

/**
 * Created by Junaid-Invision on 7/28/2016.
 */
public class PickSetPagerAdapter extends FragmentPagerAdapter {
    public PickSetPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        if(position ==0)
        {
            return new pickSetHome1fragment();
        }
        return new pickSetHome2fragment();
    }

    @Override
    public int getCount() {
        return 2;
    }
}
