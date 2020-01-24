package com.appsnipp.admin;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.appsnipp.admin.Navigation_Profile.Navigation_Activity;
import com.appsnipp.admin.apiinterface.Api;
import com.appsnipp.admin.apiinterface.ApiClient;
import com.appsnipp.admin.apiinterface.CommanResponse;
import com.appsnipp.admin.registration.Forgotpassword_form;
import com.appsnipp.admin.registration.Registration;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    TextView re;
    EditText no,pass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);
        re = (TextView) findViewById(R.id.regi);
        re.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(LoginActivity.this, Registration.class);
                startActivity(i);

            }
        });
        no=(EditText) findViewById(R.id.mono);
        pass=(EditText) findViewById(R.id.password);

    }


    public void viewForgotPAssword(View view) {
        Intent i = new Intent(LoginActivity.this, Forgotpassword_form.class);
        startActivity(i);

    }

    public void login(View view) {
        String n=no.getText().toString();
        String p=pass.getText().toString();
        Api api = ApiClient.getClient().create(Api.class);
        Call<CommanResponse> call=api.login("loginmember",n,p);
        call.enqueue(new Callback<CommanResponse>() {
            @Override
            public void onResponse(Call<CommanResponse> call, Response<CommanResponse> response) {
                if (response.body().getSuccess()==405) {
                    Intent i = new Intent(LoginActivity.this, Navigation_Activity.class);
                    startActivity(i);
                    Toast.makeText(LoginActivity.this, response.body().getMessage()+"", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(LoginActivity.this, response.body().getMessage()+"", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<CommanResponse> call, Throwable t) {
                Toast.makeText(LoginActivity.this, t.getLocalizedMessage()+"", Toast.LENGTH_SHORT).show();
            }
        });








//        String s1=no.getText().toString();
//        String s2=pass.getText().toString();
//        if (s1.isEmpty()){
//            no.setError("Enter Mobile No");
//        }
//        if (s2.isEmpty()){
//            pass.setError("Enter Pssword");
//        }
//        if(s2.equals("aaa") && s1.equals("1111111111")) {
//            LayoutInflater li = getLayoutInflater();
//            View layout = li.inflate(R.layout.toast,(ViewGroup) findViewById(R.id.toast));
//            Toast t=new Toast(getApplicationContext());
//            t.setDuration(Toast.LENGTH_SHORT);
//            t.setView(layout);
//t.show();
//
        }

}
