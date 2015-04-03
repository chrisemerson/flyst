package uk.co.cemerson.flyst.dialog;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;

import java.util.UUID;

import uk.co.cemerson.flyst.R;
import uk.co.cemerson.flyst.entity.InstructorCategory;
import uk.co.cemerson.flyst.entity.Member;
import uk.co.cemerson.flyst.repository.MemberRepository;

public class EditMemberDialog extends DialogFragment
{
    private static final String EXTRA_FIRST_NAME = "uk.co.cemerson.flyst.editmemberdialog.first_name";
    private static final String EXTRA_SURNAME = "uk.co.cemerson.flyst.editmemberdialog.surname";
    public static final String EXTRA_MEMBER_ID = "uk.co.cemerson.flyst.editmemberdialog.member_ID";

    private UUID mMemberID = null;

    private EditText mFirstName;
    private EditText mSurname;
    private CheckBox mIsWinchDriver;
    private CheckBox mIsRetrieveDriver;
    private Spinner mInstructorCategory;

    public static EditMemberDialog newInstance(Member member)
    {
        Bundle args = new Bundle();
        args.putSerializable(EXTRA_MEMBER_ID, member.getID());

        EditMemberDialog editMemberDialog = new EditMemberDialog();
        editMemberDialog.setArguments(args);

        return editMemberDialog;
    }

    public static EditMemberDialog newInstance(String memberName)
    {
        Bundle args = new Bundle();

        String[] nameParts = memberName.split("\\s+");

        StringBuilder firstNameParts = new StringBuilder();
        String surname = null;

        for (int i = 0; i < nameParts.length; i++) {
            if (i == nameParts.length - 1) {
                surname = nameParts[i];
            } else {
                if (i > 0) {
                    firstNameParts.append(" ");
                }

                firstNameParts.append(nameParts[i]);
            }
        }

        String firstName = firstNameParts.toString();

        args.putString(EXTRA_FIRST_NAME, firstName);
        args.putString(EXTRA_SURNAME, surname);

        EditMemberDialog editMemberDialog = new EditMemberDialog();
        editMemberDialog.setArguments(args);

        return editMemberDialog;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState)
    {
        View view = getActivity().getLayoutInflater().inflate(R.layout.dialog_edit_member, null);

        initView(view);
        initFromArguments();

        return new AlertDialog.Builder(getActivity())
            .setView(view)
            .setTitle(getDialogTitle())
            .setPositiveButton(getResources().getString(R.string.save_member_button_text), new SaveMemberListener())
            .setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener()
            {
                @Override
                public void onClick(DialogInterface dialog, int which)
                {
                    closeDialogBox(Activity.RESULT_CANCELED, null);
                }
            })
            .create();
    }

    private void initFromArguments()
    {
        UUID memberID = (UUID) getArguments().getSerializable(EXTRA_MEMBER_ID);

        if (memberID != null) {
            MemberRepository memberRepository = MemberRepository.getInstance(getActivity().getApplicationContext());
            Member member = memberRepository.findByID(memberID);

            mMemberID = memberID;
            mFirstName.setText(member.getFirstName());
            mSurname.setText(member.getSurname());
            mIsWinchDriver.setChecked(member.isWinchDriver());
            mIsRetrieveDriver.setChecked(member.isRetrieveDriver());

            SpinnerAdapter adapter = mInstructorCategory.getAdapter();

            for (int i = 0; i < adapter.getCount(); i++) {
                if (adapter.getItem(i) == member.getInstructorCategory()) {
                    mInstructorCategory.setSelection(i);
                }
            }
        } else {
            String firstName = getArguments().getString(EXTRA_FIRST_NAME);
            String surname = getArguments().getString(EXTRA_SURNAME);

            if (firstName != null) {
                mFirstName.setText(firstName);
            }

            if (surname != null) {
                mSurname.setText(surname);
            }
        }
    }

    private String getDialogTitle()
    {
        if (mMemberID == null) {
            return getResources().getString(R.string.add_member_dialog_title);
        } else {
            return getResources().getString(R.string.edit_member_dialog_title);
        }
    }

    private void initView(View view)
    {
        initViewReferences(view);
        initInstructorSpinner();
    }

    private void initViewReferences(View view)
    {
        mFirstName = (EditText) view.findViewById(R.id.edit_text_first_name);
        mSurname = (EditText) view.findViewById(R.id.edit_text_surname);
        mIsWinchDriver = (CheckBox) view.findViewById(R.id.checkbox_is_winch_driver);
        mIsRetrieveDriver = (CheckBox) view.findViewById(R.id.checkbox_is_retrieve_driver);
        mInstructorCategory = (Spinner) view.findViewById(R.id.spinner_instructor_category);
    }

    private void initInstructorSpinner()
    {
        ArrayAdapter<InstructorCategory> adapter = new ArrayAdapter<>(
            getActivity(),
            android.R.layout.simple_spinner_item,
            InstructorCategory.values()
        );

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        mInstructorCategory.setAdapter(adapter);
    }

    private class SaveMemberListener implements DialogInterface.OnClickListener
    {
        @Override
        public void onClick(DialogInterface dialog, int which)
        {
            saveMember();
        }
    }

    private void saveMember()
    {
        MemberRepository memberRepository = MemberRepository.getInstance(getActivity().getApplicationContext());
        Member member;

        if (mMemberID == null) {
            member = new Member(getActivity().getApplicationContext());
        } else {
            member = memberRepository.findByID(mMemberID);
        }

        member.setFirstName(mFirstName.getText().toString());
        member.setSurname(mSurname.getText().toString());
        member.setIsWinchDriver(mIsWinchDriver.isChecked());
        member.setIsRetrieveDriver(mIsRetrieveDriver.isChecked());
        member.setInstructorCategory((InstructorCategory) mInstructorCategory.getSelectedItem());

        memberRepository.addMember(member);
        memberRepository.save();

        closeDialogBox(Activity.RESULT_OK, member.getID());
    }

    private void closeDialogBox(int resultCode, UUID memberID)
    {
        if (getTargetFragment() == null) {
            return;
        }

        Intent i = new Intent();

        if (resultCode == Activity.RESULT_OK) {
            i.putExtra(EXTRA_MEMBER_ID, memberID);
        }

        getTargetFragment().onActivityResult(getTargetRequestCode(), resultCode, i);
    }
}
