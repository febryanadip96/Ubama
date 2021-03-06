package com.example.biyan.ubama.toko;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.biyan.ubama.R;
import com.example.biyan.ubama.UrlCama;
import com.example.biyan.ubama.models.Pesanan;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by Biyan on 11/23/2017.
 */

public class TokoPesananAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    List<Pesanan> pesananList;
    Context context;
    ImageView imagePemesan;
    TextView namaPemesan;
    TextView statusPesanan;
    TextView idPesanan;
    TextView tanggalPesan;

    public TokoPesananAdapter(List<Pesanan> pesananList){
        this.pesananList = pesananList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_toko_pesanan, parent, false);
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
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        imagePemesan = (ImageView) holder.itemView.findViewById(R.id.image_pemesan);
        namaPemesan = (TextView) holder.itemView.findViewById(R.id.nama_pemesan);
        statusPesanan = (TextView) holder.itemView.findViewById(R.id.status_pesanan);
        idPesanan = (TextView) holder.itemView.findViewById(R.id.id_pesanan);
        tanggalPesan = (TextView) holder.itemView.findViewById(R.id.tanggal_pesan);
        Picasso.with(context).load(UrlCama.URL_IMAGE+pesananList.get(position).pemesan.url_profile).into(imagePemesan);
        namaPemesan.setText(pesananList.get(position).pemesan.user.name);
        statusPesanan.setText(pesananList.get(position).status);
        idPesanan.setText(pesananList.get(position).id+"");
        SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat outputFormat = new SimpleDateFormat("dd MMMM yyyy HH:mm");
        Date date = null;
        try {
            date = inputFormat.parse(pesananList.get(position).created_at);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        tanggalPesan.setText(outputFormat.format(date));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, TokoDetailPesananActivity.class);
                intent.putExtra("idPesanan", pesananList.get(position).id);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return pesananList.size();
    }
}
