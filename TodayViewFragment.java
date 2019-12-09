package group.lsg.resultinvestmentapp.Class;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.github.florent37.materialviewpager.MaterialViewPagerHelper;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import group.lsg.resultinvestmentapp.R;

public class TodayViewFragment extends Fragment {

    private int position;

    private List<InvestmentPackage> list = new ArrayList<>();
    private List<Investors> listInvestor = new ArrayList<>();


    private Context mContext;

    private RecyclerView mRecyclerView;
    private RecyclerViewMaterialAdapter mAdapter;
    private RecyclerView.Adapter adapter;

    private RecyclerView.LayoutManager layoutManager;

    static final int TODAY = 0;
    static final int YESTERDAY = 1;
    static final int THIS_WEEK = 2;
    static final int LAST_WEEK = 3;
    static final int THIS_MONTH = 4;
    static final int LAST_MONTH = 5;
    static final int THIS_YEAR = 6;
    static final int LAST_YEAR = 7;

    public static TodayViewFragment newInstance(int position) {
        TodayViewFragment fragment = new TodayViewFragment();
        Bundle args = new Bundle();
        args.putInt("POSITION", position);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getContext();
        position = getArguments() != null ?
                getArguments().getInt("POSITION") : 1;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.today_fragment, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        layoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setHasFixedSize(true);

        Calendar now = Calendar.getInstance();
        Calendar leftRange;
        Calendar rightRange;

        RecordManager recordManager =
                RecordManager.getInstance(mContext.getApplicationContext());
        int start = -1;
        int end = 0;

        switch (position) {
            case TODAY:
                leftRange = RinaUtil.GetTodayLeftRange(now);
                for (int i = recordManager.RECORDS.size() - 1; i >= 0; i--) {
                    if
                    (recordManager.RECORDS.get(i).getCalendar().before(leftRange)) {
                        end = i + 1;
                        break;
                    }
                    if (start == -1) {
                        start = i;
                    }
                }
                break;
            case YESTERDAY:
                leftRange = RinaUtil.GetYesterdayLeftRange(now);
                rightRange = RinaUtil.GetYesterdayRightRange(now);
                for (int i = recordManager.RECORDS.size() - 1; i >= 0; i--) {
                    if
                    (recordManager.RECORDS.get(i).getCalendar().before(leftRange)) {
                        end = i + 1;
                        break;
                    } else if
                    (!recordManager.RECORDS.get(i).getCalendar().after(rightRange)) {
                        if (start == -1) {
                            start = i;
                        }
                    }
                }
                break;
            case THIS_WEEK:
                leftRange = RinaUtil.GetThisWeekLeftRange(now);
                for (int i = recordManager.RECORDS.size() - 1; i >= 0; i--) {
                    if
                    (recordManager.RECORDS.get(i).getCalendar().before(leftRange)) {
                        end = i + 1;
                        break;
                    }
                    if (start == -1) {
                        start = i;
                    }
                }
                break;
            case LAST_WEEK:
                leftRange = RinaUtil.GetLastWeekLeftRange(now);
                rightRange = RinaUtil.GetLastWeekRightRange(now);
                for (int i = recordManager.RECORDS.size() - 1; i >= 0; i--) {
                    if
                    (recordManager.RECORDS.get(i).getCalendar().before(leftRange)) {
                        end = i + 1;
                        break;
                    } else if
                    (recordManager.RECORDS.get(i).getCalendar().before(rightRange)) {
                        if (start == -1) {
                            start = i;
                        }
                    }
                }
                break;
            case THIS_MONTH:
                leftRange = RinaUtil.GetThisMonthLeftRange(now);
                for (int i = recordManager.RECORDS.size() - 1; i >= 0; i--) {
                    if
                    (recordManager.RECORDS.get(i).getCalendar().before(leftRange)) {
                        end = i + 1;
                        break;
                    }
                    if (start == -1) {
                        start = i;
                    }
                }
                break;
            case LAST_MONTH:
                leftRange = RinaUtil.GetLastMonthLeftRange(now);
                rightRange = RinaUtil.GetLastMonthRightRange(now);
                for (int i = recordManager.RECORDS.size() - 1; i >= 0; i--) {
                    if
                    (recordManager.RECORDS.get(i).getCalendar().before(leftRange)) {
                        end = i + 1;
                        break;
                    } else if
                    (recordManager.RECORDS.get(i).getCalendar().before(rightRange)) {
                        if (start == -1) {
                            start = i;
                        }
                    }
                }
                break;
            case THIS_YEAR:
                leftRange = RinaUtil.GetThisYearLeftRange(now);
                for (int i = recordManager.RECORDS.size() - 1; i >= 0; i--) {
                    if
                    (recordManager.RECORDS.get(i).getCalendar().before(leftRange)) {
                        end = i + 1;
                        break;
                    }
                    if (start == -1) {
                        start = i;
                    }
                }
                break;
            case LAST_YEAR:
                leftRange = RinaUtil.GetLastYearLeftRange(now);
                rightRange = RinaUtil.GetLastYearRightRange(now);
                for (int i = recordManager.RECORDS.size() - 1; i >= 0; i--) {
                    if
                    (recordManager.RECORDS.get(i).getCalendar().before(leftRange)) {
                        end = i + 1;
                        break;
                    } else if
                    (recordManager.RECORDS.get(i).getCalendar().before(rightRange)) {
                        if (start == -1) {
                            start = i;
                        }
                    }
                }
                break;
        }

        adapter = new TodayViewRecyclerViewAdapter(start, end,
                mContext, position);

        mAdapter = new RecyclerViewMaterialAdapter(adapter);
        mRecyclerView.setAdapter(mAdapter);

        MaterialViewPagerHelper.registerRecyclerView(getActivity(),
                mRecyclerView, null);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        RefWatcher refWatcher = Application.getRefWatcher(getActivity());
        refWatcher.watch(this);

    }

}

