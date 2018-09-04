package adityagurjar.rajasthantourism;

/**
 * Created by adi on 23/7/16.
 */

import android.app.Application;


public class RajasthanTourism extends Application {

    private static RajasthanTourism mInstance;

    @Override
    public void onCreate() {
        super.onCreate();

        mInstance = this;


    }

    public static synchronized RajasthanTourism getInstance() {
        return mInstance;
    }

    public void setConnectivityListener(ConnectivityReceiver.ConnectivityReceiverListener listener) {
        ConnectivityReceiver.connectivityReceiverListener = listener;
    }
}