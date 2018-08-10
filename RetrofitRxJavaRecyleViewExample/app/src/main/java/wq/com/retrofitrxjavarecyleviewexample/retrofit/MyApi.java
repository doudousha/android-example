package wq.com.retrofitrxjavarecyleviewexample.retrofit;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import wq.com.retrofitrxjavarecyleviewexample.bean.Post;

/**
 * Created by dou_d on 2018/8/9.
 */

public interface MyApi {

    @GET("posts")
    Observable<List<Post>> getPosts();

    @GET("posts")
    Observable<List<Post>> getPagePosts(@Query("_page") int page,@Query("_limit")int limit );

}
