# Unified Cloud Storage

This is an example that shows how you can use [the CloudRail Unified Cloud Storage API](https://cloudrail.com/integrations/interfaces/CloudStorage) to integrate different Cloud Storage services (box, Dropbox, Egnyte, Google Drive, OneDrive, OneDrive for Business) in one application. It is a simple command-line program that makes it possible to browse folders and download files.

## Prerequisites
You need to have developer credentials for the services you want to use. Instructions on how they can be acquired can be found on our [Unified Cloud Storage API site](https://cloudrail.com/integrations/interfaces/CloudStorage;serviceIds=Box%2CDropbox%2CEgnyte%2CGoogleDrive%2COneDrive%2COneDriveBusiness). You also need a CloudRail API key that you can [get fro free here](https://cloudrail.com/signup).

Find the following piece of code in your *Main.java* file, and enter your developer credentials. Also, enter your CloudRail key and change the port as needed.

```java
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
```


## Running the Program

The program works with simple command-line arguments. On startup, it displays the list of files and folders in your root directory. You can enter a folder by entering "cd [relativePath]" where [relativePath] is the path to the folder starting from the currently displayed folder. You can go up to the parent folder by entering "cd .." and you can download a file in the current folder by entering "download [fileName]".