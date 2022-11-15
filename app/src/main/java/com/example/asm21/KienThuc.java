//package com.example.asm21;
//
//public class KienThuc {
//}

// Intent là một đối tượng message bạn có thể sử dụng để request một hành động từ một vài component trong ứng dụng

// Extra
//Các cặp key-value mang thông tin bổ sung cần thiết để hoàn thành hành động được yêu cầu.

// Để truyền dữ liệu cho intent, ta dùng phương thức putExtra(). Extra là một cặp key/value.
// key luôn luôn là kiểu string. value bạn có thể sử dụng kiểu dữ liệu nguyên thủy hoặc đối tượng của String, Bundle, ....

//Mỗi Adapter cần 3 phương thức chính:

//  onCreateViewHolder - Định nghĩa các Item layout và khởi tạo Holder.
//    onBindViewHolder - Thiết lập các thuộc tính của View và dữ liệu.
//    getItemCount - Đếm số Item trong List Data.

/*
*Trong phương thức onCreateViewHolder bạn inflate một View dựa trên viewType.
* Còn trong phương thức onBindViewHolder là nơi bạn đưa data lên View.
* android:paddingTop="?attr/actionBarSize"
* onCreate(): Gọi khi cơ sở dữ liệu được tạo ra lần đầu tiên.
* (chú ý: khi khởi tạo bảng, ta phải đặt tên khóa chính là _id)
* onUpgrade():
* Được gọi là khi cơ sở dữ liệu cần phải được nâng cấp. Dùng khi ứng dụng có nhiều phiên bản DB thêm vào.
* Class này có 2 methods getReadableDatabase() (chỉ đọc) và getWriteableDatabase()
* Để truyền dữ liệu cho intent, ta dùng phương thức putExtra().
* ContentValues
là Object cho phép xác định key/value, sử dụng để insert và update các mục CSDL
* ContentValues được sử dụng để lưu các giá trị tương ứng với các trường trong bảng
* Cursor
Hiểu một cách nôm na nó là một con trỏ, trỏ đến kết quả của câu truy vấn.
* Lớp ArrayAdapter có thể sử dụng cả mảng và các đối tượng List như là bộ dữ liệu
 */