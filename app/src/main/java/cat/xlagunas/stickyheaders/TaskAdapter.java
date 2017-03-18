package cat.xlagunas.stickyheaders;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.Collections;
import java.util.List;

import model.Task;
import viewholder.TaskViewHolder;

/**
 * Created by xlagunas on 18/3/17.
 */

public class TaskAdapter extends RecyclerView.Adapter<TaskViewHolder> {
    List<Task> tasks = Collections.emptyList();

    public TaskAdapter() {

    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
        notifyDataSetChanged();
    }

    @Override
    public TaskViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new TaskViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.task_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(TaskViewHolder holder, int position) {
        holder.onBind(tasks.get(position));
    }

    @Override
    public int getItemCount() {
        return tasks.size();
    }

    public Task getItem(int position) {
        return tasks.get(position);
    }
}
