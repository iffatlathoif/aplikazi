# Aplikazi #

## Fitur Aplikasi ##
Aplikazi akan mencakup modul-modul utama sebagai berikut:
  * **Modul Autentikasi & Manajemen Pengguna:**
    * Login / Logout (Anggota, Pustakawan, Admin)
    * Manajemen akun pengguna (Admin)
    * Tambah, ubah, hapus pengguna (oleh admin).
    * Atur role (admin, pustakawan, anggota) 
    * Ganti password & pemulihan akun.  
  * **Modul Keanggotaan:**
    * Registrasi anggota baru (Pustakawan/Admin).
    * Edit data anggota.
    * Nonaktifkan anggota (misalnya jika sudah lulus atau melanggar aturan).
    * Cetak kartu anggota.
  * **Modul Koleksi Buku:**
    * Input buku baru (judul, penulis, penerbit, ISBN, kategori).
    * Update data buku.
    * Hapus data buku.
    * Manajemen stok eksemplar (copy buku).
  * **Modul Peminjaman & Pengembalian:**
    * Peminjaman buku (oleh pustakawan).
    * Pengembalian buku.
    * Perpanjangan pinjaman (oleh pustakawan atau anggota melalui portal).
    * Denda keterlambatan (otomatis dihitung).
  * **Modul Reservasi:**
    * Reservasi buku (oleh anggota login). 
    * Persetujuan/penolakan reservasi (oleh pustakawan). 
    * Notifikasi ketersediaan buku.  
  * **Modul Koleksi Digital (Opsional):**
    * Akses e-book (anggota login).
    * Download/online reading sesuai hak akses. 
  * **Modul Laporan:**
    * Laporan peminjaman per periode.
    * Laporan denda.
    * Laporan stok buku.
    * Laporan aktivitas anggota.
## Pengguna Aplikasi ##
  * **Admin**
  * **Pustakawan (Petugas)**
  * **Anggota (login)**
## Technology Stack ##
* Java 17
* Spring Boot 3.5.4
* MySQL

## Testing ##