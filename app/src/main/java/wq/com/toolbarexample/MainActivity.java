package wq.com.toolbarexample;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends BaseActivity {


    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void init() {

    }


    @Override
    public void setToolbar() {
        getToolbar().setTitle("我的地址");
        getToolbar().setleftButtonIcon(R.drawable.icon_back_32px);
        getToolbar().setRightImgButtonIcon(R.drawable.icon_add_w);
        getToolbar().setRightButtonOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(MainActivity.this, "hello world", Toast.LENGTH_SHORT).show();
            }
        });

        getToolbar().inflateMenu(R.menu.toolbar);
    }
}
