package group.lsg.resultinvestmentapp.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import group.lsg.resultinvestmentapp.Class.RinaUtil;
import group.lsg.resultinvestmentapp.R;

public class ReportTagAdapter extends BaseAdapter {

    private ArrayList<double[]> tagInvestment;

    public ReportTagAdapter(ArrayList<double[]> tagInvestment) {
        this.tagInvestment = tagInvestment;
    }

    @Override
    public int getCount() {
        return min(tagInvestment.size() - 1,
                ReportViewFragment.MAX_TAG_INVESTMENT);
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        convertView =
                LayoutInflater.from(parent.getContext()).inflate(R.layout.item_report_tag,
                        null);

        ImageView icon = (ImageView)convertView.findViewById(R.id.icon);
        TextView name = (TextView)convertView.findViewById(R.id.tag_name);
        TextView expense = (TextView)convertView.findViewById(R.id.tag_expense);
        TextView records = (TextView)convertView.findViewById(R.id.tag_sum);

        icon.setImageDrawable(RinaUtil.getInstance().GetTagIconDrawable((int)tagInvestment.get(position
                + 1)[2]));
        name.setText(RinaUtil.getInstance().GetTagName((int)tagInvestment.get(position
                + 1)[2]) + RinaUtil.getInstance().GetPurePercentString(tagInvestment.get(position
                + 1)[1] * 100));
        expense.setText(RinaUtil.getInstance().GetInMoney((int)tagInvestment.get(position
                + 1)[0]));
        records.setText(RinaUtil.getInstance().GetInRecords((int)tagInvestment.get(position
                + 1)[3]));

        return convertView;
    }

    private int min(int a, int b) {
        return (a < b ? a : b);
    }
}
