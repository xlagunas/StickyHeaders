package cat.xlagunas.stickyheaders;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import model.Task;
import network.MyAdapterFactory;
import network.RestAPI;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private RestAPI service;
    RecyclerView recyclerView;
    TaskAdapter taskAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = (RecyclerView) findViewById(R.id.recycler);
        taskAdapter = new TaskAdapter();

        HeaderViewProvider<Task> provider = new HeaderViewProvider.Builder<Task>()
                .withRecyclerView(recyclerView)
                .withHeaderResource(R.layout.header_layout)
                .withHeaderBinder(new HeaderBinder<Integer>() {
                    @Override
                    public void onBindHeader(View header, Integer element) {
                        ((TextView) header).setText("User " + element);
                    }
                }).build();


        recyclerView.setAdapter(taskAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new HeaderDecorator<Task>(provider, new HeaderLocationProvider(recyclerView)));
        initRetrofit();
    }

    @Override
    protected void onResume() {
        super.onResume();
        service.getTasks()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Consumer<List<Task>>() {
                    @Override
                    public void accept(List<Task> tasks) throws Exception {
                        taskAdapter.setTasks(tasks);
                    }
                });
    }

    private void initRetrofit() {
        Gson gson = new GsonBuilder()
                .registerTypeAdapterFactory(MyAdapterFactory.create())
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://jsonplaceholder.typicode.com")
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        service = retrofit.create(RestAPI.class);
    }
}
