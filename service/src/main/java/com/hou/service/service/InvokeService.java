package com.hou.service.service;

import android.app.Service;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.IBinder;
import android.os.Parcel;
import android.os.RemoteException;
import android.util.Log;

import com.hou.aidl.aidl.InvokeAidl;

public class InvokeService extends Service {

    private static final String TAG = "InvokeAidlService";

    private static BasicBinder mBasicBinder;

    private class BasicBinder extends InvokeAidl.Stub{

        /**
         * 两数相加
         *
         * @param a 整数 a
         * @param b 整数 b
         * @return a + b
         */
        @Override
        public int sum(int a, int b) throws RemoteException {
            int res = a + b;
            Log.d(TAG, a + " + " + b + " = " + res);
            return res;
        }

        /**
         * 将字符串逆序
         *
         * @param b 字符串
         * @return 逆序字符串
         */
        @Override
        public String printString(String b) throws RemoteException {
            StringBuilder stringBuilder = new StringBuilder(b);
            stringBuilder.reverse();
            Log.d(TAG, stringBuilder.toString());
            return stringBuilder.toString();
        }

        @Override
        public boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
            String packageName = getPackageManager().getNameForUid(getCallingUid());
            Log.d(TAG, "packageName: " + packageName);
            int callingPermission = checkCallingPermission("com.hou.service.permission_server");
            Log.d(TAG, "callingPermission: " + callingPermission);
            if (callingPermission == PackageManager.PERMISSION_DENIED) {
                reply.writeException( new RemoteException());
            }
            Log.d(TAG, "onTransact: PASS");
            return super.onTransact(code, data, reply, flags);
        }
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mBasicBinder = new BasicBinder();
    }

    public InvokeService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // Return the communication channel to the service.
        return mBasicBinder;
    }
}