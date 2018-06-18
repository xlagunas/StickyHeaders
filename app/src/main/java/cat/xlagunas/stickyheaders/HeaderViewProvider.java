package cat.xlagunas.stickyheaders;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by xlagunas on 17/03/17.
 */

class HeaderViewProvider<T> {

    private final RecyclerView recyclerView;
    private final int headerResource;
    private final HeaderBinder headerBinder;

    private final Map<T, View> headersMap = new HashMap<>();

    private HeaderViewProvider(RecyclerView recyclerView, int headerResource, HeaderBinder headerBinder) {
        this.recyclerView = recyclerView;
        this.headerResource = headerResource;
        this.headerBinder = headerBinder;
    }

    public View getHeader(T key) {
        if (headersMap.containsKey(key)) {
            return headersMap.get(key);
        } else {
            View header = createHeaderView(key);
            headersMap.put(key, header);
            return header;
        }
    }

    private View createHeaderView(T key) {
        View view = LayoutInflater.from(recyclerView.getContext()).inflate(headerResource, recyclerView, false);
        headerBinder.onBindHeader(view, key);
        view.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
                View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
        return view;
    }

    static class Builder<T> {
        private RecyclerView recyclerView;
        private int headerResource;
        private HeaderBinder headerBinder;

        public Builder withRecyclerView(RecyclerView recyclerView) {
            this.recyclerView = recyclerView;
            return this;
        }

        public Builder withHeaderResource(int headerResource) {
            this.headerResource = headerResource;
            return this;
        }

        public Builder withHeaderBinder(HeaderBinder<T> headerBinder) {
            this.headerBinder = headerBinder;
            return this;
        }

        public HeaderViewProvider build() {
            return new HeaderViewProvider(recyclerView, headerResource, headerBinder);
        }
    }
}
