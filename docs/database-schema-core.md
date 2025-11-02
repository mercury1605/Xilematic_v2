# 🎬 Database Schema (Core Booking Flow)

This document shows the *main tables* involved in the ticket booking flow.

```sql
-- Core booking flow (các bảng chính)

[HeThongRap]
     |
     v
[CumRap]
     |
     v
[RapPhim] ---------.
     |              \
     v               \
    [Ghe]             \
                       v
                  [LichChieu] <------ [Phim]
                       |
                       v
                    [DatVe] <------ [NguoiDung]


-- Giải thích nhanh:
-- HeThongRap    = Hệ thống rạp (ví dụ CGV, BHD)
-- CumRap        = Cụm rạp thuộc hệ thống
-- RapPhim       = Một rạp cụ thể
-- Ghe           = Danh sách ghế trong rạp
-- Phim          = Phim được chiếu
-- LichChieu     = Suất chiếu (rạp nào, phim nào, giờ nào)
-- NguoiDung     = Tài khoản khách hàng
-- DatVe         = Giao dịch đặt vé (ai đặt ghế nào, suất nào)
```

👉 Xem sơ đồ đầy đủ tại: `database-schema-full.md`
