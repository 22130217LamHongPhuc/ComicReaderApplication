package admin.example.apptruyentranh.ui.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

import admin.example.apptruyentranh.R;
import admin.example.apptruyentranh.model.Banner;

public class AdapterBanner extends RecyclerView.Adapter<AdapterBanner.ViewHolder> {
    List<Banner > list;

    public AdapterBanner(List<Banner> list) {

        this.list = list;
    }

    @NonNull
    @Override
    public AdapterBanner.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_banner,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterBanner.ViewHolder holder, int position) {


        Picasso.get().load(list.get(position).getImage()).into(holder.img);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView img;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            img=itemView.findViewById(R.id.img_banner);

        }
    }
}
