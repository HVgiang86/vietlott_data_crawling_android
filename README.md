# vietlott_data_crawling_android

# Ứng dụng lấy kết quả sổ xố Vietlott Max 3D

Ứng dụng này sẽ lấy kết quả trên trang https://vietlott.vn/vi/trung-thuong/ket-qua-trung-thuong/max-3d,
lưu trữ và hiển thị trên màn hình dưới dạng danh sách kết quả theo phiên và theo tháng



## Authors

- [@Hoàng Văn Giang](https://github.com/HVgiang86/vietlott_data_crawling_android)


## Phương pháp lấy dữ liệu
**Lấy HTML theo id của phiên**

url: https://vietlott.vn/vi/trung-thuong/ket-qua-trung-thuong/max-3D?id=<StringID>&nocatche=1
Trong đó StringID là đoạn chuỗi 5 ký tự thể hiện ID, ví dụ 00020

**API**
url: https://api.vietlott.vn/services/?securitycode=vietlotcmc&jsondata={%22Command%22:%22GetMax3DResult%22,%22JsonData%22:%22{\%22PageSize\%22:<PageSize>,\%22Segment\%22:0,\%22TopN\%22:0}%22}

<PageSize> là số kết quả cần lấy tính từ kết quả cuối


## Flow Chart
