package com.innominds.itaf.driverinit;

import java.io.File;
import java.io.IOException;

import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecuteResultHandler;
import org.apache.commons.exec.DefaultExecutor;
import org.apache.commons.exec.ExecuteException;
import org.testng.Assert;

import com.innominds.itaf.frameworkengine.Constants;
import com.innominds.itaf.frameworkengine.WaitForPage;

import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;

/**
 * The Class AppiumServerManager.
 * 
 * @author Dileep Mangalapudi
 * 
 */
public class AppiumServerManager {
	AppiumDriverLocalService service = null;

	/**
	 * Instantiates a new appium server manager.
	 * 
	 */
	public AppiumServerManager() {
		String osName = System.getProperty("os.name");
		if (osName.contains("Windows")) {
			service = AppiumDriverLocalService.buildService(
					new AppiumServiceBuilder().usingDriverExecutable(new File(Constants.WIN_DRIVER_EXECUTABLE))
							.withAppiumJS(new File(Constants.WIN_APPIUM_JS_FILE)));
		} else if (osName.contains("Mac")) {
			service = AppiumDriverLocalService
					.buildService(new AppiumServiceBuilder().usingDriverExecutable(new File(Constants.MAC_APPIUM_NODE))
							.withAppiumJS(new File(Constants.MAC_APPIUM_JS)));
		} else {
			Assert.fail("Starting appium is not supporting the current OS.");
		}
	}

	/**
	 * Start appium server.
	 * 
	 * @param env
	 *            the env
	 * @throws InterruptedException
	 *             the interrupted exception
	 * @throws ExecuteException
	 *             the execute exception
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	public void startAppiumServer(String env) throws InterruptedException, ExecuteException, IOException {
		if (env.equalsIgnoreCase("android") || env.equalsIgnoreCase("atid")) {
			service.start();
		} else if (env.equalsIgnoreCase("ios")) {
			CommandLine command = new CommandLine(Constants.MAC_APPIUM_NODE);
			command.addArgument(Constants.MAC_APPIUM_JS, false);
			command.addArgument("--address", false);
			command.addArgument("0.0.0.0");
			command.addArgument("--port", false);
			command.addArgument("4723");
			command.addArgument("--full-reset", true);
			DefaultExecuteResultHandler resultHandler = new DefaultExecuteResultHandler();
			DefaultExecutor executor = new DefaultExecutor();
			executor.setExitValue(1);
			executor.execute(command, resultHandler);
		}
		WaitForPage.waitForPagetoLoad();
		System.out.println("\n Appium Server Started-------------");
	}

	/**
	 * Stop appium server.
	 * 
	 * @param env
	 *            the env
	 */
	public void stopAppiumServer(String env) {
		if (env.equalsIgnoreCase("android") || env.equalsIgnoreCase("atid")) {
			service.stop();
		} else if (env.equalsIgnoreCase("ios")) {
			String[] command = { "/usr/bin/killall", "-KILL", "node" };
			try {
				Runtime.getRuntime().exec(command);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		WaitForPage.waitForPagetoLoad();
		System.out.println("Appium Server Stopped---------------");
	}
}
