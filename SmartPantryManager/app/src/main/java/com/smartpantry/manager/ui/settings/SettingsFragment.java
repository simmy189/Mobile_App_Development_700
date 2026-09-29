package com.smartpantry.manager.ui.settings;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.Spinner;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SwitchCompat;
import androidx.fragment.app.Fragment;
import com.smartpantry.manager.R;

public class SettingsFragment extends Fragment {

    public static final String PREFS_NAME = "SmartPantryPrefs";
    public static final String KEY_EXPIRY_ALERTS = "expiry_alerts_enabled";
    public static final String KEY_UNITS_PREFERENCE = "units_preference";

    private SharedPreferences prefs;
    private boolean isInitializing = false;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_settings, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        prefs = requireContext().getSharedPreferences(PREFS_NAME, requireContext().MODE_PRIVATE);

        // Expiry Alerts Switch
        SwitchCompat switchExpiryAlerts = view.findViewById(R.id.switch_expiry_alerts);
        boolean expiryEnabled = prefs.getBoolean(KEY_EXPIRY_ALERTS, true);
        switchExpiryAlerts.setChecked(expiryEnabled);
        switchExpiryAlerts.setOnCheckedChangeListener((buttonView, isChecked) ->
                prefs.edit().putBoolean(KEY_EXPIRY_ALERTS, isChecked).apply());

        // Units Preference Spinner
        Spinner spinnerUnits = view.findViewById(R.id.spinner_units);
        String[] unitOptions = new String[]{
                getString(R.string.units_metric),
                getString(R.string.units_imperial)
        };
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(
                requireContext(),
                android.R.layout.simple_spinner_item,
                unitOptions
        );
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerUnits.setAdapter(spinnerAdapter);

        // Set initial selection based on saved preference
        String savedUnits = prefs.getString(KEY_UNITS_PREFERENCE, getString(R.string.units_metric));
        isInitializing = true;
        if (savedUnits.equals(getString(R.string.units_imperial))) {
            spinnerUnits.setSelection(1);
        } else {
            spinnerUnits.setSelection(0);
        }

        spinnerUnits.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (isInitializing) {
                    isInitializing = false;
                    return;
                }
                String selected = unitOptions[position];
                prefs.edit().putString(KEY_UNITS_PREFERENCE, selected).apply();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // No action needed
            }
        });

        // Version info
        TextView tvVersion = view.findViewById(R.id.tv_version);
        tvVersion.setText(getString(R.string.version_info, "1.0"));
    }
}
