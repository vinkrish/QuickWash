package in.vinkrish.quickwash;

import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;
import in.vinkrish.quickwash.data.Order;
import in.vinkrish.quickwash.data.OrderResponse;
import in.vinkrish.quickwash.data.QuickWashCRUD;
import in.vinkrish.quickwash.data.QuickWashContract.QuickWashEntry;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PlaceOrderActivity extends AppCompatActivity {
    @BindView(R.id.name_et) EditText nameET;
    @BindView(R.id.mobile_et) EditText mobileET;
    @BindView(R.id.alternate_mob_et) EditText alternateMobileET;
    @BindView(R.id.email_et) EditText emailET;
    @BindView(R.id.address_et) EditText addressET;
    @BindView(R.id.pincode_spn) Spinner pincodeSpinner;

    private CoordinatorLayout coordinatorLayout;
    private ApiEndPointInterface apiService;
    private Retrofit retrofit;
    private Order order;
    private String service;
    private String name, mobile, alternateMobile, email, address, pincode;
    private List<String> pincodeList = new ArrayList<>();
    private static final String BASE_URL = "http://vingel.in";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_order);
        ButterKnife.bind(this);

        coordinatorLayout = findViewById(R.id.coordinatorLayout);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        init();
    }

    private void init() {

        Bundle bundle = getIntent().getExtras();
        if (bundle != null)
            service = (String) bundle.get("service");

        pincodeList.add("Select your area pincode");
        pincodeList.add("560036");
        pincodeList.add("560037");
        pincodeList.add("560048");
        pincodeList.add("560066");
        pincodeList.add("560067");
        pincodeList.add("560087");

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.spinner_header, pincodeList);
        adapter.setDropDownViewResource(R.layout.spinner_dropdown);
        pincodeSpinner.setAdapter(adapter);
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
                makeRequestObject();
                new OrderAsyncTak().execute();
            }
        } else {
            showSnackBar("Network isn't available");
        }
    }

    private void showSnackBar(String msg) {
        Snackbar.make(coordinatorLayout, msg, Snackbar.LENGTH_LONG).show();
    }

    private boolean validateInput() {
        prepareText();
        if (name.equals("")) {
            showSnackBar("Please enter your name");
            return false;
        } else if (mobile.equals("")) {
            showSnackBar("Please enter your mobile number");
            return false;
        } else if (mobile.length() != 10) {
            showSnackBar("Please enter 10 digit mobile number");
            return false;
        } else if (address.equals("")) {
            showSnackBar("Please enter your address");
            return false;
        } else if (pincodeSpinner.getSelectedItemPosition() == 0) {
            showSnackBar("Please select area pincode");
            return false;
        } else if (!email.equals("")) {
            if (!validateEmail()) {
                showSnackBar("Please enter valid email");
                return false;
            }
        }

        return true;
    }

    private void prepareText() {
        name = nameET.getText().toString();
        mobile = mobileET.getText().toString();
        alternateMobile = alternateMobileET.getText().toString();
        email = emailET.getText().toString();
        address = addressET.getText().toString();
        pincode = pincodeSpinner.getSelectedItem().toString();
    }

    private boolean validateEmail() {
        //String EMAIL_REGEX = "^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$";
        String regex = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(email);
        if (matcher.matches()) {
            return true;
        } else return false;
    }

    private void makeRequestObject() {
        order = new Order();
        order.setName(name);
        order.setMobile(mobile);
        order.setAlternateMobile(alternateMobile);
        order.setEmail(email);
        order.setAddress(address);
        order.setPincode(pincode);
        order.setService(service);
        order.setDate(getToday());
    }

    private ContentValues createOrderValues() {
        ContentValues orderValues = new ContentValues();
        orderValues.put(QuickWashEntry.COLUMN_NAME, name);
        orderValues.put(QuickWashEntry.COLUMN_MOBILE, mobile);
        orderValues.put(QuickWashEntry.COLUMN_ALTERNATE_MOBILE, alternateMobile);
        orderValues.put(QuickWashEntry.COLUMN_EMAIL, email);
        orderValues.put(QuickWashEntry.COLUMN_ADDRESS, address);
        orderValues.put(QuickWashEntry.COLUMN_PINCODE, pincode);
        orderValues.put(QuickWashEntry.COLUMN_SERVICE, service);
        orderValues.put(QuickWashEntry.COLUMN_DATE, getToday());
        return orderValues;
    }

    private String getToday() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
        Date today = new Date();
        return dateFormat.format(today);
    }

    class OrderAsyncTak extends AsyncTask<Void, Void, Void> {
        ProgressDialog pDialog = new ProgressDialog(PlaceOrderActivity.this);

        protected void onPreExecute() {
            super.onPreExecute();
            pDialog.setMessage("Confirming Order...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected Void doInBackground(Void... params) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            apiService = retrofit.create(ApiEndPointInterface.class);

            OrderResponse orderResponse = null;
            try {
                orderResponse = apiService.saveNewOrder(order).execute().body();
            } catch (IOException e) {
                e.printStackTrace();
            }
            Log.d("response", orderResponse.getStatus());
            return null;
        }

        protected void onPostExecute(Void v) {
            super.onPostExecute(v);
            QuickWashCRUD.inertOrder(PlaceOrderActivity.this, createOrderValues());
            showSnackBar("Confirmed, we will contact you soon");
            pDialog.dismiss();
            Intent intent = new Intent(PlaceOrderActivity.this, in.vinkrish.quickwash.HomeActivity.class);
            startActivity(intent);
        }

    }

}
