package tobiapplications.com.moviebase.utils;

import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

/**
 * Created by Tobias on 06.08.2017.
 */

public class InputManager {

    public static void hideKeyboardFor(View view) {
        if (view != null) {
            InputMethodManager imm = (InputMethodManager)view.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }
}
