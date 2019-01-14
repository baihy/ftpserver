/**
 * @项目名称: networking-audit-dr
 * @文件名称: FtpFactoryUtils.java
 * @日期: 2018年6月26日
 * @版权: 2018 河南中审科技有限公司 Inc. All rights reserved.
 * @开发公司或单位：河南中审科技有限公司研发交付中心
 */
package com.baihy.utils;

import org.apache.ftpserver.FtpServer;

/**
 * 类说明：
 * 
 * @author: 柏华洋
 * @date: 2018年6月26日
 */
public class FtpServerUtils {

	private FtpServerUtils() {

	}

	private static FtpServer ftpServer;

	public static FtpServer getFtpServer() {
		return ftpServer;
	}

	public static void setFtpServer(FtpServer fs) {
		ftpServer = fs;
	}
}