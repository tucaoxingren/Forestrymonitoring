package com.example.forestrymonitoring.util;

import android.os.Environment;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class FileUtils {
	private String SDPATH;

	public String getSDPATH() {
		return SDPATH;
	}

	public void setSDPATH(String sDPATH) {
		SDPATH = sDPATH;
	}
	public FileUtils(){
		//得到当前外部存储设备的目录
		SDPATH = Environment.getExternalStorageDirectory()+"/";
	}
	/**
	 * 在sd卡上创建文件
	 */
	public File creatSDFile(String fileName) throws IOException{
		File file = new File(SDPATH+fileName);
		file.createNewFile();
		return file;
	}
	public File creatSDDir(String dirName) {
		File dir = new File(SDPATH+dirName);
		dir.mkdir();
		return dir;
	}
	/**
	 * 判断sd卡上的文件是否存在
	 */
	public boolean isFileExist(String fileName) {
		File file = new File(SDPATH+fileName);
		return file.exists();
	}
	/**
	 * 将第一个InputStream里面的数据写入到SD卡中
	 */
	public File write2SDFromInput(String path,String fileName,InputStream inputStream) {
		File file = null;
		OutputStream output = null;
		try{
			creatSDFile(path);
			file = creatSDFile(path+fileName);
			output = new FileOutputStream(file);
			byte buffer [] = new byte[4*1024];
			while((inputStream.read(buffer)) !=-1){
				output.write(buffer);
			}
			output.flush();
		} catch(Exception e){
			e.printStackTrace();
		} finally{
			try{
				output.close();
			} catch(Exception e){
				e.printStackTrace();
			}
		}
		return file;
	}

}
