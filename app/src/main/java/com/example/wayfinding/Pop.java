package com.example.wayfinding;
import  android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;

import com.example.wayfinding.R;


public class Pop extends Activity{
    EditText inputText;
    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.popup_admin);

        DisplayMetrics dm =new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;


        WindowManager.LayoutParams params = getWindow().getAttributes();

        getWindow().setAttributes(params);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        getWindow().setLayout((int)(width*.85),(int)(height*.72));
        inputText= (EditText) findViewById(R.id.text_input);
    }
}
