package network;

import android.database.Observable;

import java.util.List;

import model.Task;

/**
 * Created by xlagunas on 17/03/17.
 */

public interface RestAPI {

    Observable<List<Task>> getTasks();
}
