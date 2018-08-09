package wq.com.retrofitrxjavarecyleviewexample.retrofit;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by dou_d on 2018/8/9.
 */

public class RetrofitClient {

    private static Retrofit outInstance;

    public static  Retrofit getInstance(){

        if(outInstance == null) {
            outInstance  = new Retrofit.Builder()
                    .baseUrl("http://jsonplaceholder.typicode.com/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build();
        }

        return outInstance;
    }
}
