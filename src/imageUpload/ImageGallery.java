package imageUpload;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class ImageGallery extends JFrame {

	private ImageIcon[] images = { new ImageIcon("images/apple.png"), new ImageIcon("images/banana.png"),
			new ImageIcon("images/kiwi.png"), new ImageIcon("images/mango.png") };
    //이미지를 images 배열에 저장

	private int index = 0;
	private JLabel imageLabel = new JLabel(images[index]);
    //레이블의 초기 이미지는 images 배열의 첫번째 요소로 설정

	private JButton left = new JButton(new ImageIcon("images/leftArrow.png"));
	private JButton right = new JButton(new ImageIcon("images/rightArrow.png"));
    //왼쪽 화살표, 오른쪽 화살표 버튼

	public ImageGallery() {
		setTitle("이미지 갤러리 만들기");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Container c = getContentPane();
		c.setLayout(new BorderLayout());

		c.add(imageLabel, BorderLayout.CENTER); //이미지 라벨은 CENTER에
		c.add(new MenuPanel(), BorderLayout.SOUTH); //메뉴는 SOUTH에 부착

		setSize(500, 500);
		setVisible(true);
	}

	class MenuPanel extends JPanel { //JPanel을 상속받은 MenuPanel
		public MenuPanel() { //생성자
			setBackground(Color.GRAY);
			setLayout(new FlowLayout());

			add(left); //왼쪽 화살표 버튼 추가
			add(right); //오른쪽 화살표 버튼 추가

			left.addActionListener(new MyActionListener());
			right.addActionListener(new MyActionListener());
            //두 버튼에 Action 리스너 추가
		}
	}

	class MyActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			JButton jb = (JButton) e.getSource(); //클릭된 소스 알아내기
			if (jb == left) { //왼쪽 화살표 버튼이 클릭된 경우
				if (index == 0)
					index = images.length - 1; //index가 0이었다면 인덱스 최댓값으로 설정
				else
					index -= 1; //나머지 경우는 인덱스 1 감소
			} else { //오른쪽 화살표 버튼이 클릭된 경우
				if (index == images.length - 1)
					index = 0; //index가 최댓값이었다면 0으로 설정
				else
					index += 1; //나머지 경우는 인덱스 1 증가
			}
			imageLabel.setIcon(images[index]); //index 수정 후 해당 인덱스의 이미지로 라벨 변경
		}
	}

	public static void main(String[] args) {
		new ImageGallery();
	}
}
