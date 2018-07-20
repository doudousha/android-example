package wq.com.carttest;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import butterknife.BindView;
import butterknife.ButterKnife;
import wq.com.carttest.widget.Constants;

public class MainActivity extends AppCompatActivity {


    @BindView(R.id.btn_addAddress)
    public Button btnAddressAdd;

    @BindView(R.id.btn_changeAddress)
    public Button btnAddressChange;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        btnAddressAdd.setOnClickListener((view) -> {
            Intent intent = new Intent(this, AddressAddOrChangeActivity.class);
            intent.putExtra("tag", Constants.TAG_SAVE);
            startActivityForResult(intent, Constants.ADDRESS_ADD);
        });


        btnAddressChange.setOnClickListener((view) -> {
            Intent intent = new Intent(this, AddressAddOrChangeActivity.class);
            intent.putExtra("tag", Constants.TAG_COMPLETE);
            startActivityForResult(intent, Constants.ADDRESS_EDIT);
        });

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d("----------------------", "main - onActivityResult:"  + requestCode +":"+resultCode);

    }
}
