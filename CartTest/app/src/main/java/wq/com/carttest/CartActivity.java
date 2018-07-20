package wq.com.carttest;

/**
 * Created by dou_d on 2018/7/15.
 */

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import java.io.Serializable;
import java.util.List;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;



import java.io.Serializable;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import wq.com.carttest.adpater.CartAdapter;
import wq.com.carttest.adpater.decoration.DividerItemDecortion;
import wq.com.carttest.beans.ShoppingCart;
import wq.com.carttest.utils.CartProvider;
import wq.com.carttest.utils.ToastUtils;
import wq.com.carttest.widget.CnToolbar;

/**
 * 购物车
 * 添加商品到购物车，CartProvider获取购物车数据，并显示总价，选中的商品可进行购买跳到结算页面
 * 购物车为空则不能购买
 */
public class CartActivity  extends AppCompatActivity  implements View.OnClickListener {

    @BindView(R.id.toolbar_search_view)
    public CnToolbar mToolbar;

    
    @BindView(R.id.recyclerview_cart)
    public RecyclerView mRecyclerView;

    @BindView(R.id.checkbox_all)
    public CheckBox mCheckBox;

    @BindView(R.id.tv_total)
    public TextView mTvCount;

    @BindView(R.id.btn_order)
    public Button mBtnOrder;

    @BindView(R.id.btn_del)
    public Button mBtnDelete;

    private static final int ACTION_EDIT = 1;
    private static final int ACTION_CAMPLATE = 2;

    private CartProvider mCartProvider;

    private CartAdapter mAdapter;

        //    private OkHttpHelper okHttpHelper = OkHttpHelper.getInstance();
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.cart);
            ButterKnife.bind(this);
            mCartProvider = CartProvider.getInstance(this);
            showData();
            setToolbar();
        }


    public void setToolbar() {
        mToolbar.setRightButtonText(R.string.edit);

        mToolbar.getRightButton().setOnClickListener(this);

        mToolbar.getRightButton().setTag(ACTION_EDIT);
    }


    /**
     * 显示购物车数据
     */
    private void showData() {


        mCartProvider.clearAll();

        ShoppingCart cart1 = new ShoppingCart();
        cart1.setId(1L);
        cart1.setPrice(10f);
        cart1.setCount(10);
        cart1.setImgUrl("https://ss0.baidu.com/6ONWsjip0QIZ8tyhnq/it/u=1712881107,328142245&fm=58");
        mCartProvider.put(cart1);

        ShoppingCart cart2 = new ShoppingCart();
        cart2.setId(2L);
        cart2.setPrice(12f);
        cart2.setCount(7);
        cart2.setImgUrl("https://ss0.baidu.com/6ONWsjip0QIZ8tyhnq/it/u=1712881107,328142245&fm=58");
        mCartProvider.put(cart2);


        ShoppingCart cart3 = new ShoppingCart();
        cart3.setImgUrl("https://ss0.baidu.com/6ONWsjip0QIZ8tyhnq/it/u=1712881107,328142245&fm=58");
        cart3.setId(3L);
        cart3.setPrice(20f);
        cart3.setCount(2);
        mCartProvider.put(cart3);

        ShoppingCart cart4 = new ShoppingCart();
        cart4.setImgUrl("https://ss0.baidu.com/6ONWsjip0QIZ8tyhnq/it/u=1712881107,328142245&fm=58");
        cart4.setId(4L);
        cart4.setCount(12);
        cart4.setPrice(14f);
        mCartProvider.put(cart4);

        List<ShoppingCart> carts = mCartProvider.getAll();
        mAdapter = new CartAdapter(this, carts, mCheckBox, mTvCount);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.addItemDecoration(new DividerItemDecortion(this, DividerItemDecortion.VERTICAL_LIST));

    }

    /**
     * 刷新数据
     */
    public void refreshData() {
        mAdapter.clearData();
        List<ShoppingCart> carts = mCartProvider.getAll();
        mAdapter.addData(carts);

        mAdapter.showTotalPrice();
    }


    @Override
    public void onClick(View v) {
        //编辑
        int action = (int) v.getTag();
        if (ACTION_EDIT == action) {
            showDelControl();

        } else if (ACTION_CAMPLATE == action) {//完成
            hideDelControl();
        }

        if (v.getId() == R.id.btn_order) {
            List<ShoppingCart> carts = mAdapter.getCheckData();
            if (carts.size() != 0 && carts != null) {
              // TODO 屏蔽跳转
                //  startActivity(new Intent(this, NewOrderActivity.class));
            } else {
                ToastUtils.show(this, "请选择要购买的商品");
            }
        }

    }

    /**
     * 隐藏删除按钮
     */
    private void hideDelControl() {
        mToolbar.getRightButton().setText("编辑");
        mTvCount.setVisibility(View.VISIBLE);
        mBtnOrder.setVisibility(View.VISIBLE);

        mBtnDelete.setVisibility(View.GONE);
        //设置为编辑
        mToolbar.getRightButton().setTag(ACTION_EDIT);
        mAdapter.checkAll_None(true);
        mCheckBox.setChecked(true);
        mAdapter.showTotalPrice();
    }

    /**
     * 显示删除按钮
     */
    private void showDelControl() {
        mToolbar.getRightButton().setText("完成");
        mTvCount.setVisibility(View.GONE);
        mBtnOrder.setVisibility(View.GONE);
        mBtnDelete.setVisibility(View.VISIBLE);
        //设置为完成
        mToolbar.getRightButton().setTag(ACTION_CAMPLATE);

        mAdapter.checkAll_None(false);
        mCheckBox.setChecked(false);
    }

    @OnClick(R.id.btn_del)
    public void delCart(View v) {
        mAdapter.delCart();
    }


    /**
     * 结算按钮点击事件
     * @param v
     */
    @OnClick(R.id.btn_order)
    public void toOrder(View v) {

        if (mAdapter.getCheckData() != null && mAdapter.getCheckData().size() > 0) {
//
//            okHttpHelper.doGet(Constants.API.USER_DETAIL, new SpotsCallBack<User>(getContext()) {
//
//                @Override
//                public void onSuccess(Response response, User user) {
//                    System.out.println("onSuccess------------------" + response.code());
//
//                    Intent intent = new Intent(getActivity(), NewOrderActivity.class);
//                    intent.putExtra("carts", (Serializable) mAdapter.getCheckData());
//                    intent.putExtra("sign", Constants.CART);
//                    startActivity(intent, true);
//
//                }
//
//                @Override
//                public void onError(Response response, int code, Exception e) {
//                    System.out.println("onError------------------" + response.code());
//                }
//            });
        } else {
            ToastUtils.show(this, "请选择要购买的商品");
        }
    }

}
