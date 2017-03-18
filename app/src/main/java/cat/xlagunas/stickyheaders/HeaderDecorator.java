package cat.xlagunas.stickyheaders;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import model.Task;

public class HeaderDecorator<T> extends RecyclerView.ItemDecoration {

    private final HeaderViewProvider headerViewProvider;

    public HeaderDecorator(HeaderViewProvider headerViewProvider) {
        this.headerViewProvider = headerViewProvider;
    }

    @Override
    public void onDrawOver(Canvas canvas, RecyclerView parent, RecyclerView.State state) {

        for (int i = 0; i < parent.getChildCount(); i++) {
            View view = parent.getChildAt(i);
            if (shouldDecorateView(view, parent)) {
                canvas.save();
                View headerView = getHeaderForView(view, parent);
                headerView.layout(parent.getLeft(), 0, parent.getRight(), headerView.getMeasuredHeight());
                final int height = headerView.getMeasuredHeight();
                final int top = view.getTop() - height;
                canvas.translate(0, top);
                headerView.draw(canvas);
                canvas.restore();
            }
        }
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        if (shouldDecorateView(view, parent)) {
            View headerView = getHeaderForView(view, parent);
            outRect.top = headerView.getMeasuredHeight();
        }
    }

    private boolean shouldDecorateView(View view, RecyclerView parent) {
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

}
