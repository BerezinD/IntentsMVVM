package com.tommy.procrastinationtimer.ui.settings;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.tommy.procrastinationtimer.R;
import com.tommy.procrastinationtimer.models.Storage;

import static com.tommy.procrastinationtimer.models.Storage.*;
import static com.tommy.procrastinationtimer.ui.main.MainActivity.STORAGE_TYPE;

public class SettingsActivity extends AppCompatActivity {

    private Storage currentStorage;
    private RadioGroup radioGroupStorage;
    private RadioButton radioSharedPref;
    private RadioButton radioExternal;
    private RadioButton radioInternal;
    private RadioButton radioSql;
    private FloatingActionButton saveButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        radioGroupStorage = findViewById(R.id.storage_choose);
        radioSharedPref = findViewById(R.id.shared_pref);
        radioExternal = findViewById(R.id.external);
        radioInternal = findViewById(R.id.internal);
        radioSql = findViewById(R.id.sql);
        saveButton = findViewById(R.id.fab_save_storage);

        setInitValueFromIntent();
        setChooseStorageListener();
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra(STORAGE_TYPE, currentStorage.name());
                setResult(RESULT_OK, intent);
                finish();
            }
        });
    }

    private void setInitValueFromIntent() {
        Intent intent = getIntent();
        if (intent != null) {
            final String typeOfStorage = intent.getStringExtra(STORAGE_TYPE);
            if (typeOfStorage != null) {
                currentStorage = Enum.valueOf(Storage.class, typeOfStorage);
                switch (currentStorage) {
                    case SHARED_PREF:
                        radioSharedPref.setChecked(true);
                        break;
                    case EXTERNAL:
                        radioExternal.setChecked(true);
                        break;
                    case INTERNAL:
                        radioInternal.setChecked(true);
                        break;
                    case SQL:
                        radioSql.setChecked(true);
                        break;
                }
            }
        }
    }

    private void setChooseStorageListener() {
        radioGroupStorage.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.shared_pref:
                        currentStorage = SHARED_PREF;
                        break;
                    case R.id.external:
                        currentStorage = EXTERNAL;
                        break;
                    case R.id.internal:
                        currentStorage = INTERNAL;
                        break;
                    case R.id.sql:
                        currentStorage = SQL;
                        break;
                }
            }
        });
    }
}
