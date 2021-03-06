package com.example.biyan.ubama.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Biyan on 11/16/2017.
 */

public class Toko {
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
    @SerializedName("slogan")
    public String slogan;
    @SerializedName("url_profile")
    public String url_profile;
    @SerializedName("pengguna_id")
    public int pengguna_id;
    @SerializedName("lewati")
    public int lewati;
    @SerializedName("created_at")
    public String created_at;
    @SerializedName("updated_at")
    public String updated_at;
    @SerializedName("favorit")
    public boolean favorit;
    @SerializedName("pemilik")
    public Pemilik pemilik;
    @SerializedName("barang_jasa")
    public List<Barang_jasa> barang_jasa;

    public static class User {
        @SerializedName("id")
        public int id;
        @SerializedName("name")
        public String name;
        @SerializedName("email")
        public String email;
        @SerializedName("created_at")
        public String created_at;
        @SerializedName("updated_at")
        public String updated_at;
        @SerializedName("deleted_at")
        public String deleted_at;
    }

    public static class Pemilik {
        @SerializedName("id")
        public int id;
        @SerializedName("jenis_kelamin")
        public String jenis_kelamin;
        @SerializedName("telepon")
        public String telepon;
        @SerializedName("alamat")
        public String alamat;
        @SerializedName("notif_number")
        public String notif_number;
        @SerializedName("url_profile")
        public String url_profile;
        @SerializedName("user_id")
        public int user_id;
        @SerializedName("created_at")
        public String created_at;
        @SerializedName("updated_at")
        public String updated_at;
        @SerializedName("deleted_at")
        public String deleted_at;
        @SerializedName("user")
        public User user;
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
        @SerializedName("gambar")
        public List<Gambar> gambar;
    }
}
