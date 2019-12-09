package group.lsg.resultinvestmentapp.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.balysv.materialripple.MaterialRippleLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import group.lsg.resultinvestmentapp.Class.InvestmentPackage;
import group.lsg.resultinvestmentapp.Class.RinaUtil;
import group.lsg.resultinvestmentapp.R;

public class RecordCheckDialogRecyclerViewAdapter extends
        RecyclerView.Adapter<RecordCheckDialogRecyclerViewAdapter.viewHolder>
{

    private OnItemClickListener onItemClickListener;

    private final LayoutInflater mLayoutInflater;
    private final Context mContext;
    private List<InvestmentPackage> iP;

    public RecordCheckDialogRecyclerViewAdapter(Context context,
                                                List<InvestmentPackage> list) {
        iP = new ArrayList<>();
        iP = list;
        mContext = context;
        mLayoutInflater = LayoutInflater.from(context);
    }

    public RecordCheckDialogRecyclerViewAdapter(Context context,
                                                List<InvestmentPackage> list, OnItemClickListener onItemClickListener)
    {
        iP = new ArrayList<>();
        iP = list;
        mContext = context;
        mLayoutInflater = LayoutInflater.from(context);
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public viewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new
                viewHolder(mLayoutInflater.inflate(R.layout.record_check_item, parent,
                false));
    }

    @Override
    public void onBindViewHolder(viewHolder holder, final int position) {
        holder.imageView.setImageResource(
                RinaUtil.GetTagIcon(iP.get(position).getTag()));
        holder.date.setText(iP.get(position).getCalendarString());
        holder.money.setText(String.valueOf((int) iP.get(position).getMoney()));
        holder.money.setTextColor(

                RinaUtil.GetTagColorResource(RecordManager.TAGS.get(.get(position).getTag()).getId()));
        holder.index.setText((position + 1) + "");
        holder.remark.setText(iP.get(position).getRemark());

        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClickListener.onItemClick(v, position);
            }
        });
    }

    @Override
    public int getItemCount() {
        if (iP == null) {
            return 0;
        }
        return iP.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder implements
            View.OnClickListener {
        @InjectView(R.id.image_view)
        ImageView imageView;
        @InjectView(R.id.date)
        TextView date;
        @InjectView(R.id.remark)
        TextView remark;
        @InjectView(R.id.money)
        TextView money;
        @InjectView(R.id.index)
        TextView index;
        @InjectView(R.id.material_ripple_layout)
        MaterialRippleLayout layout;

        viewHolder(View view) {
            super(view);
            ButterKnife.inject(this, view);
        }

        @Override
        public void onClick(View v) {
//            onItemClickListener.onItemClick(v, getPosition());
        }
    }

    public interface OnItemClickListener {
        void onItemClick(View view , int position);
    }
}

