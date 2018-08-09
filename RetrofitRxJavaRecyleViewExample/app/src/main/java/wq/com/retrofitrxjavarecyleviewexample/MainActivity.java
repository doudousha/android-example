package wq.com.retrofitrxjavarecyleviewexample;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import wq.com.retrofitrxjavarecyleviewexample.adapter.PostAdapter;
import wq.com.retrofitrxjavarecyleviewexample.bean.Post;
import wq.com.retrofitrxjavarecyleviewexample.retrofit.MyApi;
import wq.com.retrofitrxjavarecyleviewexample.retrofit.RetrofitClient;

public class MainActivity extends AppCompatActivity {

    private MyApi api;
    private RecyclerView recyclerView;
    private CompositeDisposable compositeDisposable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        compositeDisposable = new CompositeDisposable();

        // view
        recyclerView = (RecyclerView) findViewById(R.id.recycler_post);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // init retrofit api
        Retrofit retrofit = RetrofitClient.getInstance();
        api = retrofit.create(MyApi.class);

        fetchData();
    }

    private void fetchData() {
        compositeDisposable.add(api.getPosts()
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(new Consumer<List<Post>>() {
            @Override
            public void accept(List<Post> posts) throws Exception {
               displayData(posts);
            }
        }));
    }

    private void displayData(List<Post> posts) {
        PostAdapter adapter = new PostAdapter(posts);
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void onStop() {
        compositeDisposable.dispose();
        super.onStop();
    }
}
