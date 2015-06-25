package uk.co.cemerson.flyst.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import uk.co.cemerson.flyst.R;
import uk.co.cemerson.flyst.dialog.AddGliderDialog;
import uk.co.cemerson.flyst.entity.FlyingList;
import uk.co.cemerson.flyst.entity.Glider;
import uk.co.cemerson.flyst.entity.GliderQueue;

public class GlidersFragment extends FlystFragment
{
    private static final String DIALOG_ADD_GLIDER = "dialog_add_glider";
    private static final int REQUEST_ADD_GLIDER = 0;

    private RadioGroup mGliderList;
    private LayoutInflater mInflater;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        mInflater = inflater;

        View view = inflater.inflate(R.layout.fragment_gliders, container, false);

        initView(view);

        return view;
    }

    private void initView(View view)
    {
        mGliderList = (RadioGroup) view.findViewById(R.id.glider_list_container);

        updateGlidersOnFlyingListView();

        Button button = (Button) view.findViewById(R.id.button_add_glider);
        button.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                FragmentManager fm = getActivity().getSupportFragmentManager();
                AddGliderDialog dialog = new AddGliderDialog();
                dialog.setTargetFragment(GlidersFragment.this, REQUEST_ADD_GLIDER);
                dialog.show(fm, DIALOG_ADD_GLIDER);
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if (resultCode == Activity.RESULT_OK && requestCode == REQUEST_ADD_GLIDER) {
            updateGlidersOnFlyingListView();
        }
    }

    private void updateGlidersOnFlyingListView()
    {
        FlyingList flyingList = getFlyingList();

        mGliderList.removeAllViews();

        for (GliderQueue gliderQueue : flyingList.getGliderQueues()) {
            Glider glider = gliderQueue.getGlider();

            RadioButton button = (RadioButton) mInflater.inflate(R.layout.list_item_glider_fragment_glider_list, mGliderList, false);

            button.setText(glider.getRegistration());

            button.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    //TODO Implement this
                }
            });

            mGliderList.addView(button);
        }
    }
}
