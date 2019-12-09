package group.lsg.resultinvestmentapp;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import group.lsg.resultinvestmentapp.Class.AboutUsContent;


/**
 * A simple {@link Fragment} subclass.
 */
public class WebsiteDetailFragment extends Fragment {

    public static final String ARG_ITEM_ID = "item_id";
    private AboutUsContent.AboutUsItem mItem;

    public WebsiteDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments().containsKey(ARG_ITEM_ID)) {

            mItem =
                    AboutUsContent.ITEM_MAP.get(getArguments().getString(ARG_ITEM_ID));
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup
            container, Bundle savedInstanceState) {
        View rootView =
                inflater.inflate(R.layout.fragment_website_detail,
                        container, false);

        if (mItem != null) {
            ((WebView) rootView.findViewById(R.id.website_detail))
                    .loadUrl(mItem.website_url);
        }
        return rootView;
    }
}
