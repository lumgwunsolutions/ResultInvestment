package group.lsg.resultinvestmentapp.Adapter;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import java.util.ArrayList;
import java.util.Calendar;

import group.lsg.resultinvestmentapp.Class.RinaUtil;

public class MonthViewFragmentAdapter extends FragmentStatePagerAdapter {

    public List<MonthViewFragment> list;

    private int monthNumber;

    private int startYear;
    private int startMonth;
    private int endYear;
    private int endMonth;

    public boolean IS_EMPTY = false;

    public MonthViewFragmentAdapter(FragmentManager fm) {
        super(fm);

        list = new ArrayList<>();
        monthNumber = 0;
        IS_EMPTY = RecordManager.RECORDS.isEmpty();

        if (!IS_EMPTY) {
            startYear =
                    RecordManager.RECORDS.get(0).getCalendar().get(Calendar.YEAR);
            endYear = RecordManager.RECORDS.
                    get(RecordManager.RECORDS.size() -
                            1).getCalendar().get(Calendar.YEAR);
            startMonth =
                    RecordManager.RECORDS.get(0).getCalendar().get(Calendar.MONTH);
            endMonth = RecordManager.RECORDS.
                    get(RecordManager.RECORDS.size() -
                            1).getCalendar().get(Calendar.MONTH);
            monthNumber = (endYear - startYear) * 12 + endMonth -
                    startMonth + 1;

            for (int i = 0; i < monthNumber; i++) {
                list.add(MonthViewFragment.newInstance(i, monthNumber));
            }
        } else {

        }
    }

    @Override
    public Fragment getItem(int i) {
        if (IS_EMPTY) return MonthViewFragment.newInstance(0, -1);
        return list.get(i);
    }

    @Override
    public int getCount() {
        if (IS_EMPTY) return 1;
        return monthNumber;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        if (IS_EMPTY) return "";
        int nowMonth = (startMonth + (monthNumber - position - 1)) % 12;
        int nowYear = startYear + (startMonth + (monthNumber -
                position - 1)) / 12;
        return RinaUtil.GetMonthShort(nowMonth + 1) + " " + nowYear;
    }
}
