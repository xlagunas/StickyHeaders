package network;

import java.util.List;

import io.reactivex.Observable;
import model.Task;
import retrofit2.http.GET;

/**
 * Created by xlagunas on 17/03/17.
 */

public interface RestAPI {

    @GET("/todos")
    Observable<List<Task>> getTasks();
}
