package helpers;

import constants.ConfigData;
import drivers.DriverManager;
import org.monte.media.Format;
import org.monte.media.FormatKeys;
import org.monte.media.Registry;
import org.monte.media.math.Rational;
import org.monte.screenrecorder.ScreenRecorder;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.io.FileHandler;
import utils.LogUtils;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.monte.media.FormatKeys.*;
import static org.monte.media.VideoFormatKeys.*;

//Kế thừa (Extends) lại class xây dựng sẵn trong thư viện Monte là ScreenRecorder
public class CaptureHelper extends ScreenRecorder {

    // Record with Monte Media library
    public static ScreenRecorder screenRecorder;
    public String name;

    //Hàm xây dựng (kế thừa lại cấu trúc hàm xây dựng của ScreenRecorder
    public CaptureHelper(GraphicsConfiguration cfg, Rectangle captureArea, Format fileFormat, Format screenFormat, Format mouseFormat, Format audioFormat, File movieFolder, String name) throws IOException, AWTException {
        super(cfg, captureArea, fileFormat, screenFormat, mouseFormat, audioFormat, movieFolder);
        this.name = name;
    }

    //Hàm này bắt buộc để ghi đè để custom lại hàm trong thư viên viết sẵn
    @Override
    protected File createMovieFile(Format fileFormat) throws IOException {

        if (!movieFolder.exists()) {
            movieFolder.mkdirs();
        } else if (!movieFolder.isDirectory()) {
            throw new IOException("\"" + movieFolder + "\" is not a directory.");
        }
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH-mm-ss");
        return new File(movieFolder, name + "-" + dateFormat.format(new Date()) + "." + Registry.getInstance().getExtension(fileFormat));
    }

    // Start record video
    public static void startRecord(String methodName) {
        //Tạo thư mục để lưu file video vào
        File file = new File(SystemHelper.getCurrentDir() + ConfigData.RECORD_VIDEO_PATH);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int width = screenSize.width;
        int height = screenSize.height;

        Rectangle captureSize = new Rectangle(0, 0, width, height);

        GraphicsConfiguration gc = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration();
        try {
            screenRecorder = new CaptureHelper(gc, captureSize, new Format(MediaTypeKey, MediaType.FILE, MimeTypeKey, MIME_AVI), new Format(MediaTypeKey, MediaType.VIDEO, EncodingKey, ENCODING_AVI_TECHSMITH_SCREEN_CAPTURE, CompressorNameKey, ENCODING_AVI_TECHSMITH_SCREEN_CAPTURE, DepthKey, 24, FrameRateKey, Rational.valueOf(15), QualityKey, 1.0f, KeyFrameIntervalKey, 15 * 60), new Format(MediaTypeKey, MediaType.VIDEO, EncodingKey, "black", FrameRateKey, Rational.valueOf(30)), null, file, methodName);
            screenRecorder.start();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (AWTException e) {
            throw new RuntimeException(e);
        }
    }

    // Stop record video
    public static void stopRecord() {
        try {
            screenRecorder.stop();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void screenshot(String imageName){

        //Tạo format ngày giờ để xíu gắn dô cái name của screenshot hoặc record video không bị trùng tên (không bị ghi đè file)
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH-mm-ss");

        //Tạo tham chiếu của TakesScreenshot
        TakesScreenshot ts = (TakesScreenshot) DriverManager.getDriver();

        //Gọi hàm để chụp ảnh màn hình
        File source = ts.getScreenshotAs(OutputType.FILE);

        //Kiểm tra folder tồn tại. Nếu không tồn tại thì tạo folder mới theo đường dẫn
        File theDir = new File(ConfigData.SCREENSHOT_PATH);
        if (!theDir.exists()) {
            theDir.mkdirs();
        }

        //Lưu file ảnh với tên cụ thể vào đường dẫn
        try {
            FileHandler.copy(source, new File(ConfigData.SCREENSHOT_PATH+ imageName + "-"+ dateFormat.format(new Date()) + ".png"));
            LogUtils.info("Screenshot successfully !!");
        } catch (IOException e) {
            LogUtils.error(e.getMessage());
        }
    }

    public static void screenshotSuccess(String imageName){

        //Tạo format ngày giờ để xíu gắn dô cái name của screenshot hoặc record video không bị trùng tên (không bị ghi đè file)
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH-mm-ss");

        //Tạo tham chiếu của TakesScreenshot
        TakesScreenshot ts = (TakesScreenshot) DriverManager.getDriver();

        //Gọi hàm để chụp ảnh màn hình
        File source = ts.getScreenshotAs(OutputType.FILE);

        //Kiểm tra folder tồn tại. Nếu không tồn tại thì tạo folder mới theo đường dẫn
        File theDir = new File(ConfigData.SUCCESS_SCREENSHOT_PATH);
        if (!theDir.exists()) {
            theDir.mkdirs();
        }

        //Lưu file ảnh với tên cụ thể vào đường dẫn
        try {
            FileHandler.copy(source, new File(ConfigData.SUCCESS_SCREENSHOT_PATH+ imageName + "-"+ dateFormat.format(new Date()) + ".png"));
            LogUtils.info("Screenshot successfully !!");
        } catch (IOException e) {
            LogUtils.error(e.getMessage());
        }
    }

    public static void screenshotFail(String imageName){

        //Tạo format ngày giờ để xíu gắn dô cái name của screenshot hoặc record video không bị trùng tên (không bị ghi đè file)
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH-mm-ss");

        //Tạo tham chiếu của TakesScreenshot
        TakesScreenshot ts = (TakesScreenshot) DriverManager.getDriver();

        //Gọi hàm để chụp ảnh màn hình
        File source = ts.getScreenshotAs(OutputType.FILE);

        //Kiểm tra folder tồn tại. Nếu không tồn tại thì tạo folder mới theo đường dẫn
        File theDir = new File(ConfigData.FAIL_SCREENSHOT_PATH);
        if (!theDir.exists()) {
            theDir.mkdirs();
        }

        //Lưu file ảnh với tên cụ thể vào đường dẫn
        try {
            FileHandler.copy(source, new File(ConfigData.FAIL_SCREENSHOT_PATH+ imageName + "-"+ dateFormat.format(new Date()) + ".png"));
            LogUtils.info("Screenshot successfully !!");
        } catch (IOException e) {
            LogUtils.error(e.getMessage());
        }
    }
}
