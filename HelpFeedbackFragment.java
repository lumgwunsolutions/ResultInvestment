package group.lsg.resultinvestmentapp;


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.github.florent37.materialviewpager.MaterialViewPagerHelper;
import com.github.ksoichiro.android.observablescrollview.ObservableScrollView;

import group.lsg.resultinvestmentapp.Class.Application;
import group.lsg.resultinvestmentapp.Class.Feedback;
import group.lsg.resultinvestmentapp.Class.RinaUtil;


public class HelpFeedbackFragment extends Fragment {
    private boolean mIsDoubleCount = true;
    private TextView title;
    private EditText input;
    private TextView help;
    private TextView number;
    private TextView send;

    private int min = 1;
    private int max = 400;
    private boolean exceed = false;

    private ObservableScrollView mScrollView;

    public static HelpFeedbackFragment newInstance() {
        HelpFeedbackFragment fragment = new HelpFeedbackFragment();
        return fragment;
    }

    private Activity activity;
    private Context mContext;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if (context instanceof Activity){
            activity = (Activity)context;
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getContext();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup
            container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_help_feedback_view,
                container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mScrollView = (ObservableScrollView) view.findViewById(R.id.scrollView);
        MaterialViewPagerHelper.registerScrollView(getActivity(),
                mScrollView, null);

        title = (TextView)view.findViewById(R.id.title);
        input = (EditText)view.findViewById(R.id.edittext);
        help = (TextView)view.findViewById(R.id.helper);
        number = (TextView)view.findViewById(R.id.number);

        send = (TextView)view.findViewById(R.id.send);
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (exceed) {
                    new MaterialDialog.Builder(mContext)
                            .title("help feedback title")
                            .content("help_feedback content")
                            .positiveText("ok")
                            .show();
                } else {

                    RinaUtil.getInstance().showToast(Application.getAppContext(),
                            Application.getAppContext().
                                    getResources().getString("help feedback sent"));
                    Feedback feedback = new Feedback();
                    feedback.setContent(input.getText().toString());
                    feedback.save(Application.getAppContext(), new
                            SaveListener() {
                                @Override
                                public void onSuccess() {

                                    RinaUtil.getInstance().showToast(Application.getAppContext(),
                                            Application.getAppContext().getResources().getString("help feedback sent successfully"));
                                }

                                @Override
                                public void onFailure(int code, String arg0) {

                                    RinaUtil.getInstance().showToast(Application.getAppContext(),
                                            Application.getAppContext().getResources().getString(Integer.parseInt("help feedback sent fail")));
                                }
                            });
                }
            }
        });

        input.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int
                    before, int count) {
                setNumberText();
                try {
                    ((OnTextChangeListener)activity)
                            .onTextChange(input.getText().toString(), exceed);
                } catch (ClassCastException cce){
                    cce.printStackTrace();
                }
            }
            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        send.requestFocus();

        setNumberText();
    }

    private void setNumberText() {
        int count = -1;
        if (mIsDoubleCount) {
            count =
                    RinaUtil.getInstance().textCounter(input.getText().toString());
        } else {
            count =input.getText().toString().length();
        }
        number.setText(count + "/" + min + "-" + max);
        if (min <= count && count <= max) {
            number.setTextColor(ContextCompat.getColor(mContext,
                    R.color.my_blue));
            exceed = false;
        } else {
            number.setTextColor(ContextCompat.getColor(mContext, R.color.red));
            exceed = true;
        }
    }

    public interface OnTextChangeListener {
        void onTextChange(String text, boolean exceed);
    }

}
