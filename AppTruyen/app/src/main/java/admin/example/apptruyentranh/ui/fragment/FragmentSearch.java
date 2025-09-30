package admin.example.apptruyentranh.ui.fragment;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.github.ybq.android.spinkit.style.FadingCircle;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import admin.example.apptruyentranh.R;
import admin.example.apptruyentranh.listener.ILoadProgress;
import admin.example.apptruyentranh.model.Comics;
import admin.example.apptruyentranh.model.Item;
import admin.example.apptruyentranh.ui.adapter.AdapterComics;
import admin.example.apptruyentranh.viewmodel.ComicsesViewModel;

public class FragmentSearch extends Fragment {
    RecyclerView rlvSearch;
    SearchView searchView;
    ShimmerFrameLayout shimmerFrameLayout;

    List<Comics> listComicses;
    boolean checkFirst = true;
    boolean isCheckLoadFirst = true;
    ProgressBar progress_search;
    String searchName = "";
    TextView txtNotFound;
    ComicsesViewModel comicsesViewModel;
    Context context;
    AdapterComics adapterComics;
    LinearLayout layout_skeleton;
    List<Item> listItem = new ArrayList<>();
    List<Item> listfillter = new ArrayList<>();
    private boolean isLoading = false;
    private boolean isLastPage = false;
    private int indexPage = 1;
    private int position = 0;
    private LinearLayoutManager layoutManager;


    public FragmentSearch(List<Comics> comics, ComicsesViewModel comicsesViewModel, Context context) {
        this.listComicses = comics;
        this.comicsesViewModel = comicsesViewModel;
        this.context = context;
        Log.d("yeah", listComicses.size() + "");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);
        mapping(view);
        initial();
        search();
       listenerObserver();

