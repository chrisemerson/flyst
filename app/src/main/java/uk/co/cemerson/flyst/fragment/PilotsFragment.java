package uk.co.cemerson.flyst.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.Date;

import uk.co.cemerson.flyst.R;
import uk.co.cemerson.flyst.entity.FlyingList;
import uk.co.cemerson.flyst.repository.MemberRepository;

public class PilotsFragment extends Fragment
{
    private MemberRepository mMemberRepository;
    private FlyingList mFlyingList;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        return inflater.inflate(R.layout.fragment_pilots, container, false);
    }

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
}
