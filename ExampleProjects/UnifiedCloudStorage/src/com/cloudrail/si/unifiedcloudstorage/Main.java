package com.cloudrail.si.unifiedcloudstorage;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

import com.cloudrail.si.CloudRail;
import com.cloudrail.si.interfaces.CloudStorage;
import com.cloudrail.si.servicecode.commands.awaitCodeRedirect.LocalReceiver;
import com.cloudrail.si.services.Box;
import com.cloudrail.si.services.Dropbox;
import com.cloudrail.si.services.Egnyte;
import com.cloudrail.si.services.GoogleDrive;
import com.cloudrail.si.services.OneDrive;
import com.cloudrail.si.services.OneDriveBusiness;
import com.cloudrail.si.types.CloudMetaData;

public class Main {
	private static CloudStorage service;
	private static BufferedReader in;
	private static String currentPath = "/";

	public static void main(String[] args) {
		CloudRail.setAppKey("[Your CloudRail Key]");
		
		int port = 8082;
		
		CloudStorage box = new Box(
			    new LocalReceiver(port),
			    "[Box Client Identifier]",
			    "[Box Client Secret]",
			    "http://localhost:" + port + "/",
			    "someState"
			);
		CloudStorage dropbox = new Dropbox(
				new LocalReceiver(port),
				"[Dropbox Client Identifier]",
			    "[Dropbox Client Secret]",
				"http://localhost:" + port + "/",
				"someState"
				);
		CloudStorage egnyte = new Egnyte(
			    new LocalReceiver(port),
			    "[Your Egnyte Domain]",
			    "[Your Egnyte API Key]",
			    "[Your Egnyte Shared Secret]",
			    "http://localhost:" + port + "/",
			    "someState"
				);
		CloudStorage googledrive = new GoogleDrive(
			    new LocalReceiver(port),
			    "[Google Drive Client Identifier]",
			    "[Google Drive Client Secret]",
			    "http://localhost:" + port + "/",
			    "someState"
				);
		CloudStorage onedrive = new OneDrive(
			    new LocalReceiver(port),
			    "[OneDrive Client Identifier]",
			    "[OneDrive Client Secret]",
			    "http://localhost:" + port + "/",
			    "someState"
				);
		CloudStorage onedrivebusiness = new OneDriveBusiness(
			    new LocalReceiver(port),
			    "[OneDrive Business Client Identifier]",
			    "[OneDrive Business Client Secret]",
			    "http://localhost:" + port + "/",
			    "someState"
				);

		service = null;
		switch (args[0]) {
			case "box":
				service = box;
				break;
			case "dropbox":
				service = dropbox;
				break;
			case "egnyte":
				service = egnyte;
				break;
			case "googledrive":
				service = googledrive;
				break;
			case "onedrive":
				service = onedrive;
				break;
			case "onedrivebusiness":
				service = onedrivebusiness;
				break;
		}
		service.login();
		
		in = new BufferedReader(new InputStreamReader(System.in));
		
		showPath();
		getNextUserCommand();
	}

	private static void getNextUserCommand() {
		try {
			List<String> input = new LinkedList<String>(Arrays.asList(in.readLine().split(" ")));
			String cmd = input.remove(0);
			switch (cmd) {
				case "help":
					showHelp();
					break;
				case "cd":
					cd(String.join(" ", input));
					break;
				case "download":
					download(String.join(" ", input));
					break;
				case "exit":
					System.exit(0);;
				default:
					System.out.println("Unknown command. Try entering \"help\".\n");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		getNextUserCommand();
	}

	private static void showPath() {
		System.out.println("showing folder " + currentPath);
		List<CloudMetaData> children = service.getChildren(currentPath);
		children.sort(new Comparator<CloudMetaData>() {
			@Override
			public int compare(CloudMetaData c1, CloudMetaData c2) {
				return c1.getName().toLowerCase().compareTo(c2.getName().toLowerCase());
			}
		});
		for (CloudMetaData c : children) {
			System.out.println(c.getName());
		}
		System.out.println("");
	}

	private static void download(String input) {
		try {
			String pathToFile = currentPath;
			if (!pathToFile.equals("/")) { pathToFile = pathToFile + "/"; }
			pathToFile = pathToFile + input;
			InputStream downloadStream = service.download(pathToFile);
			File targetFile = new File("downloads/" + input);
			OutputStream outStream;
			outStream = new FileOutputStream(targetFile);

			byte[] buffer = new byte[8 * 1024];
			int bytesRead;
			while ((bytesRead = downloadStream.read(buffer)) != -1) {
				outStream.write(buffer, 0, bytesRead);
			}
			downloadStream.close();
			outStream.close();
			System.out.println("File " + pathToFile + " downloaded.\n");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static void cd(String change) {
		if (change.equals("..")) {
			List<String> path = new LinkedList<String>(Arrays.asList(currentPath.split("/")));
			if (path.size() > 0) {
				path.remove(path.size() - 1);
			}
			String newPath = String.join("/", path);
			if (newPath.equals("")) {
				newPath = "/";
			}
			currentPath = newPath;
		} else {
			if (!currentPath.equals("/")) {
				currentPath = currentPath + "/";
			}
			currentPath = currentPath + change;
		}
		showPath();
	}

	private static void showHelp() {
		System.out.println("Possible commands:");
		System.out.println("\"help\" displays this help");
		System.out.println("\"cd relativePath\" opens a child folder, where relativePath is its path starting from the currently displayed folder.");
		System.out.println("\"cd ..\" goes to the current folder's parent folder.");
		System.out.println("\"download fileName\" downloads the respective file from the currently displayed folder.");
		System.out.println("\"exit\" quits the program.");
	}
}
