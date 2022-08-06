package imageUploadGUI;

import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.List;

//	[현재 FileViewer클래스에서는 getImgFileList(String path), getImgFileList(getPath(e))만 사용 중임!!! / 나머지는 사용X]
public class Util {

	/**
	 * 테스트를 위한 메인메소드.
	 * 
	 * public static void main(String[] args) throws IOException {
	 * 
	 * //util u = new util();
	 * 
	 * //이미지목록을 가져올 폴더위치를 지정. (절대경로, 상대경로 모두 가능) List<File> list =
	 * getImgFileList("C:\\aImageTest");
	 * 
	 * for(File f : list) { System.out.println(f.getName()); //파일 이름 출력
	 * //System.out.println(f.getPath()); //경로 출력
	 * //System.out.println(f.getAbsolutePath()); //절대 경로 출력
	 * //System.out.println(f.getCanonicalPath()); //정규 경로 출력 } }
	 */

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

	// 확장자를 제외한 파일 이름만 출력
	public String getFileNameNoExt(String filepath) {
		String fileName = filepath.substring(0, filepath.lastIndexOf("."));
		return fileName;
	}

	// 파일 확장자만 출력
	public String getFileExt(String filepath) {
		String ext = filepath.substring(filepath.lastIndexOf(".") + 1);
		return ext;
	}

	// 파일패스에서 이미지 상대경로 출력
	// 절대경로에서 이미지폴더(images)를 중심으로 상대경로를 반환.
	// 패스 : 절대결로/images/1-A/1-A_0.jpg
	// => 1-A/1-A_0.jpg
	public String getImgSrc(File target) {
		String url = target.getPath().substring(target.getPath().lastIndexOf("images"));
		return url;
	}

	// 이미지를 포함하고있는 폴더의 이름 얻기. ( 개인 용도로 작성한 메서드 이므로 무시해도 무방 )
	public String getImgDirName(File target) {
		String url = getImgSrc(target);

		// System.out.println(url);
		// System.out.println(url.indexOf("\\")+1+"/"+url.lastIndexOf("\\"));

		int comp = url.lastIndexOf("\\") - (url.indexOf("\\") + 1);
		// System.out.println(comp);
		String dirName = "";
		if (comp < 0) {
			dirName = "이미지";
		} else {
			dirName = url.substring(url.indexOf("\\") + 1, url.lastIndexOf("\\"));
		}
		System.out.println("폴더명:" + dirName);

		return dirName;
	}
}