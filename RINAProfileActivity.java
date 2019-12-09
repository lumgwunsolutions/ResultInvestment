package group.lsg.resultinvestmentapp;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SimpleItemAnimator;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.annotation.SuppressLint;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.se.omapi.Session;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.TextAppearanceSpan;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.util.Util;
import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.Indicators.PagerIndicator;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.github.florent37.materialviewpager.MaterialViewPager;
import com.github.florent37.materialviewpager.MaterialViewPagerHelper;
import com.github.florent37.materialviewpager.header.HeaderDesign;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.mikhaellopez.circularimageview.CircularImageView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;
import group.lsg.resultinvestmentapp.Class.Investors;
import group.lsg.resultinvestmentapp.Class.ProfileManager;
import group.lsg.resultinvestmentapp.Class.RinaUtil;
import group.lsg.resultinvestmentapp.Class.SettingManager;
import group.lsg.resultinvestmentapp.Enum.ProfileStatus;

import static android.media.audiofx.AudioEffect.SUCCESS;
import static com.daimajia.slider.library.SliderLayout.Transformer.Default;

public class RINAProfileActivity extends AppCompatActivity {
    private static final String TAG = RINAProfileActivity.class.getSimpleName();
    public static final String USER_ID_EXTRA_KEY =
            "RINAProfileActivity.USER_ID_EXTRA_KEY";
    public static final int CREATE_POST_FROM_PROFILE_REQUEST = 22;
    private String currentUserId;
    private String userID;
    private SwipeRefreshLayout swipeContainer;
    private ProfileManager profileManager;

    private Calendar mCalendar;
    private TextView DateCreated;
    private TextView mDateJoined
    private TextView mDateActivated;
    private TextView mDateDeActivated;
    private TextView mDateCreating;
    private TextView mName;
    private TextView mUserId;
    private TextView mPackageCount;
    private TextView mMenteesCount;
    private CircularImageView mImage;
    private ImageView mImagePCount;
    private ImageView mImageMCount;
    private TextView headername;
    private ImageView ic_back;
    private MaterialViewPager mViewPager;

    private FirebaseAuth mAuth;
    private TextView postsCounterTextView;
    private TextView postsLabelTextView;
    private ProgressBar postsProgressBar;
    private PostsByUserAdapter postsAdapter;



    private DrawerLayout mDrawer;
    private ActionBarDrawerToggle mDrawerToggle;
    private Toolbar toolbar;

    private TagViewFragmentAdapter tagModeAdapter = null;

    private Context mContext;

    private TextView names;
    private TextView email;
    private TextView revenue;
    private TextView customerNumber;

    private CircleImageView profileImage;

