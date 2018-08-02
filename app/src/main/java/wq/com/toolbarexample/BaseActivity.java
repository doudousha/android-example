package wq.com.toolbarexample;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import wq.com.toolbarexample.widget.CnToolbar;


/**
 * BaseActivity封装
 */
public abstract class BaseActivity extends AppCompatActivity {

    private void initToolbar() {
        if (getToolbar() != null){
            setToolbar();
            getToolbar().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });
        }
    }

    public CnToolbar getToolbar() {
        return (CnToolbar)findViewById(R.id.toolbar);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());

        initToolbar();
        init();
    }

    public abstract int getLayoutId();

    public abstract void init();

    public abstract void setToolbar();

}
