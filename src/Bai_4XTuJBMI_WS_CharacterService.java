import vn.medianews.CharacterService;
import vn.medianews.CharacterService_Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/*
[Mã câu hỏi (qCode): 4XTuJBMI].  Một dịch vụ web được định nghĩa và mô tả trong tệp CharacterService.wsdl, được triển khai trên server tại URL http://<Exam_IP>:8080/JNPWS/CharacterService?wsdl để xử lý các bài toán về chuỗi và ký tự.
Yêu cầu: Viết chương trình tại máy trạm (WS client) để giao tiếp với CharacterService thực hiện các công việc sau:
a. Triệu gọi phương thức requestStringArray với tham số đầu vào là mã sinh viên (studentCode) và mã câu hỏi (qCode) để nhận về một danh sách chuỗi (List<String>) từ server.
b. Phân loại các từ trong mảng chuỗi thành các nhóm có cùng số lượng nguyên âm. Tạo một chuỗi cho mỗi nhóm, trong đó liệt kê các từ cách nhau bằng dấu phẩy, và sắp xếp các từ theo thứ tự từ điển trong mỗi nhóm.
c. Triệu gọi phương thức submitCharacterStringArray(String studentCode, String qCode, List<String> data) để gửi danh sách chuỗi kết quả trở lại server, trong đó mỗi phần tử là một nhóm từ với cùng số lượng nguyên âm.
Ví dụ: Nếu danh sách chuỗi nhận được từ phương thức requestCharacter là ["apple", "banana", "pear", "grape", "kiwi"], các nhóm có thể là:
•	Nhóm 2 nguyên âm: "apple, banana"
•	Nhóm 1 nguyên âm: "grape, kiwi, pear"
Danh sách kết quả sẽ là ["apple, banana", "grape, kiwi, pear"], và danh sách này sẽ được gửi lại server qua phương thức submitCharacter.
d. Kết thúc chương trình client.
 */
public class Bai_4XTuJBMI_WS_CharacterService {
    public static void main(String[] args) {
        String studentCode = "B21DCCN657";
        String qCode = "4XTuJBMI";
        CharacterService_Service service = new CharacterService_Service();
        CharacterService port = service.getCharacterServicePort();
        List<String> a = port.requestStringArray(studentCode, qCode);
        for (String x : a) System.out.println(x);
        String vowels = "aeuio";
        List<String>[] b = new ArrayList[6];
        for (int i = 0; i < b.length; ++i) b[i] = new ArrayList<>();
        for (String x : a) {
            int cnt = 0;
            for (int i = 0; i < x.length(); ++i)
                if (vowels.contains(Character.toString(x.charAt(i)).toLowerCase())) {
                    cnt++;
                }
            b[cnt].add(x);
        }
        List<String> c = new ArrayList<>();
        for (int i = 0; i < 6; ++i) {
            if (b[i].isEmpty()) continue;
            Collections.sort(b[i]);
            StringBuilder tmp = new StringBuilder();
            for (String x : b[i]) tmp.append(x).append(", ");
            tmp.delete(tmp.length() - 2, tmp.length());
            c.add(tmp.toString());
        }
        for (String x : c) System.out.println(x);
        port.submitCharacterStringArray(studentCode, qCode, c);
    }
}
