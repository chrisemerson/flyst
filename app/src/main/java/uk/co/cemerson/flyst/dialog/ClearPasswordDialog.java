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

public class ClearPasswordDialog extends DialogFragment
{
    private static final String EXTRA_CURRENT_PASSWORD = "uk.co.cemerson.flyst.clearpassworddialog.current_password";

    private String mCurrentPassword;

    private EditText mCurrentPasswordBox;

    public static ClearPasswordDialog newInstance(@NonNull String currentPassword)
    {
        Bundle args = new Bundle();

        args.putString(EXTRA_CURRENT_PASSWORD, currentPassword);

        ClearPasswordDialog clearPasswordDialog = new ClearPasswordDialog();
        clearPasswordDialog.setArguments(args);

        return clearPasswordDialog;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState)
    {
        View view = getActivity().getLayoutInflater().inflate(R.layout.dialog_clear_password, null);

        initView(view);

        return new AlertDialog.Builder(getActivity())
            .setView(view)
            .setTitle(getResources().getString(R.string.clear_password_title))
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
                    closeDialog(Activity.RESULT_CANCELED);
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
                    if (clearPassword()) {
                        dismiss();
                    }
                }
            });
        }
    }

    private void initView(View view)
    {
        mCurrentPassword = getArguments().getString(EXTRA_CURRENT_PASSWORD);
        mCurrentPasswordBox = (EditText) view.findViewById(R.id.password);
    }

    private boolean clearPassword()
    {
        if (!mCurrentPasswordBox.getText().toString().equals(mCurrentPassword)) {
            new AlertDialog.Builder(getActivity())
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("Incorrect Password")
                .setMessage("The password you entered is incorrect.")
                .setPositiveButton(android.R.string.ok, null)
                .show();

            return false;
        }

        closeDialog(Activity.RESULT_OK);

        return true;
    }

    private void closeDialog(int resultCode)
    {
        if (getTargetFragment() == null) {
            return;
        }

        Intent i = new Intent();
        getTargetFragment().onActivityResult(getTargetRequestCode(), resultCode, i);
    }
}
