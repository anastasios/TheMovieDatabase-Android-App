package project.movies.popular.popularmovies;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;


public class MovieDetail extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);
        ViewPager viewPager = (ViewPager) findViewById(R.id.pager);
        viewPager.setAdapter(new CollectionPagerAdapter(getSupportFragmentManager(), MovieDetail.this));

        TabLayout tabLayout = (TabLayout) findViewById(R.id.sliding_tabs);
        tabLayout.setupWithViewPager(viewPager);
    }

    public static class CollectionPagerAdapter extends FragmentPagerAdapter {
        final int PAGE_COUNT = 2;
        private Context context;

        public CollectionPagerAdapter(FragmentManager fm, Context context) {
            super(fm);
            this.context = context;
        }

        @Override
        public Fragment getItem(int i) {
            if (i == 0)
                return new MovieDetailFragment();
            else
                return new MovieDetailFragment();
        }

        @Override
        public int getCount() {
            return PAGE_COUNT;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            if (position == 0)
                return "Movie Details";
            else
                return "Movie Reviews";
        }
    }
}