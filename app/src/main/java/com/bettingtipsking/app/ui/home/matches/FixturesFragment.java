package com.bettingtipsking.app.ui.home.matches;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.lifecycle.Lifecycle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bettingtipsking.app.databinding.FragmentFixturesBinding;
import com.bettingtipsking.app.databinding.FragmentLiveFixturesBinding;
import com.bettingtipsking.app.model.MainMatchesModel;
import com.bettingtipsking.app.model.MatchesModel;
import com.bettingtipsking.app.R;
import com.bettingtipsking.app.adapter.MainMatchesAdapter;
import com.bettingtipsking.app.ui.home.matches.fragment.ComingFixturesFragment;
import com.bettingtipsking.app.ui.home.matches.fragment.LiveFixturesFragment;
import com.bettingtipsking.app.ui.home.matches.fragment.PastFixturesFragment;
import com.bettingtipsking.app.ui.home.matches.fragment.TodayFixturesFragment;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;


public class FixturesFragment extends Fragment {
    FragmentFixturesBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentFixturesBinding.inflate(inflater, container, false);

        FragmentManager fm = getChildFragmentManager();
        ViewStateAdapter sa = new ViewStateAdapter(fm, getLifecycle());
        binding.viewpager2.setAdapter(sa);

        binding.tabLayout.addTab(binding.tabLayout.newTab().setText("Today"));
        binding.tabLayout.addTab(binding.tabLayout.newTab().setText("Live"));
        binding.tabLayout.addTab(binding.tabLayout.newTab().setText("Coming"));
        binding.tabLayout.addTab(binding.tabLayout.newTab().setText("Past"));


        binding.tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                binding.viewpager2.setCurrentItem(tab.getPosition());
            }
            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }
            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });

        binding.viewpager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                binding.tabLayout.selectTab(binding.tabLayout.getTabAt(position));
            }
        });

        return binding.getRoot();
    }
}

 class ViewStateAdapter extends FragmentStateAdapter {

    public ViewStateAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        if(position == 0) return new TodayFixturesFragment();
        if(position == 1) return new LiveFixturesFragment();
        if(position == 2) return new ComingFixturesFragment();
        if(position == 3) return new PastFixturesFragment();
        throw new IllegalStateException("Position is unexpectedly " + position);
    }

    @Override
    public int getItemCount() {
        return 4;
    }
}

