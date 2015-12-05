package in.vinkrish.quickwash;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class PlaceOrderActivity extends AppCompatActivity {
    @Bind(R.id.name_et)
    EditText name;
    @Bind(R.id.mobile_et)
    EditText mobile;
    @Bind(R.id.alternate_mob_et)
    EditText alternateMobile;
    @Bind(R.id.email_et)
    EditText email;
    @Bind(R.id.address_et)
    EditText address;
    @Bind(R.id.pincode_spn)
    Spinner pincode;
    @Bind(R.id.confirm_order_btn)
    Button confirmButton;

    private CoordinatorLayout coordinatorLayout;
    private Snackbar mSnackBar;
    private List<String> pincodeList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_order);
        ButterKnife.bind(this);

        coordinatorLayout = (CoordinatorLayout) findViewById(R.id.coordinatorLayout);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        init();
    }

    private void init() {
        pincodeList.add("Select your area pincode");
        pincodeList.add("560036");
        pincodeList.add("560037");
        pincodeList.add("560048");
        pincodeList.add("560066");
        pincodeList.add("560067");
        pincodeList.add("560087");

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.spinner_header, pincodeList);
        adapter.setDropDownViewResource(R.layout.spinner_dropdown);
        pincode.setAdapter(adapter);
    }

    protected boolean isOnline() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo nf = cm.getActiveNetworkInfo();
        if (nf != null && nf.isConnectedOrConnecting()) return true;
        else return false;
    }

    public void confirmOrder(View v) {
        if (isOnline()) {
            if (validateInput()) {

            }
        } else {
            showSnackBar("Network isn't available");
        }
    }

    private void showSnackBar(String msg) {
        Snackbar
                .make(coordinatorLayout, msg, Snackbar.LENGTH_LONG)
                .show();
    }

    private boolean validateInput() {
        if (name.getText().toString().equals("")) {
            showSnackBar("Please enter your name");
            return false;
        } else if (mobile.getText().toString().equals("")) {
            showSnackBar("Please enter your mobile number");
            return false;
        } else if (mobile.getText().toString().length() != 10) {
            showSnackBar("Please enter 10 digit mobile number");
            return false;
        } else if (address.getText().toString().equals("")) {
            showSnackBar("Please enter your address");
            return false;
        } else if (pincode.getSelectedItemPosition() == 0) {
            showSnackBar("Please select area pincode");
            return false;
        } else if (!email.getText().toString().equals("")) {
            if (!validateEmail()) {
                showSnackBar("Please enter valid email");
                return false;
            }
        }

        return true;
    }

    private boolean validateEmail() {
        String EMAIL_REGEX = "^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$";
        if (email.getText().toString().matches(EMAIL_REGEX)) {
            return true;
        }
        return false;
    }

    private void requestOrder() {

    }

}
