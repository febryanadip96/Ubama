package com.example.biyan.ubama;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.ButterKnife;

public class PesananAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private ImageView imageBarang;
    private TextView idPesanan;
    private TextView namaBarang;
    private TextView statusPesanan;
    private Context context;
    private List<Pesanan> pesananList;

    public PesananAdapter(List<Pesanan> pesananList) {
        this.pesananList = pesananList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_pesanan, parent, false);
        ButterKnife.bind(this, v);
        context = parent.getContext();
        RecyclerView.ViewHolder vh = new RecyclerView.ViewHolder(v) {
            @Override
            public String toString() {
                return super.toString();
            }
        };
        return vh;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        imageBarang = (ImageView) holder.itemView.findViewById(R.id.image_barang);
        idPesanan = (TextView) holder.itemView.findViewById(R.id.id_pesanan);
        namaBarang = (TextView) holder.itemView.findViewById(R.id.nama_barang);
        statusPesanan = (TextView) holder.itemView.findViewById(R.id.status_pesanan);
        if (pesananList.get(position).detail_pesanan.get(0).barang_jasa.gambar.size() > 0) {
            Picasso.with(context).load(UrlUbama.URL_IMAGE + pesananList.get(position).detail_pesanan.get(0).barang_jasa.gambar.get(0).url_gambar).into(imageBarang);
        }
        idPesanan.setText("Pesanan #" + pesananList.get(position).id);
        namaBarang.setText(pesananList.get(position).detail_pesanan.get(0).barang_jasa.nama);
        statusPesanan.setText(pesananList.get(position).status.toString());
    }

    @Override
    public int getItemCount() {
        return pesananList.size();
    }
}
