package wq.com.commondialog.widget;

import android.app.Dialog;
import android.content.Context;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import wq.com.commondialog.R;


public class CommonDialog {
    private Context mContext;
    private Dialog mDialog;
    private LinearLayout mLayout;
    private TextView mTitle;
    private TextView mContentMsg;
    private View mDividerLeftLine;
    private View mDividerRightLine;
    private Button mPositiveBtn;
    private Button mMiddleBtn;
    private Button mNegativeBtn;

    private boolean mIsShowTitle = false;
    private boolean mIsShowContentMsg = false;
    private boolean mIsShowPositiveBtn = false;
    private boolean mIsShowMiddleBtn = false;
    private boolean mIsShowNegativeBtn = false;

    public CommonDialog(Context context) {
        this.mContext = context;
    }

    public CommonDialog builder() {
        View view  = LayoutInflater.from(mContext).inflate(R.layout.dialog_tips, null);

        mLayout = (LinearLayout) view.findViewById(R.id.dialog_tips_layout);
        mTitle = (TextView) view.findViewById(R.id.dialog_tips_title);
        mContentMsg = (TextView) view.findViewById(R.id.dialog_tips_content_msg);
        mDividerLeftLine = view.findViewById(R.id.dialog_tips_divider_left_line);
        mDividerRightLine = view.findViewById(R.id.dialog_tips_divider_right_line);
        mPositiveBtn = (Button) view.findViewById(R.id.dialog_tips_positive_btn);
        mMiddleBtn = (Button) view.findViewById(R.id.dialog_tips_middle_btn);
        mNegativeBtn = (Button) view.findViewById(R.id.dialog_tips_negative_btn);

        mTitle.setVisibility(View.GONE);
        mContentMsg.setVisibility(View.GONE);
        mDividerLeftLine.setVisibility(View.GONE);
        mDividerRightLine.setVisibility(View.GONE);
        mPositiveBtn.setVisibility(View.GONE);
        mMiddleBtn.setVisibility(View.GONE);
        mNegativeBtn.setVisibility(View.GONE);

        /*

         在onCreate 方法里面必需调用setContent()方法的时候，实际上activity调用了PhoneWindow的setContent方法，
         然后加载试图，将试图放在window上。 所以Activityg构造初始化的是phoneWindow

         setContent(R.layout.main)  背后的故事:

         1. setContent(R.layout.main)其实就是getWindow().setContentView(LayoutInflater.from(this).inflate(R.layout.main,null))
         2. window 可以被看作一个载体，调用setContentView(R.Layout.main)时,R.layout.main是最底层的。如果通过
         LayoutInflater.inflate()方法加载布局
         */

        // 获取到Activity的实际屏幕信息
        DisplayMetrics dm =new DisplayMetrics();
        WindowManager manager = (WindowManager) this.mContext.getSystemService(Context.WINDOW_SERVICE);
        manager.getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;
        int height = dm.heightPixels ;

        mDialog = new Dialog(mContext, R.style.TipsDialog);
        mDialog.setContentView(view);
        // setLayout(int width, int height)
        mDialog.getWindow().setLayout((int)(dm.widthPixels * 0.8f), ViewGroup.LayoutParams.WRAP_CONTENT);
        mDialog.getWindow().setGravity(Gravity.CENTER);
        /**
         * 1.gravity的默认值为Gravity.CENTER,即Gravity.CENTER_HORIZONTAL | * Gravity.CENTER_VERTICAL.
         * 2.此gravity和android:gravity 不是指的同一个东西
         */

        // 参考:Activity、View、Window的理解一篇文章就够了 https://juejin.im/entry/596329686fb9a06bc903b6fd

        setCancelable(true);
        setCanceledOnTouchOutside(false);


        // window setContentView

        /**
         *
         * 参考:
         * Android应用Activity、Dialog、PopWindow、Toast窗口添加机制及源码分析: https://blog.csdn.net/yanbober/article/details/46361191
         * PolicyManager工厂模式与动态加载: https://www.cnblogs.com/bastard/archive/2012/04/09/2438964.html
         * Activity、Window和View三者间的关系有一定的见解: https://www.cnblogs.com/dubo-/p/6676259.html
         */

        return this;
    }

