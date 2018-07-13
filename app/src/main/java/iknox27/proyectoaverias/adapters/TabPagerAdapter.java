package iknox27.proyectoaverias.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import iknox27.proyectoaverias.fragments.BreakDownsListFragment;
import iknox27.proyectoaverias.fragments.MapFragment;

public class TabPagerAdapter extends FragmentPagerAdapter {

    int numberOfTabs;
    public TabPagerAdapter(FragmentManager fm,int tabs) {
        super(fm);
        numberOfTabs = tabs;

    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                BreakDownsListFragment tab1 = new BreakDownsListFragment();
                return tab1;
            case 1:
                MapFragment tab2 = new MapFragment();
                return tab2;
            default:
                return null;
        }

    }

    @Override
    public int getCount() {
        return numberOfTabs;
    }
}
