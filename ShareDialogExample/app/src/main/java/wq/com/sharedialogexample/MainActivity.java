package wq.com.sharedialogexample;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;

import wq.com.sharedialogexample.dialog.DeviceDialogFragment;
import wq.com.sharedialogexample.dialog.ShareDialogFragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnShare =  (Button)findViewById(R.id.btn_share);
        btnShare.setOnClickListener(view->{

            ShareDialogFragment shareDialogFragment = ShareDialogFragment.newInstance(MainActivity.this, null, 0);
            shareDialogFragment.show(getSupportFragmentManager(), null);
        });

        Button btn_removeDevice =  (Button)findViewById(R.id.btn_removeDevice);
        btn_removeDevice.setOnClickListener(view->{
            DeviceDialogFragment deviceDialogFragment = DeviceDialogFragment.newInstance(this, null, 0);
            deviceDialogFragment.show(getSupportFragmentManager(), null);
        });
    }
}
