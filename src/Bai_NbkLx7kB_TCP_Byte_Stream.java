import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

/*
[Mã câu hỏi (qCode): NbkLx7kB].  Một chương trình server hỗ trợ kết nối qua giao thức TCP tại cổng 2206 (hỗ trợ thời gian giao tiếp tối đa cho mỗi yêu cầu là 5s).
Yêu cầu xây dựng chương trình client thực hiện kết nối tới server trên sử dụng luồng byte dữ liệu (InputStream/OutputStream) để trao đổi thông tin theo thứ tự:
a.	Gửi mã sinh viên và mã câu hỏi theo định dạng "studentCode;qCode". Ví dụ: "B16DCCN999;C64967DD"
b.	Nhận dữ liệu từ server là một chuỗi gồm các giá trị nguyên được phân tách với nhau bằng  "|"
Ex: 2|5|9|11
c.	Thực hiện tìm giá trị tổng của các số nguyên trong chuỗi và gửi lên server
Ex: 27
d.	Đóng kết nối và kết thúc
 */
public class Bai_NbkLx7kB_TCP_Byte_Stream {
    public static void main(String[] args) {
        String address = "203.162.10.109";
        int port = 2206;
        String idStudent = "B21DCCN657;NbkLx7kB";
        try (
                Socket socket = new Socket(address, port);
                InputStream inputStream = socket.getInputStream();
                OutputStream outputStream = socket.getOutputStream();
        ) {
            outputStream.write(idStudent.getBytes());
            outputStream.flush();

            byte[] buffer = new byte[1024];
            int len = inputStream.read(buffer);
            String response = new String(buffer, 0, len);
            String[] a = response.split("\\|");
            int ans = 0;
            for (String x : a) ans += Integer.parseInt(x);

            outputStream.write(String.valueOf(ans).getBytes());
            outputStream.flush();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
