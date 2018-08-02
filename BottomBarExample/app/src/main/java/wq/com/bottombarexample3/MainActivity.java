package wq.com.bottombarexample3;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import java.lang.reflect.Method;

import butterknife.BindView;
import butterknife.ButterKnife;
import wq.com.bottombarexample3.fragment.HomeFragment;
import wq.com.bottombarexample3.fragment.MoreFragment;
import wq.com.bottombarexample3.fragment.MyAssertsFragment;
import wq.com.bottombarexample3.fragment.ProductFragment;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @BindView(R.id.layout_home)
    LinearLayout layoutHome;
    @BindView(R.id.layout_product)
    LinearLayout layoutProduct;
    @BindView(R.id.layout_myAsserts)
    LinearLayout layoutMyAsserts;
    @BindView(R.id.layout_more)
    LinearLayout layoutMore;

    private static final String FRAGMENT_TAB_HOME = "fragment_home";
    private static final String FRAGMENT_TAB_PRODUCT = "frament_home";
    private static final String FRAGMENT_TAB_MYASSERTS = "fragment_myasserts";
    private static final String FRAGMENT_TAB_MORE = "fragment_more";



    private LinearLayout[] linearLayouts;
    private String[] fragmentTags = new String[]{FRAGMENT_TAB_HOME, FRAGMENT_TAB_PRODUCT, FRAGMENT_TAB_MYASSERTS, FRAGMENT_TAB_MORE};
    private Fragment homeFragment;
    private Fragment moreFragment;
    private Fragment myAssertFragment;
    private Fragment productFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        initData();
        setListner();
        setDefaultMenuItemSelected(FRAGMENT_TAB_HOME);
    }

    private void setDefaultMenuItemSelected(String tabTag) {

        if (tabTag == FRAGMENT_TAB_HOME) {
            layoutHome.performClick();
        } else if (tabTag == FRAGMENT_TAB_PRODUCT) {
            layoutProduct.performClick();
        } else if (tabTag == FRAGMENT_TAB_MYASSERTS) {
            layoutMyAsserts.performClick();
        } else if (tabTag == FRAGMENT_TAB_MORE) {
            layoutMore.performClick();
        }
    }

    private void setListner() {
        for (LinearLayout linearLayout : linearLayouts) {
            linearLayout.setOnClickListener(this);
        }

    }

    private void initData() {
        linearLayouts = new LinearLayout[]{layoutHome, layoutProduct, layoutMyAsserts, layoutMore};
    }


    @Override
    public void onClick(View view) {
        onTabClick(view);
    }

    private void onTabClick(View view) {
        // 隐藏全部fragment
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        hideFraments(fragmentManager, fragmentTransaction);

        // 设置所有菜单选中项
        for (LinearLayout linearLayout : linearLayouts) {
            linearLayout.setSelected(false);
        }
        view.setSelected(true);


        int id = view.getId();
        if (id == R.id.layout_home) {
            selectedFragment(homeFragment, HomeFragment.class, fragmentTransaction, FRAGMENT_TAB_HOME);
        } else if (id == R.id.layout_more) {
            selectedFragment(moreFragment, MoreFragment.class, fragmentTransaction, FRAGMENT_TAB_MORE);
        } else if (id == R.id.layout_myAsserts) {
            selectedFragment(myAssertFragment, MyAssertsFragment.class, fragmentTransaction, FRAGMENT_TAB_MYASSERTS);
        } else if (id == R.id.layout_product) {
            selectedFragment(productFragment, ProductFragment.class, fragmentTransaction, FRAGMENT_TAB_PRODUCT);
        }

        fragmentTransaction.commit();
    }

    private void selectedFragment(Fragment fragment, Class<?> clazz
            , FragmentTransaction fragmentTransaction, String tag) {

        if (fragment == null) {

            try {
                Method method = clazz.getDeclaredMethod("newInstance");
                fragment = (Fragment) method.invoke(null);
                fragmentTransaction.add(R.id.fragment_container,fragment,tag); // 添加到队列和fragment 容器
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        fragmentTransaction.show(fragment);
    }

    public void hideFraments(FragmentManager fragmentManager, FragmentTransaction fragmentTransaction) {
        for (String fragmentTag : fragmentTags) {
            Fragment fragment = fragmentManager.findFragmentByTag(fragmentTag);
            if (fragment != null && fragment.isVisible()) { // fragment 不为空，并且是可见的
                fragmentTransaction.hide(fragment);
            }
        }
    }
}
