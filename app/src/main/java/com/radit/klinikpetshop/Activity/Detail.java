package com.radit.klinikpetshop.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.radit.klinikpetshop.R;

public class Detail extends AppCompatActivity {

    private TextView tvNama, tvDeskripsi, tvLokasi;
    private ImageView ivFoto;
    private String yNama, yFoto, yDeskripsi, yLokasi, yKoordinat;

    private Button btn_lokasi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        tvNama = findViewById(R.id.tv_nama);
        tvLokasi = findViewById(R.id.tv_lokasi);
        tvDeskripsi = findViewById(R.id.tv_deskripsi);
        ivFoto = findViewById(R.id.iv_foto);
        btn_lokasi = findViewById(R.id.bt_koordinat);

        Intent tangkap = getIntent();
        yNama = tangkap.getStringExtra("xNama");
        yFoto = tangkap.getStringExtra("xFoto");
        yDeskripsi = tangkap.getStringExtra("xDeskripsi");
        yLokasi = tangkap.getStringExtra("xLokasi");
        yKoordinat = tangkap.getStringExtra("xKoordinat");

        tvNama.setText(yNama);
        tvDeskripsi.setText(yDeskripsi);
        tvLokasi.setText(yLokasi);
        Glide
                .with(Detail.this)
                .load(yFoto)
                .into(ivFoto);

        btn_lokasi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri lokasi = Uri.parse("geo:0,0?q=" + yKoordinat);
                Intent bukalokasi = new Intent(Intent.ACTION_VIEW, lokasi);
                startActivity(bukalokasi);
            }
        });
    }
}