package dal;
import java.util.logging.Logger;
import java.util.logging.Level;

public class ExceptionHandlers { // Đổi tên lớp để tránh nhầm lẫn

    private static final Logger LOGGER = Logger.getLogger(ExceptionHandlers.class.getName());

    private ExceptionHandlers() {
        throw new IllegalStateException("Utility class");
    }

    public static void handleException(Exception ex) {
        LOGGER.log(Level.SEVERE, "Đã xảy ra lỗi: {0}", ex.getMessage());
        LOGGER.log(Level.SEVERE, "Chi tiết lỗi:", ex);
    }
}