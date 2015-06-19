package uk.co.cemerson.flyst.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import uk.co.cemerson.flyst.entity.FlyingList;
import uk.co.cemerson.flyst.repository.FlyingListRepository;

abstract public class FlystFragment extends Fragment
{
    private static FlyingList mFlyingList = null;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        if (mFlyingList == null) {
            FlyingListRepository flyingListRepository = FlyingListRepository.getInstance(
                getActivity().getApplicationContext(),
                null
            );

            mFlyingList = flyingListRepository.getCurrentFlyingList();
        }
    }

    protected FlyingList getFlyingList()
    {
        return mFlyingList;
    }
}
