import com.baidu.translate.demo.TransApi;

public class Main {

    // 在平台申请的APP_ID 详见 http://api.fanyi.baidu.com/api/trans/product/desktop?req=developer
    public static void main(String[] args) {
        String query = "GDHH-083 親に隠れてこっそり兄妹近親相姦 親の前ではわざと兄妹ゲンカ！しかし、実は兄妹以上の関係で2人きりになるとすぐに近親相姦セックスを始める！9.mp4";
        System.out.println(TransApi.getTransResult(query, "jp", "zh"));
    }

}
