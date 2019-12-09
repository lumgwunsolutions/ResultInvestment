package group.lsg.resultinvestmentapp.Adapter;

import androidx.fragment.app.Fragment;

import group.lsg.resultinvestmentapp.Class.Application;
import group.lsg.resultinvestmentapp.HelpFeedbackFragment;

public class HelpFragmentAdapter extends FragmentStatePagerAdapter {

    private int position = 0;

    public HelpFragmentAdapter(android.support.v4.app.FragmentManager fm) {
        super(fm);
    }

    public HelpFragmentAdapter(android.support.v4.app.FragmentManager
                                       fm, int position) {
        super(fm);
        this.position = position;
    }

    @Override
    public Fragment getItem(int position) {
        switch (this.position) {
            case 0: return HelpRINAFragment.newInstance();
            case 1: return HelpFeedbackFragment.newInstance();
            case 2: return HelpAboutFragment.newInstance();
        }
        return null;
    }

    @Override
    public int getCount() {
        return 1;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (this.position) {
            case 0: return
                    Application.getAppContext().getResources().getString(R.string.app_name);
            case 1: return
                    Application.getAppContext().getResources().getString(R.string.feedback);
            case 2: return
                    Application.getAppContext().getResources().getString(R.string.about);
        }
        return "";
    }
}

