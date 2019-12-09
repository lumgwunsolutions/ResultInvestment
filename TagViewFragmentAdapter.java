package group.lsg.resultinvestmentapp.Adapter;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import group.lsg.resultinvestmentapp.Class.Application;
import group.lsg.resultinvestmentapp.Class.RinaUtil;

public class TagViewFragmentAdapter extends FragmentStatePagerAdapter {

    public TagViewFragmentAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int i) {
        return TagViewFragment.newInstance(i);
    }

    @Override
    public int getCount() {
        return RecordManager.getInstance(Application.getAppContext()).TAGS.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return RinaUtil.GetTagName(

                RecordManager.getInstance(Application.getAppContext()).TAGS.get(position
                        % RecordManager.TAGS.size()).getId());
    }
}
