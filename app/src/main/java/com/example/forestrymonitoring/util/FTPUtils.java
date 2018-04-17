package com.example.forestrymonitoring.util;
  
import android.os.NetworkOnMainThreadException;
import android.util.Log;

import com.example.forestrymonitoring.common.ChatConstant;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.SocketException;


/** 
 * 2013年09月20日21:38:04 
 *  
 * 用于Android和FTP服务器进行交互的工具类 
 *  
 * @author xiaoyaomeng 
 * 
 */  
public class FTPUtils {  
    private FTPClient ftpClient = null;
    private static FTPUtils ftpUtilsInstance = null;  
    private String FTPUrl;  
    private int FTPPort;  
    private String UserName;  
    private String UserPassword;  
      
    private FTPUtils()  
    {  
        ftpClient = new FTPClient();  
    }  
    /* 
     * 得到类对象实例（因为只能有一个这样的类对象，所以用单例模式） 
     */  
    public  static FTPUtils getInstance() {  
        if (ftpUtilsInstance == null)  
        {  
            ftpUtilsInstance = new FTPUtils();  
        }  
        return ftpUtilsInstance;  
    }  
      
    /** 
     * 设置FTP服务器 
     * @param FTPUrl   FTP服务器ip地址 
     * @param FTPPort   FTP服务器端口号 
     * @param UserName    登陆FTP服务器的账号 
     * @param UserPassword    登陆FTP服务器的密码 
     * @return 
     */  
    public boolean initFTPSetting(String FTPUrl, int FTPPort, String UserName, String UserPassword)  
    {     
        this.FTPUrl = FTPUrl;  
        this.FTPPort = FTPPort;  
        this.UserName = UserName;  
        this.UserPassword = UserPassword;  
          
        int reply;  
          
        try {  
            //1.要连接的FTP服务器Url,Port  
            ftpClient.connect(FTPUrl, FTPPort);  
              
            //2.登陆FTP服务器  
            ftpClient.login(UserName, UserPassword);  
              
            //3.看返回的值是不是230，如果是，表示登陆成功  
            reply = ftpClient.getReplyCode();
            Log.d("info","connect ftp success");
          
            if (!FTPReply.isPositiveCompletion(reply))  
            {  
                //断开  
                ftpClient.disconnect();  
                return false;  
            }  
              
            return true;  
              
        } catch (NetworkOnMainThreadException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return false;
        }
        catch (SocketException e) {
            // TODO Auto-generated catch block  
            e.printStackTrace();  
            return false;
        } catch (IOException e) {  
            // TODO Auto-generated catch block  
            e.printStackTrace();  
            return false;  
        }  
    }  
      
    /** 
     * 上传文件 
     * @param FilePath    要上传文件所在SDCard的路径 
     * @param FileName    要上传的文件的文件名(如：Sim唯一标识码) 
     * @return    true为成功，false为失败 
     */  
    public boolean uploadFile(String FilePath, String FileName) {  
          
        if (!ftpClient.isConnected())  
        {  
            if (!initFTPSetting(FTPUrl,  FTPPort,  UserName,  UserPassword))  
            {  
                return false;  
            }  
        }  
          
        try {  
              
            //设置存储路径  
            ftpClient.makeDirectory(ChatConstant.ftpDatePath);
            ftpClient.changeWorkingDirectory(ChatConstant.ftpDatePath);
              
            //设置上传文件需要的一些基本信息  
            ftpClient.setBufferSize(1024);    
            ftpClient.setControlEncoding("UTF-8");   
            ftpClient.enterLocalPassiveMode();     
            ftpClient.setFileType(FTP.BINARY_FILE_TYPE);

            /* 上传文件时若此文件已存在则先删除再上传 */
            // 转到指定目录
            ftpClient.changeWorkingDirectory(ChatConstant.ftpDatePath);
            // 列出该目录下所有文件
            FTPFile[] files = ftpClient.listFiles();
            // 遍历所有文件，找到指定的文件
            for (FTPFile file : files) {
                if (file.getName().equals(FileName)) {
                    ftpClient.deleteFile(ChatConstant.ftpDatePath+file.getName());
                }
            }

            //文件上传
            FileInputStream fileInputStream = new FileInputStream(FilePath);  
            ftpClient.storeFile(FileName, fileInputStream);  
              
            //关闭文件流  
            fileInputStream.close();  
              
            //退出登陆FTP，关闭ftpCLient的连接  
            ftpClient.logout();  
            ftpClient.disconnect();  
              
        } catch (IOException e) {  
            // TODO Auto-generated catch block  
            e.printStackTrace();  
            return false;  
        }  
        return true;  
    }  
      
    /** 
     * 下载文件 
     * @param FilePath  要存放的文件的路径 
     * @param FileName   远程FTP服务器上的那个文件的名字
     * @param FileFtpDir    下载文件在ftp服务器上的地址
     * @return   true为成功，false为失败 
     */  
    public boolean downLoadFile(String FilePath, String FileName,String FileFtpDir) {
          
        if (!ftpClient.isConnected())  
        {  
            if (!initFTPSetting(FTPUrl,  FTPPort,  UserName,  UserPassword))  
            {  
                return false;  
            }  
        }
        try {
            //设置文件类型为二进制否则可能导致乱码文件无法打开
            ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
            ftpClient.setFileTransferMode(FTP.BINARY_FILE_TYPE);
            // 转到指定下载目录  
            ftpClient.changeWorkingDirectory(FileFtpDir);
            // 列出该目录下所有文件  
            FTPFile[] files = ftpClient.listFiles();
            if(files.length == 0){
                return false;
            }
            // 遍历所有文件，找到指定的文件  
            for (FTPFile file : files) {  
                if (file.getName().equals(FileName)) {  
                    //根据绝对路径初始化文件  
                    File localFile = new File(FilePath);
                    // 输出流  
                    OutputStream outputStream = new FileOutputStream(localFile);
                    // 下载文件  
                    ftpClient.retrieveFile(file.getName(), outputStream);
                    System.out.println("download success");
                    //关闭流  
                    outputStream.close();  
                }  
            }
            //退出登陆FTP，关闭ftpCLient的连接  
            ftpClient.logout();  
            ftpClient.disconnect();
              
        } catch (IOException e) {  
            // TODO Auto-generated catch block  
            e.printStackTrace();
            Log.d("FTPDownload",ChatConstant.dateName+"  download fail");
            return false;
        }
          
        return true;  
    }  
      
}