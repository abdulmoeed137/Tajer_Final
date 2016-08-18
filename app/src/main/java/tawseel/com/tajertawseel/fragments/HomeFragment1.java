package tawseel.com.tajertawseel.fragments;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import tawseel.com.tajertawseel.R;
import tawseel.com.tajertawseel.activities.AddNewOrderActivity;
import tawseel.com.tajertawseel.activities.HomePickSetActivity;
import tawseel.com.tajertawseel.activities.PostNewGroupActivity;

/**
 * Created by Junaid-Invision on 8/2/2016.
 */
public class HomeFragment1 extends Fragment{



    View mRootView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.fragment_home1, null, false);
        setupComponents();
        return mRootView;

    }

    public void setupComponents() {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);



        mRootView.findViewById(R.id.BtnAddGroupHome).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialogue();
               // showNotificationDialogue();
            }
        });

    }


//
//    else if(v.getId() == R.id.new_button)
//    {
//        showDialogue();
//    }


    public void showDialogue()
    {
        final Dialog dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialog.setContentView(R.layout.new_button_dialogue);
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(getActivity().getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.MATCH_PARENT;
        lp.gravity = Gravity.BOTTOM;
        lp.dimAmount = 0.3f;
        dialog.show();


        dialog.findViewById(R.id.option3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),HomePickSetActivity.class);
                startActivity(intent);
            }
        });
        dialog.findViewById(R.id.BtnNewOrder).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), AddNewOrderActivity.class));
            }
        });
        dialog.findViewById(R.id.BtnNewGroup).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), PostNewGroupActivity.class));
            }
        });
    }




}
