package uk.co.cemerson.flyst.fragment.settings;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import uk.co.cemerson.flyst.R;

public class SettingsFragment extends Fragment
{
    private LayoutInflater mInflater;
    private RadioGroup mSettingsButtonContainer;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        mInflater = inflater;

        View view = inflater.inflate(R.layout.fragment_settings, container, false);

        initSettingsSections(view);

        return view;
    }

    private void initSettingsSections(View view)
    {
        mSettingsButtonContainer = (RadioGroup) view.findViewById(R.id.settings_button_container);

        addSettingsSection(
            R.string.settings_button_general,
            android.R.drawable.ic_menu_preferences,
            new GeneralSettingsFragment()
        );

        addSettingsSection(
            R.string.settings_button_members,
            android.R.drawable.ic_menu_preferences,
            new MemberSettingsFragment()
        );

        addSettingsSection(
            R.string.settings_button_gliders,
            android.R.drawable.ic_menu_preferences,
            new GliderSettingsFragment()
        );

        addSettingsSection(
            R.string.settings_button_system,
            android.R.drawable.ic_menu_preferences,
            new SystemSettingsFragment()
        );

        setDefaultSelectedButton();
    }

    private void setDefaultSelectedButton()
    {
        mSettingsButtonContainer.getChildAt(0).performClick();
    }

    private void changeSettingsPane(Fragment newFragment)
    {
        int containerID = R.id.settings_pane;

        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();

        if (fragmentManager.findFragmentById(containerID) == null) {
            fragmentManager
                .beginTransaction()
                .add(containerID, newFragment)
                .commit();
        } else {
            fragmentManager
                .beginTransaction()
                .replace(containerID, newFragment)
                .commit();
        }
    }

    private void addSettingsSection(int nameStringResource, int drawableID, final Fragment settingsFragment)
    {
        RadioButton button = (RadioButton) mInflater.inflate(R.layout.list_item_settings_button, mSettingsButtonContainer, false);

        button.setText(getResources().getString(nameStringResource));
        button.setCompoundDrawablesWithIntrinsicBounds(drawableID, 0, 0, 0);

        button.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                changeSettingsPane(settingsFragment);
            }
        });

        mSettingsButtonContainer.addView(button);
    }
}
