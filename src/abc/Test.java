package abc;

import java.awt.*;
import java.io.*;
import java.awt.event.*;
import java.util.*;
import java.util.List;

import javax.swing.*;
import javax.swing.event.*;
import javax.swing.tree.*;

class Test extends JFrame implements TreeWillExpandListener, MouseListener, TreeSelectionListener {

	private BorderLayout bl = new BorderLayout();
	private JSplitPane splitpane = new JSplitPane();
	private DefaultMutableTreeNode root = new DefaultMutableTreeNode("My Computer");
	private JTree jtree_dir = new JTree(root);
	private JScrollPane scrollpane_tree = new JScrollPane(jtree_dir);

//		private Vector f_header = new Vector();
//		private Vector f_data = new Vector();
//		private JTable jtable_file;
//		private JScrollPane scrollpane_table;	

//	private TreePath curr_tp;
	private String UUU;
	private JPanel imgPanel;
	private String DDD;
	private JScrollPane treePanel;

	public Test(String title) {
		super(title);
	}

	//// * (1) ImageCollection 클래스에서 확인 버튼 누를 시 실행
	public void startup(int width, int height) {

		init(); // (2)로 이동 : 프레임 초기 설정을 하는 메서드
		start(); // (3)로 이동 :

		this.setSize(width, height);
		//// * 프레임 크기를 지정하는 코드라 필요 없을 듯
////*		Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
////*		Dimension frm = this.getSize();
//		int xpos = (int)(screen.getWidth() / 2 - frm.getWidth() / 2);
//		int ypos = (int)(screen.getHeight() / 2 - frm.getHeight() / 2);
		this.setLocation(600, 300);
		this.setResizable(true);
		this.setVisible(true);
	}

	//// * (2) startup() 메서드에서 init() 실행 - 프레임 초기 설정 메서드
	public void init() {
		//// * (2-1) 컨테이너 생성
		Container con = this.getContentPane();
		con.setLayout(bl);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

//		f_header.add("File Name");
//		f_header.add("File Size");
//		f_header.add("Last Update");

		// 패널 우측과 좌측을 나눔
		splitpane = new JSplitPane();
		splitpane.setResizeWeight(1);
		splitpane.setDividerLocation(150);

		//// * 아마 우측 패널?
		imgPanel = new JPanel();
		imgPanel.setBackground(Color.BLUE);
		//// add(imgPanel);

		//// * 아마 좌측 패널(트리 코드)
		treePanel = new JScrollPane(jtree_dir);
		File[] dir = File.listRoots(); //// * listRoots() : 하드디스크의 루트 경로를 반환한다. /드라이브를 표시(c:, d:등)

		//// * DefaultMutableTreeNode : 비어있는 트리 노드를 생성(트리의 데이터를 표현하기 위해서 사용되는 노드 생성자)
		DefaultMutableTreeNode temp; // for문을 이용하여 하드디스크 경로를 트리노드로 표현

		//// * 트리에 존재하는 노드와 '없음'노드를 표현하는 for문
		for (int i = 0; i < dir.length; ++i) {
			//// DefaultMutableTreeNode drive = new
			//// DefaultMutableTreeNode(dir[i].toString().trim());
			//// drive.add(new DefaultMutableTreeNode(null));
			//// root.add(drive);

			temp = new DefaultMutableTreeNode(dir[i].getPath());
			temp.add(new DefaultMutableTreeNode("없음"));
			root.add(temp);
//			Vector vc = new Vector();
//			vc.add(dir[i].toString().trim());
//			vc.add("<DRIVE>");
//			vc.add("");
//			f_data.add(vc);

		}
		//// jtree_dir.expandRow(0); ////* 무슨 역활을 하는지 잘 모르겠당 ㅠㅠ
		//// scrollpane_tree = new JScrollPane(jtree_dir);
		//// splitpane.setLeftComponent(scrollpane_tree);
		//// con.add("Center", splitpane);
		// 좌측 패널
		splitpane.setLeftComponent(treePanel);
		splitpane.setRightComponent(imgPanel);
		splitpane.setRightComponent(imgPanel);
		add(splitpane);

	}

