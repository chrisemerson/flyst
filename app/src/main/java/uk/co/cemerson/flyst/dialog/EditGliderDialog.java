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
import android.widget.CheckBox;
import android.widget.EditText;

import uk.co.cemerson.flyst.R;
import uk.co.cemerson.flyst.entity.Glider;
import uk.co.cemerson.flyst.repository.GliderRepository;

public class EditGliderDialog extends DialogFragment
{
    public static final String EXTRA_REGISTRATION = "uk.co.cemerson.flyst.editgliderdialog.registration";

    private String mGliderRegistration = null;

    private EditText mRegistration;
    private EditText mType;
    private CheckBox mIsTwoSeater;
    private CheckBox mIsClubGlider;

    public static EditGliderDialog newInstance(Glider glider)
    {
        Bundle args = new Bundle();
        args.putSerializable(EXTRA_REGISTRATION, glider.getRegistration());

        EditGliderDialog editGliderDialog = new EditGliderDialog();
        editGliderDialog.setArguments(args);

        return editGliderDialog;
    }

    public static EditGliderDialog newInstance()
    {
        return new EditGliderDialog();
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState)
    {
        View view = getActivity().getLayoutInflater().inflate(R.layout.dialog_edit_glider, null);

        initView(view);
        initFromArguments();

        return new AlertDialog.Builder(getActivity())
            .setView(view)
            .setTitle(getDialogTitle())
            .setPositiveButton(getResources().getString(R.string.save_glider_button_text), new SaveGliderListener())
            .setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener()
            {
                @Override
                public void onClick(DialogInterface dialog, int which)
                {
                    closeDialog(Activity.RESULT_CANCELED, null);
                }
            })
            .create();
    }

    private void initFromArguments()
    {
        Bundle args = getArguments();

        if (args != null) {
            String registration = args.getString(EXTRA_REGISTRATION);

            GliderRepository gliderRepository = GliderRepository.getInstance(getActivity().getApplicationContext());
            Glider glider = gliderRepository.findByRegistration(registration);

            mGliderRegistration = registration;

            mRegistration.setText(glider.getRegistration());
            mType.setText(glider.getType());
            mIsTwoSeater.setChecked(glider.isTwoSeater());
            mIsClubGlider.setChecked(glider.isClubGlider());
        }
    }

    private String getDialogTitle()
    {
        if (mGliderRegistration == null) {
            return getResources().getString(R.string.add_glider_dialog_title);
        } else {
            return getResources().getString(R.string.edit_glider_dialog_title);
        }
    }

    private void initView(View view)
    {
        mRegistration = (EditText) view.findViewById(R.id.edit_text_registration);
        mType = (EditText) view.findViewById(R.id.edit_text_type);
        mIsTwoSeater = (CheckBox) view.findViewById(R.id.checkbox_is_two_seater);
        mIsClubGlider = (CheckBox) view.findViewById(R.id.checkbox_is_club_glider);
    }

    private class SaveGliderListener implements DialogInterface.OnClickListener
    {
        @Override
        public void onClick(DialogInterface dialog, int which)
        {
            saveMember();
        }
    }

    private void saveMember()
    {
        GliderRepository gliderRepository = GliderRepository.getInstance(getActivity().getApplicationContext());
        Glider glider;

        if (mGliderRegistration == null) {
            glider = new Glider(getActivity().getApplicationContext());
            gliderRepository.addGlider(glider);
        } else {
            glider = gliderRepository.findByRegistration(mGliderRegistration);
        }

        glider.setRegistration(mRegistration.getText().toString());
        glider.setType(mType.getText().toString());
        glider.setTwoSeater(mIsTwoSeater.isChecked());
        glider.setClubGlider(mIsClubGlider.isChecked());

        gliderRepository.save();

        closeDialog(Activity.RESULT_OK, glider.getRegistration());
    }

    private void closeDialog(int resultCode, String gliderRegistration)
    {
        if (getTargetFragment() == null) {
            return;
        }

        Intent i = new Intent();

        if (resultCode == Activity.RESULT_OK) {
            i.putExtra(EXTRA_REGISTRATION, gliderRegistration);
        }

        getTargetFragment().onActivityResult(getTargetRequestCode(), resultCode, i);
    }
}
