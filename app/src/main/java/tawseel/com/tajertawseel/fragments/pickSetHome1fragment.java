package tawseel.com.tajertawseel.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;

import tawseel.com.tajertawseel.R;
import tawseel.com.tajertawseel.adapters.pick_dummy_adapter;

/**
 * Created by Junaid-Invision on 7/28/2016.
 */
public class pickSetHome1fragment extends Fragment {

    View mRootView;
    ListView listView;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        mRootView = inflater.inflate(R.layout.fragment_picksethome1, null,false);

        setupComponents();

        return mRootView;
    }

    public void setupComponents()
    {
        listView = (ListView) mRootView.findViewById(R.id.listView);
        listView.setAdapter(new pick_dummy_adapter(getActivity()));


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                RelativeLayout layout = (RelativeLayout) view.findViewById(R.id.container);
                ImageView tickView = (ImageView) layout.findViewById(R.id.tick_view);


                if(tickView.getVisibility() == View.INVISIBLE)
                {
                    tickView.setVisibility(View.VISIBLE);
                    layout.setBackgroundColor(getResources().getColor(R.color.grey));
                }
                else
                {
                    tickView.setVisibility(View.INVISIBLE);
                    layout.setBackgroundColor(getResources().getColor(android.R.color.transparent));
                }


            }
        });

    }
}
