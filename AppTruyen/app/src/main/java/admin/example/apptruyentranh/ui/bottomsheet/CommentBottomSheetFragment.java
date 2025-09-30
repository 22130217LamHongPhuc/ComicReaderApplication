package admin.example.apptruyentranh.ui.bottomsheet;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.ArrayList;
import java.util.List;

import admin.example.apptruyentranh.R;
import admin.example.apptruyentranh.model.Comment;
import admin.example.apptruyentranh.ui.MainAppActivity;
import admin.example.apptruyentranh.ui.adapter.AdapterComment;
import admin.example.apptruyentranh.viewmodel.ComicsesViewModel;

public class CommentBottomSheetFragment extends BottomSheetDialogFragment {
    String id;
    EditText editContent;
    ImageView imgSend;
    TextView txtSLCmt;
    RecyclerView rlvCmt;
    ComicsesViewModel comicsesViewModel;
    private TextView txtCheckLogin;
    private ArrayList<Comment> list;
    private AdapterComment adapterComment;
    IHideKeyBoard IHideKeyBoard;


    public CommentBottomSheetFragment(String id, ComicsesViewModel comicsesViewModel) {
        this.id=id;
        this.comicsesViewModel=comicsesViewModel;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.bottomsheet_cmt,null,false);
        mapping(view);
        initial();
        loadCmt();

        imgSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String content=editContent.getText().toString();
                Comment comment=new Comment(System.currentTimeMillis()+"",id,content,MainAppActivity.getUserCurrent().getUid(),"",MainAppActivity.getUserCurrent().getEmail());
                if(!content.isEmpty()){
                    comicsesViewModel.writeComment(comment);
                    editContent.setText("");
                    hideKeyboard();
                    Log.d("idimg",imgSend.getId()+"");
                }
            }
        });

       
        return view;
    }

    private void loadCmt() {
            comicsesViewModel.getComment().observe((LifecycleOwner) getContext(), new Observer<List<Comment>>() {
                @Override
                public void onChanged(List<Comment> comments) {
                    if(comments!=null){
                        adapterComment.setData(comments);
                      txtSLCmt.setText("Binh luan("+comments.size()+")");
                    }
                }
            });

    }



    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
         BottomSheetBehavior bottomSheetBehavior = BottomSheetBehavior.from((View) view.getParent());
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);


        super.onViewCreated(view, savedInstanceState);
    }

    private void initial() {



        if(MainAppActivity.getUserCurrent()==null){
            editContent.setVisibility(View.GONE);
            imgSend.setVisibility(View.GONE);
        }else{
            txtCheckLogin.setVisibility(View.INVISIBLE);
        }

        list=new ArrayList<>();
        LinearLayoutManager layoutManager=new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rlvCmt.setLayoutManager(layoutManager);
        rlvCmt.setHasFixedSize(true);
        adapterComment=new AdapterComment(list, getContext(),AdapterComment.TYPE_CMTSHEET);

        rlvCmt.setAdapter(adapterComment);
    }

    private void mapping(View view) {
        editContent=view.findViewById(R.id.txtcotentcmt);
        imgSend=view.findViewById(R.id.img_sendcmt);
        txtSLCmt=view.findViewById(R.id.txt_cmt);
        rlvCmt=view.findViewById(R.id.reclview_cmt);
        txtCheckLogin=view.findViewById(R.id.checkLogin);
    }
    public interface IHideKeyBoard {
        void hideKeyboard();

    }
    private void hideKeyboard() {
        Activity activity = getActivity();
        if (activity != null) {
            // Hide the keyboard from the EditText directly
            InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
            if (imm != null) {
                imm.hideSoftInputFromWindow(editContent.getWindowToken(), 0);
            }
        } else {
            Log.d("viewww", "activitynull");
        }
    }

}
