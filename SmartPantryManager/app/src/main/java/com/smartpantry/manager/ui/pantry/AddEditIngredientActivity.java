package com.smartpantry.manager.ui.pantry;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import com.smartpantry.manager.R;
import com.smartpantry.manager.database.AppDatabase;
import com.smartpantry.manager.database.entity.Ingredient;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;

public class AddEditIngredientActivity extends AppCompatActivity {

    public static final String EXTRA_INGREDIENT_ID = "ingredient_id";
    public static final String EXTRA_INGREDIENT_NAME = "ingredient_name";
    public static final String EXTRA_INGREDIENT_QUANTITY = "ingredient_quantity";
    public static final String EXTRA_INGREDIENT_UNIT = "ingredient_unit";
    public static final String EXTRA_INGREDIENT_EXPIRY = "ingredient_expiry";

    private static final String[] UNITS = {
            "g", "kg", "ml", "L", "tbsp", "tsp", "cup", "unit", "pinch", "clove", "slice"
    };

    private EditText etName;
    private EditText etQuantity;
    private Spinner spinnerUnit;
    private EditText etExpiryDate;

    private boolean isEditMode = false;
    private int ingredientId = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_ingredient);

        Toolbar toolbar = findViewById(R.id.toolbar_add_edit);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        etName = findViewById(R.id.et_ingredient_name);
        etQuantity = findViewById(R.id.et_ingredient_quantity);
        spinnerUnit = findViewById(R.id.spinner_unit);
        etExpiryDate = findViewById(R.id.et_expiry_date);

        // Set up unit spinner
        ArrayAdapter<String> unitAdapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_item,
                UNITS
        );
        unitAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerUnit.setAdapter(unitAdapter);

        // Check if we're in edit mode
        if (getIntent().hasExtra(EXTRA_INGREDIENT_ID)) {
            isEditMode = true;
            ingredientId = getIntent().getIntExtra(EXTRA_INGREDIENT_ID, -1);
            String name = getIntent().getStringExtra(EXTRA_INGREDIENT_NAME);
            double quantity = getIntent().getDoubleExtra(EXTRA_INGREDIENT_QUANTITY, 0);
            String unit = getIntent().getStringExtra(EXTRA_INGREDIENT_UNIT);
            String expiry = getIntent().getStringExtra(EXTRA_INGREDIENT_EXPIRY);

            etName.setText(name);
            // Format quantity for display
            if (quantity == Math.floor(quantity)) {
                etQuantity.setText(String.valueOf((int) quantity));
            } else {
                etQuantity.setText(String.valueOf(quantity));
            }
            etExpiryDate.setText(expiry != null ? expiry : "");

            // Set spinner selection for unit
            for (int i = 0; i < UNITS.length; i++) {
                if (UNITS[i].equals(unit)) {
                    spinnerUnit.setSelection(i);
                    break;
                }
            }

            if (getSupportActionBar() != null) {
                getSupportActionBar().setTitle(R.string.edit_ingredient);
            }
        } else {
            if (getSupportActionBar() != null) {
                getSupportActionBar().setTitle(R.string.add_ingredient);
            }
        }

        Button btnSave = findViewById(R.id.btn_save_ingredient);
        btnSave.setOnClickListener(v -> saveIngredient());
    }

    private void saveIngredient() {
        String name = etName.getText().toString().trim();
        String quantityStr = etQuantity.getText().toString().trim();
        String unit = (String) spinnerUnit.getSelectedItem();
        String expiryDate = etExpiryDate.getText().toString().trim();

        boolean valid = true;

        if (TextUtils.isEmpty(name)) {
            etName.setError(getString(R.string.error_name_empty));
            valid = false;
        } else {
            etName.setError(null);
        }

        double quantity = 0;
        if (TextUtils.isEmpty(quantityStr)) {
            etQuantity.setError(getString(R.string.error_quantity_empty));
            valid = false;
        } else {
            try {
                quantity = Double.parseDouble(quantityStr);
                if (quantity <= 0) {
                    etQuantity.setError(getString(R.string.error_quantity_positive));
                    valid = false;
                } else {
                    etQuantity.setError(null);
                }
            } catch (NumberFormatException e) {
                etQuantity.setError(getString(R.string.error_quantity_invalid));
                valid = false;
            }
        }

        if (!TextUtils.isEmpty(expiryDate) && !isValidDate(expiryDate)) {
            etExpiryDate.setError(getString(R.string.error_date_invalid));
            valid = false;
        } else {
            etExpiryDate.setError(null);
        }

        if (!valid) return;

        final double finalQuantity = quantity;
        final String finalExpiryDate = TextUtils.isEmpty(expiryDate) ? null : expiryDate;

        if (isEditMode) {
            Ingredient updated = new Ingredient(name, finalQuantity, unit, finalExpiryDate);
            updated.id = ingredientId;
            new Thread(() -> {
                AppDatabase.getInstance(getApplicationContext()).ingredientDao().update(updated);
                runOnUiThread(this::finish);
            }).start();
        } else {
            Ingredient newIngredient = new Ingredient(name, finalQuantity, unit, finalExpiryDate);
            new Thread(() -> {
                AppDatabase.getInstance(getApplicationContext()).ingredientDao().insert(newIngredient);
                runOnUiThread(this::finish);
            }).start();
        }
    }

    // Accepts only real calendar dates in yyyy-MM-dd format (rejects e.g. 2026-02-30 or 12/10/2026)
    private boolean isValidDate(String date) {
        if (!date.matches("\\d{4}-\\d{2}-\\d{2}")) return false;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
        sdf.setLenient(false);
        try {
            sdf.parse(date);
            return true;
        } catch (ParseException e) {
            return false;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
