/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package util;
import java.util.Random;
/**
 *
 * @author root
 */
public class CaptchaUtil {
    public static int generateCaptcha() {
        Random r = new Random();
        int a = r.nextInt(10);
        int b = r.nextInt(10);
        return a + b;
    }
}
