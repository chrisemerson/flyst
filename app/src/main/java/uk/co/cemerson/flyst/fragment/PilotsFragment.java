package uk.co.cemerson.flyst.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import uk.co.cemerson.flyst.R;
import uk.co.cemerson.flyst.entity.FlyingList;

public class PilotsFragment extends FlystFragment
{
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        return inflater.inflate(R.layout.fragment_pilots, container, false);
    }

    @Override
    public void onFlyingListUpdate(FlyingList flyingList)
    {

    }
}
