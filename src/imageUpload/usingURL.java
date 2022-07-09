package imageUpload;

import java.awt.image.BufferedImage;
import java.io.File;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.imageio.stream.ImageOutputStream;

public class usingURL {
	public static void main(String[] args) {
		// 이미지가 있는 url 주소
		String imageURL = "https://blog.kakaocdn.net/dn/VIxFi/btqZqqf3QFS/n2otuLtHQo8TQVOwMAmmbk/img.png";
		
		try {
			// 해당 url로 서버에게 요청
			URL imgURL = new URL(imageURL);
			// url 파일 확장자
			String extension = imageURL.substring(imageURL.lastIndexOf(".")+1);
			String fileName = "test"; // 이미지 이름
			
			// 이미지 검색
			BufferedImage image = ImageIO.read(imgURL);
			File file = new File("myImage/" + fileName + "." + extension); 
			if(!file.exists()) { // 해당 경로의 폴더가 존재하지 않을 경우
				file.mkdirs(); // 해당 경로의 폴더 생성
			}
			
			ImageIO.write(image, extension, file); // image를 file로 업로드
			System.out.println("이미지 업로드 완료!");
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}