package viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Switch;
import android.widget.TextView;

import cat.xlagunas.stickyheaders.R;
import model.Task;

/**
 * Created by xlagunas on 18/3/17.
 */

public class TaskViewHolder extends RecyclerView.ViewHolder {

    private TextView title;
    private Switch status;

    public TaskViewHolder(View itemView) {
        super(itemView);
        title = (TextView) itemView.findViewById(R.id.task_title);
        status = (Switch) itemView.findViewById(R.id.task_completed);
    }

    public void onBind(Task task) {
        this.title.setText(task.title());
        this.status.setChecked(task.completed());
    }
}
