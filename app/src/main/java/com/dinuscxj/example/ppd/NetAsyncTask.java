package com.dinuscxj.example.ppd;

import android.os.AsyncTask;

import com.dinuscxj.example.model.OpenProjectFactory;
import com.dinuscxj.example.model.OpenProjectModel;

import java.util.ArrayList;

/**
 * Created by zhangyang131 on 2017/11/9.
 */

public abstract class NetAsyncTask extends AsyncTask<Integer, String, ArrayList<OpenProjectModel>> {
    public static final int SIMULATE_UNSPECIFIED = 0;
    public static final int SIMULATE_FRESH_FIRST = 1;
    public static final int SIMULATE_FRESH_NO_DATA = 2;
    public static final int SIMULATE_FRESH_FAILURE = 3;

    @Override
    protected ArrayList<OpenProjectModel> doInBackground(Integer... status) {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if (status[0] == SIMULATE_FRESH_FAILURE) {
            return null;
        } else if (status[0] == SIMULATE_FRESH_NO_DATA) {
            return new ArrayList<>();
        } else {
            return OpenProjectFactory.createOpenProjects();
        }
    }

    @Override
    protected void onPostExecute(ArrayList<OpenProjectModel> result) {
        super.onPostExecute(result);
        onResult(result);
    }

    public abstract void onResult(ArrayList<OpenProjectModel> result);
}
