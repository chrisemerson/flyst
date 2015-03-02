package uk.co.cemerson.flyst.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.app.Fragment;

abstract public class TimeTickReceivingFragment extends Fragment
{
    @Override
    public void onResume()
    {
        super.onResume();

        IntentFilter filter = new IntentFilter(Intent.ACTION_TIME_TICK);
        getActivity().registerReceiver(mTimeTickReceiver, filter);
    }

    @Override
    public void onPause()
    {
        super.onPause();

        getActivity().unregisterReceiver(mTimeTickReceiver);
    }

    private BroadcastReceiver mTimeTickReceiver = new BroadcastReceiver()
    {
        @Override
        public void onReceive(Context context, Intent intent)
        {
            onTimeTick();
        }
    };

    abstract protected void onTimeTick();
}
