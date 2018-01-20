package testfairy;

import android.content.Context;

import com.testfairy.TestFairy;

/**
 * Created by yb34982 on 24/12/2017.
 */

public class TestFairyInit {
    public static void start(Context activity, String msg) {
        TestFairy.begin(activity, "6f4c3da555567854de9cc48b9844007c2c63eeb2");
        TestFairy.addEvent(msg);
    }
}
