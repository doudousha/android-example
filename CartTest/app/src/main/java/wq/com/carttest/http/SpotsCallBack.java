package wq.com.carttest.http;

import android.app.AlertDialog;
import android.content.Context;


import java.io.IOException;

import dmax.dialog.SpotsDialog;
import okhttp3.Request;
import okhttp3.Response;


/**
 * 带加载框的Callback封装
 * @param <T>
 */
public abstract class SpotsCallBack<T> extends SimpleCallback<T> {

    private AlertDialog dialog;

    public SpotsCallBack(Context context) {
        super(context);

         dialog = new SpotsDialog.Builder().setContext(context).setMessage("正在努力加载中...").build();
    }

    private void showDialog() {
        dialog.show();
    }

    public void dismissDialog() {
        if (dialog != null)
            dialog.dismiss();
    }

    private void setMessage(String message) {
        dialog.setMessage(message);
    }

    @Override
    public void onRequestBefore(Request request) {
        showDialog();
    }

    @Override
    public void onFailure(Request request, IOException e) {
        dismissDialog();
    }

    @Override
    public void onResponse(Response response) {
        dismissDialog();
    }

}
