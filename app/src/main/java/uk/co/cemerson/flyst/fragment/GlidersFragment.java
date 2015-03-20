package uk.co.cemerson.flyst.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import uk.co.cemerson.flyst.R;

public class GlidersFragment extends FlystFragment
{
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        return inflater.inflate(R.layout.fragment_gliders, container, false);
    }

    @Override
    public void onFlyingListUpdate()
    {
        //Load stats and information from flying list here
    }
}
