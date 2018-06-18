package cat.xlagunas.stickyheaders;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import model.Task;

public class HeaderDecorator<T> extends RecyclerView.ItemDecoration {

    private final HeaderViewProvider headerViewProvider;
    private final HeaderLocationProvider headerLocationProvider;

    private List<HeaderHelper> headerHelperList = Collections.emptyList();

    public HeaderDecorator(HeaderViewProvider headerViewProvider, HeaderLocationProvider headerLocationProvider) {
        this.headerViewProvider = headerViewProvider;
        this.headerLocationProvider = headerLocationProvider;
    }

    @Override
    public void onDrawOver(Canvas canvas, RecyclerView parent, RecyclerView.State state) {
        if (parent.getAdapter().getItemCount() > 0) {
            headerHelperList = getVisibleHeaders(parent);

            for (HeaderHelper helper : headerHelperList) {
                helper.headerView().layout(parent.getLeft(), 0, parent.getRight(), helper.headerView().getMeasuredHeight());
                float offsetTop = headerLocationProvider.computeHeightForHeader(helper);
                canvas.save();
                canvas.translate(0, offsetTop);
                helper.headerView().draw(canvas);
                canvas.restore();
            }
        }
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        if (shouldAddTopMargin(view, parent)) {
            View headerView = getHeaderForView(view, parent);
            outRect.top = headerView.getMeasuredHeight();
        }
    }

    private boolean shouldAddTopMargin(View view, RecyclerView parent) {
        int position = parent.getChildAdapterPosition(view);
        TaskAdapter adapter = (TaskAdapter) parent.getAdapter();
        if (adapter.getItemCount() > 1) {
            if (position == 0
                    || (position != adapter.getItemCount() - 1
                    && !adapter.getItem(position).getGroupKey().equals(adapter.getItem(position - 1).getGroupKey()))) {
                return true;
            }
        }

        return false;
    }

    private View getHeaderForView(View view, RecyclerView parent) {
        int adapterPosition = parent.getChildAdapterPosition(view);
        Task task = ((TaskAdapter) parent.getAdapter()).getItem(adapterPosition);
        return headerViewProvider.getHeader(task.getGroupKey());
    }

    private List<HeaderHelper> getVisibleHeaders(RecyclerView recyclerView) {
        LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
        TaskAdapter adapter = (TaskAdapter) recyclerView.getAdapter();
        List<HeaderHelper> helperList = new ArrayList<>();

        int initialVisiblePos = layoutManager.findFirstVisibleItemPosition();
        int finalVisiblePos = layoutManager.findLastVisibleItemPosition();
        List<Task> visibleTasksList = adapter.getTasksRange(initialVisiblePos, finalVisiblePos);

        int endingPos = 0;
        int startingPos = 0;
        while (endingPos < visibleTasksList.size() - 1) {
            endingPos = locateEndOfGroup(visibleTasksList, startingPos);

            helperList.add(HeaderHelper.builder()
                    .setHeaderView(headerViewProvider.getHeader(visibleTasksList.get(startingPos).getGroupKey()))
                    .setFirstVisiblePosition(startingPos + initialVisiblePos)
                    .setLastVisiblePosition(endingPos + initialVisiblePos)
                    .build());

            startingPos = endingPos + 1;
        }

        return helperList;
    }

    private static int locateEndOfGroup(List<Task> tasks, int startLookupPosition) {
        for (int i = startLookupPosition; i < tasks.size() - 1; i++) {
            if (!tasks.get(i).getGroupKey().equals(tasks.get(i + 1).getGroupKey())) {
                return i;
            }
        }
        return tasks.size() - 1;
    }

}
