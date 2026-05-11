package BUS;

public class Logger {
    
    // Bật/tắt chế độ debug tại đây
    private static final boolean DEBUG_MODE = false;
    
    // Chỉ hiện khi DEBUG_MODE = true (dùng khi dev/fix bug)
    public static void debug(String message) {
        if (DEBUG_MODE) {
            System.out.println("[DEBUG] " + message);
        }
    }
    
}