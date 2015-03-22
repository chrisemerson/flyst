package uk.co.cemerson.flyst.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import java.util.Date;

import uk.co.cemerson.flyst.entity.FlyingList;
import uk.co.cemerson.flyst.repository.MemberRepository;

abstract public class FlystFragment extends Fragment
{
    private MemberRepository mMemberRepository;
    private FlyingList mFlyingList;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        mFlyingList = FlyingList.getInstance(new Date());
        mMemberRepository = new MemberRepository();
    }

    @Override
    public void onDestroy()
    {
        super.onDestroy();

        mFlyingList.save();
        mFlyingList = null;

        mMemberRepository = null;
    }

    protected FlyingList getFlyingList()
    {
        return mFlyingList;
    }

    protected MemberRepository getMemberRepository()
    {
        return mMemberRepository;
    }
}
