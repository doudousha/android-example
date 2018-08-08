package wq.com.refreshrecyclerviewdemo;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by dou_d on 2018/8/7.
 */

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {

    private List<String> datas ;
    private Context context;

    public MyAdapter(List<String> datas, Context context) {
        this.datas = datas;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
       
        // 加载布局
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycle_item_view,parent,false);
        return new  MyAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        holder.textView.setText(datas.get(position));
    }

    @Override
    public int getItemCount() {
        return this.datas.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public ViewHolder(View itemView) {
            super(itemView);

            this.textView =  (TextView)itemView.findViewById(R.id.txt_we);
        }

        private TextView textView ;

        public TextView getTextView() {
            return textView;
        }
    }
}
