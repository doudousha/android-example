package wq.com.retrofitrxjavarecyleviewexample.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import wq.com.retrofitrxjavarecyleviewexample.R;
import wq.com.retrofitrxjavarecyleviewexample.bean.Post;

/**
 * Created by dou_d on 2018/8/9.
 */

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.ViewHolder> {


    private List<Post> datas;

    public PostAdapter(List<Post> datas) {
        this.datas = datas;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

       View view =  LayoutInflater.from(parent.getContext()).inflate(R.layout.post,parent,false);

       return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Post post =  datas.get(position);

        holder.author.setText(String.valueOf(post.getId()));
        holder.title.setText(post.getTitle());
        holder.content.setText(post.getBody());
    }

    public void add(List<Post> posts) {
        int position =  datas.size() ;
        datas.addAll(position,posts);
        notifyItemInserted(position);
    }

    public void refresh(List<Post> posts){
        datas.removeAll(datas);
        datas.addAll(posts);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(View itemView) {
            super(itemView);

            title =(TextView) itemView.findViewById(R.id.txt_title);
            content =  (TextView)itemView.findViewById(R.id.txt_content);
            author = (TextView)itemView.findViewById(R.id.txt_author);
        }

        private TextView title;
        private TextView content;
        private  TextView author ;

        public TextView getTitle() {
            return title;
        }

        public TextView getContent() {
            return content;
        }

        public TextView getAuthor() {
            return author;
        }
    }
}
