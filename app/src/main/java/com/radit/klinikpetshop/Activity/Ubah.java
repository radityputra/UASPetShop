package com.radit.klinikpetshop.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.radit.klinikpetshop.API.APIRequestData;
import com.radit.klinikpetshop.API.RetroServer;
import com.radit.klinikpetshop.Model.ModelResponse;
import com.radit.klinikpetshop.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Ubah extends AppCompatActivity {

    private String yId, yNama, yFoto, yDeskripsi, yLokasi, yKoordinat;
    private EditText etNama, etFoto, etDeskripsi, etLokasi, etKoordinat;
    private Button btnUbah;
    private String Nama, Foto, Deskripsi, Lokasi, Koordinat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ubah);

        etNama = findViewById(R.id.et_nama);
        etFoto = findViewById(R.id.et_foto);
        etDeskripsi = findViewById(R.id.et_deskripsi);
        etLokasi = findViewById(R.id.et_lokasi);
        etKoordinat = findViewById(R.id.et_koordinat);
        btnUbah = findViewById(R.id.btn_ubah);

        Intent terima = getIntent();
        yId = terima.getStringExtra("xId");
        yNama = terima.getStringExtra("xNama");
        yKoordinat = terima.getStringExtra("xKoordinat");
        yFoto = terima.getStringExtra("xFoto");
        yDeskripsi = terima.getStringExtra("xDeskripsi");
        yLokasi = terima.getStringExtra("xLokasi");

        etNama.setText(yNama);
        etKoordinat.setText(yKoordinat);
        etFoto.setText(yFoto);
        etDeskripsi.setText(yDeskripsi);
        etLokasi.setText(yLokasi);

        btnUbah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Nama = etNama.getText().toString();
                Koordinat = etKoordinat.getText().toString();
                Foto = etFoto.getText().toString();
                Deskripsi = etDeskripsi.getText().toString();
                Lokasi = etLokasi.getText().toString();

                if (Nama.trim().isEmpty()) {
                    etNama.setError("Nama harus di isi!");
                } else if (Koordinat.trim().isEmpty()) {
                    etKoordinat.setError("Koordinat harus di isi!");
                } else if (Foto.trim().isEmpty()) {
                    etFoto.setError("Foto harus di isi!");
                } else if (Lokasi.trim().isEmpty()) {
                    etLokasi.setError("Alamat harus di isi!");
                } else if (Deskripsi.trim().isEmpty()) {
                    etDeskripsi.setError("Tentang harus di isi!");
                } else {
                    ubahPetShop();
                }
            }
        });
    }

    private void ubahPetShop(){
        APIRequestData API = RetroServer.konekRetrofit().create(APIRequestData.class);
        Call<ModelResponse> proses = API.ardUpdate(yId, Nama, Foto, Deskripsi, Lokasi, Koordinat);

        proses.enqueue(new Callback<ModelResponse>() {
            @Override
            public void onResponse(Call<ModelResponse> call, Response<ModelResponse> response) {
                String kode, pesan;
                kode = response.body().getKode();
                pesan = response.body().getPesan();

                Toast.makeText(Ubah.this, "kode : " + kode +" Pesan : " + pesan, Toast.LENGTH_SHORT).show();
                finish();

            }
            @Override
            public void onFailure(Call<ModelResponse> call, Throwable t) {
                Toast.makeText(Ubah.this, "Error: Gagal ubah data! ", Toast.LENGTH_SHORT).show();
            }
        });
    }
}