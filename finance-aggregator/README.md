# Allo Bank Backend Developer Take-Home Test: Finance Data Aggregator

Proyek ini adalah aplikasi Spring Boot REST API tingkat produksi (*production-ready*) yang mengumpulkan dan memanipulasi data nilai tukar mata uang dari Frankfurter Exchange Rate API (fokus pada IDR).

Aplikasi ini mendemonstrasikan penerapan *clean code*, desain *thread-safe*, *DTO layers*, *graceful error handling*, dan pola arsitektur lanjutan dalam ekosistem Spring Boot.

## 👤 Personalization Note
Sesuai instruksi, kalkulasi nilai **USD_BuySpread_IDR** menggunakan faktor unik yang diturunkan dari username GitHub.
* **GitHub Username:** `jufrin01`
* **Sum of Unicode (ASCII) Values:** 106+117+102+114+105+110+48+49 = 751
* **Spread Factor Calculation:** `(751 % 1000) / 100000.0`
* **Final Spread Factor:** **0.00751**

## 🚀 Setup & Run Instructions

### Prasyarat
* Java 17
* Maven
* Koneksi internet (untuk fetch data dari API saat startup)

### Cara Menjalankan
1. **Jalankan Unit & Integration Test:**
   ```bash
   mvn clean test
   ```

Build dan Jalankan Aplikasi:

Bash
mvn spring-boot:run
Catatan: Saat aplikasi menyala, perhatikan log console. Aplikasi akan secara otomatis mengambil data dari Frankfurter API dan menyimpannya ke In-Memory Store yang immutable.

Aplikasi akan berjalan di port 8080.

🌐 Endpoint Usage
Aplikasi ini mengekspos satu polymorphic endpoint: GET /api/finance/data/{resourceType}.

1. Latest IDR Rates (Termasuk kalkulasi USD_BuySpread_IDR)
```
curl -X GET http://localhost:8080/api/finance/data/latest_idr_rates
```
2. Historical Data (IDR to USD, Jan 1 - Jan 5, 2024)
```
curl -X GET http://localhost:8080/api/finance/data/historical_idr_usd
```
3. Supported Currencies
```
curl -X GET http://localhost:8080/api/finance/data/supported_currencies
```
🛠️ Architectural Rationale
    Berikut adalah penjelasan mengenai keputusan arsitektur yang diterapkan dalam aplikasi ini untuk memenuhi persyaratan tingkat produksi (production-ready).

1. Polymorphism Justification (The Strategy Pattern)
   Mengapa menggunakan Strategy Pattern dibandingkan blok kondisional (if/else atau switch) di service layer?
   Penggunaan Strategy Pattern menerapkan prinsip Open-Closed Principle (OCP) dari desain SOLID. Jika di masa depan sistem perlu mendukung resourceType baru, kita hanya perlu membuat satu class baru yang mengimplementasikan IDRDataFetcher tanpa perlu menyentuh atau memodifikasi FinanceDataController maupun FinanceDataService yang sudah ada.

    Dengan memanfaatkan Map-based lookup yang di-inject otomatis oleh Spring, kode terhindar dari logika if/else yang panjang (Cyclomatic Complexity yang tinggi). Hal ini membuat aplikasi sangat extensible (mudah diperluas), maintainable (mudah dirawat), dan lebih mudah untuk diuji secara terisolasi.

2. Client Factory (FactoryBean)
   Mengapa menggunakan FactoryBean<T> untuk mengonstruksi API Client dibandingkan metode @Bean standar?
   Meskipun @Bean umum digunakan, FactoryBean memberikan tingkat enkapsulasi yang lebih tinggi ketika proses pembuatan suatu objek sangat kompleks. Dalam kasus pembuatan RestTemplate ini, objek klien membutuhkan konfigurasi berlapis seperti pengaturan timeout kustom, penetapan Base URL dari file external properties (application.yml), dan modifikasi header default.

    Dengan membungkus logika perakitan ini di dalam FactoryBean, kita memisahkan (decouple) kompleksitas perakitan objek dari kelas konfigurasi utama Spring. Selain itu, FactoryBean memungkinkan kontrol eksplisit terhadap lifecycle dan sifat singleton dari klien HTTP tersebut.

3. Startup Runner Choice (ApplicationRunner vs @PostConstruct)
   Mengapa menggunakan ApplicationRunner untuk konsumsi data awal dibandingkan metode @PostConstruct?
   Anotasi @PostConstruct dieksekusi selama fase inisialisasi bean, yang berarti Application Context Spring belum sepenuhnya selesai dibangun. Jika pengambilan data eksternal (yang berpotensi lambat) dilakukan di dalam @PostConstruct, hal itu dapat memblokir proses startup keseluruhan aplikasi.

Sekarang kamu bisa langsung membuka terminal di IntelliJ-mu, mengetikkan 
```
mvn clean test 

```

lalu tekan Enter. Pastikan semua tesnya berwarna hijau (sukses). Jika sudah, ketik

```
mvn spring-boot:run
```
untuk menyalakan aplikasinya.
