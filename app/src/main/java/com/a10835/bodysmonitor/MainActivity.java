package com.a10835.bodysmonitor;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.a10835.bodysmonitor.Helper.TcpClientConnector;

import org.w3c.dom.Text;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {
    private TextView mTemText;
    private TextView mPlusText;
    TcpClientConnector tcpClientConnector;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        tcpClientConnector = TcpClientConnector.getInstance();
        tcpClientConnector.setOnConnectLinstener(new TcpClientConnector.ConnectLinstener() {
            @Override
            public void onReceiveData(String data) {
                if (data.contains("t")){
                    mTemText.setText(data.substring(1)+"");
                    mPlusText.setText("");
                }
                else {
                    mPlusText.setText(  data.substring(1)+"");
                    mTemText.setText("");
                }

            }
        });
    }

    public void initView(){
        mTemText = findViewById(R.id.tv_temp);
        mPlusText = findViewById(R.id.tv_plus);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        try {
            tcpClientConnector.disconnect();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
