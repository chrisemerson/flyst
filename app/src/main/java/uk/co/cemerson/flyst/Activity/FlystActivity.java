package uk.co.cemerson.flyst.Activity;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.WindowManager;

public class FlystActivity extends ActionBarActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        setWindowOptions();

        super.onCreate(savedInstanceState);
    }

    protected void setWindowOptions()
    {
        getWindow().addFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN
                | WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED
                | WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD
        );
    }
}
