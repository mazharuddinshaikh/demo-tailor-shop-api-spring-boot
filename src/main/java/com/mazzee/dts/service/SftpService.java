//package com.mazzee.dts.service;
//
//import org.springframework.stereotype.Service;
//
//import com.jcraft.jsch.Channel;
//import com.jcraft.jsch.ChannelSftp;
//import com.jcraft.jsch.JSch;
//import com.jcraft.jsch.JSchException;
//import com.jcraft.jsch.Session;
//import com.jcraft.jsch.SftpException;
//
//@Service
//public class SftpService {
//
//	public void transferFileToServer(String localfilePath) {
//		String remotePath = "dts-images/images/upload.jpg";
//		String hostName = "127.0.0.1";
//		String userName = "";
//		String password = "";
//		int remotePort = 22;
//		Session jschSession = null;
//
//		try {
//			JSch jsch = new JSch();
//			jschSession = jsch.getSession(userName, hostName, remotePort);
//			jschSession.setPassword(password);
//			jschSession.connect(20000);
//			Channel channel = jschSession.openChannel("sftp");
//			channel.connect(20000);
//			ChannelSftp sftpChannel = (ChannelSftp) channel;
//			sftpChannel.put(localfilePath, remotePath);
//			sftpChannel.exit();
//		} catch (JSchException | SftpException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//
//	}
//}
