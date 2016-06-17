package com.vince.demo.customdemo.texttospeech;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.vince.demo.customdemo.R;

public class TextToSpeech extends Activity {

    private TextToSpeech mSpeech = null;
    private Button btn = null;
    private EditText ev = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text_to_speech);
    }

    public void onClick(View v) {

    }


}
