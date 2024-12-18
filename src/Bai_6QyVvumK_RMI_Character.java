/*
[Mã câu hỏi (qCode): 6QyVvumK].  Một chương trình (tạm gọi là RMI Server) cung cấp giao diện cho phép triệu gọi từ xa để xử lý chuỗi. Cụ thể:
Giao diện từ xa:
    public interface CharacterService extends Remote {
        public String requestCharacter(String studentCode, String qCode) throws RemoteException;
        public void submitCharacter(String studentCode, String qCode, String strSubmit) throws RemoteException;
    }
Trong đó:
•	Interface CharacterService được viết trong package RMI.
•	Đối tượng cài đặt giao diện từ xa CharacterService được đăng ký với RegistryServer với tên là: RMICharacterService.
Yêu cầu: Viết chương trình tại máy trạm (RMI client) để thực hiện các công việc sau với chuỗi được nhận từ RMI Server:
a. Triệu gọi phương thức requestCharacter để nhận chuỗi ngẫu nhiên từ server.
b. Thực hiện thao tác giải mã Caesar (mã hóa chuỗi bằng cách dịch từng ký tự đi một số lượng vị trí nhất định). Biết rằng giá trị dịch đúng bằng kích thước của chuỗi chia lấy dư cho 7.
c. Triệu gọi phương thức submitCharacter để gửi chuỗi đã được giải mã trở lại server.
d. Kết thúc chương trình client.
*/

import RMI.CharacterService;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

public class Bai_6QyVvumK_RMI_Character {
    public static void main(String[] args) throws MalformedURLException, NotBoundException, RemoteException {
        String studentCode = "B21DCCN657";
        String qCode = "6QyVvumK";
        CharacterService characterService = (CharacterService) Naming.lookup("rmi://203.162.10.109/RMICharacterService");
        String s = characterService.requestCharacter(studentCode, qCode);
        System.out.println(s);
        int a = s.length() % 7;
        StringBuilder ans = new StringBuilder();
        for (int i = 0; i < s.length(); ++i) ans.append((char) ((s.charAt(i) - 'a' - a + 26) % 26 + 'a'));
        System.out.println(ans);
        characterService.submitCharacter(studentCode, qCode, ans.toString());
    }
}
