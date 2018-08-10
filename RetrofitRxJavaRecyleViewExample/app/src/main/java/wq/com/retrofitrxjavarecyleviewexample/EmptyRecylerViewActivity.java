package wq.com.retrofitrxjavarecyleviewexample;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import wq.com.retrofitrxjavarecyleviewexample.adapter.PostAdapter;
import wq.com.retrofitrxjavarecyleviewexample.bean.Post;
import wq.com.retrofitrxjavarecyleviewexample.weight.EmptyRecylerView;

public class EmptyRecylerViewActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.empty_recyclerview);

        Button btnEmpty = (Button)findViewById(R.id.btn_empty);
        Button btnHasMore = (Button)findViewById(R.id.btn_hasMore);
        TextView emptyView = (TextView)findViewById(R.id.empty_view);


        EmptyRecylerView recylerView =  (EmptyRecylerView)findViewById(R.id.emptyRecycler_view);
        recylerView.setLayoutManager(new LinearLayoutManager(this));
        recylerView.setEmptyView(emptyView);



        btnEmpty.setOnClickListener(view->{
            List<Post> posts = new ArrayList<>();
            PostAdapter adapter = new PostAdapter(posts);
            recylerView.setAdapter(adapter);
        });

        btnHasMore.setOnClickListener(view->{
            List<Post> posts = new ArrayList<>();
            Post post1 = new Post();
            post1.setTitle("title ");
            post1.setBody("这是内容");
            posts.add(post1);
            PostAdapter adapter = new PostAdapter(posts);
            recylerView.setAdapter(adapter);
        });
    }
}
