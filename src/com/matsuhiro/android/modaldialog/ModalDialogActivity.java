package com.matsuhiro.android.modaldialog;

import com.matsuhiro.android.view.ModalDialog;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class ModalDialogActivity extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        Button btn = (Button) findViewById(R.id.start);
        btn.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Context c = v.getContext();
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        final int result = ModalDialog.show(c);
                        ModalDialogActivity.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast toast;
                                switch (result) {
                                    case ModalDialog.YES:
                                        toast = Toast.makeText(c, "YES", Toast.LENGTH_SHORT);
                                        break;
                                    case ModalDialog.NO:
                                        toast = Toast.makeText(c, "NO", Toast.LENGTH_SHORT);
                                        break;
                                    case ModalDialog.CANCEL:
                                    default:
                                        toast = Toast.makeText(c, "CANCEL", Toast.LENGTH_SHORT);
                                        break;
                                }
                                toast.show();
                            }
                            
                        });
                    }
                }).start();
            }
            
        });
    }
    
    
}