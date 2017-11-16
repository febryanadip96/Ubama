package com.example.biyan.ubama.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Biyan on 11/6/2017.
 */

public class Pesanan {
    @SerializedName("id")
    public int id;
    @SerializedName("total_harga")
    public int total_harga;
    @SerializedName("jumlah_barang")
    public int jumlah_barang;
    @SerializedName("alamat")
    public String alamat;
    @SerializedName("alasan_ditolak")
    public String alasan_ditolak;
    @SerializedName("status")
    public String status;
    @SerializedName("waktu_selesai")
    public String waktu_selesai;
    @SerializedName("pengguna_id")
    public int pengguna_id;
    @SerializedName("created_at")
    public String created_at;
    @SerializedName("updated_at")
    public String updated_at;
    @SerializedName("detail_pesanan")
    public List<Detail_pesanan> detail_pesanan;
    @SerializedName("log_pesanan")
    public List<Log_pesanan> log_pesanan;

    public static class Toko {
        @SerializedName("id")
        public int id;
        @SerializedName("nama")
        public String nama;
        @SerializedName("deskripsi")
        public String deskripsi;
        @SerializedName("alamat")
        public String alamat;
        @SerializedName("catatan_toko")
        public String catatan_toko;
        @SerializedName("open")
        public int open;
        @SerializedName("total_rating")
        public int total_rating;
        @SerializedName("slogan")
        public String slogan;
        @SerializedName("url_profile")
        public String url_profile;
        @SerializedName("pengguna_id")
        public int pengguna_id;
        @SerializedName("created_at")
        public String created_at;
        @SerializedName("updated_at")
        public String updated_at;
    }

    public static class Gambar {
        @com.google.gson.annotations.SerializedName("id")
        public int id;
        @com.google.gson.annotations.SerializedName("barang_jasa_id")
        public int barang_jasa_id;
        @com.google.gson.annotations.SerializedName("url_gambar")
        public String url_gambar;
        @com.google.gson.annotations.SerializedName("created_at")
        public String created_at;
        @com.google.gson.annotations.SerializedName("updated_at")
        public String updated_at;
        @com.google.gson.annotations.SerializedName("deleted_at")
        public String deleted_at;
    }

    public static class Barang_jasa {
        @SerializedName("id")
        public int id;
        @SerializedName("nama")
        public String nama;
        @SerializedName("deskripsi")
        public String deskripsi;
        @SerializedName("jenis")
        public String jenis;
        @SerializedName("harga")
        public int harga;
        @SerializedName("baruBekas")
        public String baruBekas;
        @SerializedName("catatan_penjual")
        public String catatan_penjual;
        @SerializedName("jumlah_dilihat")
        public int jumlah_dilihat;
        @SerializedName("jumlah_terjual")
        public int jumlah_terjual;
        @SerializedName("jumlah_komentar")
        public int jumlah_komentar;
        @SerializedName("jumlah_faq")
        public int jumlah_faq;
        @SerializedName("total_rating")
        public double total_rating;
        @SerializedName("min_pesan")
        public int min_pesan;
        @SerializedName("toko_id")
        public int toko_id;
        @SerializedName("created_at")
        public String created_at;
        @SerializedName("updated_at")
        public String updated_at;
        @SerializedName("deleted_at")
        public String deleted_at;
        @SerializedName("toko")
        public Toko toko;
        @SerializedName("gambar")
        public List<Gambar> gambar;
    }

    public static class Detail_pesanan {
        @SerializedName("id")
        public int id;
        @SerializedName("harga")
        public int harga;
        @SerializedName("jumlah")
        public int jumlah;
        @SerializedName("subtotal")
        public int subtotal;
        @SerializedName("catatan_pembeli")
        public String catatan_pembeli;
        @SerializedName("barang_jasa_id")
        public int barang_jasa_id;
        @SerializedName("pesanan_id")
        public int pesanan_id;
        @SerializedName("created_at")
        public String created_at;
        @SerializedName("updated_at")
        public String updated_at;
        @SerializedName("barang_jasa")
        public Barang_jasa barang_jasa;
    }

    public static class Log_pesanan {
        @SerializedName("id")
        public int id;
        @SerializedName("keterangan")
        public String keterangan;
        @SerializedName("pesanan_id")
        public int pesanan_id;
        @SerializedName("created_at")
        public String created_at;
        @SerializedName("updated_at")
        public String updated_at;
    }
}
