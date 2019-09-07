package com.java.common.utils;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Hashtable;

/**
 * 二维码的生成需要借助MatrixToImageWriter类，该类是由Google提供的，可以将该类直接拷贝到源码中使用
 */
public class GenerateQRCodeUtil {
    private static final int BLACK = 0xFF000000;
    private static final int WHITE = 0xFFFFFFFF;

    public static BufferedImage toBufferedImage(BitMatrix matrix) {
        int width = matrix.getWidth();
        int height = matrix.getHeight();
        BufferedImage image = new BufferedImage(width, height,
                BufferedImage.TYPE_INT_RGB);
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                image.setRGB(x, y, matrix.get(x, y) ? BLACK : WHITE);
            }
        }
        return image;
    }

    public static void writeToFile(BitMatrix matrix, String format, File file)
            throws IOException {
        BufferedImage image = toBufferedImage(matrix);
        if (!ImageIO.write(image, format, file)) {
            throw new IOException("Could not write an image of format "
                    + format + " to " + file);
        }
    }

    public void writeToStream(BitMatrix matrix, String format,
                                     OutputStream stream) throws IOException {
        BufferedImage image = toBufferedImage(matrix);
        if (!ImageIO.write(image, format, stream)) {
            throw new IOException("Could not write an image of format " + format);
        }
    }

    /**
     * 生成二维码
     * @param param 二维码里的参数
     * @param url 二维码存放路径
     * @param fileName 二维码文件名，jpg格式
     * @return
     */
    public static String generateQR(String param, String url, String fileName){
        int width = 300; // 二维码图片宽度
        int height = 300; // 二维码图片高度
        String format = "jpg";// 二维码的图片格式

        Hashtable<EncodeHintType, String> hints = new Hashtable<EncodeHintType, String>();
        hints.put(EncodeHintType.CHARACTER_SET, "utf-8"); // 内容所使用字符集编码

        // 生成路径
//        File outputFile = new File(url + "new.jpg");
        File f = new File(url);
        if(!f.exists()){
            f.mkdirs();
        }
        File file = new File(f,fileName);
        try {
            BitMatrix bitMatrix = new MultiFormatWriter().encode(param,
                        BarcodeFormat.QR_CODE, width, height, hints);
            // 生成二维码
            writeToFile(bitMatrix, format, file);
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
        return url + "/" + fileName;
    }

    public static void main(String[] args) {
        generateQR("生成成功",System.getProperty("user.dir")+"/test_image", "new.jpg");
    }
}
