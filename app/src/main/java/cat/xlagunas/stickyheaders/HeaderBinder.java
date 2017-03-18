package cat.xlagunas.stickyheaders;

import android.view.View;

/**
 * Created by xlagunas on 17/03/17.
 */

public interface HeaderBinder<T>  {

    void onBindHeader(View header, T element);
}
