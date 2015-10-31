package com.takeme.services;

import android.os.AsyncTask;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.mobileconnectors.s3.transfermanager.TransferManager;
import com.amazonaws.mobileconnectors.s3.transfermanager.Upload;
import com.amazonaws.mobileconnectors.s3.transfermanager.model.UploadResult;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;

import java.io.File;

public class AwsS3Provider {

    private final String IMAGES_BUCKET_NAME = "petsi-bucket";
    private final String IMAGES_PREFIX = "pets-pictures/";

    private AWSCredentials awsCredentials; //= new BasicAWSCredentials()
    TransferManager transferManager;

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

    private AwsS3Provider() {

//        this.context = context;
        awsCredentials = new BasicAWSCredentials("User Name", "Password");
        transferManager  = new TransferManager(awsCredentials);
    }

    public String getPicUrl(UploadResult uploadResult){

        return ("http://" + uploadResult.getBucketName() + ".s3.amazonaws.com/" + uploadResult.getKey());
    }

    public void uploadImage(String fileName,File file, FileUploadCallBack fileUploadCallBack){

        String fileKey = IMAGES_PREFIX + fileName;

        UploadToS3AsynchTask uploadToS3AsynchTask = new UploadToS3AsynchTask(fileUploadCallBack);
        uploadToS3AsynchTask.execute(new FileToUpload(IMAGES_BUCKET_NAME,fileKey,file));
    }

    private class FileToUpload{
        public String bucketName;
        public String key;
        public File file;

        private FileToUpload(String bucketName, String key, File file) {
            this.bucketName = bucketName;
            this.key = key;
            this.file = file;
        }
    }

    private class UploadToS3AsynchTask extends AsyncTask<FileToUpload,Integer,UploadResult> {

        private FileUploadCallBack fileUploadCallBack;

        public UploadToS3AsynchTask(FileUploadCallBack fileUploadCallBack) {
            this.fileUploadCallBack = fileUploadCallBack;
        }

        @Override
        protected UploadResult doInBackground(FileToUpload... fileToUploads) {

            //Upload upload = transferManager.upload(fileToUploads[0].bucketName,fileToUploads[0].key,fileToUploads[0].file);

            PutObjectRequest putObjectRequest = new PutObjectRequest(fileToUploads[0].bucketName,fileToUploads[0].key,fileToUploads[0].file);
            putObjectRequest.setCannedAcl(CannedAccessControlList.PublicRead);

            Upload upload = transferManager.upload(putObjectRequest);
//            transferManager.upload(PutObjectRequest)
            try {
                UploadResult uploadResult = upload.waitForUploadResult();

                if (uploadResult.getKey().isEmpty())
                    return null;
                else return uploadResult;

            } catch (InterruptedException e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(UploadResult uploadResult) {

            fileUploadCallBack.onUploadToS3Completed(uploadResult);
        }
    }

    public interface FileUploadCallBack {

        public void onUploadToS3Completed(UploadResult uploadResult);
    }

}
