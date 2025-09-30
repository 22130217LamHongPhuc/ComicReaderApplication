package admin.example.apptruyentranh.ui.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import admin.example.apptruyentranh.R;
import admin.example.apptruyentranh.api.ApiService;
import admin.example.apptruyentranh.model.Comics;
import admin.example.apptruyentranh.model.Comment;
import admin.example.apptruyentranh.ui.DetailComicsActivity;
import admin.example.apptruyentranh.ui.adapter.AdapterComment;
import admin.example.apptruyentranh.viewmodel.ComicsesViewModel;


public class FragmentInformation extends Fragment {
    TextView txtSlReadedDetail, txtSlStarDetail, txtSlListChapterDetail, txtCategoryDetail1, txtCategoryDetail2, txtContentDetail,
            txtAuthorDetail1, txtAuthorDetail2;
    ComicsesViewModel comicsesViewModel;
    Context context;
    Random rd = new Random();
    List<Comment> list;
    AdapterComment adapterComment;
    private RecyclerView rlvCmt;
    Comics comics;


    public FragmentInformation(ComicsesViewModel comicsesViewModel, Context context,Comics comics) {
        this.comicsesViewModel = comicsesViewModel;
        this.context = context;
        this.comics=comics;

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_information, null);
        mapping(view);
        initital();


        return view;
    }

    private void initital() {

        if (comics != null) {
            loadCmt(comics.getData().getItem().getId());

            String urlImg = ApiService.BASE_URLIMG + comics.getData().getItem().getThumbUrl();

            txtContentDetail.setText(comics.getData().getItem().getContent());
            String name = comics.getData().getItem().getName();


            txtCategoryDetail1.setText(comics.getData().getItem().getCategory().get(0).getName());
            if (comics.getData().getItem().getCategory().size() >= 2) {
                txtCategoryDetail2.setText(comics.getData().getItem().getCategory().get(1).getName());
            }

            if(comics.getData().getItem().getAuthor().size()!=0){
                if(comics.getData().getItem().getAuthor().size()>1){
                    txtAuthorDetail1.setText(comics.getData().getItem().getAuthor().get(0));
                    txtAuthorDetail2.setText(comics.getData().getItem().getAuthor().get(1));

                }else{
                    txtAuthorDetail1.setText(comics.getData().getItem().getAuthor().get(0));
                    txtAuthorDetail2.setVisibility(View.INVISIBLE);

                }
            }else{
                txtAuthorDetail1.setVisibility(View.INVISIBLE);
                txtAuthorDetail2.setVisibility(View.INVISIBLE);
            }

            txtSlReadedDetail.setText((rd.nextInt(90) + 10) + " K");
            int k = rd.nextInt(5)+1;
            txtSlStarDetail.setText(k + "");
            txtSlListChapterDetail.setText(comics.getData().getItem().getChapters().get(0).getServerData().size() + "");

        }

        list = new ArrayList<>();
        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        rlvCmt.setLayoutManager(layoutManager);
        rlvCmt.setHasFixedSize(true);
        adapterComment = new AdapterComment(list, context, AdapterComment.TYPE_CMTFRAGMENT);

        rlvCmt.setAdapter(adapterComment);

    }


    private void loadCmt(String id) {
        comicsesViewModel.loadCmt(id);

        comicsesViewModel.getComment().observe((LifecycleOwner) context, new Observer<List<Comment>>() {
            @Override
            public void onChanged(List<Comment> comments) {
                if (comments != null) {
                    adapterComment.setData(comments);
                }
            }
        });
    }

    void mapping(View view) {
        txtSlReadedDetail = view.findViewById(R.id.txtSlReadedDetail);

        txtSlStarDetail = view.findViewById(R.id.txtSlStarDetail);

        txtSlListChapterDetail = view.findViewById(R.id.txtSlListChapterDetail);

        txtCategoryDetail1 = view.findViewById(R.id.txtcategorydetail1);
        txtCategoryDetail2 = view.findViewById(R.id.txtcategorydetail2);

        txtContentDetail = view.findViewById(R.id.txtcontentDetail);
        rlvCmt = view.findViewById(R.id.rlvcmtInfor);
        txtAuthorDetail1=view.findViewById(R.id.txtauthordetail1);
        txtAuthorDetail2=view.findViewById(R.id.txtauthordetail2);



    }





}
