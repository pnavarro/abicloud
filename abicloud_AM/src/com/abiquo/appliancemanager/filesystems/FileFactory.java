package com.abiquo.appliancemanager.filesystems;

import java.io.InputStream;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import com.abiquo.appliancemanager.exceptions.DownloadException;

public class FileFactory
{

    public static InputStream open(URL target) throws DownloadException
    {
        IFileSystem file;

        String protocol = target.getProtocol().toUpperCase();

        if ("FILE".equalsIgnoreCase(protocol))
        {
            file = new FileLocal();
        }
        else if ("HTTP".equalsIgnoreCase(protocol))
        {
            file = new FileHttp();
        }
        else if ("FTP".equalsIgnoreCase(protocol))
        {
            file = new FileFTP();
        }
        else if ("S3".equalsIgnoreCase(protocol))
        {
            file = new FileS3();
        }
        else
        {
            final String msg = "Unknow protocol " + protocol;
            throw new DownloadException(msg);
        }

        return file.open(target);
    }
}
