package com.vince.demo.customdemo.acquisition_configuration;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.vince.demo.customdemo.R;

public class AcquisitionConfiguration extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_acquisition_configuration);

        Log.i("AcquisitionConfiguration--------------->型号", android.os.Build.MODEL);
    }
}
