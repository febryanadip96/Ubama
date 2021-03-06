package com.example.biyan.ubama.toko;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.error.AuthFailureError;
import com.android.volley.error.VolleyError;
import com.android.volley.request.SimpleMultiPartRequest;
import com.android.volley.toolbox.Volley;
import com.example.biyan.ubama.R;
import com.example.biyan.ubama.UrlCama;
import com.example.biyan.ubama.UserToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class TokoJualProdukActivity extends AppCompatActivity {

    @BindView(R.id.image1)
    ImageView image1;
    @BindView(R.id.image2)
    ImageView image2;
    @BindView(R.id.image3)
    ImageView image3;
    @BindView(R.id.image4)
    ImageView image4;
    @BindView(R.id.hapus_image1)
    ImageView hapusImage1;
    @BindView(R.id.hapus_image2)
    ImageView hapusImage2;
    @BindView(R.id.hapus_image3)
    ImageView hapusImage3;
    @BindView(R.id.hapus_image4)
    ImageView hapusImage4;
    @BindView(R.id.nama_produk)
    EditText namaProduk;
    @BindView(R.id.deskripsi_produk)
    EditText deskripsiProduk;
    @BindView(R.id.catatan_penjual)
    EditText catatanPenjual;
    @BindView(R.id.harga_produk)
    EditText hargaProduk;
    @BindView(R.id.minimal_pembelian)
    EditText minimalPembelian;
    @BindView(R.id.kategori)
    RelativeLayout kategori;
    @BindView(R.id.fakultas)
    RelativeLayout fakultas;
    @BindView(R.id.baru)
    RadioButton baru;
    @BindView(R.id.bekas)
    RadioButton bekas;
    @BindView(R.id.kondisi)
    RadioGroup kondisi;
    @BindView(R.id.barang)
    RadioButton barang;
    @BindView(R.id.jasa)
    RadioButton jasa;
    @BindView(R.id.jenis)
    RadioGroup jenis;
    @BindView(R.id.simpan)
    Button simpan;
    @BindView(R.id.kurang)
    ImageView kurang;
    @BindView(R.id.tambah)
    ImageView tambah;
    @BindView(R.id.nama_kategori)
    TextView namaKategori;
    @BindView(R.id.nama_fakultas)
    TextView namaFakultas;
    @BindView(R.id.layout_nama_produk)
    TextInputLayout layoutNamaProduk;
    @BindView(R.id.layout_harga)
    TextInputLayout layoutHarga;
    @BindView(R.id.layout_min_pembelian)
    TextInputLayout layoutMinPembelian;
    @BindView(R.id.layout_deskripsi_produk)
    TextInputLayout layoutDeskripsiProduk;
    @BindView(R.id.layout_catatan_penjual)
    TextInputLayout layoutCatatanPenjual;

    int pilih = 0;
    String imagePath1 = "", imagePath2 = "", imagePath3 = "", imagePath4 = "";
    final int GALLERY_REQUEST = 1;
    final int PERMISSION_REQUEST_READ_STORAGE = 2;

    final int PILIH_KATEGORI = 3;
    final int PILIH_FAKULTAS = 4;

    int idSubkategori = 0;
    int idFakultas = 0;

    int baruBekas = 1, jenisProduk = 1;
    RequestQueue queue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_toko_jual_produk);
        ButterKnife.bind(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_EXTERNAL_STORAGE)) {
                //
            } else {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        PERMISSION_REQUEST_READ_STORAGE);
            }
        }
        queue = Volley.newRequestQueue(this);
        minimalPembelian.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    int tertulis = Integer.parseInt(minimalPembelian.getText().toString()) + 0;
                    if (tertulis < 1) {
                        tertulis = 1;
                        minimalPembelian.setText(tertulis + "");
                        Toast.makeText(TokoJualProdukActivity.this, "Pemesanan minimal 1", Toast.LENGTH_LONG).show();
                    } else {
                        minimalPembelian.setText(tertulis + "");
                    }
                }
            }
        });

        jenis.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(checkedId == R.id.jasa){
                    baru.setChecked(true);
                    for(int i = 0; i < kondisi.getChildCount(); i++){
                        (kondisi.getChildAt(i)).setEnabled(false);
                    }
                }
                else{
                    for(int i = 0; i < kondisi.getChildCount(); i++){
                        (kondisi.getChildAt(i)).setEnabled(true);
                    }
                }
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            this.finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Bundle res;
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case GALLERY_REQUEST:
                    Uri selectedImage = data.getData();
                    switch (pilih) {
                        case 1:
                            imagePath1 = getRealPathFromURI(selectedImage);
                            try {
                                Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImage);
                                image1.setImageBitmap(bitmap);
                            } catch (IOException e) {
                                Log.i("TAG", "Some exception " + e);
                            }
                            hapusImage1.setVisibility(View.VISIBLE);
                            break;
                        case 2:
                            imagePath2 = getRealPathFromURI(selectedImage);
                            try {
                                Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImage);
                                image2.setImageBitmap(bitmap);
                            } catch (IOException e) {
                                Log.i("TAG", "Some exception " + e);
                            }
                            hapusImage2.setVisibility(View.VISIBLE);
                            break;
                        case 3:
                            imagePath3 = getRealPathFromURI(selectedImage);
                            try {
                                Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImage);
                                image3.setImageBitmap(bitmap);
                            } catch (IOException e) {
                                Log.i("TAG", "Some exception " + e);
                            }
                            hapusImage3.setVisibility(View.VISIBLE);
                            break;
                        case 4:
                            imagePath4 = getRealPathFromURI(selectedImage);
                            try {
                                Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImage);
                                image4.setImageBitmap(bitmap);
                            } catch (IOException e) {
                                Log.i("TAG", "Some exception " + e);
                            }
                            hapusImage4.setVisibility(View.VISIBLE);
                            break;
                    }
                    break;
                case PILIH_KATEGORI:
                    res = data.getExtras();
                    idSubkategori = res.getInt("idSubkategori");
                    namaKategori.setText(res.getString("namaSubkategori"));
                    break;
                case PILIH_FAKULTAS:
                    res = data.getExtras();
                    idFakultas = res.getInt("idFakultas");
                    namaFakultas.setText(res.getString("namaFakultas"));
                    break;
            }
        }
    }


    private String getRealPathFromURI(Uri contentUri) {
        String[] proj = {MediaStore.Images.Media.DATA};
        CursorLoader loader = new CursorLoader(this, contentUri, proj, null, null, null);
        Cursor cursor = loader.loadInBackground();
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        String result = cursor.getString(column_index);
        cursor.close();
        return result;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_REQUEST_READ_STORAGE: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    //
                } else {
                    finish();
                }
                return;
            }
        }
    }

    @OnClick({R.id.image1, R.id.image2, R.id.image3, R.id.image4})
    public void onImageClicked(View view) {
        switch (view.getId()) {
            case R.id.image1:
                pilih = 1;
                break;
            case R.id.image2:
                pilih = 2;
                break;
            case R.id.image3:
                pilih = 3;
                break;
            case R.id.image4:
                pilih = 4;
                break;
        }
        startGallery();
    }

    public void startGallery() {
        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
        photoPickerIntent.setType("image/*");
        startActivityForResult(photoPickerIntent, GALLERY_REQUEST);
    }

    @OnClick(R.id.kategori)
    public void onKategoriClicked() {
        Intent intent = new Intent(this, TokoProdukPilihKategoriActivity.class);
        startActivityForResult(intent, PILIH_KATEGORI);
    }

    @OnClick(R.id.fakultas)
    public void onFakultasClicked() {
        Intent intent = new Intent(this, TokoProdukPilihFakultasActivity.class);
        startActivityForResult(intent, PILIH_FAKULTAS);
    }

    @OnClick(R.id.simpan)
    public void onSimpanClicked() {
        if (!(!imagePath1.equals("") || !imagePath2.equals("") || !imagePath3.equals("") || !imagePath4.equals(""))) {
            Toast.makeText(getApplicationContext(), "Masukkan gambar produk barang minimal 1", Toast.LENGTH_SHORT).show();
            return;
        }
        if (namaProduk.getText().toString().equals("")) {
            layoutNamaProduk.setError("Nama produk harus diisi");
            return;
        } else {
            layoutNamaProduk.setError(null);
        }
        if (hargaProduk.getText().toString().equals("") || hargaProduk.getText().toString().equals("0")) {
            layoutHarga.setError("Harga produk tidak valid");
            return;
        } else {
            layoutHarga.setError(null);
        }
        if (minimalPembelian.getText().toString().equals("") || minimalPembelian.getText().toString().equals("0")) {
            minimalPembelian.setError("Minimal pembelian tidak valid");
            return;
        } else {
            minimalPembelian.setError(null);
        }
        if (idSubkategori == 0) {
            Toast.makeText(getApplicationContext(), "Pilihan Kategori tidak valid", Toast.LENGTH_SHORT).show();
            return;
        }
        if (idFakultas == 0) {
            Toast.makeText(getApplicationContext(), "Pilihan fakultas tidak valid", Toast.LENGTH_SHORT).show();
            return;
        }
        if (deskripsiProduk.getText().toString().equals("")) {
            layoutDeskripsiProduk.setError("Deskripsi produk harus diisi");
            return;
        } else {
            layoutDeskripsiProduk.setError(null);
        }
        if (catatanPenjual.getText().toString().equals("")) {
            layoutCatatanPenjual.setError("Catatan penjual harus diisi");
            return;
        } else {
            layoutCatatanPenjual.setError(null);
        }

        simpanProduk();
    }

    public void simpanProduk() {
        final ProgressDialog loading = new ProgressDialog(this);
        loading.setIndeterminate(true);
        loading.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        loading.setMessage("Mohon menunggu");
        loading.show();
        int checkedRadioButtonIdKondisi = kondisi.getCheckedRadioButtonId();
        if (checkedRadioButtonIdKondisi == R.id.baru) {
            baruBekas = 1;
        } else if (checkedRadioButtonIdKondisi == R.id.bekas) {
            baruBekas = 2;
        }
        int checkedRadioButtonIdJenis = jenis.getCheckedRadioButtonId();
        if (checkedRadioButtonIdJenis == R.id.barang) {
            jenisProduk = 1;
        } else if (checkedRadioButtonIdJenis == R.id.jasa) {
            jenisProduk = 2;
        }
        String url = UrlCama.USER_JUAL_PRODUK;
        SimpleMultiPartRequest request = new SimpleMultiPartRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        loading.dismiss();
                        Log.d("Response", response);
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            Boolean tersimpan = jsonResponse.getBoolean("tersimpan");
                            if (tersimpan) {
                                finish();
                            }

                        } catch (JSONException e) {
                            // JSON error
                            e.printStackTrace();
                            Log.e("JSONException TokoCreateActivity", e.toString());
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                loading.dismiss();
                Log.e("Error Volley", error.toString());
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("Authorization", UserToken.getToken(getApplicationContext()));
                params.put("Accept", "application/json");
                return params;
            }
        };
        if (!imagePath1.equals("")) {
            request.addFile("gambar1", imagePath1);
        }
        if (!imagePath2.equals("")) {
            request.addFile("gambar2", imagePath2);
        }
        if (!imagePath3.equals("")) {
            request.addFile("gambar3", imagePath3);
        }
        if (!imagePath4.equals("")) {
            request.addFile("gambar4", imagePath4);
        }
        request.addMultipartParam("nama", "text/plain", namaProduk.getText().toString());
        request.addMultipartParam("harga", "text/plain", hargaProduk.getText().toString());
        request.addMultipartParam("min_pesan", "text/plain", minimalPembelian.getText().toString());
        request.addMultipartParam("subkategori_id", "text/plain", idSubkategori + "");
        request.addMultipartParam("fakultas_id", "text/plain", idFakultas + "");
        request.addMultipartParam("baruBekas", "text/plain", baruBekas + "");
        request.addMultipartParam("jenis", "text/plain", jenisProduk + "");
        request.addMultipartParam("deskripsi", "text/plain", deskripsiProduk.getText().toString());
        request.addMultipartParam("catatan_penjual", "text/plain", catatanPenjual.getText().toString());
        request.setRetryPolicy(new DefaultRetryPolicy(
                30000,
                0,  // maxNumRetries = 0 means no retry
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        request.setShouldCache(false);
        queue.add(request);
    }

    @OnClick({R.id.kurang, R.id.tambah})
    public void ubahJumlah(View view) {
        int minimal = Integer.parseInt(minimalPembelian.getText().toString()) + 0;
        switch (view.getId()) {
            case R.id.kurang:
                if (minimal <= 1) {
                    minimal = 1;
                    minimalPembelian.setText(minimal + "");
                    Toast.makeText(this, "Pemesanan minimal 1", Toast.LENGTH_LONG).show();
                } else {
                    minimal--;
                    minimalPembelian.setText(minimal + "");
                }
                break;
            case R.id.tambah:
                minimal++;
                minimalPembelian.setText(minimal + "");
                break;
        }
    }

    @OnClick({R.id.hapus_image1, R.id.hapus_image2, R.id.hapus_image3, R.id.hapus_image4})
    public void onHapusClicked(View view) {
        switch (view.getId()) {
            case R.id.hapus_image1:
                hapusImage1.setVisibility(View.GONE);
                imagePath1 = "";
                image1.setImageResource(R.drawable.ic_add_image);
                break;
            case R.id.hapus_image2:
                hapusImage2.setVisibility(View.GONE);
                imagePath2 = "";
                image2.setImageResource(R.drawable.ic_add_image);
                break;
            case R.id.hapus_image3:
                hapusImage3.setVisibility(View.GONE);
                imagePath3 = "";
                image3.setImageResource(R.drawable.ic_add_image);
                break;
            case R.id.hapus_image4:
                hapusImage4.setVisibility(View.GONE);
                imagePath4 = "";
                image4.setImageResource(R.drawable.ic_add_image);
                break;
        }
    }
}
