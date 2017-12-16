package in.vinkrish.quickwash;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

/**
 * Created by vinkrish.
 */
class ReplaceFragment {

    static void replace(Fragment f, FragmentManager fm) {
        fm
                .beginTransaction()
                .replace(R.id.content_frame, f)
                .commit();
    }
}
