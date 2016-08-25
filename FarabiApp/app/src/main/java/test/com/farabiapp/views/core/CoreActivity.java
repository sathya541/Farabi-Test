package test.com.farabiapp.views.core;

import java.util.ArrayList;

import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.Toast;

import test.com.farabiapp.Constants;
import test.com.farabiapp.R;
import test.com.farabiapp.UIHelper;
import test.com.farabiapp.api.manager.DataManager;
import test.com.farabiapp.api.model.AnnouncementData;
import test.com.farabiapp.views.core.fragments.FragmentAnnouncementDetails;
import test.com.farabiapp.views.core.fragments.FragmentAnnouncementList;
import test.com.farabiapp.views.core.fragments.FragmentProgress;

public class CoreActivity extends AppCompatActivity implements ICoreFlow {

    private Toolbar toolbar;
    private FrameLayout progressFrameLayout;
    private Fragment currentFragment;
    private DataManager manager;
    private boolean isBlocking = false;
    private int numberOfBackPresses = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        updateStatusBarColor();
        setContentView(R.layout.activity_core);

        initialize();
        onGetStarted();

    }

    private void updateStatusBarColor() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(getResources().getColor(R.color.colorPrimaryDark));
        }

    }

    private void initialize() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setupToolbar(false, false, false, false);
        progressFrameLayout = (FrameLayout) findViewById(R.id.overlay_progress_frame);

        manager = DataManager.getInstance(this, this);
    }

    @Override
    public void onGetStarted() {

        manager.fetchAnnouncementData(this, Constants.ANNOUNCEMENT_LIST_URL, new DataManager.DataCallback<AnnouncementData>() {
            @Override
            public void onCompleted(ArrayList<AnnouncementData> modelList) {

                showAnnouncementListFragment(modelList);
            }

            @Override
            public void onError() {
                UIHelper.showErrorDialog(CoreActivity.this, "Sorry, unable to fetch Details!");
            }

        });

    }

    private void showAnnouncementListFragment(ArrayList<AnnouncementData> modelList) {
        FragmentAnnouncementList fragmentAnnouncementList = new FragmentAnnouncementList();
        fragmentAnnouncementList.setDataList(modelList);
        showFragment(fragmentAnnouncementList, null, true, null);
    }

    private void showAnnouncementDetailFragment(String htmlContent) {
        Bundle bundle = new Bundle();
        bundle.putString("html", htmlContent);
        showFragment(new FragmentAnnouncementDetails(), bundle, true, null);
    }

    @Override
    public void onAnnouncementSelected(String htmlContent) {
        showAnnouncementDetailFragment(htmlContent);
    }

    @Override
    public void showProgress(String text) {
        isBlocking = true;

        progressFrameLayout.setVisibility(View.VISIBLE);
        progressFrameLayout.setClickable(true);

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentProgress fragmentProgress = new FragmentProgress();
        fragmentProgress.setProgressMessage(text);
        fragmentManager.beginTransaction().replace(R.id.overlay_progress_frame, fragmentProgress).commitAllowingStateLoss();
    }

    @Override
    public void endProgress() {
        isBlocking = false;
        progressFrameLayout.setVisibility(View.GONE);
    }

    @Override
    public void showToolbar() {
        toolbar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideToolbar() {
        toolbar.setVisibility(View.GONE);
    }

    public void setupToolbar(boolean showBackButton, boolean showAppIcon, boolean showTitle, boolean showSubtitle) {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(showBackButton);
            getSupportActionBar().setDisplayShowHomeEnabled(showAppIcon);
            getSupportActionBar().setDisplayShowTitleEnabled(showTitle);
            if (!showSubtitle) {
                getSupportActionBar().setSubtitle("");
            }
        }
    }

    public void setToolBarTitle(String title) {
        toolbar.setTitle(title);
    }

    public void showFragment(Fragment fragment, Bundle arguments, boolean addToBackStack, Fragment replaceFragment) {
        if (arguments != null) {
            fragment.setArguments(arguments);
        }
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction().setCustomAnimations(R.anim.slide_in_left, 0, 0,
                R.anim.slide_out_left);

        if (replaceFragment != null) {
            fragmentTransaction.remove(replaceFragment);
        }
        fragmentTransaction.add(R.id.fragment_container, fragment, Integer.toString(getFragmentCount()));
        if (addToBackStack) {
            fragmentTransaction.addToBackStack(fragment.getClass().getSimpleName());
        }
        if (!this.isFinishing()) {
            fragmentTransaction.commitAllowingStateLoss();
            currentFragment = fragment;
        }
    }

    protected int getFragmentCount() {
        return getSupportFragmentManager().getBackStackEntryCount();
    }

    private Fragment getFragmentAt(int index) {
        return getFragmentCount() > 0 ? getSupportFragmentManager().findFragmentByTag(Integer.toString(index)) : null;
    }

    public Fragment getCurrentFragment() {
        return getFragmentAt(getFragmentCount() - 1);
    }

    @Override
    public void onBackPressed() {

        if (getCurrentFragment() instanceof FragmentAnnouncementList) {
            if (numberOfBackPresses == 1) {
                finish();
            } else {
                numberOfBackPresses++;
                Toast.makeText(this, "Press back once more to exit the app", Toast.LENGTH_SHORT).show();
            }
        } else if (getCurrentFragment() instanceof FragmentAnnouncementDetails) {

            setToolBarTitle("Announcement List");

            super.onBackPressed();
        }
    }
}
