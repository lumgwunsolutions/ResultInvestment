package group.lsg.resultinvestmentapp.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.util.TableInfo;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Map;

import butterknife.Optional;
import group.lsg.resultinvestmentapp.Class.InvestmentPackage;
import group.lsg.resultinvestmentapp.Class.RinaUtil;
import group.lsg.resultinvestmentapp.Class.SettingManager;
import group.lsg.resultinvestmentapp.R;

public class TagViewRecyclerViewAdapter
        extends RecyclerView.Adapter<TagViewRecyclerViewAdapter.viewHolder> {

    private Context mContext;

    private List<List<InvestmentPackage>> contents;
    private List<Integer> type;
    private List<Double> SumList;
    private List<Map<Integer, Double>> AllTagInvestment;
    private int[] DayInvestmentSum;
    private int[] MonthInvestmentSum;
    private int[] SelectedPosition;
    private float Sum;
    private int year;
    private int month;
    private int startYear;
    private int startMonth;
    private int endYear;
    private int endMonth;

    private String dialogTitle;

    private int chartType;

    static final int TYPE_HEADER = 0;
    static final int TYPE_CELL = 1;

    static final Integer SHOW_IN_YEAR = 0;
    static final Integer SHOW_IN_MONTH = 1;

    static final int PIE = 0;
    static final int SUM_HISTOGRAM = 1;
    static final int HISTOGRAM = 2;

    private int fragmentPosition;
    private int fragmentTagId;

    private boolean IS_EMPTY = false;

    public TagViewRecyclerViewAdapter(List<InvestmentPackage> iP,
                                      Context context, int position) {

        mContext = context;
        fragmentPosition = position;
        if (position == 0) {
            chartType = PIE;
        } else if (position == 1) {
            chartType = SUM_HISTOGRAM;
        } else {
            chartType = HISTOGRAM;
        }

        IS_EMPTY = iP.isEmpty();

        Sum = 0;

        if (!IS_EMPTY) {

            Collections.sort(iP, new Comparator<InvestmentPackage>() {
                @Override
                public int compare(InvestmentPackage lhs,
                                   InvestmentPackage rhs) {
                    return rhs.getCalendar().compareTo(lhs.getCalendar());
                }
            });
            contents = new ArrayList<>();
            SumList = new ArrayList<>();
            type = new ArrayList<>();
            year = iP.get(0).getCalendar().get(Calendar.YEAR);
            month = iP.get(0).getCalendar().get(Calendar.MONTH) + 1;
            endYear = year;
            endMonth = month;
            int yearPosition = 0;
            double monthSum = 0;
            double yearSum = 0;
            List<InvestmentPackage> yearSet = new ArrayList<>();
            List<InvestmentPackage> monthSet = new ArrayList<>();

            for (InvestmentPackage iP : iPS) {
                Sum += iP.getAmountInvested();
                if (iP.getCalendar().get(Calendar.YEAR) == year) {
                    yearSet.add(iP);
                    yearSum += iP.getAmountInvested();
                    if (iP.getCalendar().get(Calendar.MONTH) == month - 1) {
                        monthSet.add(iP);
                        monthSum += iP.getAmountInvested();
                    } else {
                        contents.add(monthSet);
                        SumList.add(monthSum);
                        monthSum = iP.getAmountInvested();
                        type.add(SHOW_IN_MONTH);
                        monthSet = new ArrayList<>();
                        monthSet.add(iP);
                        month = iP.getCalendar().get(Calendar.MONTH) + 1;
                    }
                } else {
                    contents.add(monthSet);
                    SumList.add(monthSum);
                    monthSum = iP.getAmountInvested();
                    type.add(SHOW_IN_MONTH);
                    monthSet = new ArrayList<>();
                    monthSet.add(iP);
                    month = iP.getCalendar().get(Calendar.MONTH) + 1;

                    contents.add(yearPosition, yearSet);
                    SumList.add(yearPosition, yearSum);
                    yearSum = iP.getAmountInvested();
                    type.add(yearPosition, SHOW_IN_YEAR);
                    yearPosition = contents.size();
                    yearSet = new ArrayList<>();
                    yearSet.add(iP);
                    year = iP.getCalendar().get(Calendar.YEAR);
                    monthSet = new ArrayList<>();
                    monthSet.add(iP);
                    month = iP.getCalendar().get(Calendar.MONTH) + 1;
                }
            }
            contents.add(monthSet);
            SumList.add(monthSum);
            type.add(SHOW_IN_MONTH);
            contents.add(yearPosition, yearSet);
            SumList.add(yearPosition, yearSum);
            type.add(yearPosition, SHOW_IN_YEAR);

            startYear = year;
            startMonth = month;

            if (chartType == PIE) {
                AllTagInvestment = new ArrayList<>();
                for (int i = 0; i < contents.size(); i++) {

                    Map<Integer, Double> tagInvestment = new TreeMap<>();

                    for (Tag tag : RecordManager.TAGS) {
                        tagInvestment.put(tag.getId(), Double.valueOf(0));
                    }

                    for (InvestmentPackage iP : contents.get(i)) {
                        double d = tagInvestment.get(iP.getTag());
                        d += iP.getAmountInvested();
                        tagInvestment.put(iP.getTag(), d);
                    }

                    tagInvestment = RINAUtil.SortTreeMapByValues(tagInvestment);

                    AllTagInvestment.add(tagInvestment);
                }
            }

            if (chartType == SUM_HISTOGRAM) {
                DayInvestmentSum = new int[(endYear - startYear + 1) * 372];
                for (InvestmentPackage iP : iPS) {
                    DayExpanseSum[(iP.getCalendar().get(Calendar.YEAR)
                            - startYear) * 372 +
                            iP.getCalendar().get(Calendar.MONTH) * 31 +

                            iP.getCalendar().get(Calendar.DAY_OF_MONTH) - 1] += (int)
                            iP.getMoney();
                }
            }

            MonthInvestmentSum = new int[(endYear - startYear + 1) * 12];
            for (InvestmentPackage iP : iPS) {

                MonthInvestmentSum[(iP.getCalendar().get(Calendar.YEAR) - startYear) *
                        12 +
                        iP.getCalendar().get(Calendar.MONTH)] += (int)
                        iP.getAmountInvested();
            }

            SelectedPosition = new int[contents.size() + 1];
            for (int i = 0; i < SelectedPosition.length; i++) {
                SelectedPosition[i] = 0;
            }

            fragmentTagId = contents.get(0).get(0).getTag();
            if (fragmentPosition == 0) {
                fragmentTagId = -2;
            }
            if (fragmentPosition == 1) {
                fragmentTagId = -1;
            }
        }
    }

    @Override
    public int getItemViewType(int position) {
        switch (position) {
            case 0:
                return TYPE_HEADER;
            default:
                return TYPE_CELL;
        }
    }

    @Override
    public int getItemCount() {
        if (IS_EMPTY) return 1;
        return contents.size() + 1;
    }

    @Override
    public TagViewRecyclerViewAdapter.viewHolder onCreateViewHolder(
            ViewGroup parent, int viewType) {

        View view;

        switch (viewType) {
            case TYPE_HEADER: {
                view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.tag_list_view_head, parent, false);
                return new viewHolder(view) {
                };
            }
            case TYPE_CELL: {
                switch (chartType) {
                    case PIE:
                        view = LayoutInflater.from(parent.getContext())

                                .inflate(R.layout.tag_list_view_pie_body, parent, false);
                        return new viewHolder(view) {
                        };
                    case HISTOGRAM:
                        view = LayoutInflater.from(parent.getContext())

                                .inflate(R.layout.tag_list_view_histogram_body, parent, false);
                        return new viewHolder(view) {
                        };
                    case SUM_HISTOGRAM:
                        view = LayoutInflater.from(parent.getContext())

                                .inflate(R.layout.tag_list_view_histogram_body, parent, false);
                        return new viewHolder(view) {
                        };
                }
            }
        }

        return null;
    }


    @Override
    public void onBindViewHolder(final viewHolder holder, final int position) {

        switch (getItemViewType(position)) {
            case TYPE_HEADER:
                if (IS_EMPTY) {

                    holder.sum.setText(mContext.getResources().getString(R.string.tag_empty));
                    holder.sum.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
                    holder.from.setVisibility(View.INVISIBLE);
                    holder.to.setVisibility(View.INVISIBLE);
                } else {
                    holder.from.setText(

                            mContext.getResources().getString(R.string.from) + " " +
                                    startYear + " " +
                                    RinaUtil.GetMonthShort(startMonth));
                    holder.sum.setText(RinaUtil.GetInMoney((int)Sum));
                    holder.to.setText(
                            mContext.getResources().getString("to") + " " +
                                    endYear + " " +
                                    RinaUtil.GetMonthShort(endMonth));
                    holder.to.setTypeface(RinaUtil.GetTypeface());
                    holder.from.setTypeface(RinaUtil.GetTypeface());
                }
                holder.sum.setTypeface(RinaUtil.typefaceLatoLight);
                break;
            case TYPE_CELL:
                int year = contents.get(position -
                        1).get(0).getCalendar().get(Calendar.YEAR);
                int month = contents.get(position -
                        1).get(0).getCalendar().get(Calendar.MONTH) + 1;
                PieChartData pieChartData;
                List<SubcolumnValue> subcolumnValues;
                final List<TableInfo.Column> columns;
                ColumnChartData columnChartData;
                final List<SliceValue> sliceValues;
                holder.date.setTypeface(RinaUtil.GetTypeface());
                holder.investment.setTypeface(RinaUtil.GetTypeface());
                switch (chartType) {
                    case PIE:
                        sliceValues = new ArrayList<>();
                        for (Map.Entry<Integer, Double> entry :
                                AllTagInvestment.get(position - 1).entrySet()) {
                            if (entry.getValue() >= 1) {
                                SliceValue sliceValue = new SliceValue(
                                        (float)(double)entry.getValue(),
                                        mContext.getResources().

                                                getColor(RinaUtil.GetTagColorResource(entry.getKey())));

                                sliceValue.setLabel(String.valueOf(entry.getKey()));
                                sliceValues.add(sliceValue);
                            }
                        }

                        pieChartData = new PieChartData(sliceValues);
                        pieChartData.setHasLabels(false);
                        pieChartData.setHasLabelsOnlyForSelected(false);
                        pieChartData.setHasLabelsOutside(false);

                        pieChartData.setHasCenterCircle(SettingManager.getInstance().getIsHollow());

                        holder.pie.setPieChartData(pieChartData);
                        holder.pie.setOnValueTouchListener(new
                                PieValueTouchListener(position - 1));
                        holder.pie.setChartRotationEnabled(false);

                        if (type.get(position - 1).equals(SHOW_IN_MONTH)) {
                            holder.date.setText(year + " " +
                                    RinaUtil.GetMonthShort(month));
                        } else {
                            holder.date.setText(year + " ");
                        }


                        holder.investment.setText(RinaUtil.GetInMoney((int) (double)
                                SumList.get(position - 1)));

                        holder.iconRight.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                SelectedPosition[position]
                                        = (SelectedPosition[position]
                                        + 1) % sliceValues.size();
                                SelectedValue selectedValue =
                                        new SelectedValue(
                                                SelectedPosition[position],
                                                0,
                                                SelectedValue.SelectedValueType.NONE);
                                holder.pie.selectValue(selectedValue);
                            }
                        });

                        holder.iconLeft.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                SelectedPosition[position]
                                        = (SelectedPosition[position]
                                        - 1 + sliceValues.size()) % sliceValues.size();
                                SelectedValue selectedValue =
                                        new SelectedValue(
                                                SelectedPosition[position],
                                                0,
                                                SelectedValue.SelectedValueType.NONE);
                                holder.pie.selectValue(selectedValue);
                                                                       }
                                                                   });

                        break;
                    case SUM_HISTOGRAM:
                        columns = new ArrayList<>();
                        if (type.get(position - 1).equals(SHOW_IN_YEAR)) {
                            int numColumns = 12;
                            for (int i = 0; i < numColumns; i++) {
                                subcolumnValues = new ArrayList<>();
                                SubcolumnValue value = new SubcolumnValue(
                                        MonthInvestmentSum[(year -
                                                startYear) * 12 + i],
                                        RINAUtil.GetRandomColor());

                                value.setLabel(RinaUtil.MONTHS_SHORT[month] + " " + year);
                                subcolumnValues.add(value);
                                TableInfo.Column column = new TableInfo.Column(subcolumnValues);
                                column.setHasLabels(false);
                                column.setHasLabelsOnlyForSelected(false);
                                columns.add(column);
                            }

                            columnChartData = new ColumnChartData(columns);

                            Axis axisX = new Axis();
                            List<AxisValue> axisValueList = new ArrayList<>();
                            for (int i = 0; i < numColumns; i++) {
                                axisValueList.add(new AxisValue(i)

                                        .setLabel(RinaUtil.GetMonthShort(i + 1)));
                            }
                            axisX.setValues(axisValueList);
                            Axis axisY = new Axis().setHasLines(true);
                            columnChartData.setAxisXBottom(axisX);
                            columnChartData.setAxisYLeft(axisY);
                            columnChartData.setStacked(true);

                            holder.chart.setColumnChartData(columnChartData);
                            holder.chart.setZoomEnabled(false);
                            holder.chart.setOnValueTouchListener(
                                    new ValueTouchListener(position - 1));

                            holder.date.setText(year + "");

                            holder.expanse.setText(CoCoinUtil.GetInMoney((int)(double)SumList.get(position
                                    - 1)));
                        }
                        if (type.get(position - 1).equals(SHOW_IN_MONTH)) {
                            Calendar tempCal = new
                                    GregorianCalendar(year, month - 1, 1);
                            int daysInMonth =
                                    tempCal.getActualMaximum(Calendar.DAY_OF_MONTH);

                            int numColumns = daysInMonth;

                            for (int i = 0; i < numColumns; ++i) {
                                subcolumnValues = new ArrayList<>();
                                SubcolumnValue value = new
                                        SubcolumnValue((float)
                                        DayInvestmentSum[(year -
                                                startYear) * 372
                                                + (month - 1) * 31 + i],
                                        RinaUtil.GetRandomColor());

                                value.setLabel(RinaUtil.MONTHS_SHORT[month] + " " + (i + 1) + " " +
                                        year);
                                subcolumnValues.add(value);
                                TableInfo.Column column = new TableInfo.Column(subcolumnValues);
                                column.setHasLabels(false);
                                column.setHasLabelsOnlyForSelected(false);
                                columns.add(column);
                            }

                            columnChartData = new ColumnChartData(columns);

                            Axis axisX = new Axis();
                            List<AxisValue> axisValueList = new ArrayList<>();
                            for (int i = 0; i < daysInMonth; i++) {
                                axisValueList.add(new
                                        AxisValue(i).setLabel(i + 1 + ""));
                            }
                            axisX.setValues(axisValueList);
                            Axis axisY = new Axis().setHasLines(true);
                            columnChartData.setAxisXBottom(axisX);
                            columnChartData.setAxisYLeft(axisY);
                            columnChartData.setStacked(true);

                            holder.chart.setColumnChartData(columnChartData);
                            holder.chart.setZoomEnabled(false);
                            holder.chart.setOnValueTouchListener(new
                                    ValueTouchListener(position - 1));

                            holder.date.setText(year + " " +
                                    RINAUtil.GetMonthShort(month));

                            holder.expanse.setText(RINAUtil.GetInMoney((int)(double)SumList.get(position
                                    - 1)));
                        }

                        holder.iconRight.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                do {
                                    SelectedPosition[position]
                                            =
                                            (SelectedPosition[position] + 1) % columns.size();
                                } while
                                (holder.chart.getChartData().getColumns()
                                        .get(SelectedPosition[position])
                                        .getValues().size() == 0 ||
                                        holder.chart.getChartData().getColumns()
                                                .get(SelectedPosition[position])
                                                .getValues().get(0).getValue() == 0);
                                SelectedValue selectedValue =
                                        new SelectedValue(
                                                SelectedPosition[position],
                                                0,
                                                SelectedValue.SelectedValueType.COLUMN);
                                holder.chart.selectValue(selectedValue);
                            }
                        });
                        holder.iconLeft.setOnClickListener(new View.OnClickListener() {
                                                                       @Override
                                                                       public void onClick(View v) {
                                                                           do {
                                                                               SelectedPosition[position]
                                                                                       =
                                                                                       (SelectedPosition[position] - 1 + columns.size())
                                                                                               % columns.size();
                                                                           } while
                                                                           (holder.chart.getChartData().getColumns()
                                                                                   .get(SelectedPosition[position])
                                                                                   .getValues().size() == 0 ||

                                                                                   holder.chart.getChartData().getColumns()

                                                                                           .get(SelectedPosition[position])
                                                                                           .getValues().get(0).getValue() == 0);
                                                                           SelectedValue selectedValue =
                                                                                   new SelectedValue(
                                                                                           SelectedPosition[position],
                                                                                           0,

                                                                                           SelectedValue.SelectedValueType.COLUMN);
                                                                           holder.chart.selectValue(selectedValue);
                                                                       }
                                                                   });

                        break;
                    case HISTOGRAM:
                        columns = new ArrayList<>();
                        if (type.get(position - 1).equals(SHOW_IN_YEAR)) {
                            int numColumns = 12;
                            for (int i = 0; i < numColumns; i++) {
                                subcolumnValues = new ArrayList<>();
                                SubcolumnValue value = new SubcolumnValue(
                                        MonthInvestmentSum[(year -
                                                startYear) * 12 + i],
                                        RinaUtil.GetRandomColor());

                                value.setLabel(RINAUtil.MONTHS_SHORT[month] + " " + year);
                                subcolumnValues.add(value);
                                TableInfo.Column column = new TableInfo.Column(subcolumnValues);
                                column.setHasLabels(false);
                                column.setHasLabelsOnlyForSelected(false);
                                columns.add(column);
                            }

                            columnChartData = new ColumnChartData(columns);

                            Axis axisX = new Axis();
                            List<AxisValue> axisValueList = new ArrayList<>();
                            for (int i = 0; i < numColumns; i++) {
                                axisValueList.add(new AxisValue(i)

                                        .setLabel(CoCoinUtil.GetMonthShort(i + 1)));
                            }
                            axisX.setValues(axisValueList);
                            Axis axisY = new Axis().setHasLines(true);
                            columnChartData.setAxisXBottom(axisX);
                            columnChartData.setAxisYLeft(axisY);
                            columnChartData.setStacked(true);

                            holder.chart.setColumnChartData(columnChartData);
                            holder.chart.setZoomEnabled(false);
                            holder.chart.setOnValueTouchListener(
                                    new ValueTouchListener(position - 1));

                            holder.date.setText(year + "");

                            holder.investment.setText(RinaUtil.GetInMoney((int)(double)SumList.get(position
                                    - 1)));

                        }
                        if (type.get(position - 1).equals(SHOW_IN_MONTH)) {
                            Calendar tempCal = Calendar.getInstance();
                            tempCal.set(year, month - 1, 1);
                            tempCal.add(Calendar.SECOND, 0);
                            int daysInMonth =
                                    tempCal.getActualMaximum(Calendar.DAY_OF_MONTH);
                            int p = contents.get(position - 1).size() - 1;
                            int numColumns = daysInMonth;

                            for (int i = 0; i < numColumns; ++i) {
                                subcolumnValues = new ArrayList<>();
                                SubcolumnValue value = new SubcolumnValue(0,
                                        CoCoinUtil.GetRandomColor());
                                subcolumnValues.add(value);
                                while (p >= 0
                                        && contents.get(position -
                                        1).get(p).getCalendar().
                                        get(Calendar.DAY_OF_MONTH) == i + 1) {
                                    subcolumnValues.get(0).setValue(
                                            subcolumnValues.get(0).getValue() +
                                                    (float)
                                                            contents.get(position - 1).get(p).getMoney());
                                    subcolumnValues.get(0).setLabel(p + "");
                                    p--;
                                }
                                Column column = new Column(subcolumnValues);
                                column.setHasLabels(false);
                                column.setHasLabelsOnlyForSelected(false);
                                columns.add(column);
                            }

                            columnChartData = new ColumnChartData(columns);

                            Axis axisX = new Axis();
                            List<AxisValue> axisValueList = new ArrayList<>();
                            for (int i = 0; i < daysInMonth; i++) {
                                axisValueList.add(new
                                        AxisValue(i).setLabel(i + 1 + ""));
                            }
                            axisX.setValues(axisValueList);
                            Axis axisY = new Axis().setHasLines(true);
                            columnChartData.setAxisXBottom(axisX);
                            columnChartData.setAxisYLeft(axisY);
                            columnChartData.setStacked(true);

                            holder.chart.setColumnChartData(columnChartData);
                            holder.chart.setZoomEnabled(false);
                            holder.chart.setOnValueTouchListener(new
                                    ValueTouchListener(position - 1));

                            holder.date.setText(year + " " +
                                    RinaUtil.GetMonthShort(month));

                            holder.investment.setText(RinaUtil.GetInMoney((int)(double)SumList.get(position
                                    - 1)));
                        }

                        holder.iconRight.setOnClickListener(new
                                                                    View.OnClickListener() {
                                                                        @Override
                                                                        public void onClick(View v) {
                                                                            do {
                                                                                SelectedPosition[position]
                                                                                        =
                                                                                        (SelectedPosition[position] + 1) % columns.size();
                                                                            } while
                                                                            (holder.chart.getChartData().getColumns()
                                                                                    .get(SelectedPosition[position])
                                                                                    .getValues().size() == 0 ||

                                                                                    holder.chart.getChartData().getColumns()

                                                                                            .get(SelectedPosition[position])
                                                                                            .getValues().get(0).getValue() == 0);
                                                                            SelectedValue selectedValue =
                                                                                    new SelectedValue(
                                                                                            SelectedPosition[position],
                                                                                            0,

                                                                                            SelectedValue.SelectedValueType.NONE);
                                                                            holder.chart.selectValue(selectedValue);
                                                                        }
                                                                    });

                        holder.iconLeft.setOnClickListener(new
                                                                   View.OnClickListener() {
                                                                       @Override
                                                                       public void onClick(View v) {
                                                                           do {
                                                                               SelectedPosition[position]
                                                                                       =
                                                                                       (SelectedPosition[position] - 1 + columns.size())
                                                                                               % columns.size();
                                                                           } while
                                                                           (holder.chart.getChartData().getColumns()
                                                                                   .get(SelectedPosition[position])
                                                                                   .getValues().size() == 0 ||
                                                                                   holder.chart.getChartData().getColumns()
                                                                                           .get(SelectedPosition[position])
                                                                                           .getValues().get(0).getValue() == 0);
                                                                           SelectedValue selectedValue =
                                                                                   new SelectedValue(
                                                                                           SelectedPosition[position],
                                                                                           0,

                                                                                           SelectedValue.SelectedValueType.NONE);
                                                                           holder.chart.selectValue(selectedValue);
                                                                       }
                                                                   });

                        break;
                }
        }
    }

    public static class viewHolder extends RecyclerView.ViewHolder {
        @Optional
        @InjectView(R.id.from)
        TextView from;
        @Optional
        @InjectView(R.id.sum)
        TextView sum;
        @Optional
        @InjectView(R.id.to)
        TextView to;
        @Optional
        @InjectView(R.id.chart_pie)
        PieChartView pie;
        @Optional
        @InjectView(R.id.chart)
        ColumnChartView chart;
        @Optional
        @InjectView(R.id.date)
        TextView date;
        @Optional
        @InjectView(R.id.expanse)
        TextView expanse;
        @Optional
        @InjectView(R.id.icon_left)
        MaterialIconView iconLeft;
        @Optional
        @InjectView(R.id.icon_right)
        MaterialIconView iconRight;

        viewHolder(View view) {
            super(view);
            ButterKnife.inject(this, view);
        }
    }

    private class ValueTouchListener implements
            ColumnChartOnValueSelectListener {

        private int position;

        public ValueTouchListener(int position) {
            this.position = position;
        }

        @Override
        public void onValueSelected(final int columnIndex, int
                subColumnIndex, SubcolumnValue value) {
            Snackbar snackbar =
                    Snackbar.with(mContext)
                            .type(SnackbarType.MULTI_LINE)
                            .duration(Snackbar.SnackbarDuration.LENGTH_SHORT)
                            .position(Snackbar.SnackbarPosition.BOTTOM)
                            .margin(15, 15)

                            .backgroundDrawable(RinaUtil.GetSnackBarBackground(fragmentTagId))
                            .textColor(Color.WHITE)
                            .textTypeface(RinaUtil.GetTypeface())

                            .actionLabel(mContext.getResources().getString("check"))
                            .actionLabelTypeface(RinaUtil.typefaceLatoLight)
                            .actionColor(Color.WHITE);
            if (fragmentPosition == SUM_HISTOGRAM) {
                if (type.get(position).equals(SHOW_IN_MONTH)) {
                    String text = "";
                    String timeString =
                            contents.get(position).get(0).getCalendarString();
                    int month =
                            contents.get(position).get(0).getCalendar().get(Calendar.MONTH) + 1;
                    timeString = " " + RinaUtil.GetMonthShort(month)
                            + " " + (columnIndex + 1) + " "
                            + timeString.substring(timeString.length()
                            - 4, timeString.length());
                    if ("zh".equals(RinaUtil.GetLanguage())) {
                        text = mContext.getResources().getString("on")
                                + timeString + "\n" +
                                RinaUtil.GetSpendString((int) value.getValue());
                        dialogTitle =
                                mContext.getResources().getString("on") + timeString +
                                        RinaUtil.GetSpendString((int) value.getValue());
                    } else {
                        text = RinaUtil.GetSpendString((int)
                                value.getValue()) + "\n" +

                                mContext.getResources().getString("on") + timeString;
                        dialogTitle = RinaUtil.GetSpendString((int)
                                value.getValue()) +

                                mContext.getResources().getString(R.string.on) + timeString;
                    }
                    snackbar.text(text);
                    snackbar.actionListener(new ActionClickListener() {
                        @Override
                        public void onActionClicked(Snackbar snackbar) {
                            List<InvestmentPackage>
                                    shownInvestmentPackages = new ArrayList<>();
                            boolean isSamed = false;
                            for (InvestmentPackage iP :
                                    contents.get(position)) {
                                if
                                (iP.getCalendar().get(Calendar.DAY_OF_MONTH) == columnIndex + 1) {
                                    shownInvestmentPackages.add(iP);
                                    isSamed = true;
                                } else {
                                    if (isSamed) {
                                        break;
                                    }
                                }
                            }

                            ((FragmentActivity)mContext).getSupportFragmentManager()
                                    .beginTransaction()
                                    .add(new RecordCheckDialogFragment(
                                            mContext,
                                            shownInvestmentPackages, dialogTitle), "MyDialog")
                                    .commit();
                        }
                    });
                    SnackbarManager.show(snackbar);
                }
                if (type.get(position).equals(SHOW_IN_YEAR)) {
                    String text;
                    String timeString = " " +

                            contents.get(position).get(0).getCalendar().get(Calendar.YEAR);
                    timeString = " " +
                            RinaUtil.GetMonthShort(columnIndex + 1) + " "
                            + timeString.substring(timeString.length()
                            - 4, timeString.length());
                    if ("zh".equals(RinaUtil.GetLanguage())) {
                        text = mContext.getResources().getString("in")
                                + timeString + "\n" +
                                RinaUtil.GetSpendString((int) value.getValue());
                        dialogTitle =
                                mContext.getResources().getString("in") + timeString +
                                        RinaUtil.GetSpendString((int) value.getValue());
                    } else {
                        text = RinaUtil.GetSpendString((int)
                                value.getValue()) + "\n" +

                                mContext.getResources().getString("in") + timeString;
                        dialogTitle = RinaUtil.GetSpendString((int)
                                value.getValue()) +

                                mContext.getResources().getString(R.string.in) + timeString;
                    }
                    snackbar.text(text);
                    snackbar.actionListener(new ActionClickListener() {
                        @Override
                        public void onActionClicked(Snackbar snackbar) {
                            List<InvestmentPackage>
                                    shownInvestmentPackages = new ArrayList<>();
                            boolean isSamed = false;
                            for (InvestmentPackage iP :
                                    contents.get(position)) {
                                if
                                (iP.getCalendar().get(Calendar.MONTH) == columnIndex) {
                                    shownCoCoinRecords.add(iP);
                                    isSamed = true;
                                } else {
                                    if (isSamed) {
                                        break;
                                    }
                                }
                            }

                            ((FragmentActivity)mContext).getSupportFragmentManager()
                                    .beginTransaction()
                                    .add(new RecordCheckDialogFragment(
                                            mContext,
                                            shownInvestmentPackages, dialogTitle), "MyDialog")
                                    .commit();
                        }
                    });
                    SnackbarManager.show(snackbar);
                }
            } else {
                if (type.get(position).equals(SHOW_IN_MONTH)) {
                    String text = "";
                    String timeString =
                            contents.get(position).get(0).getCalendarString();
                    timeString = timeString.substring(6, timeString.length());
                    int month =
                            contents.get(position).get(0).getCalendar().get(Calendar.MONTH) + 1;
                    timeString = " " + RinaUtil.GetMonthShort(month)
                            + " " + (columnIndex + 1) + " "
                            + timeString.substring(timeString.length()
                            - 4, timeString.length());
                    if ("zh".equals(RinaUtil.GetLanguage())) {
                        text = mContext.getResources().getString("on")
                                + timeString +
                                RinaUtil.GetSpendString((int)
                                        value.getValue()) + "\n" +
                                "RINA" +
                                RinaUtil.GetTagName(contents.get(position).get(0).getTag());
                        dialogTitle =
                                mContext.getResources().getString("on") + timeString +
                                        RinaUtil.GetSpendString((int)
                                                value.getValue()) + "\n" +
                                        "RINA" +
                                        RinaUtil.GetTagName(contents.get(position).get(0).getTag());
                    } else {
                        text = RinaUtil.GetSpendString((int) value.getValue()) +

                                mContext.getResources().getString(R.string.on) + timeString + "\n"
                                + "in " +
                                RinaUtil.GetTagName(contents.get(position).get(0).getTag());
                        dialogTitle = RinaUtil.GetSpendString((int)
                                value.getValue()) +

                                mContext.getResources().getString(R.string.on) + timeString + "\n"
                                + "in " +
                                RinaUtil.GetTagName(contents.get(position).get(0).getTag());
                    }
                    snackbar.text(text);
                    snackbar.actionListener(new ActionClickListener() {
                        @Override
                        public void onActionClicked(Snackbar snackbar) {
                            List<InvestmentPackage>
                                    shownInvestmentPackages = new ArrayList<>();
                            boolean isSamed = false;
                            for (InvestmentPackage iP :
                                    contents.get(position)) {
                                if (iP.getCalendar().
                                        get(Calendar.DAY_OF_MONTH) ==
                                        columnIndex + 1) {
                                    shownInvestmentPackages.add(iP);
                                    isSamed = true;
                                } else {
                                    if (isSamed) {
                                        break;
                                    }
                                }
                            }

                            ((FragmentActivity)mContext).getSupportFragmentManager()
                                    .beginTransaction()
                                    .add(new RecordCheckDialogFragment(
                                            mContext,
                                            shownInvestmentPackages, dialogTitle), "MyDialog")
                                    .commit();
                        }
                    });
                    SnackbarManager.show(snackbar);
                }
                if (type.get(position).equals(SHOW_IN_YEAR)) {
                    String text;
                    String timeString = "" +

                            contents.get(position).get(0).getCalendar().get(Calendar.YEAR);
                    timeString = " " +
                            RinaUtil.GetMonthShort(columnIndex + 1) + " "
                            + timeString.substring(timeString.length()
                            - 4, timeString.length());
                    if ("zh".equals(RinaUtil.GetLanguage())) {
                        text = mContext.getResources().getString("in")
                                + timeString +
                                RinaUtil.GetSpendString((int)
                                        value.getValue()) + "\n" +
                                "RINA" +
                                RinaUtil.GetTagName(contents.get(position).get(0).getTag());
                        dialogTitle =
                                mContext.getResources().getString(R.string.in) + timeString +
                                        RinaUtil.GetSpendString((int)
                                                value.getValue()) + "\n" +
                                        "NAIJA" +
                                        RinaUtil.GetTagName(contents.get(position).get(0).getTag());
                    } else {
                        text = RinaUtil.GetSpendString((int) value.getValue()) +

                                mContext.getResources().getString(R.string.in) + timeString + "\n"
                                + "in " +
                                RinaUtil.GetTagName(contents.get(position).get(0).getTag());
                        dialogTitle = RinaUtil.GetSpendString((int)
                                value.getValue()) +

                                mContext.getResources().getString(R.string.in) + timeString + "\n"
                                + "in " +
                                RinaUtil.GetTagName(contents.get(position).get(0).getTag());
                    }
                    snackbar.text(text);
                    snackbar.actionListener(new ActionClickListener() {
                        @Override
                        public void onActionClicked(Snackbar snackbar) {
                            List<InvestmentPackage>
                                    shownInvestmentPackages = new ArrayList<>();
                            boolean isSamed = false;
                            for (InvestmentPackage iP :
                                    contents.get(position)) {
                                if
                                (iP.getCalendar().get(Calendar.MONTH) == columnIndex) {
                                    shownInvestmentPackages.add(iP);
                                    isSamed = true;
                                } else {
                                    if (isSamed) {
                                        break;
                                    }
                                }
                            }

                            ((FragmentActivity)mContext).getSupportFragmentManager()
                                    .beginTransaction()
                                    .add(new RecordCheckDialogFragment(
                                            mContext,
                                            shownInvestmentPackages, dialogTitle), "MyDialog")
                                    .commit();
                        }
                    });
                    SnackbarManager.show(snackbar);
                }
            }
        }

        @Override
        public void onValueDeselected() {

        }

    }

    private class PieValueTouchListener implements
            PieChartOnValueSelectListener {

        private int position;

        public PieValueTouchListener(int position) {
            this.position = position;
        }

        @Override
        public void onValueSelected(int i, SliceValue sliceValue) {
            String text = "";
            String timeString =
                    contents.get(position).get(0).getCalendarString();
            int month =
                    contents.get(position).get(0).getCalendar().get(Calendar.MONTH) + 1;
            timeString = timeString.substring(6, timeString.length());
            if (type.get(position).equals(SHOW_IN_YEAR)) {
                timeString = timeString.substring(timeString.length()
                        - 4, timeString.length());
            } else {
                timeString = RinaUtil.GetMonthShort(month) + " " +
                        timeString.substring(timeString.length() - 4,
                                timeString.length());
            }
            final int tagId =
                    Integer.valueOf(String.valueOf(sliceValue.getLabelAsChars()));
            Double percent = sliceValue.getValue() /
                    SumList.get(position) * 100;
            if ("zh".equals(RinaUtil.GetLanguage())) {
                text = RinaUtil.GetSpendString((int) sliceValue.getValue()) +
                        RinaUtil.GetPercentString(percent) + "\n" +
                        "NAIJA" + RinaUtil.GetTagName(tagId);
                dialogTitle =
                        mContext.getResources().getString(R.string.in) + timeString +
                                RinaUtil.GetSpendString((int)
                                        sliceValue.getValue()) + "\n" +
                                "NAIJA" + RinaUtil.GetTagName(tagId);

            } else {
                text = RinaUtil.GetSpendString((int) sliceValue.getValue()) +
                        RinaUtil.GetPercentString(percent) + "\n" +
                        "in " +
                        RinaUtil.GetTagName(RecordManager.TAGS.get(tagId).getId());
                dialogTitle = RinaUtil.GetSpendString((int)
                        sliceValue.getValue()) +
                        mContext.getResources().getString(R.string.in)
                        + timeString + "\n" +
                        "in " +
                        RinaUtil.GetTagName(RecordManager.TAGS.get(tagId).getId());
            }
            Snackbar snackbar =
                    Snackbar
                            .with(mContext)
                            .type(SnackbarType.MULTI_LINE)
                            .duration(Snackbar.SnackbarDuration.LENGTH_SHORT)
                            .position(Snackbar.SnackbarPosition.BOTTOM)
                            .margin(15, 15)

                            .backgroundDrawable(RinaUtil.GetSnackBarBackground(fragmentTagId))
                            .text(text)
                            .textTypeface(RinaUtil.GetTypeface())
                            .textColor(Color.WHITE)
                            .actionLabelTypeface(RinaUtil.typefaceLatoLight)

                            .actionLabel(mContext.getResources().getString(R.string.check))
                            .actionColor(Color.WHITE)
                            .actionListener(new ActionClickListener() {
                                @Override
                                public void onActionClicked(Snackbar snackbar) {
                                    List<InvestmentPackage>
                                            shownInvestmentPackages = new ArrayList<>();
                                    for (InvestmentPackage iP :
                                            contents.get(position)) {
                                        if (iP.getTag() == tagId) {
                                            shownCoCoinRecords.add(iP);
                                        }
                                    }
                                    ((FragmentActivity)
                                            mContext).getSupportFragmentManager()
                                            .beginTransaction()
                                            .add(new RecordCheckDialogFragment(
                                                    mContext,
                                                    shownCoInvestmentPackages, dialogTitle), "MyDialog")
                                            .commit();
                                }
                            });
            SnackbarManager.show(snackbar);
        }

        @Override
        public void onValueDeselected() {

        }
    }

}

