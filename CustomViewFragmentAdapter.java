package group.lsg.resultinvestmentapp.Adapter;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

public class CustomViewFragmentAdapter extends FragmentStatePagerAdapter {

    public CustomViewFragmentAdapter(FragmentManager
                                             fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int i) {
        return CustomViewFragment.newInstance();
    }

    @Override
    public int getCount() {
        return 1;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return "";
    }
}
