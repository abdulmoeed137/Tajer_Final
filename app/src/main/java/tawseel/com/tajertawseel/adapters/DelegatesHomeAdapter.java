package tawseel.com.tajertawseel.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import tawseel.com.tajertawseel.fragments.HomeFragment2;
import tawseel.com.tajertawseel.fragments.delegateHomeFragment1;

/**
 * Created by Junaid-Invision on 8/16/2016.
 */
public class DelegatesHomeAdapter extends FragmentPagerAdapter {
    public DelegatesHomeAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        if(position ==1)
        {
            return new HomeFragment2();
        }
        return new delegateHomeFragment1();
    }

    @Override
    public int getCount() {
        return 2;
    }
}
