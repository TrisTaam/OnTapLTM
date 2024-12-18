/*
[Mã câu hỏi (qCode): B2LCgcqu].  Một chương trình (tạm gọi là RMI Server) cung cấp giao diện cho phép triệu gọi từ xa để xử lý dữ liệu.
Giao diện từ xa:
public interface DataService extends Remote {
public Object requestData(String studentCode, String qCode) throws RemoteException;
public void submitData(String studentCode, String qCode, Object data) throws RemoteException;
}
Trong đó:
•	Interface DataService được viết trong package RMI.
•	Đối tượng cài đặt giao diện từ xa DataService được đăng ký với RegistryServer với tên là: RMIDataService.
Yêu cầu: Viết chương trình tại máy trạm (RMI client) để thực hiện các công việc sau với dữ liệu nhận được từ RMI Server:
a. Triệu gọi phương thức requestData để nhận một số nguyên dương lớn từ server, gọi là N.
b. Thực hiện phân rã số N thành các thừa số nguyên tố. Kết quả trả về là danh sách các thừa số nguyên tố của N.
Ví dụ: Với N = 84, kết quả là danh sách “2, 2, 3, 7”.
c. Triệu gọi phương thức submitData để gửi danh sách các thừa số nguyên tố đã tìm được trở lại server.
d. Kết thúc chương trình client.
*/

import RMI.DataService;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

public class Bai_B2LCgcqu_RMI_Data {
    public static void main(String[] args) throws MalformedURLException, NotBoundException, RemoteException {
        String studentCode = "B21DCCN657";
        String qCode = "B2LCgcqu";
        DataService dataService = (DataService) Naming.lookup("rmi://203.162.10.109/RMIDataService");
        int n = (int) dataService.requestData(studentCode, qCode);
        System.out.println(n);
        List<Integer> a = new ArrayList<>();
        int i = 2;
        while (n > 1) {
            while (n % i == 0) {
                a.add(i);
                n /= i;
            }
            i++;
        }
        for (Integer x : a) System.out.printf("%d ", x);
        dataService.submitData(studentCode, qCode, a);
    }
}
