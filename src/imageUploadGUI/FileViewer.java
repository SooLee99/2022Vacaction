package imageUploadGUI;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextField;
import javax.swing.JTree;
import javax.swing.event.TreeExpansionEvent;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.event.TreeWillExpandListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;

@SuppressWarnings("serial")
public class FileViewer extends JFrame implements TreeWillExpandListener, TreeSelectionListener, ActionListener {
	private JSplitPane panel_Main;
	private JTextField tf_PathText;
	private JPanel panel_North;
	private JLabel lbl_Path;
	private JScrollPane panel_Left;
	private JPanel panel_Right;
	private DefaultMutableTreeNode root = new DefaultMutableTreeNode("내 컴퓨터");
	private JTree tree;
	private JPanel panel_South;
	private JButton btn_download;
	private Util util = new Util();
	private JScrollPane spRight;

	public FileViewer() {
		init(); // 프레임 초기 설정 메서드
	}

	// 프레임 초기 설정 메서드
	void init() {
		setTitle("이미지 탐색기");

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(800, 600);
		setLocation(300, 100);
		setResizable(true);
		getContentPane().setLayout(new BorderLayout());

		// 상단 패널
		panel_North = new JPanel();

		lbl_Path = new JLabel("경  로  :  ");
		panel_North.add(lbl_Path);

		tf_PathText = new JTextField();
		tf_PathText.setPreferredSize(new Dimension(600, 20));
		panel_North.add(tf_PathText);

		// 패널 우측과 좌측을 나눔
		panel_Main = new JSplitPane();
		panel_Main.setResizeWeight(1);
		panel_Main.setDividerLocation(150);

		// 좌측 패널에 JTree 구현
		tree = new JTree(root);
		panel_Left = new JScrollPane(tree);

		File file = new File("");

		@SuppressWarnings("static-access")
		File list[] = file.listRoots(); // listRoots() : 하드디스크의 루트 경로를 반환한다. /드라이브를 표시(c:, d:등)

		// DefaultMutableTreeNode : 비어있는 트리 노드를 생성(트리의 데이터를 표현하기 위해서 사용되는 노드 생성자)
		DefaultMutableTreeNode temp;

		// 트리에서 노드와 '없음'노드를 표시
		for (int i = 0; i < list.length; ++i) {
			temp = new DefaultMutableTreeNode(list[i].getPath());
			temp.add(new DefaultMutableTreeNode("없음"));
			root.add(temp);
		}
		// 우측 패널 구현
		panel_Right = new JPanel(new GridLayout(0, 2));

		spRight = new JScrollPane(panel_Right, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		panel_Main.setLeftComponent(panel_Left);
		panel_Main.setRightComponent(spRight);

		// 하단 패널 구현
		panel_South = new JPanel();
		btn_download = new JButton("이미지 다운받기");
		btn_download.addActionListener(this);
		panel_South.add(btn_download);

		add(panel_North, "North");
		add(panel_Main);
		add(panel_South, "South");

		tree.addTreeWillExpandListener(this);
		tree.addTreeSelectionListener(this);
		setVisible(true);
	}

	// 메인 메서드
	public static void main(String args[]) {
		// 프레임에 상단 바에 이미지 아이콘으로 세팅
		JFrame.setDefaultLookAndFeelDecorated(true);
		new FileViewer();
	}

	// 트리 선택 해제 시 이벤트 처리 메서드
	String getPath(TreeExpansionEvent e) {
		// StringBuffer : String 클래스의 인스턴스(only Read),
		// But, StringBuffer 클래스의 인스턴스(Read & Change & Add)가능
		StringBuffer path = new StringBuffer();
		// TreePath : 트리의 노드에 대한 경로를 고유하게 식별하는 개체의 배열(노드 이름?)
		TreePath temp = e.getPath();
		Object list[] = temp.getPath();
		for (int i = 0; i < list.length; ++i) {
			if (i > 0) {
				// path라는 (StringBuffer)인스턴스에 경로 삽입
				path.append(((DefaultMutableTreeNode) list[i]).getUserObject() + "\\");
			}
		}
		// 삽입된 path를 한꺼번에 return
		return path.toString();
	}

	// 트리 선택 시 이벤트 처리 메서드
	String getPath(TreeSelectionEvent e) {
		// StringBuffer : String 클래스의 인스턴스(only Read), But StringBuffer 클래스의 인스턴스(Read &
		// Change & Add)가능
		StringBuffer path = new StringBuffer();
		// TreePath : 트리의 노드에 대한 경로를 고유하게 식별하는 개체의 배열(노드 이름?)
		TreePath temp = e.getPath();
		Object list[] = temp.getPath();
		for (int i = 0; i < list.length; ++i) {
			if (i > 0) {
				// path라는 (StringBuffer)인스턴스에 경로 삽입
				path.append(((DefaultMutableTreeNode) list[i]).getUserObject() + "\\");
			}
		}
		// 삽입된 path를 한꺼번에 return
		return path.toString();
	}

	// 트리에서 단일 경로를 식별하는 데 사용되는 이벤트 매서드
	public void treeWillCollapse(TreeExpansionEvent event) {
	}

	// 트리를 확대할 때 이벤트 메서드
	public void treeWillExpand(TreeExpansionEvent e) {
		if (((String) ((DefaultMutableTreeNode) e.getPath().getLastPathComponent()).getUserObject()).equals("내 컴퓨터")) {
		} else {
			try {
				DefaultMutableTreeNode parent = (DefaultMutableTreeNode) e.getPath().getLastPathComponent();
				File tempFile = new File(getPath(e));
				File list[] = tempFile.listFiles();
				DefaultMutableTreeNode tempChild;
				for (File temp : list) {
					if (temp.isDirectory() && !temp.isHidden()) {
						tempChild = new DefaultMutableTreeNode(temp.getName());
						if (true) {
							File inFile = new File(getPath(e) + temp.getName() + "\\");
							File inFileList[] = inFile.listFiles();
							for (File inTemp : inFileList) {
								if (inTemp.isDirectory() && !inTemp.isHidden()) {
									tempChild.add(new DefaultMutableTreeNode("없음"));
									break;
								}
							}
						}
						parent.add(tempChild);
					}
				}
				parent.remove(0);
			} catch (Exception ex) {
				// JOptionPane.showMessageDialog(this, "디스크 혹은 파일을 찾을 수 없습니다.");
			}
		}
	}

	// 경로와 노드 이름이 변경될 때 사용하는 메서드
	public void valueChanged(TreeSelectionEvent e) {
		// 맨 처음 노드 초기 이름과 초기 경로 이름을 '내컴퓨터'로 지정 / 그게 아니라면 진짜 경로를 텍스트 필드와 우측 화면에 표시
		if (((String) ((DefaultMutableTreeNode) e.getPath().getLastPathComponent()).getUserObject()).equals("내 컴퓨터")) {
			tf_PathText.setText("내 컴퓨터");
		} else {
			try {
				tf_PathText.setText(getPath(e)); // 상단 주소
				panel_Right = new JPanel(new GridLayout(0, 2)); // 우측 패널 초기화
				//panel_Right.setBackground(Color.BLACK);

				// 이미지목록 가져올 폴더위치를 지정. (절대경로, 상대경로 모두 가능) (Util클래스 인스턴스 사용은 밑 줄이 유일함.)
				List<File> list = util.getImgFileList(getPath(e));

				JLabel[] imgLabel = new JLabel[list.size()];
				ImageIcon[] icon = new ImageIcon[list.size()];

				int i = 0;
				for (File f : list) {
					System.out.println(f.getName()); // 이미지 파일 이름 출력
					System.out.println(f.getAbsolutePath()); // 이미지 파일 경로 출력

					// 이미지 레이블 출력
					icon[i] = new ImageIcon(f.getAbsolutePath());
					// 이미지 짤리지 않게 크기 변경(이미지레이블 -> 이미지 -> (이미지)크기변경 -> (크기 변경된)이미지레이블)
					Image img = icon[i].getImage(); // ImageIcon을 Image로 변환.
					Image updateImg = img.getScaledInstance(170, 170, java.awt.Image.SCALE_SMOOTH);

					ImageIcon updateImageIcon = new ImageIcon(updateImg); // Image로 ImageIcon 생성
					imgLabel[i] = new JLabel(updateImageIcon);
					imgLabel[i].setBorder(BorderFactory.createEmptyBorder(10 , 0 , 0 , 0));
					panel_Right.add(imgLabel[i]);
					i++;
					// System.out.println(f.getPath()); //경로 출력
					// System.out.println(f.getAbsolutePath()); //절대 경로 출력
					// System.out.println(f.getCanonicalPath()); //정규 경로 출력
				}
				spRight = new JScrollPane(panel_Right, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
						JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
				spRight.setPreferredSize(new Dimension(400, 200));
				panel_Main.setRightComponent(spRight);
			} catch (Exception ex) {
				// JOptionPane.showMessageDialog(this, "디스크 혹은 파일을 찾을 수 없습니다.");
			}
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		Object obj = e.getSource();
		if (obj == btn_download) {
			@SuppressWarnings("unused")
			ImageUploadGUI iugui = new ImageUploadGUI();
		}

	}
}
