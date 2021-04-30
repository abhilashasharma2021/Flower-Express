package com.flowerexpress.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.flowerexpress.HomeActivity;
import com.flowerexpress.R;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        TextView txtSignup = findViewById(R.id.txtSignup);
        Button btnLogin = findViewById(R.id.btnLogin);


        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), HomeActivity.class));
                finish();
            }
        });


        SpannableString sp = new SpannableString("Don't have an account yet? Signup");

        ClickableSpan clickableSpan = new ClickableSpan() {
            @Override
            public void onClick(@NonNull View view) {

                startActivity(new Intent(getApplicationContext(), SignupActivity.class));
                finish();
            }

            @Override
            public void updateDrawState(@NonNull TextPaint ds) {
                super.updateDrawState(ds);
                ds.setUnderlineText(false);
            }
        };


        sp.setSpan(clickableSpan, 27, 33, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        txtSignup.setText(sp);
        txtSignup.setMovementMethod(LinkMovementMethod.getInstance());
        txtSignup.setHighlightColor(Color.TRANSPARENT);
    }
}