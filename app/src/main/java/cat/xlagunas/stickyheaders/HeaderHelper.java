package cat.xlagunas.stickyheaders;

import android.view.View;

import com.google.auto.value.AutoValue;

/**
 * Created by xlagunas on 19/3/17.
 */

@AutoValue
public abstract class HeaderHelper {
    public abstract View headerView();

    public abstract int firstVisiblePosition();

    public abstract int lastVisiblePosition();

    public static HeaderHelper.Builder builder() {
        return new AutoValue_HeaderHelper.Builder();
    }

    @AutoValue.Builder
    public abstract static class Builder {

        public abstract Builder setHeaderView(View header);

        public abstract Builder setFirstVisiblePosition(int position);

        public abstract Builder setLastVisiblePosition(int position);

        public abstract HeaderHelper build();
    }

}
