package br.com.mbarbosa.blog.util;

import br.com.mbarbosa.blog.models.Photo;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.UUID;

public class FileUtil {

    private FileUtil() { }

    public static boolean isJpeg(String imageBase64) {
        return imageBase64.startsWith("data:image/jpeg;base64,");
    }

    public static void saveImageFile(Photo photo, String uploadDir) throws IOException {
        String contents = photo.getImageContents().split("data:image/jpeg;base64,")[1];
        byte[] imageContents = Base64.getDecoder().decode(contents);
        Path absolutePath = Paths.get(uploadDir, photo.getName());

        ByteArrayInputStream bis = new ByteArrayInputStream(imageContents);
        BufferedImage bufferedImage = ImageIO.read(bis);
        ImageIO.write(bufferedImage, "jpg", new File(absolutePath.toString()));
    }

    public static boolean removeFile(Photo photo, String uploadDir) {

        if(photo.getName() == null) {
            return false;
        }

        Path absolutePath = Paths.get(uploadDir, photo.getName());
        return new File(absolutePath.toString()).delete();
    }

    public static String generateFotoName(Photo photo) {
        String imageName = UUID.randomUUID().toString() + ".jpg";
        photo.setName(imageName);
        return imageName;
    }

}
