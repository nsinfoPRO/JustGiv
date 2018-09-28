package give.util;

public class FileUtil {
	public static final String FILE_EXT_PNG  = "png";
	public static final String FILE_EXT_JPG  = "jpg";
	public static final String FILE_EXT_JPEG = "jpeg";
	
	/**
	 *  Get file extension from the path.
	 * 
	 * @param path
	 * @return
	 */
	public static final String getFileExt(String path) {
		String strExt = "";
		
		int index = -1;
		String folders[] = path.split("//");
		if(folders.length > 0) {
			index = folders[folders.length - 1].lastIndexOf('.');
		}
		
		if(index > -1) {
			strExt = folders[folders.length - 1].substring(index + 1);
		}
		
		return strExt;
	}
	
	public static final boolean isPNG(String path) {
		String strExt = getFileExt(path);
		
		return (strExt.compareToIgnoreCase(FileUtil.FILE_EXT_PNG) == 0);
	}

	public static final boolean isJPG(String path) {
		String strExt = getFileExt(path);

		return (strExt.compareToIgnoreCase(FileUtil.FILE_EXT_JPG) == 0) || (strExt.compareToIgnoreCase(FileUtil.FILE_EXT_JPEG) == 0);
	}
}
