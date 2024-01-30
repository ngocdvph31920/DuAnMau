package fpoly.ngocdvph31920.duanmau.preference;

import android.content.Context;
import android.content.SharedPreferences;

public class UserPreferences {

    //khoi tao
    private static final String KEY_LOGIN_STATE = "key_login_state";
    private final SharedPreferences sharedPreferences;

    // tạo contructor
    public UserPreferences(Context context) {
        sharedPreferences = context.getSharedPreferences("user_preferences", Context.MODE_PRIVATE);
    }


    //is login if true and not login if false
    public boolean isLogin() {

        return sharedPreferences.getBoolean(KEY_LOGIN_STATE, false);

    }

    //set login state
    public void setLogin(boolean state) {

        sharedPreferences.edit()
                .putBoolean(KEY_LOGIN_STATE, state)
                .apply();

    }

    //logout
    public void logout() {

        sharedPreferences.edit()
                .clear()
                .apply();

    }

    //Save id user
    public void setIdUser(String id) {

        sharedPreferences.edit()
                .putString("id_user", id)
                .apply();

    }

    //Get id user
    public int getIdUser() {

        return sharedPreferences.getInt("id_user", 0);

    }

    public void setFirstTime(boolean state) {

        sharedPreferences.edit()
                .putBoolean("first_time", state)
                .apply();

    }

    //get first time
    public boolean getFirstTime() {

        return sharedPreferences.getBoolean("first_time", true);

    }
}
