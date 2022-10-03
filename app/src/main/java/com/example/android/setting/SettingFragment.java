package com.example.android.setting;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.preference.EditTextPreference;
import androidx.preference.ListPreference;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.PreferenceScreen;

public class SettingFragment extends PreferenceFragmentCompat implements SharedPreferences.OnSharedPreferenceChangeListener,Preference.OnPreferenceChangeListener {
    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        addPreferencesFromResource(R.xml.preference_screen);

        SharedPreferences sharedPreferences = getPreferenceScreen().getSharedPreferences();
        PreferenceScreen preferenceScreen = getPreferenceScreen();

        int count = preferenceScreen.getPreferenceCount();

        for(int i = 0;i<count;i++){
            Preference p = preferenceScreen.getPreference(i);
            if (p instanceof ListPreference){
                String value = sharedPreferences.getString(p.getKey(),"");
                setPreferenceSummary(p,value);
            }else if(p instanceof EditTextPreference){
                String value = sharedPreferences.getString(p.getKey(),"");
                p.setSummary(value);

            }

        }
        Preference preference = findPreference(getString(R.string.pref_edit_key));
        preference.setOnPreferenceChangeListener(this);
    }
    private void setPreferenceSummary(Preference preference,String value){
        if (preference instanceof ListPreference){
            ListPreference listPreference = (ListPreference) preference;
            int preIndex = listPreference.findIndexOfValue(value);
            if(preIndex>=0){
                listPreference.setSummary(listPreference.getEntries()[preIndex]);
            }
        }
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String s) {
        Preference preference = findPreference(s);
        if (preference instanceof ListPreference){
            String value = sharedPreferences.getString(preference.getKey(),"");
            setPreferenceSummary(preference,value);
        }
        else if(preference instanceof EditTextPreference){
            String value = sharedPreferences.getString(preference.getKey(),"");
            preference.setSummary(value);
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getPreferenceScreen().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        getPreferenceScreen().getSharedPreferences().unregisterOnSharedPreferenceChangeListener(this);
    }

    @Override
    public boolean onPreferenceChange(Preference preference, Object newValue) {
        String key_editText = getString(R.string.pref_edit_key);
        if(preference.getKey().equals(key_editText)){
            String value = (String) newValue;
            try{
                float edit_value = Float.parseFloat(value);
                if (edit_value>10 || edit_value<=0){
                    Toast.makeText(getContext(), "Enter value between 0-10", Toast.LENGTH_SHORT).show();
                    return false;
                }
            }catch (NumberFormatException nfe){
                Toast.makeText(getContext(), "Enter value between 0-10.", Toast.LENGTH_SHORT).show();
                return false;
            }
        }
        return true;
    }
}
