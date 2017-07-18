package com.cloudrail.si.unifiedbucketcloudstorage;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import com.cloudrail.si.CloudRail;
import com.cloudrail.si.interfaces.BusinessCloudStorage;
import com.cloudrail.si.services.AmazonS3;
import com.cloudrail.si.services.Backblaze;
import com.cloudrail.si.services.GoogleCloudPlatform;
import com.cloudrail.si.services.MicrosoftAzure;
import com.cloudrail.si.services.Rackspace;
import com.cloudrail.si.types.Bucket;
import com.cloudrail.si.types.BusinessFileMetaData;

public class Main {
	private static BusinessCloudStorage service;
	private static BufferedReader in;
	private static Bucket currentBucket;
	
	public static void main(String[] args) {
		CloudRail.setAppKey("[Your CloudRail Key]");

		AmazonS3 amazons3 = new AmazonS3(
		    null,
		    "[Your S3 Access Key ID]",
		    "[Your S3 Secret Access Key]",
		    "[Your AWS region]"
		);
		Backblaze backblaze = new Backblaze(
		    null,
		    "[Your Backblaze Account ID]",
		    "[Your Backblaze App Key]"
		);
		GoogleCloudPlatform googlecloudplatform = new GoogleCloudPlatform(
		    null,
		    "[Your Google Client Email]",
		    "[Your Google Private Key]",
		    "[Your Google Project ID]"
		);
		MicrosoftAzure microsoftazure = new MicrosoftAzure(
		    null,
		    "[Your Azure Account Name]",
		    "[Your Azure Access Key]"
		);
		Rackspace rackspace = new Rackspace(
		    null,
		    "[Your Rackspace User Name]",
		    "[Your Rackspace API Key]",
		    "[Your Rackspace Region]"
		);

		switch(args[0]) {
		    case "amazons3": service = amazons3; break;
		    case "backblaze": service = backblaze; break;
		    case "googlecloudplatform": service = googlecloudplatform; break;
		    case "microsoftazure": service = microsoftazure; break;
		    case "rackspace": service = rackspace; break;
		}

		in = new BufferedReader(new InputStreamReader(System.in));
		
		showBuckets();
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
				case "buckets":
					showBuckets();
					break;
				case "show":
					showBucket(String.join(" ", input));
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

	private static void showHelp() {
		System.out.println("Possible commands:");
		System.out.println("\"help\" displays this help");
		System.out.println("\"buckets\" shows a list of all Buckets.");
		System.out.println("\"show bucketname\" shows the Bucket with the specified name.");
		System.out.println("\"download fileName\" downloads the respective file from the currently displayed Bucket.");
		System.out.println("\"exit\" quits the program.");
	}

	private static void showBuckets() {
		System.out.println("List of Buckets:");
		List<Bucket> buckets = service.listBuckets();
		for (Bucket b : buckets) {
			System.out.println(b.getName());
		}
		System.out.println("");
	}

	private static void showBucket(String bucketName) {
		List<Bucket> buckets = service.listBuckets();
		Bucket bucket = null;
		for (Bucket b : buckets) {
			if (b.getName().equals(bucketName)) {
				bucket = b;
				currentBucket = b;
			}
		}
		List<BusinessFileMetaData> fileMetaDatas = service.listFiles(bucket);
		for (BusinessFileMetaData fileMetaData : fileMetaDatas) {
			System.out.println(fileMetaData.getFileName());
		}
		System.out.println("");;
	}

	private static void download(String fileName) {
		service.downloadFile(fileName, currentBucket);
		try {
			InputStream downloadStream = service.downloadFile(fileName, currentBucket);
			File targetFile = new File("downloads/" + fileName);
			OutputStream outStream;
			outStream = new FileOutputStream(targetFile);

			byte[] buffer = new byte[8 * 1024];
			int bytesRead;
			while ((bytesRead = downloadStream.read(buffer)) != -1) {
				outStream.write(buffer, 0, bytesRead);
			}
			downloadStream.close();
			outStream.close();
			System.out.println("File " + fileName + " from Bucket " + currentBucket.getName() + " downloaded.\n");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
