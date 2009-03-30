package com.abiquo.appliancemanager.filesystems;

import java.io.InputStream;
import java.net.URL;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.abiquo.appliancemanager.exceptions.DownloadException;

public class FileFactory
{

    private final static Logger log = LoggerFactory.getLogger(FileFactory.class);
    
    public static InputStream open(URL target) throws DownloadException
    {
        IFileSystem file;

        String protocol = target.getProtocol().toUpperCase();

        log.debug("Opening file protocol "+protocol+" complet uri "+target.toString());
        
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
