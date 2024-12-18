/*
[Mã câu hỏi (qCode): oPsw1fAb].  Thông tin khách hàng được yêu cầu thay đổi định dạng lại cho phù hợp với khu vực, cụ thể:
a.	Tên khách hàng cần được chuẩn hóa theo định dạng mới. Ví dụ: nguyen van hai duong -> DUONG, Nguyen Van Hai
b.	Ngày sinh của khách hàng đang ở dạng mm-dd-yyyy, cần được chuyển thành định dạng dd/mm/yyyy. Ví dụ: 10-11-2012 -> 11/10/2012
c.	Tài khoản khách hàng được tạo từ các chữ cái in thường được sinh tự động từ họ tên khách hàng. Ví dụ: nguyen van hai duong -> nvhduong


Một chương trình server cho phép giao tiếp qua giao thức UDP tại cổng 2209. Yêu cầu là xây dựng một chương trình client giao tiếp với server theo mô tả sau:
a.	Đối tượng trao đổi là thể hiện của lớp UDP.Customer được mô tả như sau
•	Tên đầy đủ của lớp: UDP.Customer
•	Các thuộc tính: id String, code String, name String, , dayOfBirth String, userName String
•	Một Hàm khởi tạo với đầy đủ các thuộc tính được liệt kê ở trên
•	Trường dữ liệu: private static final long serialVersionUID = 20151107;

b.	Client giao tiếp với server theo các bước
•       Gửi thông điệp là một chuỗi chứa mã sinh viên và mã câu hỏi theo định dạng “;studentCode;qCode”. Ví dụ: “;B15DCCN001;EE29C059”

•	Nhận thông điệp chứa: 08 byte đầu chứa chuỗi requestId, các byte còn lại chứa một đối tượng là thể hiện của lớp Customer từ server. Trong đó, các thuộc tính id, code, name,dayOfBirth đã được thiết lập sẵn.
•	Yêu cầu thay đổi thông tin các thuộc tính như yêu cầu ở trên và gửi lại đối tượng khách hàng đã được sửa đổi lên server với cấu trúc:
08 byte đầu chứa chuỗi requestId và các byte còn lại chứa đối tượng Customer đã được sửa đổi.
•	Đóng socket và kết thúc chương trình.
*/

import UDP.Customer;

import java.io.*;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class Bai_oPsw1fAb_UDP_Object {
    public static byte[] serialize(Customer customer) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
        objectOutputStream.writeObject(customer);
        return byteArrayOutputStream.toByteArray();
    }

    public static Customer deserialize(byte[] bytes) throws IOException, ClassNotFoundException {
        ObjectInputStream objectInputStream = new ObjectInputStream(new ByteArrayInputStream(bytes));
        return (Customer) objectInputStream.readObject();
    }

    public static void res(Customer customer) {
        String[] s = customer.getName().trim().split("\\s+");
        StringBuilder newName = new StringBuilder(s[s.length - 1].toUpperCase()).append(",");
        for (int i = 0; i < s.length - 1; ++i)
            newName.append(" ").append(s[i].substring(0, 1).toUpperCase()).append(s[i].substring(1).toLowerCase());
        customer.setName(newName.toString());
        String[] dob = customer.getDayOfBirth().split("-");
        customer.setDayOfBirth(dob[1] + "/" + dob[0] + "/" + dob[2]);
        StringBuilder userName = new StringBuilder();
        for (int i = 0; i < s.length - 1; ++i) userName.append(s[i].substring(0, 1).toLowerCase());
        userName.append(s[s.length - 1].toLowerCase());
        customer.setUserName(userName.toString());
    }

    public static void main(String[] args) {
        String address = "203.162.10.109";
        int port = 2209;
        String idStudent = ";B21DCCN657;oPsw1fAb";
        try (
                DatagramSocket datagramSocket = new DatagramSocket();
        ) {
            InetAddress inetAddress = InetAddress.getByName(address);
            byte[] sendBytes;
            byte[] receiveBytes = new byte[100000];
            DatagramPacket sendPacket;
            DatagramPacket receivePacket;

            sendBytes = idStudent.getBytes();
            sendPacket = new DatagramPacket(sendBytes, sendBytes.length, inetAddress, port);
            datagramSocket.send(sendPacket);

            receivePacket = new DatagramPacket(receiveBytes, receiveBytes.length);
            datagramSocket.receive(receivePacket);

            byte[] requestIdBytes = new byte[8];
            System.arraycopy(receivePacket.getData(), 0, requestIdBytes, 0, requestIdBytes.length);
            String requestId = new String(requestIdBytes);
            System.out.println(requestId);

            byte[] objectBytes = new byte[receivePacket.getLength() - requestIdBytes.length];
            System.arraycopy(receivePacket.getData(), requestIdBytes.length, objectBytes, 0, objectBytes.length);

            Customer customer = deserialize(objectBytes);
            System.out.println(customer);
            res(customer);
            System.out.println(customer);

            byte[] customerBytes = serialize(customer);
            sendBytes = new byte[requestIdBytes.length + customerBytes.length];
            System.arraycopy(requestIdBytes, 0, sendBytes, 0, requestIdBytes.length);
            System.arraycopy(customerBytes, 0, sendBytes, requestIdBytes.length, customerBytes.length);

            sendPacket = new DatagramPacket(sendBytes, sendBytes.length, inetAddress, port);
            datagramSocket.send(sendPacket);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
