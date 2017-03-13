package lock.com.locked;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by DravitLochan on 13-03-2017.
 */

public class PassPref {

    int PRIVATE_MODE = 1;
    private static final String PREF_NAME = "Locked";
    private static final String PASSWORD = "password";
    private static final String IS_PASSWORD_SET="IsPasswordSet";
    private static final String IS_FIRST_TIME="IsFirstTime";

    SharedPreferences pref;
    SharedPreferences.Editor editor;
    Context context;

    PassPref(Context context) {
        this.context = context;
        pref = context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    boolean getIsPasswordSet() {
        return pref.getBoolean(IS_PASSWORD_SET,false);
    }

    void setIsPasswordSet(boolean isPasswordSet) {
        editor.putBoolean(IS_PASSWORD_SET,isPasswordSet);
        editor.commit();
    }

    String getPassword() {
        return pref.getString(PASSWORD,null);
    }

    void setPassword(String password) {
        editor.putString(PASSWORD,password);
        editor.commit();
    }

    boolean getIsFirstTime()
    {
        return pref.getBoolean(IS_FIRST_TIME,true);
    }

    void setIsFirstTime(boolean isFirstTime)
    {
        editor.putBoolean(IS_FIRST_TIME,isFirstTime);
        editor.commit();
    }
}
