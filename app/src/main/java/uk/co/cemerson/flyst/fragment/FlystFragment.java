package uk.co.cemerson.flyst.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import java.util.Date;
import java.util.Observable;
import java.util.Observer;

import uk.co.cemerson.flyst.entity.FlyingList;
import uk.co.cemerson.flyst.repository.MemberRepository;

abstract public class FlystFragment extends Fragment implements Observer
{
    protected MemberRepository mMemberRepository;
    protected FlyingList mFlyingList;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        mFlyingList = FlyingList.getInstance(new Date());
        mFlyingList.addObserver(this);

        mMemberRepository = new MemberRepository();
    }

    @Override
    public void update(Observable observable, Object data)
    {
        if (observable instanceof FlyingList) {
            onFlyingListUpdate((FlyingList) observable);
        }
    }

    public abstract void onFlyingListUpdate(FlyingList flyingList);

    @Override
    public void onDestroy()
    {
        super.onDestroy();

        mFlyingList.save();
        mFlyingList.deleteObserver(this);
        mFlyingList = null;

        mMemberRepository = null;
    }
}
