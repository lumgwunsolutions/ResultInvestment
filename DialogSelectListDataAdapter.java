package group.lsg.resultinvestmentapp.Adapter;

import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import group.lsg.resultinvestmentapp.Class.RinaUtil;
import group.lsg.resultinvestmentapp.R;

public class DialogSelectListDataAdapter extends BaseAdapter {

    private ArrayList<double[]> data;

    public DialogSelectListDataAdapter(ArrayList<double[]> data) {
        this.data = data;
    }

    @Override
    public int getCount() {
        if (data != null) return data.size();
        else return 0;
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
                LayoutInflater.from(parent.getContext()).inflate(R.layout.item_dialog_select_list_data,
                        null);

        TextView month = (TextView)convertView.findViewById(R.id.month);
        TextView year = (TextView)convertView.findViewById(R.id.year);
        TextView investment = (TextView)convertView.findViewById(R.id.expense);
        TextView sum = (TextView)convertView.findViewById(R.id.sum);

        sum.setTypeface(RinaUtil.getInstance().typefaceLatoLight);

        double monthNum = data.get(position)[1];
        if (monthNum == -1) {
            year.setText((int)data.get(position)[0] + "");
            year.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
            month.setText("--");
        } else {
            month.setText(RinaUtil.getInstance().GetMonthShort((int)data.get(position)[1]));
            year.setText((int)data.get(position)[0] + "");
        }
        investment.setText(RinaUtil.GetInMoney((int)data.get(position)[3]));
        sum.setText((int)data.get(position)[2] + "'s");

        return convertView;
    }
}