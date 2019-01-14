/**

 * @项目名称: networking-audit-dr
 * @文件名称: LocaLFtpServerUtils.java
 * @日期: 2018年6月14日
 * @版权: 2018 河南中审科技有限公司 Inc. All rights reserved.
 * @开发公司或单位：河南中审科技有限公司研发交付中心
 */
package com.baihy.utils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.ftpserver.FtpServer;
import org.apache.ftpserver.FtpServerFactory;
import org.apache.ftpserver.ftplet.Authority;
import org.apache.ftpserver.ftplet.FtpException;
import org.apache.ftpserver.listener.ListenerFactory;
import org.apache.ftpserver.usermanager.impl.BaseUser;
import org.apache.ftpserver.usermanager.impl.WritePermission;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 类说明：
 * 
 * @author: 柏华洋
 * @date: 2018年6月14日
 */
public class LocaLFtpServerUtils {

	private static final Logger LOGGER = LoggerFactory.getLogger(LocaLFtpServerUtils.class);

	private LocaLFtpServerUtils() {
	}

	public static void startFtp() {
		String ftpIp = "192.168.0.101";
		Integer ftpPort = 2222;
		String ftpUsername = "ftp";
		String ftpPassword = "ftp";
		String ftpDir = "F:\\ftpPath";
		createFtpServerAndStart(ftpIp, ftpPort, ftpUsername, ftpPassword, ftpDir);
	}

	/**
	 * 创建并启动本地ftp服务器
	 */
	private static void createFtpServerAndStart(String ftpIp, Integer ftpPort, String ftpUsername, String ftpPassword, String homeDirectory) {
		LOGGER.info("FTP服务器信息：fptIp:{}, ftpPort:{}, ftpUsername:{}, ftpPassword:{}, ftpDir:{}", ftpIp, ftpPort, ftpUsername, ftpPassword, homeDirectory);
		if (!StringUtils.isEmpty(ftpUsername) && !StringUtils.isEmpty(ftpPassword) && ftpPort != null && !StringUtils.isEmpty(homeDirectory)) {
			File homeFile = new File(homeDirectory);
			if (!homeFile.exists()) {
				homeFile.mkdirs();
			}
			ListenerFactory factory = new ListenerFactory();
			factory.setPort(ftpPort);
			factory.setServerAddress(ftpIp);
			BaseUser user = new BaseUser();
			user.setName(ftpUsername);
			user.setPassword(ftpPassword);
			user.setHomeDirectory(homeDirectory);
			List<Authority> authorities = new ArrayList<>();
			authorities.add(new WritePermission());
			user.setAuthorities(authorities);
			try {
				FtpServer server = FtpServerUtils.getFtpServer();
				if (server != null && !server.isStopped()) {
					server.stop();
					LOGGER.info("本地FTP服务器成功停止" + server);
				}
				FtpServerFactory ftpServerFactory = new FtpServerFactory();
				ftpServerFactory.addListener("default", factory.createListener());
				ftpServerFactory.getUserManager().save(user);
				FtpServer newServer = ftpServerFactory.createServer();
				FtpServerUtils.setFtpServer(newServer);
				newServer.start();
				LOGGER.info("FTP服务器启动成功,ftp信息是：fptIp:{}, ftpPort:{}, ftpUsername:{}, ftpPassword:{}, ftpDir:{}", ftpIp, ftpPort, ftpUsername, ftpPassword, homeDirectory);
			} catch (FtpException e) {
				LOGGER.error("本地FTP服务器启动失败...", e);
			}
		} else {
			LOGGER.error("本地FTP服务器启动失败...");
		}
	}
}