    /**
     * 设置标题
     * @param title
     * @return
     */
    public CommonDialog setTitle(String title) {
        mIsShowTitle = true;
        if (TextUtils.isEmpty(title)) {
            mTitle.setText("提示");
        } else {
            mTitle.setText(title);
        }
        return this;
    }

    public CommonDialog setTitle(int titleResId) {
        mIsShowTitle = true;
        mTitle.setText(titleResId);
        return this;
    }

    /**
     * 设置内容
     * @param msg
     * @return
     */
    public CommonDialog setContentMsg(String msg) {
        mIsShowContentMsg = true;
        if (TextUtils.isEmpty(msg)) {
            mContentMsg.setText("内容");
        } else {
            mContentMsg.setText(msg);
        }
        return this;
    }

    public CommonDialog setContentMsg(int msgResId) {
        mIsShowContentMsg = true;
        mContentMsg.setText(msgResId);

        return this;
    }

    public CommonDialog setCancelable(boolean cancel) {
        mDialog.setCancelable(cancel);
        return this;
    }

    public CommonDialog setCanceledOnTouchOutside(boolean cancel) {
        mDialog.setCanceledOnTouchOutside(cancel);
        return this;
    }

    /**
     * 设置确定按钮
     * @param text
     * @param onPositiveListener
     * @return
     */
    public CommonDialog setPositiveBtn(String text, final OnPositiveListener onPositiveListener) {
        mIsShowPositiveBtn = true;
        if (TextUtils.isEmpty(text)) {
            mPositiveBtn.setText("确定");
        } else {
            mPositiveBtn.setText(text);
        }
        mPositiveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onPositiveListener != null) {
                    onPositiveListener.onPositive(v);
                }
                mDialog.dismiss();
            }
        });
        return this;
    }

    /**
     * 设置中间按钮
     * @param text
     * @param onMiddleListener
     * @return
     */
    public CommonDialog setMiddleBtn(String text, final OnMiddleListener onMiddleListener) {
        mIsShowMiddleBtn = true;
        if (TextUtils.isEmpty(text)) {
            mMiddleBtn.setText("选择");
        } else {
            mMiddleBtn.setText(text);
        }
        mMiddleBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onMiddleListener != null) {
                    onMiddleListener.onMiddle(v);
                }
                mDialog.dismiss();
            }
        });
        return this;
    }

    /**
     * 设置取消按钮
     * @param text
     * @param onNegativeListener
     * @return
     */
    public CommonDialog setNegativeBtn(String text, final OnNegativeListener onNegativeListener) {
        mIsShowNegativeBtn = true;
        if (TextUtils.isEmpty(text)) {
            mNegativeBtn.setText("取消");
        } else {
            mNegativeBtn.setText(text);
        }
        mNegativeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onNegativeListener != null) {
                    onNegativeListener.onNegative(v);
                }
                mDialog.dismiss();
            }
        });
        return this;
    }

    private void setLayout() {
        // 是否设置了某个子组件
        boolean isSetAnyComponent = false;

        /**
         * 显示标题
         */
        if (mIsShowTitle) {
            isSetAnyComponent = true;
            mTitle.setVisibility(View.VISIBLE);
        }

        /**
         * 显示内容
         */
        if (mIsShowContentMsg) {
            isSetAnyComponent = true;
            mContentMsg.setVisibility(View.VISIBLE);
        }

        /**
         * 显示三个按钮
         */
        if (mIsShowPositiveBtn && mIsShowMiddleBtn && mIsShowNegativeBtn) {
            isSetAnyComponent = true;
            mPositiveBtn.setVisibility(View.VISIBLE);
            mPositiveBtn.setBackgroundResource(R.drawable.dialog_tips_positive_btn_sel);

            mMiddleBtn.setVisibility(View.VISIBLE);
            mMiddleBtn.setBackgroundResource(R.drawable.dialog_tips_middle_btn_sel);

            mNegativeBtn.setVisibility(View.VISIBLE);
            mNegativeBtn.setBackgroundResource(R.drawable.dialog_tips_negative_btn_sel);

            mDividerLeftLine.setVisibility(View.VISIBLE);
            mDividerRightLine.setVisibility(View.VISIBLE);
        }

        /**
         * 显示两个按钮
         */
        if (mIsShowPositiveBtn && !mIsShowMiddleBtn && mIsShowNegativeBtn) {
            isSetAnyComponent = true;
            mPositiveBtn.setVisibility(View.VISIBLE);
            mPositiveBtn.setBackgroundResource(R.drawable.dialog_tips_positive_btn_sel);
            mNegativeBtn.setVisibility(View.VISIBLE);
            mNegativeBtn.setBackgroundResource(R.drawable.dialog_tips_negative_btn_sel);
            mDividerLeftLine.setVisibility(View.VISIBLE);
        }

        /**
         * 显示两个按钮
         */
        if (mIsShowPositiveBtn && mIsShowMiddleBtn && !mIsShowNegativeBtn) {
            isSetAnyComponent = true;
            mPositiveBtn.setVisibility(View.VISIBLE);
            mPositiveBtn.setBackgroundResource(R.drawable.dialog_tips_positive_btn_sel);
            mMiddleBtn.setVisibility(View.VISIBLE);
            mMiddleBtn.setBackgroundResource(R.drawable.dialog_tips_negative_btn_sel);
            mDividerRightLine.setVisibility(View.VISIBLE);
        }

        /**
         * 显示两个按钮
         */
        if (!mIsShowPositiveBtn && mIsShowMiddleBtn && mIsShowNegativeBtn) {
            isSetAnyComponent = true;
            mMiddleBtn.setVisibility(View.VISIBLE);
            mMiddleBtn.setBackgroundResource(R.drawable.dialog_tips_positive_btn_sel);
            mNegativeBtn.setVisibility(View.VISIBLE);
            mNegativeBtn.setBackgroundResource(R.drawable.dialog_tips_negative_btn_sel);
            mDividerLeftLine.setVisibility(View.VISIBLE);
        }


        /**
         * 显示一个按钮
         */
        if (mIsShowPositiveBtn && !mIsShowMiddleBtn && !mIsShowNegativeBtn) {
            isSetAnyComponent = true;
            mPositiveBtn.setVisibility(View.VISIBLE);
            mPositiveBtn.setBackgroundResource(R.drawable.dialog_tips_only_one_btn_sel);
        }

        /**
         * 显示一个按钮
         */
        if (!mIsShowPositiveBtn && mIsShowMiddleBtn && !mIsShowNegativeBtn) {
            isSetAnyComponent = true;
            mMiddleBtn.setVisibility(View.VISIBLE);
            mMiddleBtn.setBackgroundResource(R.drawable.dialog_tips_only_one_btn_sel);
        }

        /**
         * 显示一个按钮
         */
        if (!mIsShowPositiveBtn && !mIsShowMiddleBtn && mIsShowNegativeBtn) {
            isSetAnyComponent = true;
            mNegativeBtn.setVisibility(View.VISIBLE);
            mNegativeBtn.setBackgroundResource(R.drawable.dialog_tips_only_one_btn_sel);
        }

        /**
         * 未设置任何组件的时候，默认一个标题
         */
        if (!isSetAnyComponent) {
            mTitle.setText("未设置任何组件!");
            mTitle.setVisibility(View.VISIBLE);
        }
    }

    public void show() {
        setLayout();
        mDialog.show();
    }

    /** start----------------------------------------- */
    public CommonDialog setTitleText(String text) {
        mIsShowTitle = true;
        if (TextUtils.isEmpty(text)) {
            mTitle.setText("提示");
        } else {
            mTitle.setText(text);
        }
        return this;
    }

    public CommonDialog setTitleText(int textId) {
        mIsShowTitle = true;
        mTitle.setText(textId);
        return this;
    }

    public CommonDialog setTitleTextColor(int colorId) {
        mIsShowTitle = true;
        mTitle.setTextColor(colorId);
        return this;
    }

    public CommonDialog setTitleTextSize(int textSize) {
        mIsShowTitle = true;
        mTitle.setTextSize(textSize);
        return this;
    }
    /** end----------------------------------------- */

    /** start----------------------------------------- */
    public CommonDialog setContentMsgText(String text) {
        mIsShowContentMsg = true;
        if (TextUtils.isEmpty(text)) {
            mContentMsg.setText("取消");
        } else {
            mContentMsg.setText(text);
        }
        return this;
    }

    public CommonDialog setContentMsgText(int textId) {
        mIsShowContentMsg = true;
        mContentMsg.setText(textId);
        return this;
    }

    public CommonDialog setContentMsgTextColor(int colorId) {
        mIsShowContentMsg = true;
        mContentMsg.setTextColor(colorId);
        return this;
    }

    public CommonDialog setContentMsgTextSize(int textSize) {
        mIsShowContentMsg = true;
        mContentMsg.setTextSize(textSize);
        return this;
    }
    /** end----------------------------------------- */

    /** start----------------------------------------- */
    public CommonDialog setNegativeBtnText(String text) {
        mIsShowNegativeBtn = true;
        if (TextUtils.isEmpty(text)) {
            mNegativeBtn.setText("取消");
        } else {
            mNegativeBtn.setText(text);
        }
        return this;
    }

    public CommonDialog setNegativeBtnText(int textId) {
        mIsShowNegativeBtn = true;
        mNegativeBtn.setText(textId);
        return this;
    }

    public CommonDialog setNegativeBtnTextColor(int colorId) {
        mIsShowNegativeBtn = true;
        mNegativeBtn.setTextColor(colorId);
        return this;
    }

    public CommonDialog setNegativeBtnTextSize(int textSize) {
        mIsShowNegativeBtn = true;
        mNegativeBtn.setTextSize(textSize);
        return this;
    }

    public CommonDialog setNegativeBtnListener(final OnNegativeListener onNegativeListener) {
        mIsShowPositiveBtn = true;
        mNegativeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onNegativeListener != null) {
                    onNegativeListener.onNegative(v);
                }
                mDialog.dismiss();
            }
        });
        return this;
    }
    /** end----------------------------------------- */

    /** start----------------------------------------- */
    public CommonDialog setMiddleBtnText(String text) {
        mIsShowMiddleBtn = true;
        if (TextUtils.isEmpty(text)) {
            mMiddleBtn.setText("取消");
        } else {
            mMiddleBtn.setText(text);
        }
        return this;
    }

    public CommonDialog setMiddleBtnText(int textId) {
        mIsShowMiddleBtn = true;
        mMiddleBtn.setText(textId);
        return this;
    }

    public CommonDialog setMiddleBtnTextColor(int colorId) {
        mIsShowMiddleBtn = true;
        mMiddleBtn.setTextColor(colorId);
        return this;
    }

    public CommonDialog setMiddleBtnTextSize(int textSize) {
        mIsShowMiddleBtn = true;
        mMiddleBtn.setTextSize(textSize);
        return this;
    }

    public CommonDialog setMiddleBtnListener(final OnMiddleListener onMiddleListener) {
        mIsShowPositiveBtn = true;
        mMiddleBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onMiddleListener != null) {
                    onMiddleListener.onMiddle(v);
                }
                mDialog.dismiss();
            }
        });
        return this;
    }
    /** end----------------------------------------- */

    /** start----------------------------------------- */
    public CommonDialog setPositiveBtnText(String text) {
        mIsShowPositiveBtn = true;
        if (TextUtils.isEmpty(text)) {
            mPositiveBtn.setText("取消");
        } else {
            mPositiveBtn.setText(text);
        }
        return this;
    }

    public CommonDialog setPositiveBtnText(int textId) {
        mIsShowPositiveBtn = true;
        mPositiveBtn.setText(textId);
        return this;
    }

    public CommonDialog setPositiveBtnTextColor(int colorId) {
        mIsShowPositiveBtn = true;
        mPositiveBtn.setTextColor(colorId);
        return this;
    }

    public CommonDialog setPositiveBtnTextSize(int textSize) {
        mIsShowPositiveBtn = true;
        mPositiveBtn.setTextSize(textSize);
        return this;
    }

    public CommonDialog setPositiveBtnListener(final OnPositiveListener onPositiveListener) {
        mIsShowPositiveBtn = true;
        mPositiveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onPositiveListener != null) {
                    onPositiveListener.onPositive(v);
                }
                mDialog.dismiss();
            }
        });
        return this;
    }
    /** end----------------------------------------- */
    
    public TextView getTitleView() {
        return this.getTitleView();
    }

    public TextView getContentView() {
        return this.mContentMsg;
    }

    public Button getPositiveBtn() {
        return this.mPositiveBtn;
    }

    public Button getMiddleBtn() {
        return this.mMiddleBtn;
    }

    public Button getNegativeBtn() {
        return this.mNegativeBtn;
    }

    public interface OnPositiveListener {
        public void onPositive(View view);
    }

    public interface OnMiddleListener {
        public void onMiddle(View view);
    }

    public interface OnNegativeListener {
        public void onNegative(View view);
    }
}
