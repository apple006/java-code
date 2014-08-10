package com.xdc.basic.api.socket.udp;

import java.io.File;
import java.io.FileInputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

import org.apache.commons.io.IOUtils;

import com.xdc.basic.skills.GetPath;

class FortuneServer extends Thread
{
	DatagramSocket	ServerSocket;

	public FortuneServer()
	{
		super("FortuneServer");
		try
		{
			ServerSocket = new DatagramSocket(1114);
			System.out.println("FortuneServer up and running...");
		}
		catch (SocketException e)
		{
			System.err.println("Exception: couldn't create datagram sockter");
			System.exit(1);
		}
	}

	public static void main(String args[])
	{
		FortuneServer server = new FortuneServer();
		server.start();
	}

	public void run()
	{
		String curPath = GetPath.getRelativePath();

		FileInputStream inStream = null;
		while (true)
		{
			try
			{
				// 创建缓冲区
				byte[] data = new byte[256];
				// 创建接收数据包
				DatagramPacket rPacket = new DatagramPacket(data, data.length);

				System.out.println("等待客户端连接...");
				// 等待接收数据包
				ServerSocket.receive(rPacket);
				System.out.println("已有客户端发来请求: " + rPacket.getAddress().getHostAddress() + ":" + rPacket.getPort());

				// 读取待发送的内容
				inStream = new FileInputStream(new File(curPath + "Fortunes.txt"));
				if (inStream.read(data) <= 0)
				{
					System.err.println("Error: couldn't read fortunes");
				}

				// 创建发送数据包
				DatagramPacket sPacket = new DatagramPacket(data, data.length, rPacket.getAddress(), rPacket.getPort());
				// 发送报文
				ServerSocket.send(sPacket);
			}
			catch (Exception e)
			{
				System.err.println("Exception: " + e);
				e.printStackTrace();
			}
			finally
			{
				// 大师鸟悄的关闭输入流
				IOUtils.closeQuietly(inStream);
			}
		}
	}
}
