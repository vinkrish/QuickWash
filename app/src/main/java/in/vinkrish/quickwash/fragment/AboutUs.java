package in.vinkrish.quickwash.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import in.vinkrish.quickwash.R;

/**
 * Created by vinkrish on 08/12/15.
 */
public class AboutUs extends android.support.v4.app.Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.about_us, container, false);

        view.findViewById(R.id.call_customerCare).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:9164065688"));
                startActivity(intent);
            }
        });

        view.findViewById(R.id.email_costumerCare).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND);
                emailIntent.setType("plain/text");
                emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL, new String[]{"quickwashbangalore@gmail.com"});
                emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "QuickWash Service");
                startActivity(Intent.createChooser(emailIntent, "Send mail..."));

                /*Uri uri = Uri.parse("mailto:" + "quickwashbangalore@gmail.com")
                        .buildUpon()
                        .appendQueryParameter("subject", "QuickWash Service")
                        .build();

                Intent emailIntent = new Intent(Intent.ACTION_SENDTO, uri);
                startActivity(Intent.createChooser(emailIntent, "Send mail..."));*/

            }
        });

        return view;
    }
}
