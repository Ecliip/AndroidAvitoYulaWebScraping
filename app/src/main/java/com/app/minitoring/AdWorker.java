package com.app.minitoring;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import java.util.Random;

public class AdWorker extends Worker {
    private static final String TAG = AdWorker.class.getSimpleName();
    AdRepository adRepository;
//    private long counter = 1;

    public AdWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {

        int random = new Random().nextInt();

        String counterVal = Integer.toString(random);
        Ad ad = new Ad("fakeId-" + counterVal, counterVal, "fakeUrl"+ counterVal, "fakeQuery"+ counterVal);

        System.out.println(random);
                    return Result.success();
//        try {
//            Log.i("worker",  counterVal);
//            AppDatabase.getDatabase(getApplicationContext()).adDAO().insertAd(ad);
//
//            // If there were no errors, return SUCCESS
//            return Result.success();
//        } catch (Throwable throwable) {
//
//            // Technically WorkManager will return Result.failure()
//            // but it's best to be explicit about it.
//            // Thus if there were errors, we're return FAILURE
//            Log.e(TAG, "Error", throwable);
//            return Result.failure();
//        }
    }

}
