package wq.com.dialoginputexample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.InputFilter;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import wq.com.dialoginputexample.widget.InputDialog;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.btn_nick)
    Button btnNick;
    @BindView(R.id.btn_sign)
    Button btnSign;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);


        btnNick.setOnClickListener(view -> {
            inputNickName();
        });

        btnSign.setOnClickListener(view -> {
            inputSign();
            ;
        });
    }


    private void inputNickName() {
        InputDialog inputDialog = new InputDialog(this).builder();
        inputDialog.getContentView().setSingleLine(true);
        inputDialog.setTitle("标题1")
                .setPositiveBtn("确定", new InputDialog.OnPositiveListener() {
                    @Override
                    public void onPositive(View view, String inputMsg) {
                        Toast.makeText(MainActivity.this, "onPositive click", Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeBtn("取消", null)
                .setContentMsg("内容1");
        inputDialog.getContentView().setFilters(new InputFilter[]{new InputFilter.LengthFilter(15)});
        inputDialog.show();
    }

    private void inputSign() {
        InputDialog inputDialog = new InputDialog(this).builder()
                .setTitle("标题 inputSign")
                .setPositiveBtn("确定", new InputDialog.OnPositiveListener() {
                    @Override
                    public void onPositive(View view, String inputMsg) {
                        Toast.makeText(MainActivity.this, "onPositive", Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeBtn("取消", null)
                .setContentMsg("内容 inputSign");
        inputDialog.getContentView().setHint("请输入50字以内的个性签名");
        inputDialog.getContentView().setFilters(new InputFilter[]{new InputFilter.LengthFilter(50)});
        inputDialog.show();
    }
}
