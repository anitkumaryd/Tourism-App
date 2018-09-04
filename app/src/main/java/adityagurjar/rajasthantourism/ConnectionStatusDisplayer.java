package adityagurjar.rajasthantourism;

import android.graphics.Color;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.TextView;

/**
 * Created by adi on 23/7/16.
 */
public class ConnectionStatusDisplayer implements  ConnectivityReceiver.ConnectivityReceiverListener {
    View currentview;
    public ConnectionStatusDisplayer(View view)
    {
        currentview=view;
    }

    public boolean checkConnection() {
        boolean isConnected = ConnectivityReceiver.isConnected();
        showSnack(isConnected,currentview);
        return isConnected;
    }
    // Showing the status in Snackbar
    private void showSnack(boolean isConnected,View v) {

        Snackbar snackbar = null;

        if (isConnected) {


        } else {


            snackbar= Snackbar.make(v, "No Internet Connection!", Snackbar.LENGTH_INDEFINITE).setAction(
                            "Retry", new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    checkConnection();
                                }
                            }
                    );
            View snackBarView = snackbar.getView();
            snackBarView.setBackgroundColor(Color.RED);
            TextView textView = (TextView) snackBarView.findViewById(android.support.design.R.id.snackbar_text);
            textView.setTextColor(Color.WHITE);
            snackbar.show();
        }

    }

    @Override
    public void onNetworkConnectionChanged(boolean isConnected) {
        showSnack(isConnected,currentview);
    }
}
