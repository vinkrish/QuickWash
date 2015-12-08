package in.vinkrish.quickwash.adapter;

//import android.app.FragmentManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

import in.vinkrish.quickwash.fragment.ServiceFragment;

public class ScreenSliderAdapter extends FragmentPagerAdapter {

	List<Integer> layouts;
	
	public ScreenSliderAdapter(FragmentManager fm, List<Integer> layouts) {
		super(fm);
		this.layouts = layouts;
	}

	@Override
	public Fragment getItem(int position) {
		return ServiceFragment.newInstance(layouts.get(position));
	}

	@Override
	public int getCount() {
		return layouts.size();
	}

}
