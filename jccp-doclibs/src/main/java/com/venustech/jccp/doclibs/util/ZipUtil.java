package com.venustech.jccp.doclibs.util;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;

import org.apache.log4j.Logger;
import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipFile;

import com.jfinal.kit.StrKit;

/**
 * 解压Zip文件工具类
 * 
 */
public class ZipUtil {

	private static final Logger LOGGER = Logger.getLogger(ZipUtil.class);

	private static final int buffer = 2048;

	/**
	 * 解压Zip文件
	 * 
	 * @param zipFilePath
	 *            ： zip file Path
	 * @param savePath
	 *            : the save path
	 */
	public static boolean unZip(String zipFilePath, String savePath) {
		if (StrKit.isBlank(zipFilePath)
				|| !zipFilePath.toLowerCase().endsWith(".zip")
				|| !new File(zipFilePath).exists()) {
			LOGGER.error("Not zip file!");
			return false;
		}
		if (StrKit.isBlank(savePath)) {
			LOGGER.error("Save path cannot be empty.");
			return false;
		} else {
			File dir = new File(savePath);
			if (!dir.exists()) {
				dir.mkdirs();
			}
		}

		int count = -1;

		File file = null;
		InputStream is = null;
		FileOutputStream fos = null;
		BufferedOutputStream bos = null;

		ZipFile zipFile = null;
		try {
			zipFile = new ZipFile(zipFilePath, "gbk"); // 解决中文乱码问题
			Enumeration<?> entries = zipFile.getEntries();

			while (entries.hasMoreElements()) {
				byte buf[] = new byte[buffer];

				ZipEntry entry = (ZipEntry) entries.nextElement();

				String filename = entry.getName();
				boolean ismkdir = false;
				if (filename.lastIndexOf("/") != -1) { // 检查此文件是否带有文件夹
					ismkdir = true;
				}
				filename = savePath + File.separator + filename;

				if (entry.isDirectory()) { // 如果是文件夹先创建
					file = new File(filename);
					file.mkdirs();
					continue;
				}
				file = new File(filename);
				if (!file.exists()) { // 如果是目录先创建
					if (ismkdir) {
						new File(filename.substring(0,
								filename.lastIndexOf("/"))).mkdirs(); // 目录先创建
					}
				}
				file.createNewFile(); // 创建文件

				is = zipFile.getInputStream(entry);
				fos = new FileOutputStream(file);
				bos = new BufferedOutputStream(fos, buffer);

				while ((count = is.read(buf)) > -1) {
					bos.write(buf, 0, count);
				}
				bos.flush();
				bos.close();
				fos.close();

				is.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
			LOGGER.error(e.getMessage(), e);
			return false;
		} finally {
			try {
				if (bos != null)
					bos.close();
				if (fos != null)
					fos.close();
				if (is != null)
					is.close();
				if (zipFile != null)
					zipFile.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return true;
	}
}