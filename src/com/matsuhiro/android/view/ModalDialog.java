package com.matsuhiro.android.view;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Handler;
import android.os.Looper;

import java.util.concurrent.CountDownLatch;

public class ModalDialog extends AlertDialog {
    public static final int YES = 0;
    public static final int NO = 1;
    public static final int CANCEL = 2;
    
    private static int mResult;
    private static CountDownLatch mStartSignal = new CountDownLatch(1);
    
    protected ModalDialog(Context context) {
        super(context);
    }
    
    public static int show(Context context) {
        final ModalDialog.Builder builder = new ModalDialog.Builder(context);
        builder.setPositiveButton("YES", new OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                mResult = YES;
                mStartSignal.countDown();
            }
        })
        .setNegativeButton("NO", new OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                mResult = NO;
                mStartSignal.countDown();
            }
        })
        .setOnCancelListener(new OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                mResult = CANCEL;
                mStartSignal.countDown();
            }
        });
        Looper looper = Looper.getMainLooper();
        new Handler(looper).post(new Runnable() {
            @Override
            public void run() {
                builder.create().show();
            }
        });
        try {
            mStartSignal.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
        return mResult;
    }

}
