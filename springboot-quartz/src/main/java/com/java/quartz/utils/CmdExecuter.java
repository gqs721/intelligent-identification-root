package com.java.quartz.utils;
import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.List;

@Slf4j
public class CmdExecuter {

	public static String exec(List<String> cmd) {
		String converted_time = null;
		Process proc =null;
		try {
			ProcessBuilder builder = new ProcessBuilder();
			builder.command(cmd);
			builder.redirectErrorStream(true);
			proc = builder.start();

			dealStream(proc);
		} catch (IndexOutOfBoundsException ex) {
			converted_time = null;
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			try {
				proc.waitFor();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return converted_time;
	}

	/**
	 * 处理process输出流和错误流，防止进程阻塞
	 * 在process.waitFor();前调用
	 * @param process
	 */
	private static void dealStream(Process process) {
		if (process == null) {
			return;
		}
		// 处理InputStream的线程
		new Thread() {
			@Override
			public void run() {
				BufferedReader in = new BufferedReader(new InputStreamReader(process.getInputStream()));
				String line = null;
				try {
					while ((line = in.readLine()) != null) {
						log.info("output: " + line);
					}
				} catch (IOException e) {
					e.printStackTrace();
				} finally {
					try {
						in.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}.start();
		// 处理ErrorStream的线程
		new Thread() {
			@Override
			public void run() {
				BufferedReader err = new BufferedReader(new InputStreamReader(process.getErrorStream()));
				String line = null;
				try {
					while ((line = err.readLine()) != null) {
						log.info("err: " + line);
					}
				} catch (IOException e) {
					e.printStackTrace();
				} finally {
					try {
						err.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}.start();
	}
}
