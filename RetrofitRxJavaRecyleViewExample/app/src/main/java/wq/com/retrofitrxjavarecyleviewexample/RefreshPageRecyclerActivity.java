package wq.com.retrofitrxjavarecyleviewexample;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.widget.TextView;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.http.POST;
import wq.com.retrofitrxjavarecyleviewexample.adapter.PostAdapter;
import wq.com.retrofitrxjavarecyleviewexample.bean.Post;
import wq.com.retrofitrxjavarecyleviewexample.retrofit.MyApi;
import wq.com.retrofitrxjavarecyleviewexample.retrofit.RetrofitClient;
import wq.com.retrofitrxjavarecyleviewexample.weight.EmptyRecylerView;

public class RefreshPageRecyclerActivity extends AppCompatActivity {

    private int page = 1;
    private CompositeDisposable compositeDisposable;
    private MyApi api;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_refresh_page_recycler);


        TextView emptyView = (TextView) findViewById(R.id.empty_view);
        EmptyRecylerView recylerView = (EmptyRecylerView) findViewById(R.id.recycler_pager);
        recylerView.setLayoutManager(new LinearLayoutManager(this));
        recylerView.setEmptyView(emptyView);

        List<Post> posts = new ArrayList<>();
        PostAdapter adapter = new PostAdapter(posts);
        recylerView.setAdapter(adapter);


        compositeDisposable = new CompositeDisposable();
        api = RetrofitClient.getInstance().create(MyApi.class);


        SmartRefreshLayout smartRefreshLayout = (SmartRefreshLayout) findViewById(R.id.smart_refresh);
        smartRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshLayout) {
                compositeDisposable.add(api.getPagePosts(page, 1)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Consumer<List<Post>>() {
                            @Override
                            public void accept(List<Post> posts) throws Exception {
                                adapter.refresh(posts);
                                page = 1;
                                smartRefreshLayout.finishRefresh();
                            }
                        }));
            }
        });

        smartRefreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshLayout) {
                compositeDisposable.add(api.getPagePosts(page, 1)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Consumer<List<Post>>() {
                            @Override
                            public void accept(List<Post> posts) throws Exception {
                                adapter.add(posts);
                                page += 1;
                                smartRefreshLayout.finishLoadMore();
                            }
                        }));
            }
        });

    }


    @Override
    protected void onStop() {
        compositeDisposable.dispose();
        super.onStop();
    }
}
