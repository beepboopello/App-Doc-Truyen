package com.example.appdoctruyen.admin.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.appdoctruyen.R;
import com.example.appdoctruyen.admin.adapter.ViewPagerAdapter;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class AdminActivity extends AppCompatActivity {



    private BottomNavigationView navigationView;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViewPager();

    }



    private void initViewPager() {
        navigationView = findViewById(R.id.bottom_nav);
        viewPager = findViewById(R.id.viewPager);
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager(), FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }
            @Override
            public void onPageSelected(int position) {
                switch (position){
                    case 0: navigationView.getMenu().findItem(R.id.menu1).setChecked(true);
                        break;
                    case 1: navigationView.getMenu().findItem(R.id.menu2).setChecked(true);
                        break;
                    case 2: navigationView.getMenu().findItem(R.id.menu3).setChecked(true);
                        break;

                }
            }
            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
        navigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case  R.id.menu1:
                        viewPager.setCurrentItem(0);
                        break;
                    case R.id.menu2:
                        viewPager.setCurrentItem(1);
                        break;
                    case R.id.menu3:
                        viewPager.setCurrentItem(2);
                        break;
                }
                return true;
            }
        });
    }




}