    private SliderLayout mDemoSlider;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rinaprofile);
        userID = getIntent().getStringExtra(USER_ID_EXTRA_KEY);

        mAuth = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        if (firebaseUser != null) {
            currentUserId = firebaseUser.getUid();
        }
        mLinearLayoutTime = (LinearLayout) findViewById(R.id.time_text);
        postsCounterTextView = (TextView)
                findViewById(R.id.postsCounterTextView);
        swipeContainer = (SwipeRefreshLayout) findViewById(R.id.swipeContainer);
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                onRefreshAction();
            }
        });

        loadPostsList();
        supportPostponeEnterTransition();
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        DateCreated = (TextView) findViewById(R.id.text_created);
        mDateJoined = (TextView) findViewById(R.id.text_joined);
        mDateActivated = (TextView) findViewById(R.id.text_activated);
        mDateDeActivated = (TextView) findViewById(R.id.text_deactivated);
        mName = (TextView) findViewById(R.id.text_name);
        mPackageCount = (TextView) findViewById(R.id.text_count);
        mMenteesCount = (TextView) findViewById(R.id.text_mentees_count);
        mUserId = (TextView) findViewById(R.id.text_user_id);
        mImage = (CircularImageView) findViewById(R.id.text_profile_pix);
        mImagePCount = (ImageView) findViewById(R.id.package_image);
        mImageMCount = (ImageView) findViewById(R.id.menteee_image);
        headername = (TextView) findViewById(R.id.headername);
        ic_back = (ImageView) findViewById(R.id.ic_back);
        headername.setText("Your Profile");
        SetOnclicklistener();


        mViewPager = (MaterialViewPager) findViewById(R.id.materialViewPager);

        View view = mViewPager.getRootView();
        TextView title = (TextView)view.findViewById(R.id.logo_white);

        title.setText("Your Profile");

        mViewPager.getPagerTitleStrip();

        setTitle("");

        toolbar = mViewPager.getToolbar();
        mDrawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        names = (TextView)findViewById(R.id.user_name);
        customerNumber = (TextView)findViewById(R.id.customer_number);
        revenue = (TextView)findViewById(R.id.revenue);
        Investors user = new Investors();
        if (user != null) {
            int lName = Investors.getLastName();
            int fName = Investors.getFirstName();
            int initial =Investors.getMiddleName();
            string names = (lName + ""fName + ""initial)
            userName.setText(Investors.setString(names));
            userEmail.setText(Investors.getEmail());
            userName.setText(Investors.getUsername());
            userName.setText(Investors.getUsername());
            userName.setText(Investors.getUsername());
        }
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawer, 0, 0);
        View logo = findViewById(R.id.logo_white);
        if (logo != null) {
            logo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mViewPager.notifyHeaderChanged();
                }
            });
        }

        tagModeAdapter = new
                TagViewFragmentAdapter(getSupportFragmentManager());
        mViewPager.getViewPager().setOffscreenPageLimit(tagModeAdapter.getCount());
        mViewPager.getViewPager().setAdapter(tagModeAdapter);
        mViewPager.getPagerTitleStrip().setViewPager(mViewPager.getViewPager());

        mViewPager.clearAnimation();
        if (SettingManager.getInstance().getShowPicture()) {
            mViewPager.setMaterialViewPagerListener(new MaterialViewPager.Listener() {
                @Override
                public HeaderDesign getHeaderDesign(int page) { return HeaderDesign.fromColorAndUrl(
                        RinaUtil.GetTagColor(RecordManager.TAGS.get(page).getId()),
                             RinaUtil.GetTagUrl(RecordManager.TAGS.get(page).getId())); }
            });
        } else {
            mViewPager.setMaterialViewPagerListener(new
                                                            MaterialViewPager.Listener() {
                                                                @Override
                                                                public HeaderDesign getHeaderDesign(int page) { return HeaderDesign.fromColorAndDrawable(
                            RinaUtil.GetTagColor(RecordManager.TAGS.get(page).getId()),
                  RinaUtil.GetTagDrawable(-3)); }
            });
        }

        profileImage= (CircleImageView)mDrawer.findViewById(R.id.profile_image);
        profileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (SettingManager.getInstance().getLoggenOn()) {
                    RinaUtil.showToast(mContext, "change photo");
                } else {
                    RinaUtil.showToast(mContext, "login");
                }
            }
        });

        mDemoSlider = (SliderLayout)findViewById(R.id.slider);

        HashMap<String, Integer> urls = RinaUtil.GetDrawerTopUrl();

        for(String name : urls.keySet()){
            CustomSliderView customSliderView = new CustomSliderView(this);
            // initialize a SliderLayout
            customSliderView
                    .image(urls.get(name))
                    .setScaleType(BaseSliderView.ScaleType.Fit);
            mDemoSlider.addSlider(customSliderView);
        }
        mDemoSlider.setPresetTransformer(SliderLayout.Transformer.ZoomOut);
        mDemoSlider.setCustomAnimation(new DescriptionAnimation());
        mDemoSlider.setDuration(4000);
        mDemoSlider.setCustomIndicator((PagerIndicator)
                findViewById(R.id.custom_indicator));

        loadLogo();


    }
    @Override
    public void SetOnclicklistener() {
        super.SetOnclicklistener();
        ic_back.setOnClickListener(this);
        mImage.setOnClickListener(this);
        mImagePCount.setOnClickListener(this);
        mImageMCount.setOnClickListener(this);
        default_payment.setOnClickListener(this);
        try {
            JSONObject jsonObject_main = new JSONObject();
            JSONObject jsonObject = new JSONObject();
            jsonObject_main = getCommontHeaderParams();
            jsonObject.put("customerId",
                    Session.getUserID(RINAProfileActivity.this));

            jsonObject_main.put("body", jsonObject);
            CallGETPAYMENTAPI(jsonObject_main);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void onClick(View v) {
        mImage.setVisibility(View.VISIBLE);
        mImagePCount.setVisibility(View.VISIBLE);
        mImageMCount.setVisibility(View.VISIBLE);

        switch (v.getId()) {
            case R.id.ic_back:
                finish();
                break;
            case R.id.text_profile_pix:
                if (edt_emailid.getText().toString().trim().length() == 0) {
                    Util.ShowToast(RINAProfileActivity.this, "Enter email id");
                } else if
                (!Util.isEmailValid(edt_emailid.getText().toString().trim())) {
                    Util.ShowToast(RINAProfileActivity.this, "Enter valid email id");
                } else if (addedPaymentData == null) {
                    Util.ShowToast(RINAProfileActivity.this, "Select Default payment");
                } else {
                    try {
                        JSONObject jsonObject_main = new JSONObject();
                        JSONObject jsonObject = new JSONObject();
                        jsonObject_main = getCommontHeaderParams();
                        jsonObject.put("customerId",
                                Session.getUserID(RINAProfileActivity.this));
                        jsonObject.put("paymentId", addedPaymentData.paymentId);
                        jsonObject.put("email",
                                edt_emailid.getText().toString().trim());
                        jsonObject.put("accountType", "P");
                        jsonObject_main.put("body", jsonObject);
                        CallADDPAYMENTAPI(jsonObject_main);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                break;
            case R.id.default_payment:
                Intent intentGetMessage = new Intent(this,
                        SeletectPaymentActivity.class);
                startActivityForResult(intentGetMessage, 2);
                break;
        }
    }

    public void setValues(AddedPaymentData addedPaymentData) {
        edt_emailid.setText(addedPaymentData.email);
        default_payment.setText(addedPaymentData.gatewayId);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode,
                                    Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 2) {
            if (null != data) {

                addedPaymentData = (AddedPaymentData)
                        data.getExtras().getSerializable("mDataset");
                default_payment.setText(addedPaymentData.gatewayId);
            }
        }else if(resultCode == RESULT_OK) {
            switch (requestCode) {
                case CreateInvestorActivity.CREATE_NEW_INVESTOR_REQUEST:
                    postsAdapter.loadPosts();
                    showSnackBar("Investor was created");
                    setResult(RESULT_OK);
                    break;

                case InvestorDetailsActivity.UPDATE_INVESTOR_REQUEST:
                    if (data != null) {
                        PostStatus postStatus = (PostStatus)
                                data.getSerializableExtra(PostDetailsActivity.POST_STATUS_EXTRA_KEY);
                        if (postStatus.equals(PostStatus.REMOVED)) {
                            postsAdapter.removeSelectedPost();

                        } else if (postStatus.equals(PostStatus.UPDATED)) {
                            postsAdapter.updateSelectedPost();
                        }
                    }
                    break;
            }
        }

        public void CallADDPAYMENTAPI(JSONObject params) {
            if (Util.isNetworkConnected(PersonalActivity.this)) {
                try {
                    if (progressdialog.isShowing())
                        progressdialog.dismiss();
                    progressdialog.show();
                    new CallAPI(ADDUPDATEACCOUNTPROFILE,
                            "ADDUPDATEACCOUNTPROFILE", params, PersonalActivity.this,
                            GetPlaces_Handler, true);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else {
                progressdialog.dismissanimation(ProgressDialogView.ERROR);
                Util.ShowToast(PersonalActivity.this,
                        getString(R.string.nointernetmessage));
            }
        }

        @SuppressLint("HandlerLeak") Handler GetPlaces_Handler = new Handler() {
            public void handleMessage(Message msg) {

                PrintMessage("Handler " + msg.getData().toString());
                if (msg.getData().getBoolean("flag")) {
                    if (msg.getData().getInt("code") == SUCCESS) {

                        progressdialog.dismissanimation(ProgressDialogView.ERROR);


                        Util.ShowToast(PersonalActivity.this, "Update successfully!");

                    } else if (msg.getData().getInt("code") == FROMGENERATETOKEN) {
                        ParseSessionDetails(msg.getData().getString("responce"));
                        try {
                            CallADDPAYMENTAPI(new JSONObject(msg.getData().getString("mExtraParam")));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    } else if (msg.getData().getInt("code") == SESSIONEXPIRE) {
                        if (Util.isNetworkConnected(PersonalActivity.this)) {
                            CallSessionID(GetPlaces_Handler, msg.getData()
                                    .getString("mExtraParam"));
                        } else {

                            progressdialog.dismissanimation(ProgressDialogView.ERROR);
                            Util.ShowToast(PersonalActivity.this,
                                    getString(R.string.nointernetmessage));
                        }
                    } else {
                        progressdialog.dismissanimation(ProgressDialogView.ERROR);
                        Util.ShowToast(PersonalActivity.this,
                                msg.getData().getString("msg"));
                    }
                } else {
                    progressdialog.dismissanimation(ProgressDialogView.ERROR);
                    Util.ShowToast(PersonalActivity.this,
                            msg.getData().getString("msg"));

                }
            }
        };

        public void CallGETPAYMENTAPI(JSONObject params) {
            if (Util.isNetworkConnected(PersonalActivity.this)) {
                try {
                    if (progressdialog.isShowing())
                        progressdialog.dismiss();
                    progressdialog.show();
                    new CallAPI(PaymentTPROFILE, "PaymentTPROFILE",
                            params, PersonalActivity.this, GetGetpayment_Handler, true);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else {
                progressdialog.dismissanimation(ProgressDialogView.ERROR);
                Util.ShowToast(PersonalActivity.this,
                        getString(R.string.nointernetmessage));
            }
        }

        @SuppressLint("HandlerLeak") Handler GetGetpayment_Handler = new Handler() {
            public void handleMessage(Message msg) {

                PrintMessage("Handler " + msg.getData().toString());
                if (msg.getData().getBoolean("flag")) {
                    if (msg.getData().getInt("code") == SUCCESS) {

                        progressdialog.dismissanimation(ProgressDialogView.ERROR);
                        AddedPaymentData addedPaymentData = new AddedPaymentData();
                        try {
                            JSONArray jsonArray = new
                                    JSONArray(msg.getData().getString("responce"));
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jsonObject = jsonArray.getJSONObject(i);

                                if
                                (jsonObject.getString("accountType").equalsIgnoreCase("P")) {
                                    addedPaymentData.id = 0;
                                    if (jsonObject.has("defalt_payment"))
                                        addedPaymentData.defalt_payment =
                                                jsonObject.getInt("defalt_payment");
                                    addedPaymentData.paymentId =
                                            jsonObject.getString("paymentId");
                                    addedPaymentData.customerId =
                                            jsonObject.getString("customerId");
                                    addedPaymentData.email =
                                            jsonObject.getString("email");
                                    addedPaymentData.gatewayId =
                                            jsonObject.getString("gatewayId");

                                    setValues(addedPaymentData);
                                    break;
                                }
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    } else if (msg.getData().getInt("code") == FROMGENERATETOKEN) {
                        ParseSessionDetails(msg.getData().getString("responce"));
                        try {
                            CallGETPAYMENTAPI(new JSONObject(msg.getData().getString("mExtraParam")));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    } else if (msg.getData().getInt("code") == SESSIONEXPIRE) {
                        if (Util.isNetworkConnected(PersonalActivity.this)) {
                            CallSessionID(GetGetpayment_Handler, msg.getData()
                                    .getString("mExtraParam"));
                        } else {

                            progressdialog.dismissanimation(ProgressDialogView.ERROR);
                            Util.ShowToast(PersonalActivity.this,
                                    getString(R.string.nointernetmessage));
                            finish();
                        }
                    } else {
                        progressdialog.dismissanimation(ProgressDialogView.ERROR);
                        Util.ShowToast(PersonalActivity.this,
                                msg.getData().getString("msg"));
                        finish();
                    }
                } else {
                    progressdialog.dismissanimation(ProgressDialogView.ERROR);
                    Util.ShowToast(PersonalActivity.this,
                            msg.getData().getString("msg"));
                    finish();

                }
            }
        };
        @Override
        protected void onResume() {
            super.onResume();
            mDemoSlider.startAutoCycle();
        }

        @Override
        protected void onStop() {
            mDemoSlider.stopAutoCycle();
            super.onStop();
            profileManager.closeListeners(this);
        }

        @Override
        protected void onStart() {
            super.onStart();
            loadProfile();
        }

        @Override
        protected void onDestroy() {
            super.onDestroy();

            MaterialViewPagerHelper.unregister(this);
        }

        @Override
        protected void onPostCreate(Bundle savedInstanceState) {
            super.onPostCreate(savedInstanceState);
            mDrawerToggle.syncState();
        }

        @Override
        public boolean onOptionsItemSelected(MenuItem item) {
            return mDrawerToggle.onOptionsItemSelected(item) ||
                    super.onOptionsItemSelected(item);
        }

        @Override
        public void onBackPressed() {
            if (mDrawer.isDrawerOpen(GravityCompat.START)) {
                mDrawer.closeDrawers();
                return;
            }
            super.onBackPressed();
        }

        private MaterialDialog progressDialog;
        public class LoadViews extends AsyncTask<String, Void, String> {

            @Override
            protected String doInBackground(String... params) {

                return null;
            }
            @Override
            protected void onPostExecute(String result) {
                if (progressDialog != null) progressDialog.cancel();
            }
        }

        private void loadLogo() {
            Investors user =
                    BmobUser.getCurrentUser(RinaUtil.getAppContext(), Investors.class);
            if (user != null) {
                try {


            } else {
                // use the default logo
                profileImage.setImageResource(R.drawable.ic_user);
            }
        }
        private void onRefreshAction() {
            postsAdapter.loadPosts();
        }

        private void loadPostsList() {
            if (recyclerView == null) {

                recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
                postsAdapter = new PostsByUserAdapter(this, userID);
                postsAdapter.setCallBack(new PostsByUserAdapter.CallBack() {
                    @Override
                    public void onItemClick(final Investors investor,
                                            final View view) {

                        PostManager.getInstance(ProfileActivity.this).isPostExistSingleValue(investor.getId(),
                                new OnObjectExistListener<Investors>() {
                                    @Override
                                    public void onDataChanged(boolean exist) {
                                        if (exist) {
                                            openPostDetailsActivity(investor, view);
                                        } else {
                                            showSnackBar("error Investors was removed");
                                        }
                                    }
                                });
                    }

                    @Override
                    public void onPostsListChanged(int postsCount) {
                        String postsLabel =
                                getResources().getQuantityString(R.plurals.posts_counter_format,
                                        postsCount, postsCount);

                        postsCounterTextView.setText(buildCounterSpannable(postsLabel,
                                postsCount));
                        if (postsCount > 0) {
                            postsLabelTextView.setVisibility(View.VISIBLE);
                        }

                        swipeContainer.setRefreshing(false);
                        hideLoadingPostsProgressBar();
                    }
                    @Override
                    public void onPostLoadingCanceled() {
                        swipeContainer.setRefreshing(false);
                        hideLoadingPostsProgressBar();
                    }
                });

                recyclerView.setLayoutManager(new LinearLayoutManager(this));
                ((SimpleItemAnimator)
                        recyclerView.getItemAnimator()).setSupportsChangeAnimations(false);
                recyclerView.setAdapter(postsAdapter);
                postsAdapter.loadPosts();
            }
        }
        private Spannable buildCounterSpannable(String label, int value) {
            SpannableStringBuilder contentString = new SpannableStringBuilder();
            contentString.append(String.valueOf(value));
            contentString.append("\n");
            int start = contentString.length();
            contentString.append(label);
            contentString.setSpan(new TextAppearanceSpan(this,
                            R.style.TextAppearance_Second_Light), start, contentString.length(),
                    Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            return contentString;
        }
        private void openPostDetailsActivity(Post post, View v) {
            Intent intent = new Intent(ProfileActivity.this,
                    PostDetailsActivity.class);
            intent.putExtra(PostDetailsActivity.POST_ID_EXTRA_KEY, post.getId());
            intent.putExtra(PostDetailsActivity.AUTHOR_ANIMATION_NEEDED_EXTRA_KEY,
                    true);

            if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

                View imageView = v.findViewById(R.id.postImageView);

                ActivityOptions options = ActivityOptions.
                        makeSceneTransitionAnimation(ProfileActivity.this,
                                new android.util.Pair<>(imageView,
                                        getString(R.string.post_image_transition_name))
                        );
                startActivityForResult(intent,
                        PostDetailsActivity.UPDATE_POST_REQUEST, options.toBundle());
            } else {
                startActivityForResult(intent,
                        PostDetailsActivity.UPDATE_POST_REQUEST);
            }
            private void loadProfile() {
                profileManager = ProfileManager.getInstance(this);
                profileManager.getProfileValue(ProfileActivity.this, userID,
                        createOnProfileChangedListener());
            }

            private OnObjectChangedListener<Profile> createOnProfileChangedListener() {
                return new OnObjectChangedListener<Profile>() {
                    @Override
                    public void onObjectChanged(Profile obj) {
                        fillUIFields(obj);
                    }
                };
            }
            private void fillUIFields(Profile profile) {
                if (profile != null) {
                    nameEditText.setText(profile.getUsername());

                    if (profile.getPhotoUrl() != null) {
                        Glide.with(this)
                                .load(profile.getPhotoUrl())
                                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                                .crossFade()
                                .error(R.drawable.ic_stub)
                                .listener(new RequestListener<String, GlideDrawable>() {
                                    @Override
                                    public boolean onException(Exception e,
                                                               String model, Target<GlideDrawable> target, boolean isFirstResource) {
                                        scheduleStartPostponedTransition(imageView);
                                        progressBar.setVisibility(View.GONE);
                                        return false;
                                    }

                                    @Override
                                    public boolean
                                    onResourceReady(GlideDrawable resource, String model,
                                                    Target<GlideDrawable> target, boolean isFromMemoryCache, boolean
                                                            isFirstResource) {
                                        scheduleStartPostponedTransition(imageView);
                                        progressBar.setVisibility(View.GONE);
                                        return false;
                                    }
                                })
                                .into(imageView);
                    } else {
                        progressBar.setVisibility(View.GONE);
                        imageView.setImageResource(R.drawable.ic_stub);
                    }

                    int likesCount = (int) profile.getLikesCount();
                    String likesLabel =
                            getResources().getQuantityString(R.plurals.likes_counter_format,
                                    likesCount, likesCount);
                    likesCountersTextView.setText(buildCounterSpannable(likesLabel, likesCount));
                }
            }
            private void hideLoadingPostsProgressBar() {
                if (postsProgressBar.getVisibility() != View.GONE) {
                    postsProgressBar.setVisibility(View.GONE);
                }
            }

            private void scheduleStartPostponedTransition(final ImageView imageView) {
                imageView.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
                    @Override
                    public boolean onPreDraw() {
                        imageView.getViewTreeObserver().removeOnPreDrawListener(this);
                        supportStartPostponedEnterTransition();
                        return true;
                    }
                });
            }
            private void startMainActivity() {
                Intent intent = new Intent(ProfileActivity.this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK |
                        Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }

            private void startEditProfileActivity() {
                if (hasInternetConnection()) {
                    Intent intent = new Intent(ProfileActivity.this,
                            EditProfileActivity.class);
                    startActivity(intent);
                } else {
                    showSnackBar(R.string.internet_connection_failed);
                }
            }

            @Override
            public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
                LogUtil.logDebug(TAG, "onConnectionFailed:" + connectionResult);
            }
            private void openCreatePostActivity() {
                Intent intent = new Intent(this, CreatePostActivity.class);
                startActivityForResult(intent,
                        CreatePostActivity.CREATE_NEW_POST_REQUEST);
            }
            @Override
            public boolean onCreateOptionsMenu(Menu menu) {
                if (userID.equals(currentUserId)) {
                    MenuInflater inflater = getMenuInflater();
                    inflater.inflate(R.menu.profile_menu, menu);
                    return true;
                }

                return super.onCreateOptionsMenu(menu);
            }

            @Override
            public boolean onOptionsItemSelected(MenuItem item) {
                // Handle item selection
                switch (item.getItemId()) {
                    case R.id.editProfile:
                        startEditProfileActivity();
                        return true;
                    case R.id.signOut:
                        LogoutHelper.signOut(mGoogleApiClient, this);
                        startMainActivity();
                        return true;
                    case R.id.createPost:
                        if (hasInternetConnection()) {
                            openCreatePostActivity();
                        } else {
                            showSnackBar("internet connection failed");
                        }
                    default:
                        return super.onOptionsItemSelected(item);
                }
            }

        }

    }

                        }

