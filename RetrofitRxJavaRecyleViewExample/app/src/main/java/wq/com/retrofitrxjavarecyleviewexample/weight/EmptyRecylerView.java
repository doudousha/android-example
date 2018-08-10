package wq.com.retrofitrxjavarecyleviewexample.weight;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by dou_d on 2018/8/9.
 */

public class EmptyRecylerView extends RecyclerView {

    public void setEmptyView(View emptyView) {
        this.emptyView = emptyView;
    }

    private View emptyView ;

    private static final String TAG = "emptyRecyclerView";
    private AdapterDataObserver observer = new AdapterDataObserver() {
        @Override
        public void onChanged() {
            checkIfEmpty();
        }

        @Override
        public void onItemRangeInserted(int positionStart, int itemCount) {
            checkIfEmpty();
        }

        @Override
        public void onItemRangeRemoved(int positionStart, int itemCount) {
            checkIfEmpty();
        }
    };

    private void checkIfEmpty() {
        if(emptyView==null && getAdapter() == null)
            return;

        boolean emptyViewVisible = getAdapter().getItemCount() == 0  ;
        emptyView.setVisibility( emptyViewVisible  ? VISIBLE :GONE);
        setVisibility(emptyViewVisible ? GONE :VISIBLE);
    }

    public EmptyRecylerView(Context context) {
        super(context);
    }

    public EmptyRecylerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public EmptyRecylerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public void setAdapter(Adapter adapter) {

       final Adapter oldAdapter = getAdapter();
       if(oldAdapter!=null) {
           oldAdapter.unregisterAdapterDataObserver(observer); // 解决重复调用setAdapter
       }
        super.setAdapter(adapter);
       if(adapter!=null){
           adapter.registerAdapterDataObserver(observer);
       }

       checkIfEmpty();
    }
}
