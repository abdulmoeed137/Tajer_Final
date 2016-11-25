package tawseel.com.tajertawseel.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import tawseel.com.tajertawseel.fragments.HomeFragment1;
import tawseel.com.tajertawseel.fragments.HomeFragment2;
import tawseel.com.tajertawseel.fragments.HomeFragment3;

/**
 * Created by Junaid-Invision on 8/2/2016.
 */
public class HomePagerAdapter extends FragmentPagerAdapter {
    public HomePagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {

        if(position ==1)
        {
            return new HomeFragment3();
        }
        return new HomeFragment1();
    }

    @Override
    public int getCount() {
        return 2;
    }
}
