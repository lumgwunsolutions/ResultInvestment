package group.lsg.resultinvestmentapp.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import group.lsg.resultinvestmentapp.Class.RinaUtil;
import group.lsg.resultinvestmentapp.R;

public class DrawerTagChooseGridViewAdapter extends BaseAdapter {

    private LayoutInflater inflater;

    public DrawerTagChooseGridViewAdapter(Context context) {
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return RecordManager.TAGS.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = this.inflater.inflate(R.layout.item_tag_choose, null);
            holder.tagImage = (ImageView)
                    convertView.findViewById(R.id.tag_image);
            holder.tagName = (TextView) convertView.findViewById(R.id.tag_name);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.tagImage.setImageResource(
                RinaUtil.GetTagIcon(RecordManager.TAGS.get(position).getId()));
        holder.tagName.setText(RinaUtil.GetTagName(RecordManager.TAGS.get(position).getId()));

        return convertView;
    }

    private class ViewHolder {
        ImageView tagImage;
        TextView tagName;
    }
}

