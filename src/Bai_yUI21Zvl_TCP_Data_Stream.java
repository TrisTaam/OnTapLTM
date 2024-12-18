import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;

/*
[Mã câu hỏi (qCode): yUI21Zvl].  Một chương trình server cho phép kết nối qua TCP tại cổng 2207 (hỗ trợ thời gian liên lạc tối đa cho mỗi yêu cầu là 5 giây). Yêu cầu là xây dựng chương trình client tương tác với server bằng các byte stream (DataInputStream/DataOutputStream) để trao đổi thông tin theo trình tự sau:

a. Gửi một chuỗi chứa mã sinh viên và mã câu hỏi ở định dạng "studentCode;qCode". Ví dụ: "B10DCCN000;A1B2C3D4".

b. Nhận từ server một mảng chứa n số nguyên, với n được gửi từ máy chủ. Ví dụ: Server gửi mảng [5, 9, 3, 6, 8].

c. Tính tổng, trung bình cộng, và phương sai của mảng. Gửi kết quả lần lượt lên server dưới dạng số nguyên và float. Ví dụ, gửi lên lần lượt: 31, 6.2, 4.5599995.

d. Đóng kết nối và kết thúc chương trình.
 */
public class Bai_yUI21Zvl_TCP_Data_Stream {
    public static void main(String[] args) {
        String address = "203.162.10.109";
        int port = 2207;
        String idStudent = "B21DCCN657;yUI21Zvl";
        try (
                Socket socket = new Socket(address, port);
                DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());
                DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());
        ) {
            dataOutputStream.writeUTF(idStudent);
            dataOutputStream.flush();

            int n = dataInputStream.readInt();
            int s = 0;
            int[] a = new int[n];
            for (int i = 0; i < n; ++i) {
                a[i] = dataInputStream.readInt();
                s += a[i];
            }
            float average = (float) s / n;
            float variance = 0;
            for (int i = 0; i < n; ++i) variance += (a[i] - average) * (a[i] - average);
            variance /= n;
            System.out.println(variance);

            dataOutputStream.writeInt(s);
            dataOutputStream.writeFloat(average);
            dataOutputStream.writeFloat(variance);
            dataOutputStream.flush();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
