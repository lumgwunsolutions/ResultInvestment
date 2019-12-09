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
import java.util.List;

import group.lsg.resultinvestmentapp.Adapter.TagViewRecyclerViewAdapter;
import group.lsg.resultinvestmentapp.R;

public class TagViewFragment extends Fragment {

    private int position;

    private List<InvestmentPackage> list = new ArrayList<>();
    private List<Investors> listInvestors = new ArrayList<>();


    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;

    private Context mContext;

    public static TagViewFragment newInstance(int position) {
        TagViewFragment fragment = new TagViewFragment();
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
        return inflater.inflate(R.layout.account_book_fragment_recyclerview,
                container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        RecyclerView.LayoutManager layoutManager = new
                LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setHasFixedSize(true);

        if (position == 0) {
            for (InvestmentPackage iP : RecordManager.RECORDS) {
                list.add(iP);
            }
        } if (position == 1) {
            for (InvestmentPackage iP : RecordManager.RECORDS) {
                list.add(iP);
            }
        } else {
            for (InvestmentPackage iP : RecordManager.RECORDS) {
                if (iP.getTag() == RecordManager.TAGS.get(position).getId()) {
                    list.add(iP);
                }
            }
        }

        mAdapter = new RecyclerViewMaterialAdapter(new
                TagViewRecyclerViewAdapter(list, mContext, position));
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

