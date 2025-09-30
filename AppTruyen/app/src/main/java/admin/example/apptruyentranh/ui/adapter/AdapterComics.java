package admin.example.apptruyentranh.ui.adapter;

import static android.os.Build.VERSION_CODES.O;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;

import admin.example.apptruyentranh.R;
import admin.example.apptruyentranh.api.ApiService;
import admin.example.apptruyentranh.listener.ILoadProgress;
import admin.example.apptruyentranh.model.Comics;
import admin.example.apptruyentranh.model.Item;
import admin.example.apptruyentranh.ui.DetailComicsActivity;
import admin.example.apptruyentranh.viewmodel.ComicsesViewModel;

public class AdapterComics extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    int typeCurrent;
    public static int TYPESMALL = 0;
    public static int TYPEBIG = 1;
    public static int TYPEBIGANOTHER = 6;
    public static int TYPEBIGSEARCH = 3;
    public static int TYPECATEGORY = 2;
    public static int TYPECATEGORYS = 5;
    public static int TYPERATING = 4;
    Context context;
    ILoadProgress loadProgress;


    List<Item> list;
    Random rd = new Random();
    ComicsesViewModel comicsesViewModel;

    public AdapterComics(List<Item> list, int type, Context context) {

        this.list = list;
        this.context = context;
        this.typeCurrent = type;
    }

    public AdapterComics(List<Item> list, int type, Context context,  ComicsesViewModel comicsesViewModel,ILoadProgress loadProgress) {

        this.list = list;
        this.context = context;
        this.typeCurrent = type;
        this.comicsesViewModel=comicsesViewModel;
        this.loadProgress=loadProgress;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (typeCurrent == TYPEBIG || typeCurrent==TYPEBIGANOTHER) {
            return new AdapterComics.ViewHolderComics(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_comicsbig, parent, false));

        } else if (typeCurrent == TYPESMALL) {
            return new AdapterComics.ViewHolderComics(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_comicssmall, parent, false));

        } else {
            return new AdapterComics.ViewHolderCategory(LayoutInflater.from(parent.getContext()).inflate(R.layout.itemcategory, parent, false));

        }
    }


    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        Item item = list.get(position);


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, DetailComicsActivity.class);

                Log.d("sluggg",item.getSlug());
                loadProgress.startProgress();

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
                            loadProgress.endProgress();

                        }



                    }
                });

            }
        });
        if (typeCurrent == TYPEBIG || typeCurrent == TYPESMALL || typeCurrent==TYPEBIGANOTHER) {
            ((ViewHolderComics) holder).bind(item, position);
        } else {
            ((ViewHolderCategory) holder).bind(item, position);

        }

    }

    @Override
    public int getItemCount() {


        if (typeCurrent == TYPECATEGORY) {
            return 5;
        } else if (typeCurrent == TYPEBIGSEARCH) {

            return list.size();
        } else if (typeCurrent == TYPERATING) {
            return list.size();
        } else if (typeCurrent == TYPECATEGORYS) {
            return list.size();
        } else if (typeCurrent == TYPEBIGANOTHER) {
            return 9;
        }

        return 8;
    }

    public void setData(Comics comics) {
        this.list = comics.getData().getItems();
        notifyDataSetChanged();
    }

    public void setData(List<Item> listUpdate) {
        this.list = listUpdate;
        notifyDataSetChanged();

    }
    public ArrayList<Item> getList(){
        return (ArrayList<Item>) list;
    }
    public void setListUpdate(List<Item> listUpdate){
        Log.d("rating",listUpdate.size()+"");
        Log.d("rating",this.list.size()+"");

        int oldPosition=this.list.size();
        this.list.addAll(listUpdate);
        Log.d("rating",this.list.size()+"");

        notifyItemRangeInserted(oldPosition,listUpdate.size());
    }


    public class ViewHolderComics extends RecyclerView.ViewHolder {
        ImageView img;
        TextView txtName;

        public ViewHolderComics(@NonNull View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.imgItemNameUpdate);
            txtName = itemView.findViewById(R.id.txtItemNameUpdate);


        }

        void bind(Item item, int position) {
            txtName.setText(item.getName());
            String urlImg = ApiService.BASE_URLIMG + item.getThumbUrl();
            Picasso.get().load(urlImg).into(img);
        }
    }


    public class ViewHolderCategory extends RecyclerView.ViewHolder {
        ImageView img, imgstart, imgheart;
        ImageView imgrating;

        TextView txtName;
        TextView txtView;
        TextView txtstatus;
        TextView txtStar;


        public ViewHolderCategory(@NonNull View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.imgitemcategory);
            txtName = itemView.findViewById(R.id.txtitemnameCategory);
            txtView = itemView.findViewById(R.id.txtitemviewCategory);
            txtStar = itemView.findViewById(R.id.txtitemstarCategory);
            imgheart = itemView.findViewById(R.id.imgitemheartCategory);
            imgstart = itemView.findViewById(R.id.imgitemstarCategory);
            txtstatus = itemView.findViewById(R.id.txtitemStatusCategory);
            imgrating = itemView.findViewById(R.id.imgRatingCategory);


        }

        void bind(Item item, int position) {
            txtName.setText(item.getName());
            String urlImg = ApiService.BASE_URLIMG + item.getThumbUrl();
            Glide.with(img.getContext()).
                    load(urlImg)
                    .into(img);
            if (position + 4 < list.size()) {
                for (int i = position + 1; i < position + 4; i++) {
                    String nextUrl = ApiService.BASE_URLIMG + list.get(i).getThumbUrl();
                    Glide.with(img.getContext()).load(nextUrl).preload();
                }

            }

            DateTimeFormatter formatter = null;
            if (android.os.Build.VERSION.SDK_INT >= O) {
                formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSX");
                ZonedDateTime zonedDateTime = ZonedDateTime.parse(item.getUpdatedAt(), formatter);

                // Converting to LocalDateTime
                LocalDateTime localDateTime = zonedDateTime.toLocalDateTime();
                txtstatus.setText(localDateTime.getDayOfMonth() + "-" + localDateTime.getMonthValue() + "-" + localDateTime.getYear() + "");
            }
            if (typeCurrent == TYPECATEGORY || typeCurrent == TYPEBIGSEARCH || typeCurrent == TYPECATEGORYS) {
                imgstart.setVisibility(View.VISIBLE);
                txtView.setText((rd.nextInt(9000) + 1000) + " luot xem");
                int k = rd.nextInt(5);
                txtStar.setText(k + "/5");


            } else if (typeCurrent == TYPERATING) {
                imgheart.setVisibility(View.VISIBLE);
                txtStar.setText(item.getLike() + "");

                txtView.setText(item.getViewer() + " luot xem");
                if (position == 0) {
                    imgrating.setVisibility(View.VISIBLE);
                    imgrating.setImageResource(R.drawable.rating1);
                } else if (position == 1) {
                    imgrating.setVisibility(View.VISIBLE);

                    imgrating.setImageResource(R.drawable.rating2);

                } else if (position == 2) {
                    imgrating.setVisibility(View.VISIBLE);

                    imgrating.setImageResource(R.drawable.rating3);
                } else {
                    imgrating.setVisibility(View.INVISIBLE);

                }
            }

        }
    }

    public void setType(int type) {
        this.typeCurrent = type;

    }




}

