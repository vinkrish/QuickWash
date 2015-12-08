package in.vinkrish.quickwash.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import in.vinkrish.quickwash.R;


public class ServiceFragment extends Fragment {
	View view;

	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        int layoutScreen = getArguments().getInt("screen_count");

        if (layoutScreen == 0) {
            view = inflater.inflate(R.layout.wash_iron, container, false);
        } else if (layoutScreen == 1) {
            view = inflater.inflate(R.layout.wash_fold, container, false);
        } else if (layoutScreen == 2) {
            view = inflater.inflate(R.layout.iron, container, false);
        }else if (layoutScreen == 3){
            view = inflater.inflate(R.layout.dry_clean, container, false);
        }

        return view;
    }

    public static Fragment newInstance(int screenCount) {
    	ServiceFragment f = new ServiceFragment();
        Bundle b = new Bundle();
        b.putInt("screen_count", screenCount);
        f.setArguments(b);
        return f;
    }
    
}
