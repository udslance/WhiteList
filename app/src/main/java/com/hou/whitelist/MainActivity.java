package com.hou.whitelist;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.hou.aidl.aidl.InvokeAidl;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private static final String TAG = "MainActivity";

    private static InvokeAidl mInvokeAidl;

    private Button intButton;

    private Button stringButton;

    private EditText intA;

    private EditText intB;

    private EditText orgStr;

    private Button connectButton;

    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(TAG, "onCreate");
        intButton = findViewById(R.id.intButton);
        stringButton = findViewById(R.id.stringButton);
        connectButton = findViewById(R.id.connectButton);
        textView = findViewById(R.id.mTextView);
        intA = findViewById(R.id.intA);
        intB = findViewById(R.id.intB);
        orgStr = findViewById(R.id.orgStr);
        intButton.setOnClickListener(this);
        stringButton.setOnClickListener(this);
        connectButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.connectButton:
                connectToService();
                break;
            case R.id.intButton:
                try {
                    sum();
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
                break;
            case R.id.stringButton:
                try {
                    reverse();
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
                break;
            default:
                break;

        }
    }

    private void connectToService() {
        Log.d(TAG, "connectToService");
        Intent intent = new Intent();
        intent.setAction("com.hou.service.service.InvokeService");
        intent.setPackage("com.hou.service");
        bindService(intent, connection, Service.BIND_AUTO_CREATE);
    }

    private void sum() throws RemoteException {
        if (mInvokeAidl == null) {
            connectToService();
        }
        Log.d(TAG, "sum");
        if(TextUtils.isEmpty(intA.getText()) || TextUtils.isEmpty(intB.getText())){
            textView.setText("请正确输入");
            return;
        }
        int a = Integer.parseInt(intA.getText().toString());
        int b = Integer.parseInt(intB.getText().toString());
        String res = mInvokeAidl.sum(a, b) + "";
        textView.setText(res);
    }

    private void reverse() throws RemoteException {
        Log.d(TAG, "reverse");
        String org = orgStr.getText().toString();
        String res = mInvokeAidl.printString(org);
        textView.setText(res);
    }

    private ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            mInvokeAidl = InvokeAidl.Stub.asInterface(iBinder);
            textView.setText("成功建立连接");
        }
        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            mInvokeAidl = null;
        }
    };
}