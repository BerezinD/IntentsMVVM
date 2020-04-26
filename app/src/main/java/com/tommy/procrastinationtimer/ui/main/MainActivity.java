package com.tommy.procrastinationtimer.ui.main;

import android.content.Intent;
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

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    public static final String EXTRA_TASK = "com.tommy.procrastinationtimer.models.Task";
    public static final String STORAGE_TYPE = "com.tommy.procrastinationtimer.models.Storage";
    private static final int LAUNCH_NEW_ACTIVITY_CODE = 1;
    private static Storage storageType = Storage.SHARED_PREF;
    private Toolbar toolbar;
    private FloatingActionButton fab;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private ProgressBar progressBar;
    private NavigationView navigationView;
    private MainActivityViewModel mainActivityViewModel;

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
        mainActivityViewModel.init();
        mainActivityViewModel.getTaskList().observe(this, new Observer<List<Task>>() {
            @Override
            public void onChanged(List<Task> tasks) {
                adapter.notifyDataSetChanged();
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
                    storageType = Enum.valueOf(Storage.class, data.getStringExtra(STORAGE_TYPE));
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
                intent.putExtra(STORAGE_TYPE, storageType.name());
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
}
