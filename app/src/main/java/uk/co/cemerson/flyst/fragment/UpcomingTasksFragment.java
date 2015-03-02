package uk.co.cemerson.flyst.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import uk.co.cemerson.flyst.R;

public class UpcomingTasksFragment extends TimeTickReceivingFragment
{
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        return inflater.inflate(R.layout.fragment_upcomingtasks, container, false);
    }

    @Override
    protected void onTimeTick()
    {
        //noop currently
    }
}
