package adityagurjar.rajasthantourism;

import android.app.DownloadManager;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.view.Window;
import android.webkit.ConsoleMessage;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.hkm.ezwebview.app.BasicWebViewNormal;

import java.io.InputStream;

public class MainActivity extends AppCompatActivity {

    WebView vb;
    ConnectionStatusDisplayer connectionStatusDisplayer;
    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            getWindow().getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
        getSupportActionBar().hide();
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);

        setContentView(R.layout.activity_main);


        vb=(WebView)findViewById(R.id.webview);
        vb.getSettings().setJavaScriptEnabled(true);
        vb.setWebViewClient(new customwebviewclient());
        vb.setWebChromeClient(new chrome());
        connectionStatusDisplayer=new ConnectionStatusDisplayer(vb);
        if(connectionStatusDisplayer.checkConnection())
        {
            vb.setVisibility(View.VISIBLE);
            vb.loadUrl("http://tourism.rajasthan.gov.in/");

        }
        else
        {
            vb.setVisibility(View.GONE);
        }
    }

    class chrome extends  WebChromeClient
    {
        @Override
        public boolean onConsoleMessage(ConsoleMessage cm)
        {

            return true;
        }

    }


    class customwebviewclient extends WebViewClient
    {
        @Override
        public void onPageFinished(WebView view, String url) {

           // vb.loadUrl("javascript: var slides = document.getElementsByClassName(\"innerslide-5\"); for(var i = 0; i < slides.length; i++) {   slides.item(i).style.display='none'; }");
            super.onPageFinished(view, url);
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
//            if(url.contains("pdf"))
//            {
//                DownloadManager downloadManager = (DownloadManager)getSystemService(DOWNLOAD_SERVICE);
//                Uri Download_Uri = Uri.parse("url");
//                DownloadManager.Request request = new DownloadManager.Request(Download_Uri);
//
//                //Restrict the types of networks over which this download may proceed.
//                request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI | DownloadManager.Request.NETWORK_MOBILE);
//                //Set whether this download may proceed over a roaming connection.
//                request.setAllowedOverRoaming(false);
//                //Set the title of this download, to be displayed in notifications (if enabled).
//                request.setTitle("My Data Download");
//                //Set a description of this download, to be displayed in notifications (if enabled)
//                request.setDescription("Android Data download using DownloadManager.");
//                //Set the local destination for the downloaded file to a path within the application's external files directory
//                request.setDestinationInExternalFilesDir(MainActivity.this, Environment.DIRECTORY_DOWNLOADS,"CountryList.json");
//
//                //Enqueue a new download and same the referenceId
//                 long                 downloadReference = downloadManager.enqueue(request);
//                return true;
//
//            }

             if(url.contains("tourism.rajasthan.gov.in")) {
                if(connectionStatusDisplayer.checkConnection())
                {
                    vb.setVisibility(View.VISIBLE);
                    return false;

                }
                else
                {
                    return true;
                }
        }
        else

            {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                startActivity(intent);
                return true;
            }
            }
    }

    private void injectJS() {
        try {
            InputStream inputStream = getAssets().open("jscript.js");
            byte[] buffer = new byte[inputStream.available()];
            inputStream.read(buffer);
            inputStream.close();
            String encoded = Base64.encodeToString(buffer, Base64.NO_WRAP);
            vb.loadUrl("javascript:(" +
                    "var parent = document.getElementsByTagName('body').item(0);" +
                    "var script = document.createElement('script');" +
                    "script.type = 'text/javascript';" +
                    "script.innerHTML = window.atob('" + encoded + "');" +
                    "parent.appendChild(script);");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onBackPressed() {
        if(vb.canGoBack())
        {
            vb.goBack();
        }
        else {
            super.onBackPressed();
        }
        }
}
