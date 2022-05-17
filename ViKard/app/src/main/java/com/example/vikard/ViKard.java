package com.example.vikard;

import android.app.Application;

public class ViKard extends Application {
    private boolean login_flag = false;

    public boolean get_login_flag() {
        return login_flag;
    }

    public void set_login_flag(boolean f) {
        this.login_flag = f;
    }
}

