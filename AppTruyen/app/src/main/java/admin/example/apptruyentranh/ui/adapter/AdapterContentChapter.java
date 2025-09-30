package admin.example.apptruyentranh.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.github.ybq.android.spinkit.style.FadingCircle;
import com.squareup.picasso.Picasso;

import java.io.Serializable;
import java.util.List;

import admin.example.apptruyentranh.R;
import admin.example.apptruyentranh.model.Chapter;
import admin.example.apptruyentranh.model.ResponeChapter;
import admin.example.apptruyentranh.model.ServerDatum;
import admin.example.apptruyentranh.ui.ContentChapterActivity;

public class AdapterContentChapter extends RecyclerView.Adapter<AdapterContentChapter.ViewHolder> {




    Context context;
    ResponeChapter chapter;
    int width  ,height;
    List<ResponeChapter.ChapterImage> listImage;
    public AdapterContentChapter(ResponeChapter chapter, Context context, int width, int height, List<ResponeChapter.ChapterImage> list) {


        this.chapter=chapter;
        this.context=context;
        this.width=width;
        this.height=height;
        this.listImage=list;
    }



    @NonNull
    @Override
    public AdapterContentChapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_contentchapter,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterContentChapter.ViewHolder holder, int position) {
        ResponeChapter.ChapterImage image=listImage.get(position);
        Log.d("index",image.getImageFile());
        holder.bind(chapter,image);

        if (position + 4 < listImage.size()) {
            for (int i = position+1; i < position+4; i++) {
                String nextUrl = chapter.getUrlImage() + listImage.get(i).getImageFile();
                Glide.with(holder.itemView.getContext()).load(nextUrl).preload();
            }

        }
    }

    @Override
    public int getItemCount() {
        if(chapter!=null){
            return listImage.size();
        }
        return 0;
    }

    public void setData(List<ResponeChapter.ChapterImage>list,ResponeChapter chapter) {
        this.chapter=chapter;
        listImage=list;

    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageChapter;
        ProgressBar progressBar;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageChapter=itemView.findViewById(R.id.itemimgContentChapter);
            progressBar=itemView.findViewById(R.id.progresscontentchapter);
            progressBar.setIndeterminateDrawable(new FadingCircle());



        }


        void bind(ResponeChapter chapter , ResponeChapter.ChapterImage image){

            String url=chapter.getUrlImage()+image.getImageFile();
            Log.d("url",url);
            progressBar.setVisibility(View.VISIBLE);

            Glide.with(imageChapter.getContext())

                    .load(url)
                    .placeholder(R.drawable.blackcolorre)
                    .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                    .apply(new RequestOptions()

                            .override(width, height))
                    .listener(new RequestListener<Drawable>() {
                        @Override
                        public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {

                            return false;
                        }

                        @Override
                        public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                            progressBar.setVisibility(View.GONE);
                            return false;
                        }
                    })

                    .into(imageChapter);


//            Glide.with(context).load(url).
//                    diskCacheStrategy(DiskCacheStrategy.ALL)
//                    .centerInside()
//
//                    .into(imageChapter);
        }


    }
}
