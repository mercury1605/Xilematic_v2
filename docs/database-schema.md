# 📚 Database Schema (Full Detail)

Đây là phiên bản đầy đủ gồm tất cả các bảng và quan hệ chính trong hệ thống.

```sql
-- Full database schema (tất cả quan hệ chính)

[HeThongRap]
     |
     v
[CumRap]
     |
     v
[RapPhim] --------------------.
     |                        \
     v                         \
    [Ghe]                       \
                                 v
                            [LichChieu] <-------- [Phim]
                                 |                     |
                                 v                     |
                              [DatVe] <----- [NguoiDung]
                                                      |
                                                      v
                                               [TokenQuenMatKhau]


[Phim] ------------------------.
  |            |               |
  v            v               v
[Banner]   [BinhLuan]      [YeuThich]
                ^               ^
                |               |
          [NguoiDung]-----------'


[Phim] <----> [Phim_TheLoai] <----> [TheLoai]


-- Bảng & vai trò:
-- HeThongRap           = Hệ thống rạp
-- CumRap               = Cụm rạp trong hệ thống
-- RapPhim              = Rạp cụ thể
-- Ghe                  = Ghế trong rạp
-- Phim                 = Thông tin phim
-- Banner               = Hình ảnh quảng cáo cho phim
-- BinhLuan             = Bình luận của người dùng cho phim
-- YeuThich             = Danh sách phim yêu thích của người dùng
-- TheLoai              = Thể loại phim (Action, Comedy, ...)
-- Phim_TheLoai         = Bảng nối N-N giữa Phim và Thể loại
-- LichChieu            = Lịch chiếu (phim gì, rạp nào, suất mấy giờ)
-- NguoiDung            = Tài khoản user
-- DatVe                = Đơn đặt vé (bao gồm ghế đã đặt, giá vé,...)
-- TokenQuenMatKhau     = Mã khôi phục mật khẩu cho người dùng
```

---

## 1. HeThongRap
- `ma_he_thong_rap` INT IDENTITY PRIMARY KEY  
- `ten_he_thong_rap` NVARCHAR(255)  
- `logo` VARCHAR(255)

**Quan hệ:**
- 1 HeThongRap → N CumRap

---

## 2. CumRap
- `ma_cum_rap` INT IDENTITY PRIMARY KEY  
- `ten_cum_rap` NVARCHAR(255)  
- `dia_chi` NVARCHAR(255)  
- `ma_he_thong_rap` INT FOREIGN KEY → HeThongRap(ma_he_thong_rap)

**Quan hệ:**
- 1 CumRap → N RapPhim

---

## 3. RapPhim
- `ma_rap` INT IDENTITY PRIMARY KEY  
- `ten_rap` NVARCHAR(255)  
- `ma_cum_rap` INT FOREIGN KEY → CumRap(ma_cum_rap)

**Quan hệ:**
- 1 RapPhim → N Ghe  
- 1 RapPhim → N LichChieu

---

## 4. Ghe
- `ma_ghe` INT IDENTITY PRIMARY KEY  
- `ten_ghe` VARCHAR(10)  
- `loai_ghe` NVARCHAR(50)  
- `ma_rap` INT FOREIGN KEY → RapPhim(ma_rap) (ON DELETE CASCADE)  
- `da_dat` BIT  
- `trang_thai` NVARCHAR(10)

**Ý nghĩa:** Toàn bộ ghế trong một rạp.

---

## 5. Phim
- `ma_phim` INT IDENTITY PRIMARY KEY  
- `ten_phim` NVARCHAR(255)  
- `trailer` VARCHAR(255)  
- `hinh_anh` VARCHAR(255)  
- `mo_ta` NVARCHAR(MAX)  
- `ngay_khoi_chieu` DATE  
- `danh_gia` INT  
- `hot` BIT  
- `dang_chieu` BIT  
- `sap_chieu` BIT  
- `dien_vien_chinh` NVARCHAR(255)  
- `dao_dien` NVARCHAR(255)  
- `is_active` BIT

**Quan hệ:**
- 1 Phim → N LichChieu  
- 1 Phim → N Banner  
- 1 Phim → N BinhLuan  
- 1 Phim → N YeuThich  
- Phim ↔ TheLoai (quan hệ N-N qua Phim_TheLoai)