	public void start() {
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jtree_dir.addTreeWillExpandListener(this);
		jtree_dir.addTreeSelectionListener(this);
		//// jtree_dir.addMouseListener(this); //// 이 친구는 잘 모르겠당
	}

//  TreeWillExpandListener //// 아마도 트리에서 단일 경로를 식별하는 데 사용되는 이벤트 매서드
	public void treeWillCollapse(TreeExpansionEvent e) {
	} // Not Use!

	//// 트리를 확대할 때 자동으로 실행되는 이벤트 메서드 - 미안한데 이 부분 코드 다시 짤께 ㅠㅠ
	/*
	 * public void treeWillExpand(TreeExpansionEvent e) { if (e.getSource() ==
	 * jtree_dir) { TreePath selRow = e.getPath(); String ssRow =
	 * selRow.getLastPathComponent().toString().trim();
	 * 
	 * if (ssRow == null || ssRow.length() == 0) return; DefaultMutableTreeNode temp
	 * = (DefaultMutableTreeNode) e.getPath().getLastPathComponent();
	 * temp.removeAllChildren(); boolean direxist = false; try { File cdir = new
	 * File(ssRow); File[] dirlist = cdir.listFiles();
	 * 
	 * for (int i = 0; i < dirlist.length; i++) { if (dirlist[i].isHidden())
	 * continue; if (dirlist[i].isDirectory()) { DefaultMutableTreeNode d = new
	 * DefaultMutableTreeNode(dirlist[i].toString().trim()); d.add(new
	 * DefaultMutableTreeNode(null)); temp.add(d); direxist = true; } } if (direxist
	 * == false) { temp.add(new DefaultMutableTreeNode("No Items!"));
	 * jtree_dir.collapsePath(selRow); } } catch (NullPointerException ex) {
	 * temp.add(new DefaultMutableTreeNode("No Items!"));
	 * jtree_dir.collapsePath(selRow); } ; } }
	 */

//  MouseListener
	public void mouseClicked(MouseEvent e) {
	}

	public void mouseEntered(MouseEvent e) {
	}

	public void mouseExited(MouseEvent e) {
	}

	public void mousePressed(MouseEvent e) {
		if (e.getSource() == jtree_dir) {
			int selRow = jtree_dir.getRowForLocation(e.getX(), e.getY());
			TreePath selPath = jtree_dir.getPathForLocation(e.getX(), e.getY());
			System.out.println(selPath);
			UUU = selPath.getPathComponent(2).toString();
			DDD = selPath.getPathComponent(3).toString();
			System.out.println(UUU + "\\" + DDD);
//			
//			if(selRow == 0) {
//				f_data.clear();
//				File[] dir = File.listRoots();
//				for(int i = 0; i < dir.length; i++) {
//					Vector vc = new Vector();
//					vc.add(dir[i].toString().trim());
//					vc.add("<DRIVE>");
//					vc.add("");
//					f_data.add(vc);
//				}
//				jtable_file = new JTable(f_data, f_header);
//				scrollpane_table = new JScrollPane(jtable_file);
//				splitpane.setRightComponent(scrollpane_table);
//			} else if(selRow != -1) {
//				if(curr_tp != selPath) {
//					viewFiles(selPath);
//					curr_tp = selPath;
//				}
//			}
		}
	}

	public void mouseReleased(MouseEvent e) {
	}

//	public void viewFiles(TreePath selRow) {
//		f_data.clear();
//		String ssRow = selRow.getLastPathComponent().toString().trim();
//		if(ssRow == null || ssRow.length() == 0) return;
//		try {
//			File cdir = new File(ssRow);
//			File[] filelist = cdir.listFiles();
//			if(filelist.length == 0) {
//				Vector vc = new Vector();
//				vc.add("<No ");
//				vc.add(" Items ");
//				vc.add(" !>");
//				f_data.add(vc);
//				jtable_file = new JTable(f_data, f_header);
//				scrollpane_table = new JScrollPane(jtable_file);
//				splitpane.setRightComponent(scrollpane_table);
//				return;
//			}
//			for (int i = 0; i < filelist.length; i++) {
//				if(filelist[i].isHidden()) continue;
//				if(filelist[i].isDirectory()) {
//					Vector vc = new Vector();
//					vc.add(filelist[i].getName());
//					vc.add("<DIR>");
//					vc.add(new Date(filelist[i].lastModified()).toString());
//					f_data.add(vc);
//				}
//			}
//			for (int i = 0; i < filelist.length; i++) {
//				if(filelist[i].isHidden()) continue;
//				if(filelist[i].isFile()) {
//					Vector vc = new Vector();
//					vc.add(filelist[i].getName());
//					vc.add(filelist[i].length() + " Bytes");
//					vc.add(new Date(filelist[i].lastModified()).toString());
//					f_data.add(vc);
//				}
//			}
//			jtable_file = new JTable(f_data, f_header);
//			scrollpane_table = new JScrollPane(jtable_file);
//			splitpane.setRightComponent(scrollpane_table);
//		} catch (NullPointerException ex) {
//			Vector vc = new Vector();
//			vc.add("<No ");
//			vc.add(" Items ");
//			vc.add(" !>");
//			f_data.add(vc);
//			jtable_file = new JTable(f_data, f_header);
//			scrollpane_table = new JScrollPane(jtable_file);
//			splitpane.setRightComponent(scrollpane_table);
//		};

//	public static void main(String[] ar) {
//		Test jfec = new Test("이미지 확인하기");
//		jfec.startup(800,600);
//	}
//}

