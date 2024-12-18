import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

/*
[Mã câu hỏi (qCode): GYIQ7SPY].  Một chương trình server cho phép giao tiếp qua giao thức UDP tại cổng 2207. Yêu cầu là xây dựng một chương trình client trao đổi thông tin với server theo kịch bản:
a.	Gửi thông điệp là một chuỗi chứa mã sinh viên và mã câu hỏi theo định dạng “;studentCode;qCode”. Ví dụ: “;B15DCCN001;371EA16D”
b.	Nhận thông điệp là một chuỗi từ server theo định dạng “requestId; n; A1,A2,...An”, với
-	requestId là chuỗi ngẫu nhiên duy nhất
-	n là một số ngẫu nhiên nhỏ hơn 100.
-            A1, A2 ... Am với m <= n là các giá trị nguyên liên tiếp, nhỏ hơn hoặc bằng n và không trùng nhau.
Ví dụ: requestId;10;2,3,5,6,9
c.	Tìm kiếm các giá trị còn thiếu và gửi lên server theo định dạng “requestId;B1,B2,...,Bm”
Ví dụ: requestId;1,4,7,8,10
d.	Đóng socket và kết thúc chương trình.
 */
public class Bai_GYIQ7SPY_UDP_Data_Type {
    public static void main(String[] args) {
        String address = "203.162.10.109";
        int port = 2207;
        String idStudent = ";B21DCCN657;GYIQ7SPY";
        try (
                DatagramSocket datagramSocket = new DatagramSocket();
        ) {
            InetAddress inetAddress = InetAddress.getByName(address);
            byte[] sendBytes;
            byte[] receiveBytes = new byte[1024];
            DatagramPacket sendPacket;
            DatagramPacket receivePacket;

            sendBytes = idStudent.getBytes();
            sendPacket = new DatagramPacket(sendBytes, sendBytes.length, inetAddress, port);
            datagramSocket.send(sendPacket);

            receivePacket = new DatagramPacket(receiveBytes, receiveBytes.length);
            datagramSocket.receive(receivePacket);
            String response = new String(receivePacket.getData(), 0, receivePacket.getLength());
            System.out.println(response);
            String[] a = response.split(";");

            StringBuilder ans = new StringBuilder(a[0] + ";");
            int n = Integer.parseInt(a[1]);
            String[] b = a[2].split(",");
            boolean[] c = new boolean[n + 1];
            for (String x : b) c[Integer.parseInt(x)] = true;
            for (int i = 1; i <= n; ++i)
                if (!c[i]) ans.append(i).append(",");
            ans.deleteCharAt(ans.length() - 1);

            sendBytes = ans.toString().getBytes();
            sendPacket = new DatagramPacket(sendBytes, sendBytes.length, inetAddress, port);
            datagramSocket.send(sendPacket);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
