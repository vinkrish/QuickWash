package in.vinkrish.quickwash;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
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

    private List<String> pincodeList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_order);
        ButterKnife.bind(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        init();
    }

    private void init() {
        pincodeList.add("Select pincode");
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

}
