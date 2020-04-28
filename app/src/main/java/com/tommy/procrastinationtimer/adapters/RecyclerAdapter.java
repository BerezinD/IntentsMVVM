package com.tommy.procrastinationtimer.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.tommy.procrastinationtimer.R;
import com.tommy.procrastinationtimer.models.Task;

import java.util.List;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {

    private List<Task> taskList;
    private Context context;

    public RecyclerAdapter(Context context, List<Task> taskList) {
        this.taskList = taskList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.task_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        holder.taskTitle.setText(taskList.get(position).getTitle());
        holder.taskTime.setText(String.valueOf(taskList.get(position).getTime()));
        holder.taskButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "Here button number " + position, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return taskList.size();
    }

    public void setTaskList(List<Task> tasks) {
        this.taskList = tasks;
        notifyDataSetChanged();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView taskTitle;
        TextView taskTime;
        Button taskButton;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            taskTitle = itemView.findViewById(R.id.task_title);
            taskTime = itemView.findViewById(R.id.task_time);
            taskButton = itemView.findViewById(R.id.task_button);
        }
    }
}
