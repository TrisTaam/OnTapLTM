import vn.medianews.DataService;
import vn.medianews.DataService_Service;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/*
[Mã câu hỏi (qCode): mdh4GDBL].  Một dịch vụ web được định nghĩa và mô tả trong tệp DataService?wsdl, được triển khai trên server tại URL http://<Exam_IP>:8080/JNPWS/DataService?wsdl để xử lý các bài toán với dữ liệu nguyên thủy.
Yêu cầu: Viết chương trình tại máy trạm (WS client) để giao tiếp với DataService thực hiện các công việc sau:
a. Triệu gọi phương thức getData với tham số đầu vào là mã sinh viên (studentCode) và mã câu hỏi (qCode) để nhận về một danh sách số nguyên (List<Integer>) từ server.
    Ví dụ: 7602,9136,1090,34319,7830,6179,10584,20166,28199,30250,32179,22544,3222,10320,30590,19279
b. Thực hiện tìm số lớn nhất có thể tạo dược từ a,b,c,d...
c. Triệu gọi phương thức submitDataString(String studentCode, String qCode, String data) để gửi kết quả tổng đã tính được trở lại server.
    Ví dụ: 91367830760261793431932223217930590302502819922544201661927910901058410320
d. Kết thúc chương trình client.
 */
public class Bai_mdh4GDBL_WS_DataService {
    public static void main(String[] args) {
        String studentCode = "B21DCCN657";
        String qCode = "mdh4GDBL";
        DataService_Service service = new DataService_Service();
        DataService port = service.getDataServicePort();
        List<Integer> a = port.getData(studentCode, qCode);
        for (Integer x : a) System.out.println(x);
        Collections.sort(a, new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return -(o1.toString() + o2.toString()).compareTo(o2.toString() + o1.toString());
            }
        });
        StringBuilder ans = new StringBuilder();
        for (Integer x : a) ans.append(x);
        System.out.println(ans);
        port.submitDataString(studentCode, qCode, ans.toString());
    }
}
