package com.example.forestrymonitoring.util;

import android.os.Environment;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.Calendar;

/**
 * @author 吐槽星人
 * 文件操作工具类
 */
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
	 *  在sd卡上创建文件
	 * @param fileName 要创建的文件名
	 * @return 创建的文件对象
	 * @throws IOException 读取异常
	 */
	public static File creatSDFile(String fileName) throws IOException{
		File file = new File(fileName);
		file.createNewFile();
		return file;
	}

	/**
	 *  在sd卡上创建目录
	 * @param dirName 创建的目录名
	 * @return 目录File对象
	 */
	public static File creatSDDir(String dirName) {
		File dir = new File(dirName);
		dir.mkdir();
		return dir;
	}

	/**
	 *  判断sd卡上的文件是否存在
	 * @param fileName 要判断的文件名
	 * @return 判断标志 存在：true  不存在：false
	 */
	public static boolean isFileExist(String fileName) {
		File file = new File(fileName);
		return file.exists();
	}

	/**
	 * 将第一个InputStream里面的数据写入到SD卡中
	 * @param fileName 写入文件名
	 * @param inputStream 输入流
	 * @return 操作成功的File对象
	 */
	public static File write2SDFromInput(String fileName,InputStream inputStream) {
		File file = null;
		OutputStream output = null;
		try{
			file = creatSDFile(fileName);
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

	/**
	 * 读文件 转化为字符串
	 */
	public static String readFileToString(String FilePath){
		FileInputStream fis = null;
		try {
			fis = new FileInputStream(FilePath);
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
			System.out.println("FileNotFoundException");
		}
		BufferedReader reader = new BufferedReader(new InputStreamReader(fis));
		//new一个StringBuffer用于字符串拼接
		StringBuffer sb = new StringBuffer();
		String line = null;
		try {
			//当输入流内容读取完毕时
			while ((line = reader.readLine()) != null) {
				sb.append(line + "\n");
			}
			//关闭流数据 节约内存消耗
			fis.close();
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return sb.toString();
	}

	/**
	 * 删除文件
	 * @param FileName  路径+文件名
	 * @return 删除标志
	 */
	public static boolean deleteFile(String FileName) {
		File file = new File(FileName);
		if(file.exists()){
			file.delete();
			System.out.println(FileName+"删除成功");
			return true;
		}
		else{
			System.out.println(FileName+"删除失败");
			return false;
		}
	}

	/**
	 * 将指定字符串写入文件
	 * @param str 要写入的字符串
	 * @param filePath 要写入的文件路径
	 */
	public static void wirteFile(String filePath,String str){
		try { // 防止文件建立或读取失败，用catch捕捉错误并打印，也可以throw
			String strs = str;
            /* 写入Txt文件 */
			File writename = new File(filePath); // 相对路径，如果没有则要建立一个新的txt文件
			writename.createNewFile(); // 创建新文件
			BufferedWriter out = new BufferedWriter(new FileWriter(writename));
			out.write(strs); // 写文件
			out.flush(); // 把缓存区内容压入文件
			out.close(); // 最后记得关闭文件

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 将指定时间字符串写入文件
	 * @param str 要写入的字符串
	 * @param filePath 要写入的文件路径
	 */
	public static void wirteTimeToFile(String filePath,String str){
		try { // 防止文件建立或读取失败，用catch捕捉错误并打印，也可以throw
			String strs = getSysytemTime()+str;
            /* 写入Txt文件 */
			File writename = new File(filePath); // 相对路径，如果没有则要建立一个新的txt文件
			writename.createNewFile(); // 创建新文件
			BufferedWriter out = new BufferedWriter(new FileWriter(writename));
			out.write(strs); // 写文件
			out.flush(); // 把缓存区内容压入文件
			out.close(); // 最后记得关闭文件

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static String getSysytemTime(){
		Calendar calendar = Calendar.getInstance();
		int year = calendar.get(Calendar.YEAR);
		int month = calendar.get(Calendar.MONTH);
		int day = calendar.get(Calendar.DAY_OF_MONTH);
		int hour = calendar.get(Calendar.HOUR_OF_DAY);
		int minute = calendar.get(Calendar.MINUTE);
		int second = calendar.get(Calendar.SECOND);
		String yearStr;
		String monthStr;
		String hourStr;
		String dayStr;
		String minuteStr;
		String secondStr;
		if(year<10)
			yearStr = "0"+Integer.toString(year);
		else
			yearStr = Integer.toString(year);
		if(month<10)
			monthStr = "0"+Integer.toString(month);
		else
			monthStr = Integer.toString(month);
		if(day<10)
			dayStr = "0"+Integer.toString(day);
		else
			dayStr = Integer.toString(day);
		if(hour<10)
			hourStr = "0"+Integer.toString(hour);
		else
			hourStr = Integer.toString(hour);
		if(minute<10)
			minuteStr = "0"+Integer.toString(minute);
		else
			minuteStr = Integer.toString(minute);
		if(second<10)
			secondStr = "0"+Integer.toString(second);
		else
			secondStr = Integer.toString(second);
		String str = yearStr+monthStr+dayStr+hourStr+":"+minuteStr+":"+secondStr+"\r\n";
		return str;
	}
}
