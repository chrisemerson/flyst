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
import android.widget.Button;
import android.widget.EditText;

import uk.co.cemerson.flyst.R;

public class ChangePasswordDialog extends DialogFragment
{
    private static final String EXTRA_CURRENT_PASSWORD = "uk.co.cemerson.flyst.changepassworddialog.current_password";
    public static final String EXTRA_NEW_PASSWORD = "uk.co.cemerson.flyst.changepassworddialog.new_password";

    private boolean mPasswordAlreadySet = false;
    private String mCurrentPassword;

    private EditText mCurrentPasswordBox;
    private EditText mNewPasswordBox;
    private EditText mRepeatNewPasswordBox;

    public static ChangePasswordDialog newInstance(String currentPassword)
    {
        Bundle args = new Bundle();

        if (currentPassword != null) {
            args.putString(EXTRA_CURRENT_PASSWORD, currentPassword);
        }

        ChangePasswordDialog changePasswordDialog = new ChangePasswordDialog();
        changePasswordDialog.setArguments(args);

        return changePasswordDialog;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState)
    {
        View view = getActivity().getLayoutInflater().inflate(R.layout.dialog_change_password, null);

        initView(view);

        return new AlertDialog.Builder(getActivity())
            .setView(view)
            .setTitle(getDialogTitle())
            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener()
            {
                @Override
                public void onClick(DialogInterface dialog, int which)
                {
                    //noop
                }
            })
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

    @Override
    public void onStart()
    {
        super.onStart();

        AlertDialog dialog = (AlertDialog) getDialog();

        if (dialog != null) {
            Button positiveButton = dialog.getButton(Dialog.BUTTON_POSITIVE);

            positiveButton.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    if (changePassword()) {
                        dismiss();
                    }
                }
            });
        }
    }

    private void initView(View view)
    {
        if (getArguments().containsKey(EXTRA_CURRENT_PASSWORD)) {
            mCurrentPassword = getArguments().getString(EXTRA_CURRENT_PASSWORD);
            mPasswordAlreadySet = true;
        }

        mCurrentPasswordBox = (EditText) view.findViewById(R.id.old_password);
        mNewPasswordBox = (EditText) view.findViewById(R.id.new_password);
        mRepeatNewPasswordBox = (EditText) view.findViewById(R.id.repeat_new_password);

        if (!mPasswordAlreadySet) {
            mCurrentPasswordBox.setVisibility(View.GONE);
        }
    }

    private String getDialogTitle()
    {
        if (mPasswordAlreadySet) {
            return getResources().getString(R.string.change_password_button);
        } else {
            return getResources().getString(R.string.set_password_button);
        }
    }

    private boolean changePassword()
    {
        if (mPasswordAlreadySet && !mCurrentPasswordBox.getText().toString().equals(mCurrentPassword)) {
            new AlertDialog.Builder(getActivity())
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("Incorrect Password")
                .setMessage("The 'current' password you entered is incorrect.")
                .setPositiveButton(android.R.string.ok, null)
                .show();

            return false;
        }

        if (!mRepeatNewPasswordBox.getText().toString().equals(mNewPasswordBox.getText().toString())) {
            new AlertDialog.Builder(getActivity())
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("Passwords Don't Match")
                .setMessage("The new passwords you entered don't match.")
                .setPositiveButton(android.R.string.ok, null)
                .show();

            return false;
        }

        if (mNewPasswordBox.getText().toString().equals("")) {
            new AlertDialog.Builder(getActivity())
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("New Password Required")
                .setMessage("You must enter a new password.")
                .setPositiveButton(android.R.string.ok, null)
                .show();

            return false;
        }

        closeDialog(Activity.RESULT_OK, mNewPasswordBox.getText().toString());

        return true;
    }

    private void closeDialog(int resultCode, String newPassword)
    {
        if (getTargetFragment() == null) {
            return;
        }

        Intent i = new Intent();

        if (resultCode == Activity.RESULT_OK) {
            i.putExtra(EXTRA_NEW_PASSWORD, newPassword);
        }

        getTargetFragment().onActivityResult(getTargetRequestCode(), resultCode, i);
    }
}
