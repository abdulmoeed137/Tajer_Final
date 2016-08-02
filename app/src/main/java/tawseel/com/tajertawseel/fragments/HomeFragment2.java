package tawseel.com.tajertawseel.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import tawseel.com.tajertawseel.R;
import tawseel.com.tajertawseel.adapters.DeliveredListAdapter;

/**
 * Created by Junaid-Invision on 8/2/2016.
 */
public class HomeFragment2 extends Fragment {

    private ListView listView;
    private View mRootView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.fragment_home2,null,false);

        setupContentView();
    return mRootView;
    }


    public void setupContentView()
    {
        listView = (ListView)mRootView.findViewById(R.id.listView);
        listView.setAdapter(new DeliveredListAdapter(getActivity()));
    }
}
