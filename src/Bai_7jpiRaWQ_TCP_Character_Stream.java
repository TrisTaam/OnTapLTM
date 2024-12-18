import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.Arrays;

/*
[Mã câu hỏi (qCode): 7jpiRaWQ].  Một chương trình server cho phép kết nối qua giao thức TCP tại cổng 2208 (hỗ trợ thời gian giao tiếp tối đa cho mỗi yêu cầu là 5 giây). Yêu cầu là xây dựng một chương trình client thực hiện kết nối tới server và sử dụng luồng ký tự (BufferedWriter/BufferedReader) để trao đổi thông tin theo kịch bản sau:
a. Gửi một chuỗi gồm mã sinh viên và mã câu hỏi với định dạng "studentCode;qCode". Ví dụ: "B15DCCN999;X1107ABC".
b. Nhận từ server một chuỗi ngẫu nhiên chứa nhiều từ, các từ phân tách bởi khoảng trắng.
c. Thực hiện các bước xử lý:
    Bước 1: Tách chuỗi thành các từ dựa trên khoảng trắng.
    Bước 2: Sắp xếp các từ theo thứ tự từ điển (có phân biệt chữ cái hoa thường).
d. Gửi lại chuỗi đã sắp xếp theo thứ tự từ điển lên server.

e. Đóng kết nối và kết thúc chương trình.
 */
public class Bai_7jpiRaWQ_TCP_Character_Stream {
    public static void main(String[] args) {
        String address = "203.162.10.109";
        int port = 2208;
        String idStudent = "B21DCCN657;7jpiRaWQ";
        try (
                Socket socket = new Socket(address, port);
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        ) {
            bufferedWriter.write(idStudent);
            bufferedWriter.newLine();
            bufferedWriter.flush();

            String response = bufferedReader.readLine().trim();
            System.out.println(response);
            String[] a = response.split("\\s+");
            Arrays.sort(a);
            StringBuilder ans = new StringBuilder();
            for (String x : a) ans.append(x).append(" ");
            ans.deleteCharAt(ans.length() - 1);
            bufferedWriter.write(ans.toString());
            bufferedWriter.newLine();
            bufferedWriter.flush();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
