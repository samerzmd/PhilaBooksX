package com.phila.samergigabyte.philabooksx;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.internal.LinkedTreeMap;
import com.phila.samergigabyte.R;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class SignUpActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        final EditText pass= (EditText) findViewById(R.id.password);
        final EditText email= (EditText) findViewById(R.id.email);
        Button signUp= (Button) findViewById(R.id.signUp);
        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Net.apiClient(SignUpActivity.this).signUp("-1", "test", email.getText().toString(), pass.getText().toString(), new Callback<Object>() {
                    @Override
                    public void success(Object o, Response response) {
                        Log.d("","");
                        if(((LinkedTreeMap) o).entrySet().iterator().next().toString().equals("Saved=true")){
                            Toast.makeText(SignUpActivity.this,"success",Toast.LENGTH_SHORT).show();
                            finish();
                        }
                        else {
                            Toast.makeText(SignUpActivity.this,"something wrong",Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        Toast.makeText(SignUpActivity.this,"communication error",Toast.LENGTH_SHORT).show();
                        finish();
                    }
                });
            }
        });
    }

}
