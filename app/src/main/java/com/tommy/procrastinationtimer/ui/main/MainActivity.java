package com.tommy.procrastinationtimer.ui.main;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.tommy.procrastinationtimer.R;
import com.tommy.procrastinationtimer.adapters.RecyclerAdapter;
import com.tommy.procrastinationtimer.models.Task;
import com.tommy.procrastinationtimer.ui.tasks.CreateNewTaskActivity;
import com.tommy.procrastinationtimer.viewmodels.MainActivityViewModel;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static final String EXTRA_TASK_TITLE = "com.tommy.procrastinationtimer.task.TITLE";
    public static final String EXTRA_TASK_TIME = "com.tommy.procrastinationtimer.task.TIME";
    private static final int LAUNCH_NEW_ACTIVITY_CODE = 1;
    private Toolbar toolbar;
    private FloatingActionButton fab;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private ProgressBar progressBar;
    private MainActivityViewModel mainActivityViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = findViewById(R.id.toolbar);
        fab = findViewById(R.id.fab);
        recyclerView = findViewById(R.id.recycler_view_for_task);
        progressBar = findViewById(R.id.progress_bar);

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
            if (data != null && data.hasExtra(EXTRA_TASK_TITLE) && data.hasExtra(EXTRA_TASK_TIME)) {
                Task task = new Task(data.getExtras().getString(EXTRA_TASK_TITLE), data.getExtras().getLong(EXTRA_TASK_TIME));
                mainActivityViewModel.addTask(task);
            }
        }
    }

    private void initRecyclerView() {
        RecyclerView.LayoutManager linearLayoutManager = new LinearLayoutManager(this);
        adapter = new RecyclerAdapter(this, mainActivityViewModel.getTaskList().getValue());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
