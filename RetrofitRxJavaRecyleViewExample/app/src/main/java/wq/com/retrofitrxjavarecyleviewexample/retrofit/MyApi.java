package wq.com.retrofitrxjavarecyleviewexample.retrofit;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;
import wq.com.retrofitrxjavarecyleviewexample.bean.Post;

/**
 * Created by dou_d on 2018/8/9.
 */

public interface MyApi {

    @GET("posts")
    Observable<List<Post>> getPosts();
}
