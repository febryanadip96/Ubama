package com.example.biyan.ubama;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RegisterActivity extends AppCompatActivity {

    RequestQueue queue;
    @BindView(R.id.namaLengkap)
    EditText namaLengkap;
    @BindView(R.id.lakiLaki)
    RadioButton lakiLaki;
    @BindView(R.id.perempuan)
    RadioButton perempuan;
    @BindView(R.id.jenisKelamin)
    RadioGroup jenisKelamin;
    @BindView(R.id.telepon)
    EditText telepon;
    @BindView(R.id.alamat)
    EditText alamat;
    @BindView(R.id.email)
    EditText email;
    @BindView(R.id.password)
    EditText password;
    @BindView(R.id.passwordConfirm)
    EditText passwordConfirm;
    @BindView(R.id.register)
    Button register;
    @BindView(R.id.loading)
    ProgressBar loading;
    private int jenis;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
        queue = Volley.newRequestQueue(this);
    }

    @OnClick(R.id.register)
    public void onViewClicked() {
        CekDataRegister();
    }

    private void CekDataRegister() {
        if (namaLengkap.getText().toString().equals("") || telepon.getText().toString().equals("") || alamat.getText().toString().equals("") || email.getText().toString().equals("")) {
            Toast.makeText(getApplicationContext(), "Harap isi data dengan lengkap", Toast.LENGTH_SHORT).show();
            return;
        }
        if (!password.getText().toString().equals(passwordConfirm.getText().toString())) {
            Toast.makeText(getApplicationContext(), "Konfirmasi Password tidak sama dengan Password", Toast.LENGTH_SHORT).show();
            password.requestFocus();
            return;
        }
        jenis = 0;
        int checkedRadioButtonId = jenisKelamin.getCheckedRadioButtonId();
        if (checkedRadioButtonId == R.id.lakiLaki) {
             jenis = 0;
        } else if (checkedRadioButtonId == R.id.perempuan) {
            jenis = 1;
        }
        ProsesRegister();
    }

    private void ProsesRegister() {
        loading.setVisibility(View.VISIBLE);
        Map<String, String> params = new HashMap<>();
        params.put("name", namaLengkap.getText().toString());
        params.put("email", email.getText().toString());
        params.put("password", password.getText().toString());
        params.put("jenis_kelamin", jenis + "");
        params.put("telepon", telepon.getText().toString());
        params.put("alamat", alamat.getText().toString());
        String url = UrlUbama.Register;
        JsonObjectRequest loginRequest = new JsonObjectRequest(Request.Method.POST, url, new JSONObject(params), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                loading.setVisibility(View.GONE);
                try {
                    if (!response.isNull("message")) {
                        Toast.makeText(getApplicationContext(), response.getString("message"), Toast.LENGTH_SHORT).show();
                    }
                    if (!response.isNull("back")) {
                        if (response.getBoolean("back")) {
                            RegisterActivity.this.finish();
                        }
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                loading.setVisibility(View.GONE);
                Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Accept", "application/json");
                return params;
            }
        };
        queue.add(loginRequest);
    }


}
