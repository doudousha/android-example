package wq.com.commondialog;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;

import butterknife.BindView;
import butterknife.ButterKnife;
import wq.com.commondialog.widget.CommonDialog;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.button)
    Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);


        btn.setOnClickListener(view->{

            showDialog();
        });


    }


    public void showDialog() {
        new CommonDialog(MainActivity.this).builder().setTitle("提示")
                .setPositiveBtn("确定", null)
                .setContentMsg("您尚未在铜板街完成首次交易，无法兑换").show();
    }
}
