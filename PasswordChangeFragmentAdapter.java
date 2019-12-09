package group.lsg.resultinvestmentapp.Adapter;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class PasswordChangeFragmentAdapter extends FragmentPagerAdapter {

    public PasswordChangeFragmentAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return PasswordChangeFragment.newInstance(position);
    }

    @Override
    public int getCount() {
        return 3;
    }
}

