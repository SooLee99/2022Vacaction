package imageUpload;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

import javax.swing.JPanel;
import javax.swing.JTextField;

import org.json.JSONArray;
import org.json.JSONObject;

import javax.swing.JButton;
import javax.swing.JFileChooser;

@SuppressWarnings("serial")
public class ImageUploadGUI extends JFrame implements ActionListener {

	private JLabel lblNorth;
	private JLabel lblKeyword;
	private JLabel lblSave;
	private JTextField tfKeyword;
	private JPanel panelWest;
	private JPanel panelEast;
	private JTextField tfSave;
	private JButton btnSave;
	private JButton btnOpen;
	private String keywordSearch;
	private String saveSearch;
	
	public ImageUploadGUI() {
		setTitle("사진 모으기 프로젝트");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(310, 200);
		setLocation(600, 300);
		setResizable(true);
		getContentPane().setLayout(new BorderLayout());
		
		lblNorth = new JLabel("카카오 Open API 사진 모으기 프로젝트");
		lblNorth.setFont(new Font("나눔고딕", Font.BOLD, 15));
		lblNorth.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		lblNorth.setHorizontalAlignment(JLabel.CENTER); 
		getContentPane().add(lblNorth, BorderLayout.NORTH);
		
		panelWest();
		panelEast();
		
		setVisible(true);
	}

	private void panelWest() {
		panelWest = new JPanel();
		panelWest.setLayout(new FlowLayout());
		panelWest.setPreferredSize(new Dimension(100,100));
		panelWest.setBorder(BorderFactory.createEmptyBorder(15, 10 , 0 , 0));
		
		lblKeyword = new JLabel("키워드 검색");
		lblKeyword.setFont(new Font("나눔고딕", Font.BOLD, 12));
		
		lblSave = new JLabel("저장 위치   ");
		lblSave.setFont(new Font("나눔고딕", Font.BOLD, 12)); 
		lblSave.setBorder(BorderFactory.createEmptyBorder(11, 0, 0, 0));
		
		panelWest.add(lblKeyword);
		panelWest.add(lblSave);
		getContentPane().add(panelWest, BorderLayout.WEST);
	}
	
	private void panelEast() {
		panelEast = new JPanel();
		panelWest.setLayout(new FlowLayout());
		panelEast.setPreferredSize(new Dimension(200,100));
		panelEast.setBorder(BorderFactory.createEmptyBorder(13, 0, 0 , 0));
		
		tfKeyword = new JTextField(15);
		tfKeyword.setBounds(0, 15, 180, 20);
		tfSave = new JTextField(10);
		tfSave.setBounds(0, 50, 100, 20);
		tfSave.setEnabled(false);
		panelEast.setLayout(null);
		
		panelEast.add(tfKeyword);
		panelEast.add(tfSave);
		
		add(panelEast, BorderLayout.EAST);
		
		btnOpen = new JButton("열기");
		btnOpen.setBounds(110, 50, 70, 20);
		btnOpen.addActionListener(this);
		panelEast.add(btnOpen);
		
		btnSave = new JButton("저장");
		btnSave.setBounds(0, 90, 180, 23);
		btnSave.addActionListener(this);
		panelEast.add(btnSave);
		
	}

	@SuppressWarnings("unused")
	public static void main(String[] args) {
		ImageUploadGUI iuGUI = new ImageUploadGUI();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object obj = e.getSource();
		// 파일 위치 열기 버튼
		if(obj == btnOpen) {
			JFileChooser jfc = new JFileChooser();
            jfc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            jfc.showDialog(this, null);
            File dir = jfc.getSelectedFile();
            tfSave.setText(dir==null?"":dir.getPath());
			} 
		
		// 파일 저장 버튼
		else if(obj == btnSave) {
			if(tfKeyword.equals(""))
				JOptionPane.showMessageDialog(this, "키워드를 입력하셔야 합니다");
			else if(tfSave.equals(""))
				JOptionPane.showMessageDialog(this, "저장위치를 입력하셔야 합니다");
			else {
				keywordSearch = tfKeyword.getText();
				saveSearch = tfSave.getText()+"\\";
				
			String restApiKey = "9fe52013af1c347f70a2ce56386eabbf";  // 개인 rest-api 키 입력

			try {
				String text = URLEncoder.encode(keywordSearch, "UTF-8");
				String postParams = "src_lang=kr&target_lang=en&query=" + text;  // 파라미터
				String apiURL = "https://dapi.kakao.com/v2/search/image?" + postParams;
				URL url = new URL(apiURL);
				HttpURLConnection con = (HttpURLConnection)url.openConnection();
				String userCredentials = restApiKey;
				String basicAuth = "KakaoAK " + userCredentials;
				con.setRequestProperty("Authorization", basicAuth);
				
				// 이건 필요 유무 몰라서 빼놈
				//con.setRequestMethod("GET");
				//con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
				//con.setRequestProperty("charset", "utf-8");
				//con.setUseCaches(false);
				//con.setDoInput(true);
				//con.setDoOutput(true);
				int responseCode = con.getResponseCode();
				System.out.println("responseCode >> " + responseCode);
				BufferedReader br;
				if(responseCode == 200) {
					br = new BufferedReader(new InputStreamReader(con.getInputStream()));
				}
				else {
					br = new BufferedReader(new InputStreamReader(con.getErrorStream()));
				}
				String inputLine;
				StringBuffer res = new StringBuffer();
				while ((inputLine = br.readLine()) != null) {
					res.append(inputLine);
					
				}
				br.close();
				//System.out.println("응답결과>> " + res.toString());

				// 가장 큰 JSONObject를 가져옵니다.
			    JSONObject jObject = new JSONObject(res.toString());
			    // 배열을 가져옵니다.
			    JSONArray jArray = jObject.getJSONArray("documents");

			    String savePath = saveSearch; // 이미지 저장 파일
			    String fileFormat = "jpg";
				
			    // 배열의 모든 아이템을 출력합니다.
			    for (int i = 0; i < jArray.length(); i++) {
			        JSONObject jobj = jArray.getJSONObject(i);
			        String imgURL = jobj.getString("image_url");
			        
			        String saveFileName = "test" + i + ".jpg";
			        
			        File saveFile = new File(savePath + saveFileName);
			        
			        saveImage(imgURL, saveFile, fileFormat);
			        System.out.println("image_url(" + i + "): " + imgURL);
			        System.out.println();
			    }
			    JOptionPane.showMessageDialog(this, "사진 저장이 완료되었습니다.");
			    
			} catch (Exception e1) {
				e1.printStackTrace();
				JOptionPane.showMessageDialog(this, "사진 저장이 실패하였습니다.");
				System.out.println("--확인용-- T_Test.java에서 오류 발생");
				System.out.println(e1);
			}
		}
		}
	}
	
	public static void saveImage(String imageUrl, File saveFile, String fileFormat) {
		URL url = null;
		BufferedImage bi = null;
		
		try {
			url = new URL(imageUrl); // 다운로드 할 이미지 URL
			bi = ImageIO.read(url);
			ImageIO.write(bi, fileFormat, saveFile); // 저장할 파일 형식, 저장할 파일명
			
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
