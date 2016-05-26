package com.jk.significantmotiontest;

import android.os.AsyncTask;
import android.util.Log;

/**
 * Created by jk on 25/05/16.
 */
class UpdateUITask extends AsyncTask<String, Void, String> {

    @Override
    protected String doInBackground(String... params) {
        Log.d("UpdateUITask", "Task triggered!");
        Log.d("UpdateUITask", params[0]);

        return null;
    }

}
