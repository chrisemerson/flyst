package uk.co.cemerson.flyst.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.UUID;

import uk.co.cemerson.flyst.R;
import uk.co.cemerson.flyst.entity.InstructorCategory;
import uk.co.cemerson.flyst.entity.Member;
import uk.co.cemerson.flyst.repository.MemberRepository;

public class EditMemberDialog extends DialogFragment
{
    private UUID mMemberID = null;

    private EditText mFirstName;
    private EditText mSurname;
    private CheckBox mIsWinchDriver;
    private CheckBox mIsRetrieveDriver;
    private Spinner mInstructorCategory;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState)
    {
        View view = getActivity().getLayoutInflater().inflate(R.layout.dialog_edit_member, null);

        initView(view);

        return new AlertDialog.Builder(getActivity())
            .setView(view)
            .setTitle(getDialogTitle())
            .setPositiveButton(getResources().getString(R.string.save_member_button_text), new SaveMemberListener())
            .setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener()
            {
                @Override
                public void onClick(DialogInterface dialog, int which)
                {
                    closeDialogBox(false);
                }
            })
            .create();
    }

    private String getDialogTitle()
    {
        return getResources().getString(R.string.add_member_dialog_title);
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

        closeDialogBox(true);
    }

    private void closeDialogBox(boolean successResult)
    {

    }
}
