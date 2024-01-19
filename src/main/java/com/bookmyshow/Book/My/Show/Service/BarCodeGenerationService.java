package com.bookmyshow.Book.My.Show.Service;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;

@Service
public class BarCodeGenerationService {
    private static final String qrLocation = "./src/main/resources/static/QRcode.png";
    private static int height = 250;
    private static int width = 250;

    public void generateQR(String text) throws WriterException, IOException {
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix = qrCodeWriter.encode(text, BarcodeFormat.QR_CODE, width, height);
        Path path = FileSystems.getDefault().getPath(qrLocation);
        MatrixToImageWriter.writeToPath(bitMatrix, "PNG", path);
    }
}
