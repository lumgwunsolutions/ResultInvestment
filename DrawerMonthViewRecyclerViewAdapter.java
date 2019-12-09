package group.lsg.resultinvestmentapp.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Calendar;

import butterknife.ButterKnife;
import butterknife.Optional;
import group.lsg.resultinvestmentapp.Class.RinaUtil;
import group.lsg.resultinvestmentapp.R;

public class DrawerMonthViewRecyclerViewAdapter extends
        RecyclerView.Adapter<DrawerMonthViewRecyclerViewAdapter.viewHolder> {

    private ArrayList<Double> investments;
    private ArrayList<Integer> records;
    private ArrayList<Integer> months;
    private ArrayList<Integer> years;

    private Context mContext;

    OnItemClickListener onItemClickListener;

    public DrawerMonthViewRecyclerViewAdapter(Context context) {
        mContext = context;
        investments = new ArrayList<>();
        records = new ArrayList<>();
        months = new ArrayList<>();
        years = new ArrayList<>();

        if (RecordManager.getInstance(CoCoinApplication.getAppContext()).RECORDS.size()
                != 0) {

            int currentYear = RecordManager.RECORDS.
                    get(RecordManager.RECORDS.size() -
                            1).getCalendar().get(Calendar.YEAR);
            int currentMonth = RecordManager.RECORDS.
                    get(RecordManager.RECORDS.size() -
                            1).getCalendar().get(Calendar.MONTH);
            double currentMonthSum = 0;
            int currentMonthRecord = 0;

            for (int i = RecordManager.RECORDS.size() - 1; i >= 0; i--) {
                if
                (RecordManager.RECORDS.get(i).getCalendar().get(Calendar.YEAR) ==
                        currentYear
                        && RecordManager.RECORDS.get(i).
                        getCalendar().get(Calendar.MONTH) == currentMonth) {
                    currentMonthSum += RecordManager.RECORDS.get(i).getMoney();
                    currentMonthRecord++;
                } else {
                    investments.add(currentMonthSum);
                    records.add(currentMonthRecord);
                    years.add(currentYear);
                    months.add(currentMonth);
                    currentMonthSum = RecordManager.RECORDS.get(i).getMoney();
                    currentMonthRecord = 1;
                    currentYear =
                            RecordManager.RECORDS.get(i).getCalendar().get(Calendar.YEAR);
                    currentMonth =
                            RecordManager.RECORDS.get(i).getCalendar().get(Calendar.MONTH);
                }
            }
            investment.add(currentMonthSum);
            records.add(currentMonthRecord);
            years.add(currentYear);
            months.add(currentMonth);

        }


    }

    @Override
    public int getItemViewType(int position) {
        return 0;
    }

    @Override
    public int getItemCount() {
        return expenses.size();
    }

    @Override
    public DrawerMonthViewRecyclerViewAdapter.viewHolder
    onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_month_view_drawer, parent, false);
        return new viewHolder(view) {
        };

    }

    @Override
    public void onBindViewHolder(final viewHolder holder, final int position) {
        holder.month.setText(RINAUtil.GetMonthShort(months.get(position) + 1));


        holder.year.setText(years.get(position) + "");

        holder.sum.setText(RinaUtil.getInstance().GetInRecords(records.get(position)));

        holder.money.setText(RinaUtil.getInstance().GetInMoney((int)
                (double) (investments.get(position))));

    }

    public class viewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener {
        @Optional
        @InjectView(R.id.month)
        TextView month;
        @Optional
        @InjectView(R.id.year)
        TextView year;
        @Optional
        @InjectView(R.id.money)
        TextView money;
        @Optional
        @InjectView(R.id.sum)
        TextView sum;

        viewHolder(View view) {
            super(view);
            ButterKnife.inject(this, view);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (onItemClickListener != null) {
                onItemClickListener.onItemClick(v, getPosition());
            }
        }
    }

    public interface OnItemClickListener {
        public void onItemClick(View view, int position);
    }

    public void SetOnItemClickListener(final OnItemClickListener
                                               mItemClickListener) {
        this.onItemClickListener = mItemClickListener;
    }

}
