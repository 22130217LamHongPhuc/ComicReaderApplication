package admin.example.apptruyentranh.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.RecyclerView;

import java.io.Serializable;
import java.util.List;

import admin.example.apptruyentranh.R;
import admin.example.apptruyentranh.listener.ILoadProgress;
import admin.example.apptruyentranh.model.Category;
import admin.example.apptruyentranh.model.Comics;
import admin.example.apptruyentranh.ui.ShowMoreActivity;
import admin.example.apptruyentranh.viewmodel.ComicsesViewModel;

public class AdapterCategoryRd extends RecyclerView.Adapter<AdapterCategoryRd.ViewHolder> {
    List<Category> list;
    ComicsesViewModel comicsesViewModel;
    Context context;
    ILoadProgress iLoadProgress;


    public AdapterCategoryRd(List<Category> list, ComicsesViewModel comicsesViewModel, Context context,ILoadProgress iLoadProgress) {

        this.list = list;
        this.comicsesViewModel=comicsesViewModel;
        this.context=context;
        this.iLoadProgress=iLoadProgress;
    }

    @NonNull
    @Override
    public AdapterCategoryRd.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_categoryrd,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterCategoryRd.ViewHolder holder, int position) {
        Category category=list.get(position);
        holder.txt_nameCa.setText(category.getName());
        if(position==0){
            holder.txt_nameCa.setBackgroundResource(R.color.yellow);
            holder.txt_nameCa.setTextColor(Color.BLACK);

        }else if(position==1){
            holder.txt_nameCa.setBackgroundResource(R.color.green);
            holder.txt_nameCa.setTextColor(Color.BLACK);
        }else if(position==2){
            holder.txt_nameCa.setBackgroundResource(R.color.blue);
            holder.txt_nameCa.setTextColor(Color.WHITE);
        }else if(position==3){
            holder.txt_nameCa.setBackgroundResource(R.color.orange);
            holder.txt_nameCa.setTextColor(Color.WHITE);
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                comicsesViewModel.setLiDataComicsToCategory();
               comicsesViewModel.searchComicsToCategory(category.getSlug(),1);
               iLoadProgress.startProgress();

                    Log.d("sss",category.getName());

                comicsesViewModel.getListComicsCategory().observe((LifecycleOwner) context, new Observer<Comics>() {
                    @Override
                    public void onChanged(Comics comics) {
                        if(comics!=null){
                            Log.d("sss", comics.getData().getTitlePage());

                            Intent intent = new Intent(context, ShowMoreActivity.class);
                            intent.putExtra("Comics", (Serializable) comics);
                            intent.putExtra("slug",category.getSlug());
                            context.startActivity(intent);
                            iLoadProgress.startProgress();
                            comicsesViewModel.getListComicsCategory().removeObserver(this);

                        }

                    }
                });

            }
        });

    }

    @Override
    public int getItemCount() {
        return 12;
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txt_nameCa;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txt_nameCa=itemView.findViewById(R.id.txtItemCategoryRd);

        }
    }
}
