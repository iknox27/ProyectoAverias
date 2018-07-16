package iknox27.proyectoaverias.adapters;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import iknox27.proyectoaverias.fragments.BreakDownsListFragment;
import iknox27.proyectoaverias.fragments.MapFragment;

public class TabPagerAdapter extends FragmentPagerAdapter {

    int numberOfTabs;
    Bundle b;
    public TabPagerAdapter(FragmentManager fm,int tabs, Bundle b) {
        super(fm);
        numberOfTabs = tabs;
        this.b = b;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                BreakDownsListFragment tab1 = new BreakDownsListFragment();
                tab1.setArguments(b);
                return tab1;
            case 1:
                MapFragment tab2 = new MapFragment();
                tab2.setArguments(b);
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
