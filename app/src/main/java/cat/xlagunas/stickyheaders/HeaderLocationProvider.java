package cat.xlagunas.stickyheaders;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by xlagunas on 18/3/17.
 */

public class HeaderLocationProvider {
    final TaskAdapter adapter;
    final RecyclerView recyclerView;

    public HeaderLocationProvider(RecyclerView recyclerView) {
        this.recyclerView = recyclerView;
        this.adapter = (TaskAdapter) recyclerView.getAdapter();
    }

    public float computeHeightForHeader(HeaderHelper helper) {
        View itemView = recyclerView.findViewHolderForAdapterPosition(helper.firstVisiblePosition()).itemView;

        if (helper.firstVisiblePosition() == helper.lastVisiblePosition() && isNotCompletelyVisible(itemView)) {
            return itemView.getBottom() - helper.headerView().getMeasuredHeight();
        }

        if (isFirstGroupElement(helper.firstVisiblePosition())) {
            final int height = helper.headerView().getMeasuredHeight();
            final int top = itemView.getTop() - height;
            return top;
        } else {
            return 0;
        }
    }

    private boolean isNotCompletelyVisible(View firstView) {
        return !isFirstCompleteViewVisible(firstView);
    }

    private boolean isFirstGroupElement(int currentPosition) {
        if (currentPosition == 0) {
            return true;
        } else if (currentPosition == RecyclerView.NO_POSITION) {
            return false;
        } else {
            return !adapter.getItem(currentPosition - 1).getGroupKey().equals(adapter.getItem(currentPosition).getGroupKey());
        }
    }

    private boolean isFirstCompleteViewVisible(View itemView) {
        return ((LinearLayoutManager) recyclerView.getLayoutManager()).findFirstCompletelyVisibleItemPosition() == recyclerView.getChildAdapterPosition(itemView);
    }
}

