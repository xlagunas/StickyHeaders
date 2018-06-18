package cat.xlagunas.stickyheaders;

import android.view.View;

public interface HeaderBinder<T>  {

    void onBindHeader(View header, T element);
}
