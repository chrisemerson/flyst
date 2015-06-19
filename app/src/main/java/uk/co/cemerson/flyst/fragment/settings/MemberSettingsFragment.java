package uk.co.cemerson.flyst.fragment.settings;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;

import uk.co.cemerson.flyst.R;
import uk.co.cemerson.flyst.dialog.EditMemberDialog;
import uk.co.cemerson.flyst.entity.FlyingList;
import uk.co.cemerson.flyst.entity.Member;
import uk.co.cemerson.flyst.repository.FlyingListRepository;
import uk.co.cemerson.flyst.repository.MemberRepository;

public class MemberSettingsFragment extends Fragment
{
    private static final int REQUEST_EDIT_MEMBER = 0;
    private static final String DIALOG_EDIT_MEMBER = "dialog_edit_member";

    private ListView mMembersList;
    private MemberRepository mMemberRepository;
    private ArrayList<Member> mAllMembers;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_settings_members, container, false);

        initView(view);

        return view;
    }

    private void initView(View view)
    {
        mMemberRepository = MemberRepository.getInstance(getActivity().getApplicationContext());
        mAllMembers = (ArrayList<Member>) mMemberRepository.getAllMembers();

        Collections.sort(mAllMembers);

        mMembersList = (ListView) view.findViewById(R.id.members_list);
        mMembersList.setAdapter(new MembersByNameAdapter(mAllMembers));
    }

    private class MembersByNameAdapter extends ArrayAdapter<Member>
    {
        public MembersByNameAdapter(ArrayList<Member> members)
        {
            super(getActivity(), 0, members);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent)
        {
            if (convertView == null) {
                convertView = getActivity().getLayoutInflater().inflate(R.layout.list_item_member, null);
            }

            initView(convertView, getItem(position));

            return convertView;
        }

        private void initView(View view, final Member member)
        {
            TextView memberNameTextView = (TextView) view.findViewById(R.id.member_name);
            memberNameTextView.setText(member.getDisplayNameSurnameFirst());

            TextView memberCapabilitiesTextView = (TextView) view.findViewById(R.id.member_capabilities);
            memberCapabilitiesTextView.setText(member.getDisplayCapabilitiesString());

            ImageButton editMemberButton = (ImageButton) view.findViewById(R.id.button_edit_member);
            editMemberButton.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    FragmentManager fm = getActivity().getSupportFragmentManager();

                    EditMemberDialog dialog = EditMemberDialog.newInstance(member);
                    dialog.setTargetFragment(MemberSettingsFragment.this, REQUEST_EDIT_MEMBER);
                    dialog.show(fm, DIALOG_EDIT_MEMBER);
                }
            });

            ImageButton deleteMemberButton = (ImageButton) view.findViewById(R.id.button_delete_member);
            deleteMemberButton.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    FlyingList flyingList = FlyingListRepository.getInstance(getContext()).getCurrentFlyingList();
                    String message = "Are you sure you want to remove "
                            + member.getDisplayNameFirstNameFirst()
                            + "? This action cannot be undone.";

                    if (flyingList.isMemberOnList(member)) {
                        message += "\n\n"
                            + "This will also remove " + member.getDisplayNameFirstNameFirst() + " from the flying list.";
                    }

                    new AlertDialog.Builder(getActivity())
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setTitle("Delete Member")
                        .setMessage(message)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener()
                        {
                            @Override
                            public void onClick(DialogInterface dialog, int which)
                            {
                                removeMember(member);
                            }
                        })
                        .setNegativeButton("Cancel", null)
                        .show();
                }
            });
        }
    }

    private void removeMember(Member member)
    {
        mMemberRepository.deleteMember(member);
        refreshMemberList();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if (resultCode != Activity.RESULT_OK) {
            return;
        }

        if (requestCode == REQUEST_EDIT_MEMBER) {
            refreshMemberList();
        }
    }

    private void refreshMemberList()
    {
        mAllMembers = (ArrayList<Member>) mMemberRepository.getAllMembers();
        Collections.sort(mAllMembers);
        ((ArrayAdapter) mMembersList.getAdapter()).notifyDataSetChanged();
    }
}
