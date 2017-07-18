# Unified Bucket Cloud Storage example

This is an example that shows how you can use the [CloudRail Unified Bucket Cloud Storage API](https://cloudrail.com/integrations/interfaces/BusinessCloudStorage;platformId=Java) to integrate different Bucket Cloud Storage services in one application. It is a simple command-line program that makes it possible to list buckets and their content and to download files.

## Prerequisites

You need to have developer credentials for the services you want to use. Instructions on how they can be acquired can be found on our [Unified Bucket Cloud Storage API site](https://cloudrail.com/integrations/interfaces/BusinessCloudStorage;platformId=Java;serviceIds=AmazonS3%2CBackblaze%2CGoogleCloudPlatform%2CMicrosoftAzure%2CRackspace). You also need a CloudRail API key that you can [get fro free here](https://cloudrail.com/signup).

Find the following piece of code in your *Main.java* file, and enter your developer credentials and CloudRail App Key.

```java
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
```
## Running the Program

The program works with simple command-line arguments. On startup, it displays a list of your buckets. You can list the files in a bucket by entering *"show [bucketName]"*, list all buckets by entering *"buckets"*, download a file from the currently displayed bucket by entering *"download [fileName]"* and exit the programm by entering *"exit"*. To see all the possible commands, enter *"help"*.