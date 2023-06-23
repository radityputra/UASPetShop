package com.radit.klinikpetshop.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.radit.klinikpetshop.API.APIRequestData;
import com.radit.klinikpetshop.API.RetroServer;
import com.radit.klinikpetshop.Activity.Detail;
import com.radit.klinikpetshop.Activity.MainActivity;
import com.radit.klinikpetshop.Activity.Ubah;
import com.radit.klinikpetshop.Model.ModelPetShop;
import com.radit.klinikpetshop.Model.ModelResponse;
import com.radit.klinikpetshop.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Adapter extends RecyclerView.Adapter<Adapter.VHPetShop>{
    private Context ctx;
    private List<ModelPetShop> listPetShop;

    public Adapter(Context ctx, List<ModelPetShop> listPetShop) {
        this.ctx = ctx;
        this.listPetShop = listPetShop;
    }

    @NonNull
    @Override
    public VHPetShop onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View varView = LayoutInflater.from(ctx).inflate(R.layout.list_petshop, parent, false);
        return new VHPetShop(varView);
    }

    @Override
    public void onBindViewHolder(@NonNull VHPetShop holder, int position) {
        ModelPetShop MPS = listPetShop.get(position);
        holder.tvId.setText(MPS.getId());
        holder.tvNama.setText(MPS.getNama());
        holder.tvFoto.setText(MPS.getFoto());
        holder.tvDeskripsi.setText(MPS.getDeskripsi());
        holder.tvLokasi.setText(MPS.getLokasi());
        holder.tvKoordinat.setText(MPS.getKoordinat());
        Glide
                .with(ctx)
                .load(MPS.getFoto())
                .into(holder.ivFoto);

        holder.btnDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String xNama, xFoto,xDeskripsi, xLokasi, xKoordinat;
                xNama = MPS.getNama();
                xFoto = MPS.getFoto();
                xDeskripsi = MPS.getDeskripsi();
                xLokasi = MPS.getLokasi();
                xKoordinat = MPS.getKoordinat();

                Intent kirim = new Intent(ctx, Detail.class);
                kirim.putExtra("xNama",xNama);
                kirim.putExtra("xFoto",xFoto);
                kirim.putExtra("xDeskripsi",xDeskripsi);
                kirim.putExtra("xLokasi",xLokasi);
                kirim.putExtra("xKoordinat",xKoordinat);
                ctx.startActivity(kirim);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listPetShop.size();
    }

    public class VHPetShop extends RecyclerView.ViewHolder{
        private TextView tvId,tvNama, tvFoto,tvDeskripsi, tvLokasi, tvKoordinat;
        private Button btnHapus, btnUbah, btnDetail;
        private ImageView ivFoto;

        public VHPetShop(@NonNull View itemView) {
            super(itemView);
            tvId = itemView.findViewById(R.id.tv_id);
            tvNama = itemView.findViewById(R.id.tv_nama);
            tvFoto = itemView.findViewById(R.id.tv_foto);
            tvDeskripsi = itemView.findViewById(R.id.tv_deskripsi);
            tvLokasi = itemView.findViewById(R.id.tv_lokasi);
            tvKoordinat = itemView.findViewById(R.id.tv_koordinat);
            ivFoto = itemView.findViewById(R.id.iv_foto);
            btnHapus = itemView.findViewById(R.id.btn_hapus);
            btnUbah = itemView.findViewById(R.id.btn_ubah);
            btnDetail = itemView.findViewById(R.id.btn_detail);

            btnHapus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    deletePetShop(tvId.getText().toString());
                }
            });

            btnUbah.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent kirim = new Intent(ctx, Ubah.class);
                    kirim.putExtra("xId", tvId.getText().toString());
                    kirim.putExtra("xNama", tvNama.getText().toString());
                    kirim.putExtra("xFoto", tvFoto.getText().toString());
                    kirim.putExtra("xDeskripsi", tvDeskripsi.getText().toString());
                    kirim.putExtra("xLokasi", tvLokasi.getText().toString());
                    kirim.putExtra("xKoordinat", tvKoordinat.getText().toString());
                    ctx.startActivity(kirim);
                }
            });
        }

        void deletePetShop(String id){
            APIRequestData API = RetroServer.konekRetrofit().create(APIRequestData.class);
            Call<ModelResponse> proses = API.ardDelete(id);
            proses.enqueue(new Callback<ModelResponse>() {
                @Override
                public void onResponse(Call<ModelResponse> call, Response<ModelResponse> response) {
                    String kode = response.body().getKode();
                    String pesan = response.body().getPesan();

                    Toast.makeText(ctx, "Kode:"+kode+"Pesan : "+ pesan, Toast.LENGTH_SHORT).show();
                    ((MainActivity) ctx).retrivePetShop();
                }

                @Override
                public void onFailure(Call<ModelResponse> call, Throwable t) {
                    Toast.makeText(ctx, "Gagal terhubung ke server", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}

