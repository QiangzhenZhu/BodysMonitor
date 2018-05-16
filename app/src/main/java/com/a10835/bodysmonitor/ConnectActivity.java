package com.a10835.bodysmonitor;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.a10835.bodysmonitor.Helper.TcpClientConnector;

public class ConnectActivity extends AppCompatActivity {
    private EditText mIpEditText;
    private EditText mPortEditText;
    private ProgressBar mProgresBar;
    private TextView mBtnConnect;
    private String Ip;
    private String Port;
    private Context mContext;
    TcpClientConnector tcpClientConnector;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connect);
        mContext = this;
        initView();
        initDate();
        mBtnConnect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Connect();
            }
        });



    }
    public void initView(){
        mIpEditText = findViewById(R.id.et_ip);
        mPortEditText = findViewById(R.id.et_port);
        mProgresBar = findViewById(R.id.progress_bar);
        mBtnConnect = findViewById(R.id.btn_connect);
    }

    public void initDate(){
        ChangeProgressBarState();
    }

    //改变ProgressBar的可见性；
    public void ChangeProgressBarState(){
        if (mProgresBar.getVisibility() == View.VISIBLE){
            mProgresBar.setVisibility(View.GONE);
        }else {
            mProgresBar.setVisibility(View.VISIBLE);
        }
    }

    public void Connect() {
        Ip = mIpEditText.getText().toString();
        Port = mPortEditText.getText().toString();
        if (TextUtils.isEmpty(Ip)) {
            Toast.makeText(mContext, getResources().getString(R.string.et_ip_string), Toast.LENGTH_SHORT).show();
            return;
        } else if (TextUtils.isEmpty(Port)) {
            Toast.makeText(mContext, getResources().getString(R.string.et_port_string), Toast.LENGTH_SHORT).show();
            return;
        } else {
            ChangeProgressBarState();
            tcpClientConnector = TcpClientConnector.getInstance();
            tcpClientConnector.creatConnect(Ip, Integer.parseInt(Port));
            tcpClientConnector.setOnCreateSuccessListner(new TcpClientConnector.OnCreateSuccessListner() {
                @Override
                public void onSuccess() {
                    ChangeProgressBarState();
                    startActivity(new Intent(mContext, MainActivity.class));
                    finish();

                }
            });


        }
    }

}
