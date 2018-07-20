package wq.com.carttest.http;

import android.content.Context;
import android.content.Intent;



import java.io.IOException;

import okhttp3.Request;
import okhttp3.Response;
import wq.com.carttest.R;
import wq.com.carttest.utils.ToastUtils;

/**
 * 简单的Callback封装
 * @param <T>
 */
public abstract class SimpleCallback<T> extends BaseCallBack<T> {

    protected Context mContext;

    public SimpleCallback(Context context) {
        this.mContext = context;
    }

    @Override
    public void onRequestBefore(Request request) {

    }

    @Override
    public void onFailure(Request request, IOException e) {

    }

    @Override
    public void onResponse(Response response) {

    }


    @Override
    public void onTokenError(Response response, int code) {
        ToastUtils.show(mContext, mContext.getString(R.string.token_error));
        // TODO 暂时去掉token
//        Intent intent = new Intent(mContext, LoginActivity.class);
//        mContext.startActivity(intent);
//
//        //清空本地用户数据
//        MyApplication.getInstance().clearUser();

        System.out.println("token-------"+code);

    }
}
