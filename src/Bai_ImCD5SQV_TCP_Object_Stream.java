import TCP.Customer;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/*
[Mã câu hỏi (qCode): ImCD5SQV].  Thông tin khách hàng cần thay đổi định dạng lại cho phù hợp với khu vực, cụ thể:
a.	Tên khách hàng cần được chuẩn hóa theo định dạng mới. Ví dụ: nguyen van hai duong -> DUONG, Nguyen Van Hai
b.	Ngày sinh của khách hàng hiện đang ở dạng mm-dd-yyyy, cần được chuyển thành định dạng dd/mm/yyyy. Ví dụ: 10-11-2012 -> 11/10/2012
c.	Tài khoản khách hàng là các chữ cái in thường được sinh tự động từ họ tên khách hàng. Ví dụ: nguyen van hai duong -> nvhduong

Một chương trình server cho phép kết nối qua giao thức TCP tại cổng 2209 (hỗ trợ thời gian giao tiếp tối đa cho mỗi yêu cầu là 5s). Yêu cầu là xây dựng một chương trình client tương tác với server sử dụng các luồng đối tượng (ObjectInputStream / ObjectOutputStream) thực hiện gửi/nhận đối tượng khách hàng và chuẩn hóa. Cụ thể:
a.	Đối tượng trao đổi là thể hiện của lớp Customer được mô tả như sau
      •	Tên đầy đủ của lớp: TCP.Customer
      •	Các thuộc tính: id int, code String, name String, dayOfBirth String, userName String
      •	Hàm khởi tạo đầy đủ các thuộc tính được liệt kê ở trên
      •	Trường dữ liệu: private static final long serialVersionUID = 20170711L;
b.	Tương tác với server theo kịch bản dưới đây:
	1) Gửi đối tượng là một chuỗi gồm mã sinh viên và mã câu hỏi ở định dạng "studentCode;qCode". Ví dụ: "B15DCCN999;F2DA54F3"
	2) Nhận một đối tượng là thể hiện của lớp Customer từ server với các thông tin đã được thiết lập
	3) Thay đổi định dạng theo các yêu cầu ở trên và gán vào các thuộc tính tương ứng.  Gửi đối tượng đã được sửa đổi lên server
	4) Đóng socket và kết thúc chương trình.
*/
public class Bai_ImCD5SQV_TCP_Object_Stream {
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
        String idStudent = "B21DCCN657;ImCD5SQV";
        try (
                Socket socket = new Socket(address, port);
                ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
                ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
        ) {
            objectOutputStream.writeObject(idStudent);
            objectOutputStream.flush();

            Customer customer = (Customer) objectInputStream.readObject();
            System.out.println(customer);
            res(customer);
            System.out.println(customer);

            objectOutputStream.writeObject(customer);
            objectOutputStream.flush();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
