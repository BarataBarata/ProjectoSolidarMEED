package mz.unilurio.solidermed.model;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentStatePagerAdapter;

import mz.unilurio.solidermed.ui.fragments.AtendidosFragment;
import mz.unilurio.solidermed.ui.fragments.NotificationFragment;
import mz.unilurio.solidermed.ui.fragments.ParturientesFragment;
import mz.unilurio.solidermed.ui.fragments.TransferidosFragment;

public class PageAdapder extends FragmentStatePagerAdapter {

    private  int tabsNumber;

    public PageAdapder(@NonNull FragmentManager fm, int behavior, int tabsNumbe) {
        super(fm, behavior);
        this.tabsNumber=tabsNumbe;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return new ParturientesFragment();

            case 1:
                return new NotificationFragment();

            case 2:
                return new AtendidosFragment();

            default: return new TransferidosFragment();
        }
    }

    @Override
    public int getCount() {
        return tabsNumber;
    }
}
