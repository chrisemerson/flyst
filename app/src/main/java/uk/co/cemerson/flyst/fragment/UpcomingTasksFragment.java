package uk.co.cemerson.flyst.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import uk.co.cemerson.flyst.R;

public class UpcomingTasksFragment extends TimeTickReceivingFragment
{
    private UpcomingTasksCallbackListener callbackListener = null;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        return inflater.inflate(R.layout.fragment_upcomingtasks, container, false);
    }

    public interface UpcomingTasksCallbackListener
    {
        abstract public void newTasks();
    }

    @Override
    protected void onTimeTick()
    {
        if (callbackListener != null) {
            //Call new events for testing, just for the hell of it
            callbackListener.newTasks();
        }
    }

    @Override
    public void onAttach(Activity activity)
    {
        super.onAttach(activity);

        callbackListener = (UpcomingTasksCallbackListener) activity;
    }

    @Override
    public void onDetach()
    {
        super.onDetach();

        callbackListener = null;
    }
}
