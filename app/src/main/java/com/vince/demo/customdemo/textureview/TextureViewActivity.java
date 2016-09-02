package com.vince.demo.customdemo.textureview;

import android.app.Activity;
import android.graphics.SurfaceTexture;
import android.hardware.Camera;
import android.os.Bundle;
import android.view.Gravity;
import android.view.TextureView;
import android.widget.FrameLayout;

import java.io.IOException;

public class TextureViewActivity extends Activity implements TextureView.SurfaceTextureListener {

    private static final String TAG = "TextureViewActivity";
    private TextureView myTexture;
    private Camera mCamera;

    @Override
    public void onSurfaceTextureAvailable(SurfaceTexture surface, int width, int height) {

        mCamera = Camera.open();
        Camera.Size previewSize = mCamera.getParameters().getPreviewSize();
        myTexture.setLayoutParams(new FrameLayout.LayoutParams(
                previewSize.width, previewSize.height, Gravity.CENTER));
        try {
            mCamera.setPreviewTexture(surface);
        } catch (IOException t) {
        }
        mCamera.startPreview();
        myTexture.setAlpha(1.0f);
        myTexture.setRotation(90.0f);
    }

    @Override
    public void onSurfaceTextureSizeChanged(SurfaceTexture surface, int width, int height) {

    }

    @Override
    public boolean onSurfaceTextureDestroyed(SurfaceTexture surface) {

        mCamera.stopPreview();
        mCamera.release();
        return true;
    }

    @Override
    public void onSurfaceTextureUpdated(SurfaceTexture surface) {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /*有报错，没有详细研究
        setContentView(R.layout.activity_texture_view);
        myTexture = (TextureView) findViewById(R.id.textureView);
        myTexture.setSurfaceTextureListener(this);*/
        myTexture = new TextureView(this);
        myTexture.setSurfaceTextureListener(this);
        setContentView(myTexture);
    }
}
