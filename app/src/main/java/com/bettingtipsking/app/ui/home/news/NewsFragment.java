package com.bettingtipsking.app.ui.home.news;

import android.os.Bundle;

import androidx.core.widget.NestedScrollView;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bettingtipsking.app.Helper.QuickHelp;
import com.bettingtipsking.app.api.NewsService;
import com.bettingtipsking.app.api.RetrofitHelper;
import com.bettingtipsking.app.R;
import com.bettingtipsking.app.adapter.NewsAdapter;
import com.bettingtipsking.app.databinding.FragmentNewsBinding;
import com.bettingtipsking.app.model.news.Data;
import com.bettingtipsking.app.model.news.NewsModel;
import com.bettingtipsking.app.repository.NewsRepository;
import com.bettingtipsking.app.viewmodel.AuthSignupViewModel;
import com.bettingtipsking.app.viewmodel.NewsViewModel;
import com.bettingtipsking.app.viewmodel.viewmodelfactory.NewsViewModelFactory;

import java.util.ArrayList;
import java.util.List;


public class NewsFragment extends Fragment {

    FragmentNewsBinding binding;
    NewsViewModel viewModel;
    NewsAdapter adapter;

    int page = 1;
    List<Data> dataList = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentNewsBinding.inflate(inflater, container, false);

        adapter = new NewsAdapter(getContext(), dataList);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.recyclerView.setAdapter(adapter);


        NewsRepository newsRepository = new NewsRepository(RetrofitHelper.INSTANCE.getInstance().create(NewsService.class));
        viewModel = new ViewModelProvider(this, new NewsViewModelFactory(newsRepository)).get(NewsViewModel.class);

        sendRequest(page);


        binding.nestedScrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                if (scrollY == v.getChildAt(0).getMeasuredHeight() - v.getMeasuredHeight()) {
                    if (page <= 3) {
                        page++;
                        sendRequest(page);
                    }
                }

            }
        });

        viewModel.getNews().observe(getViewLifecycleOwner(), newsModel -> {

            if (newsModel!=null){
                for (Data data : newsModel.getData()) {
                    dataList.add(data);
                }
                adapter.notifyDataSetChanged();
            }else {
                QuickHelp.showSimpleToast(getActivity().getApplication(), "Something is wrong");
            }

        });


        return binding.getRoot();
    }


    private void sendRequest(int _page) {
        viewModel.news("HRev4VuiVI5kp4R9bCW1bDiStjnr979lSpghPPtEZk9rdBUuoftUu35yRcNv", _page);

    }
}