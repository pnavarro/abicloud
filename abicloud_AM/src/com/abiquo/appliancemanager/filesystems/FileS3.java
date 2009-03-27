package com.abiquo.appliancemanager.filesystems;

import java.io.InputStream;
import java.net.URL;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.fs.s3native.NativeS3FileSystem;

import com.abiquo.appliancemanager.exceptions.DownloadException;

public class FileS3 implements IFileSystem
{

    /***
     * Open an input stream to read the content of a file located on S3 file system.
     * 
     * @param target, an URL with ''s3:/'' protocol
     * @return an inputs stream ready to read the target file.
     * @throws DownloadException if the file do not exist or can not be accessed.
     */
    @Override
    public InputStream open(URL target) throws DownloadException
    {
        FSDataInputStream input;

        FileSystem s3 = new NativeS3FileSystem();

        try
        {
            // TODO taget.getPath ?
            s3.initialize(target.toURI(), new Configuration());
            input = s3.open(new Path(target.getFile()));
        }
        catch (Exception e) // URISyntaxException or IOException
        {
            final String msg = "The connection to " + target.toString() + "not accepted";
            throw new DownloadException(msg, e);
        }

        return input;
    }

}