        return view;
    }

    private void listenerObserver2() {
            comicsesViewModel.getSearchComis().observe((LifecycleOwner) getViewLifecycleOwner(), new Observer<Comics>() {
                @Override
                public void onChanged(Comics comics) {

                    if (comics != null) {
                        isLoading = true;
                        Log.d("FragmentSearch", "Received comics data: " + comics.getData().getTitlePage());

                        if (layout_skeleton.getVisibility() == View.VISIBLE) {
                            shimmerFrameLayout.stopShimmer();
                            layout_skeleton.setVisibility(View.GONE);
                            Log.d("FragmentSearch", "Shimmer stopped: " + shimmerFrameLayout.getVisibility());
                        }

                        List<Item> listLoad = comics.getData().getItems();

                        listfillter.addAll(listLoad);
                        Log.d("FragmentSearch", listfillter.size() + "");
                        if (listfillter.isEmpty()) {
                            txtNotFound.setVisibility(View.VISIBLE);
                            isLastPage=true;
                        } else {
                            adapterComics.setData(listfillter);
                            isLoading=false;

                        }

                    }
                }

            });

    }

    private void search() {
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                if (query.isEmpty()) {
                    return false;
                }
                if (!searchName.isEmpty() && searchName.equalsIgnoreCase(query) && !query.isEmpty()) {
                    searchName = query;
                    return false;
                }
                searchName = query;


                if (layout_skeleton.getVisibility() == View.GONE) {
                    layout_skeleton.setVisibility(View.VISIBLE);
                    shimmerFrameLayout.startShimmer();
                    Log.d("startshim", shimmerFrameLayout.isShimmerStarted() + "");
                }


                searchComics(query);
                hideKeyboard();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (newText.isEmpty()) {
                    searchName="";
                    rlvSearch.setVisibility(View.VISIBLE);
                    adapterComics.setData(listItem);
                }
                return false;
            }
        });
    }

    private void listenerObserver() {
        Log.d("FragmentSearch", "Observer setup start");


        comicsesViewModel.getSearchComis().observe((LifecycleOwner) getViewLifecycleOwner(), new Observer<Comics>() {
            @Override
            public void onChanged(Comics comics) {

                if (comics != null) {
                    isLoading=true;
                    Log.d("FragmentSearch", "Received comics data: " + comics.getData().getTitlePage());

                    if (layout_skeleton.getVisibility() == View.VISIBLE) {
                        shimmerFrameLayout.stopShimmer();
                        layout_skeleton.setVisibility(View.GONE);
                        Log.d("FragmentSearch", "Shimmer stopped: " + shimmerFrameLayout.getVisibility());
                    }

                    List<Item> listLoad = comics.getData().getItems();


                    Log.d("FragmentSearch", listfillter.size() + "");
                    if (listLoad.isEmpty() && isCheckLoadFirst) {
                        rlvSearch.setVisibility(View.INVISIBLE);
                        txtNotFound.setVisibility(View.VISIBLE);
                    } else {
                        rlvSearch.setVisibility(View.VISIBLE);
                        if (isCheckLoadFirst) {
                            position += listLoad.size();
                            adapterComics.setData(listLoad);
                            if (txtNotFound.getVisibility() == View.VISIBLE) {
                                txtNotFound.setVisibility(View.GONE);
                            }
                            isCheckLoadFirst = false;
                            isLoading=false;
                        } else {

                            if (!listLoad.isEmpty()) {
                                new Handler(Looper.getMainLooper()).post(() -> {
                                    Log.d("FragmentSearch", listfillter.size() + "");


                                    int oldPosition = position;
                                    isLoading = false;
                                    adapterComics.setListUpdate(listLoad);

                                    Log.d("FragmentSearch", "listsize " + adapterComics.getList().size());

                                    //adapterComics.notifyItemRangeInserted(oldPosition,listLoad.size());

                                    position += listLoad.size();


                                });

                            } else {
                                isLastPage = true;
                            }
                        }


                    }


                }

            }
        });
    }

    private void searchComics(String query) {
        if (!query.isEmpty()) {
            isLoading = true;
            position = 0;
            isCheckLoadFirst = true;
            indexPage = 1;
            listfillter.clear();
            Log.d("FragmentSearch", "start");
            comicsesViewModel.searchComics(query, indexPage);
            Log.d("FragmentSearch", "startSe");
        }
    }

    private void initial() {
        if (checkFirst) {
            searchView.requestFocus();
            searchView.setIconifiedByDefault(false);
        }

        checkFirst = false;


        addItem();
        adapterComics = new AdapterComics(listItem, AdapterComics.TYPEBIGSEARCH, context, comicsesViewModel, new ILoadProgress() {
            @Override
            public void startProgress() {
                progress_search.setVisibility(View.VISIBLE);
                Log.d("stapro", progress_search.getVisibility() + "");
            }

            @Override
            public void endProgress() {
                progress_search.setVisibility(View.GONE);
                searchView.clearFocus();

                Log.d("stapro", progress_search.getVisibility() + "");

            }
        });
        layoutManager = new LinearLayoutManager(getContext());
        rlvSearch.setLayoutManager(layoutManager);
        rlvSearch.setHasFixedSize(true);
        rlvSearch.setAdapter(adapterComics);

        addScrollView(layoutManager);

    }

    private void addScrollView(LinearLayoutManager layoutManager) {
        rlvSearch.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                int totalItem = layoutManager.getItemCount();
                int first = layoutManager.findFirstVisibleItemPosition();
                int visible = layoutManager.getChildCount();
                Log.d("src", first + " - " + visible + " - " + totalItem);
                Log.d("src",isLoading+" -" + isLastPage);
                if (!isLoading && first >= 0 && first + visible >= totalItem - 6 && !isLastPage) {

                    loadMore();

                }
            }
        });
    }

    private void loadMore() {
        Log.d("sss", "scroll");
        indexPage++;
        isLoading = true;
        Log.d("FragmentSearch", "startReMore");

        comicsesViewModel.searchComics(searchName, indexPage);
    }

    private void addItem() {
        Random rd = new Random();
        List<Item> listAll = new ArrayList<>();

        for (int i = 0; i < listComicses.size(); i++) {
            Comics comics = listComicses.get(i);
            if (comics.getData() != null) {
                if (!comics.getData().getTitlePage().equalsIgnoreCase("Action")) {
                    listAll.addAll(comics.getData().getItems());
                }
            }
        }

        while (listItem.size() <= 20) {
            int k = rd.nextInt(listAll.size());
            if (!listItem.contains(listAll.get(k))) {
                listItem.add(listAll.get(k));
            }
        }
    }

    private void mapping(View view) {
        searchView = view.findViewById(R.id.searchview);
        rlvSearch = view.findViewById(R.id.rlvSearch);
        searchView.setQueryHint("Nhập tên truyện bạn muốn");
        shimmerFrameLayout = view.findViewById(R.id.skeleton_search);
        layout_skeleton = view.findViewById(R.id.layout_skeleton_search);
        txtNotFound = view.findViewById(R.id.txtNotSearchComics);
        progress_search = view.findViewById(R.id.progress_search);

        EditText searchEditText = searchView.findViewById(androidx.appcompat.R.id.search_src_text);

        // Đặt màu cho văn bản gợi ý (hint)
        searchEditText.setHintTextColor(Color.WHITE);
        searchEditText.setTextColor(Color.WHITE);
        searchEditText.setTextSize(14);
    }

    private void hideKeyboard() {
        Activity activity = getActivity();
        if (activity != null) {
            // Hide the keyboard from the EditText directly
            InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
            if (imm != null) {
                imm.hideSoftInputFromWindow(searchView.getWindowToken(), 0);
            }
        } else {
            Log.d("viewww", "activitynull");
        }
    }


    @Override
    public void onStart() {

        super.onStart();
    }
}
