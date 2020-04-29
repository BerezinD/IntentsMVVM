package com.tommy.procrastinationtimer.ui.main;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.preference.PreferenceManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.tommy.procrastinationtimer.R;
import com.tommy.procrastinationtimer.adapters.RecyclerAdapter;
import com.tommy.procrastinationtimer.models.Storage;
import com.tommy.procrastinationtimer.models.Task;
import com.tommy.procrastinationtimer.ui.settings.SettingsActivity;
import com.tommy.procrastinationtimer.ui.tasks.CreateNewTaskActivity;
import com.tommy.procrastinationtimer.viewmodels.MainActivityViewModel;

import java.util.List;

import static com.tommy.procrastinationtimer.models.Storage.*;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    public static final String EXTRA_TASK = "com.tommy.procrastinationtimer.models.Task";
    public static final String STORAGE_TYPE = "com.tommy.procrastinationtimer.models.Storage";
    private static final int LAUNCH_NEW_ACTIVITY_CODE = 1;
    private static Storage storageType;
    private Toolbar toolbar;
    private FloatingActionButton fab;
    private RecyclerView recyclerView;
    private RecyclerAdapter adapter;
    private ProgressBar progressBar;
    private NavigationView navigationView;
    private MainActivityViewModel mainActivityViewModel;
    private SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = findViewById(R.id.toolbar);
        fab = findViewById(R.id.fab);
        recyclerView = findViewById(R.id.recycler_view_for_task);
        progressBar = findViewById(R.id.progress_bar);
        navigationView = findViewById(R.id.nav_view);

        navigationView.setNavigationItemSelectedListener(this);

        mainActivityViewModel = new ViewModelProvider(this).get(MainActivityViewModel.class);
        mainActivityViewModel.init(this);
        mainActivityViewModel.getTaskList().observe(this, new Observer<List<Task>>() {
            @Override
            public void onChanged(List<Task> tasks) {
                adapter.setTaskList(tasks);
            }
        });
        mainActivityViewModel.getIsUpdated().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if (aBoolean) {
                    progressBar.setVisibility(View.VISIBLE);
                } else {
                    progressBar.setVisibility(View.GONE);
                    recyclerView.smoothScrollToPosition(mainActivityViewModel.getTaskList().getValue().size() - 1);
                }
            }
        });

        initRecyclerView();
        setupSharedPreferences();
        setSupportActionBar(toolbar);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), CreateNewTaskActivity.class);
                startActivityForResult(intent, LAUNCH_NEW_ACTIVITY_CODE);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == LAUNCH_NEW_ACTIVITY_CODE) {
            if (data != null) {
                if (data.hasExtra(EXTRA_TASK)) {
                    mainActivityViewModel.addTask((Task) data.getParcelableExtra(EXTRA_TASK));
                }
                if (data.hasExtra(STORAGE_TYPE)) {
                    Storage newStorageType = Enum.valueOf(Storage.class, data.getStringExtra(STORAGE_TYPE));
                    if (newStorageType != storageType) {
                        mainActivityViewModel.changeSource(newStorageType);
                        storageType = newStorageType;
                    }
                }
            }
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.settings:
                Intent intent = new Intent(getApplicationContext(), SettingsActivity.class);
                intent.putExtra(STORAGE_TYPE, storageType.toString());
                startActivityForResult(intent, LAUNCH_NEW_ACTIVITY_CODE);
                break;
            case R.id.fb:
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/")));
                break;
            case R.id.github:
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/")));
                break;
        }
        return true;
    }

    private void initRecyclerView() {
        RecyclerView.LayoutManager linearLayoutManager = new LinearLayoutManager(this);
        adapter = new RecyclerAdapter(this, mainActivityViewModel.getTaskList().getValue());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);
    }

    private void setupSharedPreferences() {
        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        preferences.registerOnSharedPreferenceChangeListener(new SharedPreferences.OnSharedPreferenceChangeListener() {
            @Override
            public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
                String value = preferences.getString(getString(R.string.key_pref_settings), SHARED_PREF.toString());
                if (value != null) {
                    setStorageType(value);
                }
            }
        });
        setStorageType(preferences.getString(getString(R.string.key_pref_settings), SHARED_PREF.toString()));
    }

    private void setStorageType(String type) {
        if (SHARED_PREF.toString().equals(type)
                || INTERNAL.toString().equals(type)
                || EXTERNAL.toString().equals(type)
                || SQL.toString().equals(type)) {
            storageType = Storage.valueOf(type);
        } else storageType = SHARED_PREF;
    }
}
