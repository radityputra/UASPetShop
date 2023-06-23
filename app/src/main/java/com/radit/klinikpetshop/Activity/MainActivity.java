package com.radit.klinikpetshop.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.radit.klinikpetshop.API.APIRequestData;
import com.radit.klinikpetshop.API.RetroServer;
import com.radit.klinikpetshop.Adapter.Adapter;
import com.radit.klinikpetshop.Model.ModelPetShop;
import com.radit.klinikpetshop.Model.ModelResponse;
import com.radit.klinikpetshop.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private RecyclerView rvPetShop;
    private FloatingActionButton fabAdd;
    private ProgressBar pbPetShop;
    private RecyclerView.Adapter adPetShop;
    private RecyclerView.LayoutManager lmPetShop;
    private List<ModelPetShop> listPetShop = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rvPetShop = findViewById(R.id.rv_petshop);
        fabAdd = findViewById(R.id.fab_add);
        pbPetShop = findViewById(R.id.pb_petshop);

        lmPetShop = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        rvPetShop.setLayoutManager(lmPetShop);
    }

    @Override
    protected void onResume() {
        super.onResume();
        retrivePetShop();
    }

    public void retrivePetShop(){
        pbPetShop.setVisibility(View.VISIBLE);

        APIRequestData API = RetroServer.konekRetrofit().create(APIRequestData.class);
        Call<ModelResponse> proses = API.ardRetrive();
        proses.enqueue(new Callback<ModelResponse>() {
            @Override
            public void onResponse(Call<ModelResponse> call, Response<ModelResponse> response) {
                String kode = response.body().getKode();
                String pesan = response.body().getPesan();
                listPetShop = response.body().getData();
                adPetShop = new Adapter(MainActivity.this, listPetShop);
                rvPetShop.setAdapter(adPetShop);
                adPetShop.notifyDataSetChanged();
                pbPetShop.setVisibility(View.GONE);

                fabAdd.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(new Intent(MainActivity.this, Tambah.class));
                    }
                });
            }

            @Override
            public void onFailure(Call<ModelResponse> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Gagal terhubung server", Toast.LENGTH_SHORT).show();
                pbPetShop.setVisibility(View.GONE);
            }

        });
    }
}