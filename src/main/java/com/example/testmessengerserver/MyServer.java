package com.example.testmessengerserver;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * Created by Administrator on 2017/11/26 0026.
 */

public class MyServer extends Service {
    private static final int MSG_SUM = 0x110;
    private final String TAG="MESSENGER--";
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.i(TAG,"onBind----");
        return mMessenger.getBinder();
    }

    private Messenger mMessenger=new Messenger(new Handler(){
        @Override
        public void handleMessage(Message fromClient) {
            Message toClient=Message.obtain(fromClient);//返回给客户端的消息
            switch (fromClient.what){
                case MSG_SUM:
                    try {
                        Thread.sleep(2000);//模拟延时操作
                        toClient.what=MSG_SUM;
                        toClient.arg1=fromClient.arg1+fromClient.arg2;
                        fromClient.replyTo.send(toClient);
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    break;
            }
            super.handleMessage(fromClient);
        }
    });
}
