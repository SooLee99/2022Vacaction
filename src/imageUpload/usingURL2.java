package imageUpload;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;
import java.io.PushbackInputStream;
import java.net.URL;
import java.net.URLConnection;
import javax.imageio.ImageIO;

public class usingURL2 {
	public static void main(String[] args) {
		
		// 이미지가 있는 url 주소
		//String imageURL = "https://search1.kakaocdn.net/argon/600x0_65_wr/ImZk3b2X1w8";
		String imageURL = "https://search.naver.com/search.naver?where=image&sm=tab_jum&query=펭수";
		try {
			// 해당 url로 서버에게 요청
			URL imgURL = new URL(imageURL);
			
			int pushbackLimit = 100;
			InputStream urlStream = imgURL.openStream();
			PushbackInputStream pushUrlStream = new PushbackInputStream(urlStream, pushbackLimit);
			byte [] firstBytes = new byte[pushbackLimit];
			
			// 첫 번째 초기 바이트를 바이트 배열로 다운로드함.
			// URL 연결. guessContentTypeFromStream  
			pushUrlStream.read(firstBytes);
			
			// 바이트를 PushbackInputStream으로 다시 밀어 스트림을 읽을 수 있도록 합니다.
			// 이미지별전체 IO 판독기
			pushUrlStream.unread(firstBytes);
			String imageType = null;
			
			// 초기 바이트를 URLConnection에 전달합니다.내용 추측 FromStream을 다음과 같은 형식으로
			// 지원되는 표시인 ByteArrayInputStream입니다.
			ByteArrayInputStream bais = new ByteArrayInputStream(firstBytes);
			String mimeType = URLConnection.guessContentTypeFromStream(bais);
			
			if (mimeType.startsWith("image/"))
			    imageType = mimeType.substring("image/".length());
			// else handle failure here

			BufferedImage inputImage = ImageIO.read(pushUrlStream);
			
			// url 파일 확장자
			//String extension = imageURL.substring(imageURL.lastIndexOf(".")+1);
			String fileName = "test.jpg"; // 이미지 이름
			
			// 이미지 검색
			//BufferedImage image = ImageIO.read(imgURL);
			File file = new File("myImage/" + fileName); //+ "." + extension); 
			if(!file.exists()) { // 해당 경로의 폴더가 존재하지 않을 경우
				file.mkdirs(); // 해당 경로의 폴더 생성
			}
			
			ImageIO.write(inputImage, "jpg", file); // image를 file로 업로드
			System.out.println("이미지 업로드 완료!");
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
}