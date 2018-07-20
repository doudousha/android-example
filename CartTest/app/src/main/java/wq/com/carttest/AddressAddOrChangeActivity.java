package wq.com.carttest;

import android.content.Intent;
import android.location.Address;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Response;
import wq.com.carttest.http.OkHttpHelper;
import wq.com.carttest.http.SpotsCallBack;
import wq.com.carttest.msg.BaseResMsg;
import wq.com.carttest.widget.CnToolbar;
import wq.com.carttest.widget.Constants;

/**
 * Created by dou_d on 2018/7/19.
 */

public class AddressAddOrChangeActivity extends AppCompatActivity {


    @BindView(R.id.toolbar)
    public CnToolbar toolbar;


    @BindView(R.id.et_consignee)
    private EditText mEtConsignee;

    @BindView(R.id.et_phone)
    private EditText mEtPhone;

    @BindView(R.id.et_add_des)
    private EditText mEtAddDes;

    @BindView(R.id.tv_address)
    private TextView mTvAddress;


    private OkHttpHelper okHttpHelper = OkHttpHelper.getInstance();


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address_add);
        ButterKnife.bind(this);
        initToolbar();
    }


    private void initToolbar() {

        int tag = getIntent().getIntExtra("tag", -1);

        if (tag == Constants.TAG_SAVE) {
            toolbar.getRightButton().setText("保存地址");
            toolbar.setTitle("新增地址");
        } else if (Constants.TAG_COMPLETE == tag) {
            toolbar.getRightButton().setText("完成");
            toolbar.setTitle("修改地址");
        }

        // 绑定事件
        toolbar.setRightButtonOnClickListener(view -> {
            if (tag == Constants.TAG_SAVE) {
                createAddress();
            } else if (tag == Constants.TAG_COMPLETE) {
                Address address = (Address) getIntent().getExtras().getSerializable("addressBean");
                changeAddress(address);
            }
            setResult(RESULT_OK);
            finish();
        });

    }

    private void changeAddress(Address address) {


    }

    private void createAddress() {

        // 获取表单值

        String consignee = mEtConsignee.getText().toString();
        String phone = mEtPhone.getText().toString();
        String address = mTvAddress.getText().toString();
        String userId = "0";
        // 检查表单合法状态

        if (checkPhone(phone) == false) {

            return;
        }

        // 将表达之转换为params
        Map<String, String> params = new HashMap<>(1);
        params.put("user_id", userId);
        params.put("consignee", consignee);
        params.put("phone", phone);
        params.put("addr", address);
        params.put("zip_code", "000000");

        // 提交表单
        okHttpHelper.doPost(Constants.API.ADDR_CREATE, params, new SpotsCallBack<BaseResMsg>(this) {
            @Override
            public void onSuccess(Response response, BaseResMsg baseResMsg) {

            }

            @Override
            public void onError(Response response, int code, Exception e) {

            }
        });

    }

    public boolean checkPhone(String phone) {
        return false;
    }


}
