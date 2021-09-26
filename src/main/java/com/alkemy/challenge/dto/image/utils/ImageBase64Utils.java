package com.alkemy.challenge.dto.image.utils;

import com.alkemy.challenge.dto.image.exceptions.InvalidImageException;
import org.springframework.lang.Nullable;

import javax.imageio.ImageIO;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Base64;

public class ImageBase64Utils {

    @Nullable
    public static byte[] validateAndDecodeBase64Image(String stringEncoded){
        if(stringEncoded == null){
            return null;
        }
        byte[] imageDecoded;

        try {
            imageDecoded = Base64.getDecoder().decode(stringEncoded);
            if(isValidImage(imageDecoded)){
                return imageDecoded;
            }
        } catch (IllegalArgumentException exception) {
            throw new InvalidImageException("Invalid image");
        }

        throw new InvalidImageException("Invalid image");
    }

    private static boolean isValidImage(byte[] image){
        try(ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(image)) {
            return ImageIO.read(byteArrayInputStream) != null;
        } catch (IOException e){
            return false;
        }
    }

    @Nullable
    public static String encodeImageToBase64(byte[] image){
        if(image == null){
            return null;
        }

        return Base64.getEncoder().encodeToString(image);
    }

}
