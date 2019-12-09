package group.lsg.resultinvestmentapp.Adapter;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

public class ReportViewFragmentAdapter extends FragmentStatePagerAdapter {

    public ReportViewFragmentAdapter(FragmentManager
                                             fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int i) {
        return ReportViewFragment.newInstance();
    }

    @Override
    public int getCount() {
        return 1;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return ReportViewFragment.REPORT_TITLE;
    }
}


