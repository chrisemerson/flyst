package uk.co.cemerson.flyst.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import uk.co.cemerson.flyst.R;
import uk.co.cemerson.flyst.activity.GlidersActivity;
import uk.co.cemerson.flyst.activity.PilotsActivity;
import uk.co.cemerson.flyst.activity.RetrievesActivity;
import uk.co.cemerson.flyst.activity.WinchActivity;
import uk.co.cemerson.flyst.entity.FlyingList;

public class DashboardFragment extends FlystFragment
{
    private Button mPilotsButton;
    private Button mGlidersButton;
    private Button mWinchButton;
    private Button mRetrievesButton;

    private TextView mProgressBarLabel;
    private ProgressBar mProgressBar;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_dashboard, container, false);

        getReferencesFromView(view);
        addButtonListeners();

        return view;
    }

    private void getReferencesFromView(View view)
    {
        mPilotsButton = (Button) view.findViewById(R.id.dashboard_button_pilots);
        mGlidersButton = (Button) view.findViewById(R.id.dashboard_button_gliders);
        mWinchButton = (Button) view.findViewById(R.id.dashboard_button_winch);
        mRetrievesButton = (Button) view.findViewById(R.id.dashboard_button_retrieves);

        mProgressBar = (ProgressBar) view.findViewById(R.id.flying_list_pilots_flown_progress_bar);
        mProgressBarLabel = (TextView) view.findViewById(R.id.flying_list_pilots_flown_label);
    }

    private void addButtonListeners()
    {
        addClickListener(mPilotsButton, PilotsActivity.class);
        addClickListener(mGlidersButton, GlidersActivity.class);
        addClickListener(mWinchButton, WinchActivity.class);
        addClickListener(mRetrievesButton, RetrievesActivity.class);
    }

    private void addClickListener(View viewToAddListenerTo, final Class activityToStart)
    {
        viewToAddListenerTo.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                startActivityFromClassName(activityToStart);
            }
        });

    }

    private void startActivityFromClassName(Class activityToStart)
    {
        Intent i = new Intent(getActivity().getApplicationContext(), activityToStart);
        startActivity(i);
    }

    @Override
    public void onFlyingListUpdate(FlyingList flyingList)
    {
        updatePilotsFlownDisplay(flyingList);
    }

    private void updatePilotsFlownDisplay(FlyingList flyingList)
    {
        int numberOfPilotsOnList = flyingList.getNumberOfPilotsOnList();
        int numberOfPilotsFlown = flyingList.getNumberOfPilotsOnListWhoHaveFlown();

        mProgressBar.setMax(numberOfPilotsOnList);
        mProgressBar.setProgress(numberOfPilotsFlown);

        if (numberOfPilotsOnList == 0) {
            mProgressBarLabel.setText(getResources().getString(R.string.label_dashboard_no_pilots_on_list));
        } else {
            mProgressBarLabel.setText(
                getResources().getString(R.string.label_dashboard_pilots_flown)
                    + " "
                    + numberOfPilotsFlown
                    + "/"
                    + numberOfPilotsOnList
            );
        }
    }
}