	//// *트리 관련 메서드 정리*
	//// 트리 선택 해제 시 이벤트 처리 메서드
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

	// 트리를 확대할 때 이벤트 메서드
	public void treeWillExpand(TreeExpansionEvent e) {
		if (((String) ((DefaultMutableTreeNode) e.getPath().getLastPathComponent()).getUserObject()).equals("내컴퓨터")) {
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
				// JOptionPane.showMessageDialog(this, "디스크 혹은 파일을 찾을수 없습니다.");
			}
		}
	}

	// 경로와 노드 이름이 변경될 때 사용하는 메서드
	public void valueChanged(TreeSelectionEvent e) {
		// 맨 처음 노드 초기 이름과 초기 경로 이름을 '내컴퓨터'로 지정 / 그게 아니라면 진짜 경로를 텍스트 필드와 우측 화면에 표시
		if (((String) ((DefaultMutableTreeNode) e.getPath().getLastPathComponent()).getUserObject()).equals("내컴퓨터")) {
		} else {
			try {
				// panel_Right.setBackground(Color.BLACK);

				// 이미지목록 가져올 폴더위치를 지정. (절대경로, 상대경로 모두 가능) (Util클래스 인스턴스 사용은 밑 줄이 유일함.)
				List<File> list = getImgFileList(getPath(e));

				for (File f : list) {
					System.out.println(f.getName()); // 이미지 파일 이름 출력
					System.out.println(f.getAbsolutePath()); // 이미지 파일 경로 출력
				}
			} catch (Exception ex) {
				// JOptionPane.showMessageDialog(this, "디스크 혹은 파일을 찾을 수 없습니다.");
			}
		}
	}

	/**
	 * 해당 경로의 이미지 파일 목록 반환
	 */
	public List<File> getImgFileList(String path) {

		return getImgFileList(new File(path));
	}

	/**
	 * 해당 경로의 이미지 파일 목록 반환
	 */
	public List<File> getImgFileList(File file) {

		List<File> resultList = new ArrayList<File>(); // 이미지 파일을 저장할 리스트 생성

		// 지정한 이미지폴더가 존재 할지 않을경우 빈 리스트 반환.
		// System.out.println("파일존재 여부: "+file.exists());
		if (!file.exists())
			return resultList;

		File[] list = file.listFiles(new FileFilter() { // 원하는 파일만 가져오기 위해 FileFilter정의

			String strImgExt = "jpg|jpeg|png|gif|bmp"; // 허용할 이미지타입

			@Override
			public boolean accept(File pathname) {

				// System.out.println(pathname);
				boolean chkResult = false;
				if (pathname.isFile()) {
					String ext = pathname.getName().substring(pathname.getName().lastIndexOf(".") + 1);
					// System.out.println("확장자:"+ext);
					chkResult = strImgExt.contains(ext.toLowerCase());
					// System.out.println(chkResult +" "+ext.toLowerCase());
				} else {
					chkResult = true;
				}
				return chkResult;
			}
		});

		for (File f : list) {
			if (f.isDirectory()) {
				// 폴더이면 이미지목록을 가져오는 현재메서드를 재귀호출
				resultList.addAll(getImgFileList(f));
			} else {
				// 폴더가 아니고 파일이면 리스트(resultList)에 추가
				resultList.add(f);
			}
		}
		return resultList;
	}

}
