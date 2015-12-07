package in.vinkrish.quickwash;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by vinkrish on 30/11/15.
 */
public class QuickWashFragment extends android.support.v4.app.Fragment implements View.OnClickListener {
    @Bind(R.id.rg)
    RadioGroup radioGroup;
    @Bind(R.id.layoutScreen)
    CustomViewPager layoutSlideShow;
    @Bind(R.id.btn_order)
    Button placeOrderBtn;

    private boolean pageChangeEnabled;
    private Handler slideShowHandler;
    private int SLIDE_SHOW_INDEX = 0;
    private final int SILDE_SHOW_INTERVAL = 3 * 1000;
    private String service;
    private Toast mToast;
    private List<Integer> layouts = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.quick_wash, container, false);

        ButterKnife.bind(this, view);
        init();

        return view;
    }

    private void init() {
        pageChangeEnabled = false;
        layouts.add(0);
        layouts.add(1);
        layouts.add(2);
        layouts.add(3);
        if (slideShowHandler == null)
            slideShowHandler = new Handler();
        ScreenSliderAdapter featuredProductsAdapter = new ScreenSliderAdapter(getActivity().getSupportFragmentManager(), layouts);
        layoutSlideShow.setAdapter(featuredProductsAdapter);
        startSlideShow();

        radioGroup.setOnCheckedChangeListener(radioButtonChanged);

        placeOrderBtn.setOnClickListener(this);
    }

    private RadioGroup.OnCheckedChangeListener radioButtonChanged = new RadioGroup.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            switch (checkedId) {
                case R.id.wi_rb:
                    layoutSlideShow.setCurrentItem(0);
                    removeSlideShow();
                    service = "Wash + Iron";
                    break;
                case R.id.w_rb:
                    layoutSlideShow.setCurrentItem(1);
                    removeSlideShow();
                    service = "Wash";
                    break;
                case R.id.i_rb:
                    layoutSlideShow.setCurrentItem(2);
                    removeSlideShow();
                    service = "Iron";
                    break;
                case R.id.dc_rb:
                    layoutSlideShow.setCurrentItem(3);
                    removeSlideShow();
                    service = "Dryclean";
                    break;
                default:
                    break;
            }

            if (mToast != null) mToast.cancel();

            if (!pageChangeEnabled) {
                pageChangeEnabled = true;
                layoutSlideShow.addOnPageChangeListener(pageChanged);
            }

        }
    };

    private ViewPager.OnPageChangeListener pageChanged = new ViewPager.OnPageChangeListener() {

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            if (position == 0) {
                radioGroup.check(R.id.wi_rb);
            } else if (position == 1) {
                radioGroup.check(R.id.w_rb);
            } else if (position == 2) {
                radioGroup.check(R.id.i_rb);
            } else if (position == 3) {
                radioGroup.check(R.id.dc_rb);
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };

    private void removeSlideShow() {
        slideShowHandler.removeCallbacksAndMessages(null);
    }

    public void startSlideShow() {
        if (null != slideShowHandler) {
            Runnable slideShow = new Runnable() {
                @Override
                public void run() {
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (null != layoutSlideShow && null != layoutSlideShow.getAdapter()) {
                                layoutSlideShow.setCurrentItem(SLIDE_SHOW_INDEX++);
                                SLIDE_SHOW_INDEX %= layoutSlideShow.getAdapter().getCount();
                            }
                            slideShowHandler.postDelayed(this, SILDE_SHOW_INTERVAL);
                        }
                    });
                }
            };
            // Schedule the first execution
            slideShowHandler.postDelayed(slideShow, SILDE_SHOW_INTERVAL);
        }
    }

    private void showToast(String msg) {
        if (mToast != null) mToast.cancel();

        mToast = Toast.makeText(getActivity(), msg, Toast.LENGTH_LONG);
        mToast.show();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_order:
                placeOrder();
                break;
            default:
                break;
        }

    }

    protected boolean isOnline() {
        ConnectivityManager cm = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo nf = cm.getActiveNetworkInfo();
        if (nf != null && nf.isConnectedOrConnecting()) return true;
        else return false;
    }

    public void placeOrder() {
        if (isOnline()) {
            if (service != null && service != "") {
                Intent intent = new Intent(getActivity(), in.vinkrish.quickwash.PlaceOrderActivity.class);
                intent.putExtra("service", service);
                startActivity(intent);
            } else {
                showToast("Please select service");
            }
        } else {
            showToast("Network isn't available");
        }
    }


    @Override
    public void onPause() {
        super.onPause();
        removeSlideShow();
    }

}