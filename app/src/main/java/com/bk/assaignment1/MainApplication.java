package com.bk.assaignment1;

import android.app.Application;
import android.content.Context;

/**
 * Created by Saki Rezwana on 9/25/2017
 *
 * @author Biswajit Karmakar
 * @mail bkarmak@gmail.com
 * @version 1.0
 */
public class MainApplication extends Application {
    public static final String TAG = MainApplication.class
            .getSimpleName();

    private static MainApplication mInstance;

    private static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        mContext = getApplicationContext();

//        Thread.setDefaultUncaughtExceptionHandler(new CustomExceptionHandler(CustomExceptionHandler.context,
//                LoginActivity.class));

    }

    public static Context getContext() {
        if (null == mInstance) {
            mInstance = new MainApplication();
        }
        return mInstance;
    }

    public static synchronized MainApplication getInstance() {
        return mInstance;
    }
}
