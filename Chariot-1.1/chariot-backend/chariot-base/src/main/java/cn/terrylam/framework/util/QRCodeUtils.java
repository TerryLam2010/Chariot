package cn.terrylam.framework.util;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URL;

import javax.imageio.ImageIO;

import com.swetake.util.Qrcode;

public class QRCodeUtils {
	/**
	 * 生成二维码图片流(GBK)
	 * @param content
	 * @return
	 */
	public static BufferedImage encoderQRCode(String content) {
		int width = 148, height = 148;
		Qrcode qrcode = new Qrcode();
		qrcode.setQrcodeErrorCorrect('M');
		qrcode.setQrcodeEncodeMode('B');
		qrcode.setQrcodeVersion(7);
		BufferedImage bufImg = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		Graphics2D g = bufImg.createGraphics();
	
		g.setBackground(Color.WHITE);
		g.clearRect(0, 0, width, height);
		g.setColor(Color.BLACK);
	    int pixoff = 6;  
	    try {
			byte[] contentBytes = content.getBytes("GBK");  
			if (contentBytes.length > 0 && contentBytes.length < 120) {  
			    boolean[][] codeOut = qrcode.calQrcode(contentBytes);  
			    for (int i = 0; i < codeOut.length; i++) {
			        for (int j = 0; j < codeOut.length; j++) {  
			            if (codeOut[j][i]) {
			                g.fillRect(j * 3 + pixoff, i * 3 + pixoff, 3, 3);  
			            }  
			        }  
			    }
			} else {  
			    System.err.println("QRCode content bytes length = " + contentBytes.length + " not in [ 0,120 ]. ");  
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		g.dispose();
        bufImg.flush(); 
	    return bufImg;
	}
	
	/**
	 * 生成二维码图片文件
	 * @param content
	 * @param imgPath
	 * @throws IOException
	 */
	public static void encoderQRCode(String content, String imgPath) throws IOException {
		int w = 148, h = 148;
		Qrcode qrcode = new Qrcode();
		qrcode.setQrcodeErrorCorrect('M');
		qrcode.setQrcodeEncodeMode('B');
		qrcode.setQrcodeVersion(7);
		BufferedImage bufImg = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
		Graphics2D g = bufImg.createGraphics();
	
		g.setBackground(Color.WHITE);
		g.clearRect(0, 0, w, h);
		g.setColor(Color.BLACK);
	    int pixoff = 6;  
	    try {
			byte[] contentBytes = content.getBytes("GBK");  
			if (contentBytes.length > 0 && contentBytes.length < 120) {  
			    boolean[][] codeOut = qrcode.calQrcode(contentBytes);  
			    for (int i = 0; i < codeOut.length; i++) {
			        for (int j = 0; j < codeOut.length; j++) {  
			            if (codeOut[j][i]) {
			                g.fillRect(j * 3 + pixoff, i * 3 + pixoff, 3, 3);  
			            }  
			        }  
			    }
			} else {  
			    System.err.println("QRCode content bytes length = " + contentBytes.length + " not in [ 0,120 ]. ");  
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		g.dispose();
        bufImg.flush(); 
        File imgFile = new File(imgPath); 
        // 生成二维码图片 
        ImageIO.write(bufImg, "jpeg", imgFile); 
	}
	
   /**
    * 生成二维码
    * @param content
    * @param imgType
    * @param size
    * @return
    */
   public static BufferedImage encoderQRCode(String content, String imgType, int size) {
        BufferedImage bufImg = null;
         try {
             Qrcode qrcodeHandler = new Qrcode();
              // 设置二维码排错率，可选L(7%)、M(15%)、Q(25%)、H(30%)，排错率越高可存储的信息越少，但对二维码清晰度的要求越小
             qrcodeHandler.setQrcodeErrorCorrect( 'M');
             qrcodeHandler.setQrcodeEncodeMode( 'B');
              // 设置设置二维码尺寸，取值范围1-40，值越大尺寸越大，可存储的信息越大
             qrcodeHandler.setQrcodeVersion(size);
              // 获得内容的字节数组，设置编码格式
             byte[] contentBytes = content.getBytes("utf-8");
              // 图片尺寸
             int imgSize = 67 + 12 * (size - 1);
             bufImg = new BufferedImage(imgSize, imgSize, BufferedImage.TYPE_INT_RGB );
             Graphics2D gs = bufImg.createGraphics();
              // 设置背景颜色
             gs.setBackground(Color.WHITE);
             gs.clearRect(0, 0, imgSize, imgSize);
 
              // 设定图像颜色> BLACK
             gs.setColor(Color.BLACK);
              // 设置偏移量，不设置可能导致解析出错
              int pixoff = 2;
              // 输出内容> 二维码
              if (contentBytes. length > 0 && contentBytes.length < 800) {
                   boolean[][] codeOut = qrcodeHandler.calQrcode(contentBytes);
                   for ( int i = 0; i < codeOut. length; i++) {
                         for ( int j = 0; j < codeOut. length; j++) {
                              if (codeOut[j][i]) {
                                  gs.fillRect(j * 3 + pixoff, i * 3 + pixoff, 3, 3);
                             }
                        }
                  }
             } else {
                   throw new Exception( "QRCode content bytes  length = " + contentBytes. length + " not in [0, 800].");
             }
             gs.dispose();
             bufImg.flush();
        } catch (Exception e) {
             e.printStackTrace();
        }
        return bufImg;
  }
   
   /**
    * 生成带图片的二维码
    * @param content
    * @param imgType
    * @param size
    * @param photoUrl
    * @return
    */
   public static BufferedImage encoderQRCode(String content, String imgType, int size, String photoUrl){
	   if(StringUtils.isBlank(photoUrl))
		   return encoderQRCode(content, imgType, size);
	   
	   BufferedImage qrcodeBi = encoderQRCode(content, imgType, size);
	   BufferedImage photoBi = loadImageUrl(photoUrl);
	   return modifyImagetogeter(photoBi, qrcodeBi);
   }
   
   /** 
    * 导入网络图片到缓冲区 
    */  
   public static BufferedImage loadImageUrl(String imgName) {  
       try {  
           URL url = new URL(imgName);  
           return ImageIO.read(url);  
       } catch (IOException e) {  
           e.printStackTrace();
       }  
       return null;  
   } 
   
   /**
    * 合并图片(小图居中)
    * @param b
    * @param d
    * @return
    */
   public static BufferedImage modifyImagetogeter(BufferedImage bi1, BufferedImage bi2) {  
       try {  
           /*int w1 = bi1.getWidth();  
           int h1 = bi1.getHeight(); */ 
           int w2 = bi2.getWidth();  
           int h2 = bi2.getHeight();
             
           Graphics2D g = bi2.createGraphics();  
           g.drawImage(bi1, (w2*3)/8, (h2*3)/8, w2/4, h2/4, null);
           g.dispose();  
       } catch (Exception e) {  
           e.printStackTrace();
       }  
 
       return bi2;  
   }  
   
}
