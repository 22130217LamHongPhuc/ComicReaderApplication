package admin.example.apptruyentranh.ui.adapter;

import android.content.Context;
import android.content.Intent;
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
import admin.example.apptruyentranh.model.Comics;
import admin.example.apptruyentranh.model.ResponeChapter;
import admin.example.apptruyentranh.model.ServerDatum;
import admin.example.apptruyentranh.ui.ContentChapterActivity;
import admin.example.apptruyentranh.viewmodel.ComicsesViewModel;

public class AdapterChapter extends RecyclerView.Adapter<AdapterChapter.ViewHolder> {

    List<ServerDatum> listChapter;
    LoadProGressBar loadProGressBar;

    String dateupdate;
    Context context;
    ComicsesViewModel comicsesViewModel;

    public AdapterChapter(List<ServerDatum> listChapter,String Dateupdate,Context context,    ComicsesViewModel comicsesViewModel,LoadProGressBar loadProGressBar) {

        this.listChapter = listChapter;
        this.dateupdate=Dateupdate;
        this.context=context;
        this.comicsesViewModel=comicsesViewModel;
        this.loadProGressBar=loadProGressBar;
    }

    @NonNull
    @Override
    public AdapterChapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_listchapter,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterChapter.ViewHolder holder, int position) {
        holder.onClick(listChapter.get(position));

        holder.bind(listChapter.get(position));
    }

    @Override
    public int getItemCount() {
        return listChapter.size();
    }

    public void setData(List<ServerDatum> serverData, String dateupdate) {
        this.listChapter=serverData;
        this.dateupdate=dateupdate;
        notifyDataSetChanged();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txt_name,txtDate;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txt_name=itemView.findViewById(R.id.txtitemnamechapter);
            txtDate=itemView.findViewById(R.id.txtitemdatechapter);

        }


        void bind(ServerDatum chapter){


            txt_name.setText("Chapter "+chapter.getChapterName());

        }

        public void onClick(ServerDatum chapter) {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {


                    loadProGressBar.loadProgress();



                    comicsesViewModel.searchContentChapter(chapter.getChapterApiData(),new DataLoadedCallback(){
                        @Override
                        public void onDataLoaded() {
                            Log.d("comismodel","ss1");
                            loadProGressBar.addItemIntoDatabase();
                            comicsesViewModel.getContentChapter().observe((LifecycleOwner) context, new Observer<ResponeChapter>() {
                                @Override
                                public void onChanged(ResponeChapter chapter) {
                                    Log.d("comismodel","ss2");

                                    Intent intent = new Intent(context, ContentChapterActivity.class);
                                    intent.putExtra("contentChapter",chapter);
                                    Log.d("repo",chapter.getNameChapter());
                                    context.startActivity(intent);
                                    comicsesViewModel.getContentChapter().removeObserver(this);

                                }
                            });

                        }
                    });
                }
            });
        }
    }
    public interface DataLoadedCallback {
        void onDataLoaded();
    }

    public interface LoadProGressBar {
        void loadProgress();
        void addItemIntoDatabase();
    }


}
