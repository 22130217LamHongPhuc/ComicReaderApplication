package admin.example.apptruyentranh.ui.adapter;

import static android.os.Build.VERSION_CODES.O;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Random;

import admin.example.apptruyentranh.R;
import admin.example.apptruyentranh.api.ApiService;
import admin.example.apptruyentranh.dbroom.ItemDAO;
import admin.example.apptruyentranh.listener.ILoadProgress;
import admin.example.apptruyentranh.model.Comics;
import admin.example.apptruyentranh.ui.DetailComicsActivity;
import admin.example.apptruyentranh.viewmodel.ComicsesViewModel;

public class AdapterComicsDao extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


   int typeCurrent;

    Context context;


    List<ItemDAO> list;
    Random rd=new Random();
    ComicsesViewModel comicsesViewModel;
    ILoadProgress iLoadProgress;
    int type;
    public static final int TypeLong=0;
    public static final int TypeBig=1;


    public AdapterComicsDao(List<ItemDAO> list, Context context,int type ,ComicsesViewModel comicsesViewModel,
    ILoadProgress iLoadProgress) {

        this.list = list;
        this.context=context;
        this.type=type;
        this.comicsesViewModel=comicsesViewModel;
        this.iLoadProgress=iLoadProgress;

    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(type==TypeLong){
            return new ViewHolderComicsLong(LayoutInflater.from(parent.getContext()).inflate(R.layout.itemcategory,parent,false));


        }else{
            return new ViewHolderComicsBig(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_comicsbig,parent,false));

        }



    }



    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder , int position) {


        ItemDAO item= list.get(position);


        if(type==TypeLong){
            ((ViewHolderComicsLong) holder).bind(item);

        }else{
            ((ViewHolderComicsBig) holder).bind(item);
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, DetailComicsActivity.class);

                Log.d("sluggg",item.getSlug());
                iLoadProgress.startProgress();

                comicsesViewModel.searchGetComics(item.getSlug());
                comicsesViewModel.getComics().observe((LifecycleOwner) context, new Observer<Comics>() {
                    @Override
                    public void onChanged(Comics comics) {
                        if(comics!=null){
                            Log.d("sluggg",comics.getData().getItem().getSlug());
                            intent.putExtra("comics", (Serializable) comics);
                            context.startActivity(intent);
                            comicsesViewModel.getComics().removeObserver(this);
                            comicsesViewModel.setcomics();
                            iLoadProgress.endProgress();

                        }
                    }
                });

            }
        });

    }

    @Override
    public int getItemCount() {
        int size=0;
        if(list!=null){
             size=list.size()<4 ? list.size() : type == TypeLong ? list.size() : 4;

        }

        return size;
    }

    public void setData(List<ItemDAO> list) {
        this.list=list;
        notifyDataSetChanged();
    }


    public class ViewHolderComicsLong extends RecyclerView.ViewHolder {
        ImageView img;
        TextView txtName;
        TextView txtView;
        TextView txtstatus;
        TextView txtStar;


        public ViewHolderComicsLong(@NonNull View itemView) {
            super(itemView);
            img=itemView.findViewById(R.id.imgitemcategory);
            txtName=itemView.findViewById(R.id.txtitemnameCategory);
            txtView=itemView.findViewById(R.id.txtitemviewCategory);
            txtStar=itemView.findViewById(R.id.txtitemstarCategory);
            txtstatus=itemView.findViewById(R.id.txtitemStatusCategory);

        }
        void bind(ItemDAO item){
            txtName.setText(item.getName());
            String urlImg= ApiService.BASE_URLIMG+item.getImage();
            Picasso.get().load(urlImg).into(img);


            txtView.setText(( rd.nextInt(9000) + 1000)+" luot xem");
            int k=rd.nextInt(5);
            txtStar.setText(k+"/5");
            DateTimeFormatter formatter = null;
            if (android.os.Build.VERSION.SDK_INT >= O) {
                formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSX");
                ZonedDateTime zonedDateTime = ZonedDateTime.parse(item.getUpdateDate(), formatter);

                // Converting to LocalDateTime
                LocalDateTime localDateTime = zonedDateTime.toLocalDateTime();
                txtstatus.setText(localDateTime.getDayOfMonth()+"-"+localDateTime.getMonthValue()+"-"+localDateTime.getYear()+"");
            }



        }
    }

    public class ViewHolderComicsBig extends RecyclerView.ViewHolder {
        ImageView img;
        TextView txtName;



        public ViewHolderComicsBig(@NonNull View itemView) {
            super(itemView);
            txtName=itemView.findViewById(R.id.txtItemNameUpdate);
            img=itemView.findViewById(R.id.imgItemNameUpdate);
        }

        void bind(ItemDAO itemDAO){

            txtName.setText(itemDAO.getName());
            String urlImg= ApiService.BASE_URLIMG+itemDAO.getImage();
            Picasso.get().load(urlImg).into(img);

        }
    }


}