---

## 6. Banner
- `ma_banner` INT IDENTITY PRIMARY KEY  
- `ma_phim` INT FOREIGN KEY → Phim(ma_phim)  
- `hinh_anh` VARCHAR(255)

**Ý nghĩa:** Banner quảng cáo gắn với phim.

---

## 7. TheLoai
- `ma_the_loai` INT IDENTITY PRIMARY KEY  
- `ten_the_loai` NVARCHAR(100)

---

## 8. Phim_TheLoai
- `ma_phim` INT FOREIGN KEY → Phim(ma_phim)  
- `ma_the_loai` INT FOREIGN KEY → TheLoai(ma_the_loai)  
- PRIMARY KEY (`ma_phim`, `ma_the_loai`)

**Ý nghĩa:** Bảng nối N-N giữa Phim và Thể loại.  
Ví dụ: Phim A vừa là "Hành động" vừa là "Hài".

---

## 9. LichChieu
- `ma_lich_chieu` INT IDENTITY PRIMARY KEY  
- `ma_rap` INT FOREIGN KEY → RapPhim(ma_rap)  
- `ma_phim` INT FOREIGN KEY → Phim(ma_phim)  
- `ngay_gio_chieu` DATETIME

**Ý nghĩa:** Một suất chiếu cụ thể (phim nào, ở rạp nào, lúc mấy giờ).

**Quan hệ:**
- 1 LichChieu → N DatVe

---

## 10. NguoiDung
- `ma_nguoi_dung` INT IDENTITY PRIMARY KEY  
- `ten_tai_khoan` NVARCHAR(255) UNIQUE  
- `ho_ten` NVARCHAR(255)  
- `email` VARCHAR(255) UNIQUE  
- `so_dt` VARCHAR(20) UNIQUE  
- `mat_khau` VARCHAR(255)  
- `loai_nguoi_dung` VARCHAR(50)  
- `status` BIT

**Quan hệ:**
- 1 NguoiDung → N DatVe  
- 1 NguoiDung → N BinhLuan  
- 1 NguoiDung → N YeuThich  
- 1 NguoiDung → N TokenQuenMatKhau

---

## 11. DatVe
- `ma_dat_ve` INT IDENTITY PRIMARY KEY  
- `tai_khoan` INT FOREIGN KEY → NguoiDung(ma_nguoi_dung)  
- `ma_lich_chieu` INT FOREIGN KEY → LichChieu(ma_lich_chieu)  
- `ghe_da_dat` NVARCHAR(100)  -- ví dụ: 'A1,A2,A3'  
- `gia_ve` BIGINT  
- `create_at` DATETIME DEFAULT GETDATE()

**Ý nghĩa:** Kết quả đặt vé (Ai đặt? Suất nào? Ghế nào? Giá bao nhiêu?).

---

## 12. BinhLuan
- `ma_binh_luan` INT IDENTITY PRIMARY KEY  
- `ma_nguoi_dung` INT FOREIGN KEY → NguoiDung(ma_nguoi_dung)  
- `ma_phim` INT FOREIGN KEY → Phim(ma_phim)  
- `noi_dung` NVARCHAR(MAX) NOT NULL  
- `ngay_tao` DATETIME DEFAULT GETDATE()  
- `trang_thai` BIT DEFAULT 1

**Ý nghĩa:** Review / bình luận phim từ user.

---

## 13. YeuThich
- `ma_yeu_thich` INT IDENTITY PRIMARY KEY  
- `ma_nguoi_dung` INT FOREIGN KEY → NguoiDung(ma_nguoi_dung)  
- `ma_phim` INT FOREIGN KEY → Phim(ma_phim)

**Ý nghĩa:** Danh sách phim yêu thích của user (wishlist / favorite).

---

## 14. TokenQuenMatKhau
- `id` INT IDENTITY PRIMARY KEY  
- `token` VARCHAR(255) NOT NULL  
- `thoi_gian_song` DATETIME NOT NULL  
- `duoc_su_dung` BIT NOT NULL  
- `ma_nguoi_dung` INT FOREIGN KEY → NguoiDung(ma_nguoi_dung)

**Ý nghĩa:** Token khôi phục mật khẩu / reset password flow.
