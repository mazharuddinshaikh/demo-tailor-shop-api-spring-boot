package com.mazzee.dts.aws;

import java.io.File;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.mazzee.dts.utils.DtsUtils;

@Service
public class AwsS3Util {
	private final static Logger LOGGER = LoggerFactory.getLogger(AwsS3Util.class);
	private AmazonS3Client amazonS3Client;
	@Value("${dts.amazon.s3.bucketname}")
	private String amazonS3BucketName;
	@Value("${dts.amazon.s3.url}")
	private String amazonS3Url = "";
	@Value("${cloud.aws.region.static}")
	private String amazonS3Region = "";
	@Value("${dts.amazon.s3.bucketname.rootfolder}")
	private String amazonS3RootFolder = "";

//	public AmazonS3 getAmazonS3Instance() {
//		ClientConfiguration configuration = new ClientConfiguration();
//		configuration.setProtocol(Protocol.HTTP);
//		AWSCredentials credentials = new BasicAWSCredentials(accessKey, secretAccessKey);
//		AmazonS3 amazonS3 = AmazonS3ClientBuilder.standard().withRegion(Regions.AF_SOUTH_1)
//				.withCredentials(new AWSStaticCredentialsProvider(credentials)).build();
//		return amazonS3;
//	}

//	public void listBucket() {
//		System.out.println("Access key: " + accessKey + " secret access key: " + secretAccessKey);
//		AmazonS3 amazonS3 = getAmazonS3Instance();
//		System.out.println(" account owner: " + amazonS3.getS3AccountOwner().getDisplayName());
//		List<Bucket> bucketList = amazonS3.listBuckets();
//		for (Bucket bucket : bucketList) {
//			System.out.println(bucket.getName());
//		}
//	}
	@Autowired
	public void setAmazonS3Client(AmazonS3Client amazonS3Client) {
		this.amazonS3Client = amazonS3Client;
	}

	public void setAmazonS3BucketName(String amazonS3BucketName) {
		this.amazonS3BucketName = amazonS3BucketName;
	}

	public void setAmazonS3Url(String amazonS3Url) {
		this.amazonS3Url = amazonS3Url;
	}

	public void setAmazonS3Region(String amazonS3Region) {
		this.amazonS3Region = amazonS3Region;
	}

	public void setAmazonS3RootFolder(String amazonS3RootFolder) {
		this.amazonS3RootFolder = amazonS3RootFolder;
	}

//	public void listBucket() {
//		System.out.println("Region name: " + amazonS3Client.getRegionName());
//		System.out.println(" account owner: " + amazonS3Client.getS3AccountOwner());
//		List<Bucket> bucketList = amazonS3Client.listBuckets();
//		for (Bucket bucket : bucketList) {
//			System.out.println(bucket.getName());
//		}
//	}

//	public void readFile() {
//		ObjectListing objectListing = amazonS3Client.listObjects("dts-tailor-images");
//		String bucketName = objectListing.getBucketName();
//		System.out.println("bucekt name:" + bucketName);
//		for (S3ObjectSummary summary : objectListing.getObjectSummaries()) {
//			System.out.println("summary:" + summary);
//		}
//
//	}

//	public void uploadFile() {
//		Path path = Paths.get("C:\\Users\\Admin\\Pictures\\Screenshots\\Screenshot.png");
//		File testFile = path.toFile();
//		amazonS3Client.putObject("dts-tailor-images", "test/testImages/screenshot2.png", testFile);
//		amazonS3Client.setObjectAcl("dts-tailor-images", "test/testImages/screenshot2.png",
//				CannedAccessControlList.PublicReadWrite);
//	}
//
//	public void deleteFile() {
//		amazonS3Client.deleteObject("dts-tailor-images", "test/testImages/screenshot.png");
//	}

	public boolean uploadFile(String s3Key, File file) {
		LOGGER.info("Uploading image {} to s3 storage", s3Key);
		boolean isFileUploaded = false;
		if (!DtsUtils.isNullOrEmpty(s3Key)) {
			amazonS3Client.putObject(amazonS3BucketName, s3Key, file);
			amazonS3Client.setObjectAcl(amazonS3BucketName, s3Key, CannedAccessControlList.PublicReadWrite);
			isFileUploaded = true;
			LOGGER.info("Image uploaded successfully");
		}
		return isFileUploaded;
	}

	public int uploadFile(Map<String, File> fileMap) {
		boolean isFileUploaded = false;
		int uploadCount = 0;
		for (String key : fileMap.keySet()) {
			File file = fileMap.get(key);
			  isFileUploaded = uploadFile(key, file);
			  if(isFileUploaded) {
				  uploadCount++;
			  }
		}
		return uploadCount;
	}

	public String getAwsS3ImageBaseUrl() {
		String url = null;
//		commented for developement purpose
		url = "https://" + amazonS3BucketName + ".s3." + amazonS3Region + "." + amazonS3Url + "/" + amazonS3RootFolder;
		return url;
	}
}
