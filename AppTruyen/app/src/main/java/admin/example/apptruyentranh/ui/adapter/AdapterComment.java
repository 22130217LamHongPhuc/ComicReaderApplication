package admin.example.apptruyentranh.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

import admin.example.apptruyentranh.R;
import admin.example.apptruyentranh.model.Comment;

public class AdapterComment extends RecyclerView.Adapter<AdapterComment.ViewHolder> {
    List<Comment> list;
    Context context;
    int type;
    public static final int TYPE_CMTFRAGMENT=0;
    public static final int TYPE_CMTSHEET=1;



    public AdapterComment(List<Comment> list, Context context,int type) {
        this.list = list;
        this.context = context;
        this.type=type;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(type==TYPE_CMTFRAGMENT){
            return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_cmt,parent,false));

        }else{
            return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_cmtsheet,parent,false));

        }
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.bind(list.get(position));

    }

    @Override
    public int getItemCount() {
        if(type==TYPE_CMTFRAGMENT){
            if(list.size()>4){
                return 4;
            }else {
                return list.size();
            }
        }



        return list.size();

    }

    public void setData(List<Comment> comments) {
        list=comments;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView img;
        TextView name,content;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            img=itemView.findViewById(R.id.imgCmt);
            name=itemView.findViewById(R.id.txt_usercmtitem);
            content=itemView.findViewById(R.id.txt_cmtitem);

        }

        public void bind( Comment comment){
            if(!comment.getImageUser().isEmpty()){
                Picasso.get().load(comment.getImageUser()).into(img);
            }
            name.setText(comment.getUserName());
            content.setText(comment.getCmt());

        }
    }

}
