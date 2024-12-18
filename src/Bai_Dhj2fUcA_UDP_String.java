import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

/*
[Mã câu hỏi (qCode): Dhj2fUcA].  [Loại bỏ ký tự đặc biệt, số, trùng và giữ nguyên thứ tự xuất hiện]
Một chương trình server cho phép kết nối qua giao thức UDP tại cổng 2208 . Yêu cầu là xây dựng một chương trình client trao đổi thông tin với server theo kịch bản dưới đây:
a.	Gửi thông điệp là một chuỗi chứa mã sinh viên và mã câu hỏi theo định dạng ";studentCode;qCode". Ví dụ: ";B15DCCN001;06D6800D"
b.	Nhận thông điệp là một chuỗi từ server theo định dạng "requestId;strInput"
•	requestId là chuỗi ngẫu nhiên duy nhất
•	strInput là chuỗi thông điệp cần xử lý
c.	Thực hiện loại bỏ ký tự đặc biệt, số, ký tự trùng và giữ nguyên thứ tự xuất hiện của chúng. Gửi thông điệp lên server theo định dạng "requestId;strOutput", trong đó strOutput là chuỗi đã được xử lý ở trên
d.	Đóng socket và kết thúc chương trình.
 */
public class Bai_Dhj2fUcA_UDP_String {
    public static void main(String[] args) {
        String address = "203.162.10.109";
        int port = 2208;
        String idStudent = ";B21DCCN657;Dhj2fUcA";
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
            boolean[] b = new boolean[256];
            for (int i = 0; i < a[1].length(); ++i) {
                if (!Character.isAlphabetic(a[1].charAt(i)))
                    continue;
                if (!b[a[1].charAt(i)]) {
                    ans.append(a[1].charAt(i));
                    b[a[1].charAt(i)] = true;
                }
            }

            System.out.println(ans);
            sendBytes = ans.toString().getBytes();
            sendPacket = new DatagramPacket(sendBytes, sendBytes.length, inetAddress, port);
            datagramSocket.send(sendPacket);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
