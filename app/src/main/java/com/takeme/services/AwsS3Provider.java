package com.takeme.services;

import android.content.Context;
import android.os.AsyncTask;
import android.text.TextUtils;
import android.widget.Toast;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;

import com.amazonaws.mobileconnectors.s3.transfermanager.model.UploadResult;

import com.amazonaws.mobileconnectors.s3.transferutility.TransferListener;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferObserver;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferState;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferUtility;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.AmazonS3Exception;


import java.io.File;


public class AwsS3Provider {

    private AWSCredentials awsCredentials;
    TransferUtility transferUtility;
    Context context;
    AmazonS3 s3;

    /**
     * SingletonHolder is loaded on the first execution of AwsS3Provider.getInstance()
     * or the first access to SingletonHolder.INSTANCE, not before.
     */
    private static class SingletonHolder {
        private final static AwsS3Provider INSTANCE = new AwsS3Provider();
    }

    public static AwsS3Provider getInstance() {
        return SingletonHolder.INSTANCE;
    }

    public void init(Context context){
        // Create an S3 client
        this.context = context;
        awsCredentials = new BasicAWSCredentials("", "");

        s3 = new AmazonS3Client(awsCredentials);

        // Set the region of your S3 bucket
        s3.setRegion(Region.getRegion(Regions.EU_WEST_1));

        this.transferUtility = new TransferUtility(s3, this.context);

    }

    private AwsS3Provider() {

    }

    public String getPicUrl(String fileKey){

        if (fileKey == null) return null;

        return ("https://s3-" + Regions.EU_WEST_1.getName() + ".amazonaws.com/"+ Constants.IMAGES_BUCKET_NAME +"/" + fileKey);
    }

    public void uploadImage(String fileName,File file, FileUploadCallBack fileUploadCallBack){

        String fileKey = Constants.IMAGES_PREFIX + fileName;

        //UploadToS3AsynchTask uploadToS3AsynchTask = new UploadToS3AsynchTask(fileUploadCallBack);
        //uploadToS3AsynchTask.execute(new FileToUpload(Constants.IMAGES_BUCKET_NAME,fileKey,file));

        TransferObserver observer = transferUtility.upload(
                Constants.IMAGES_BUCKET_NAME,        /* The bucket to upload to */
                fileKey,       /* The key for the uploaded object */
                file          /* The file where the data to upload exists */
                        /* The ObjectMetadata associated with the object*/
        );

        observer.setTransferListener(new UploadTransferListener(fileUploadCallBack,fileKey));

    }



    private class UploadTransferListener implements  TransferListener{

        private FileUploadCallBack fileUploadCallBack;
        private String fileKey;

        public UploadTransferListener(FileUploadCallBack fileUploadCallBack, String fileKey){
            this.fileUploadCallBack = fileUploadCallBack;
            this.fileKey = fileKey;
        }

        @Override
        public void onStateChanged(int id, TransferState state) {

            if(state.equals(TransferState.COMPLETED))
            {
                fileUploadCallBack.onUploadToS3Completed(getPicUrl(this.fileKey));
            }
        }

        @Override
        public void onProgressChanged(int id, long bytesCurrent, long bytesTotal) {

        }

        @Override
        public void onError(int id, Exception ex) {
            fileUploadCallBack.onUploadToS3Failed();
        }
    }

    public interface FileUploadCallBack {

        public void onUploadToS3Completed(String uploadResult);
        public void onUploadToS3Failed();

    }

}
