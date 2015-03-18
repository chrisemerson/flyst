package uk.co.cemerson.flyst.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import uk.co.cemerson.flyst.R;

public class DashboardFragment extends Fragment
{
    private Button mFlyingListButton;
    private Button mGlidersButton;
    private Button mWinchButton;
    private Button mRetrievesButton;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        View v = inflater.inflate(R.layout.fragment_dashboard, container, false);

        mFlyingListButton = (Button) v.findViewById(R.id.dashboard_button_flying_list);
        mGlidersButton = (Button) v.findViewById(R.id.dashboard_button_gliders);
        mWinchButton = (Button) v.findViewById(R.id.dashboard_button_winch);
        mRetrievesButton = (Button) v.findViewById(R.id.dashboard_button_retrieves);

        addButtonListeners();

        return v;
    }

    private void addButtonListeners()
    {
        mFlyingListButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                startFlyingListActivity();
            }
        });

        mGlidersButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                startGlidersActivity();
            }
        });

        mWinchButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                startWinchActivity();
            }
        });

        mRetrievesButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                startRetrievesActivity();
            }
        });
    }

    private void startRetrievesActivity()
    {
    }

    private void startWinchActivity()
    {
    }

    private void startGlidersActivity()
    {
    }

    private void startFlyingListActivity()
    {
    }
}
