package cat.xlagunas.stickyheaders;

import android.content.Context;
import android.graphics.Canvas;
import android.support.annotation.LayoutRes;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;

public class HeaderDecorator<T> extends RecyclerView.ItemDecoration {

    View header;

    public HeaderDecorator(View header) {
        this.header = header;
    }

    //TODO CHECK IF THAT NULL IS HARMFUL
    public HeaderDecorator(Context context, @LayoutRes int headerLayout){
        new HeaderDecorator(LayoutInflater.from(context).inflate(headerLayout, null));
    }

    @Override
    public void onDraw(Canvas canvas, RecyclerView parent, RecyclerView.State state) {

        for (int i=0; i < parent.getChildCount(); i++) {
            View view = parent.getChildAt(i);
            if (shouldDecorateView(view, parent)) {

            }
        }
    }

    private boolean shouldDecorateView(View view, RecyclerView parent) {
        if (!(parent.getAdapter() instanceof Groupable)) {
            throw new RuntimeException("Adapter must implement Groupable interface");
        }

        int position = parent.getChildAdapterPosition(view);

        return ((Groupable<T>) parent.getAdapter()).getGroupKey(position).equals(((Groupable) parent.getAdapter()).getGroupKey(position -1));
    }
}
