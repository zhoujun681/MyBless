package com.example.administrator.mybless;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Switch;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private WebView webView;
    private Switch aSwitch;
    private long exitTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        aSwitch = (Switch) findViewById(R.id.switch1);
        webView = (WebView)findViewById(R.id.wbvw);
        webView.getSettings().setJavaScriptEnabled(true);//让浏览器支持javascript
        webView.getSettings().setSupportZoom(true);// 设置可以支持缩放
        webView.getSettings().setBuiltInZoomControls(true);// 设置出现缩放工具
        webView.getSettings().setUseWideViewPort(true);//扩大比例的缩放
        webView.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN); //这个属性可以让webview只显示一列，也就是自适应页面大小 不能左右滑动
        webView.getSettings().setLoadWithOverviewMode(true);//缩放至屏幕的大小
        webView.setWebViewClient(new WebViewClient());
        webView.loadUrl("http://www.qifo365.com");

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        webView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
        webView.clearCache(true); //清除缓存
        webView.setWebChromeClient(null);
        webView.setWebViewClient(null);
        // 清除cookie即可彻底清除缓存
        webView.clearHistory();
        webView.clearFormData();
        CookieSyncManager cookieSyncManager =  CookieSyncManager.createInstance(webView.getContext());
        CookieManager cookieManager = CookieManager.getInstance();
        cookieManager.setAcceptCookie(true);
        cookieManager.removeSessionCookie();
        cookieManager.removeAllCookie();
    }

    public void Click1(View view) {
        if(aSwitch.isChecked()){
            webView.loadUrl("file:///android_asset/qifo1/qifo1.html");
        }else {
            webView.loadUrl("http://www.qifo365.com");
        }


    }

    public void Click2(View view) {

        if(aSwitch.isChecked()){
            webView.loadUrl("file:///android_asset/qifo2/礼佛网-主页.html");
        }else {
            webView.loadUrl("http://www.chinafo.org/wap");
        }
    }

    //使用Webview的时候，返回键没有重写的时候会直接关闭程序，这时候其实我们要其执行的知识回退到上一步的操作
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        //这是一个监听用的按键的方法，keyCode 监听用户的动作，如果是按了返回键，同时Webview要返回的话，WebView执行回退操作，因为mWebView.canGoBack()返回的是一个Boolean类型，所以我们把它返回为true
        if(keyCode==KeyEvent.KEYCODE_BACK&&webView.canGoBack()){
            if (System.currentTimeMillis() - exitTime > 2000) {
                webView.goBack();
                Toast.makeText(getApplicationContext(), "再按一次退出程序", Toast.LENGTH_SHORT).show();
                exitTime = System.currentTimeMillis();
            } else {
                finish();
                System.exit(0);
                android.os.Process.killProcess(android.os.Process.myPid());
            }
            return true;
        }else {
            return super.onKeyDown(keyCode,event);
        }


    }

}
