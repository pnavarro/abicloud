package com.abiquo.appliancemanager.filesystems;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URL;

import com.abiquo.appliancemanager.exceptions.DownloadException;

public class FileLocal implements IFileSystem
{
    /***
     * Open an input stream to read the content of a file located on a local file system.
     * 
     * @param target, an URL with ''file:/'' protocol
     * @return an inputs stream ready to read the target file.
     * @throws DownloadException if the file do not exist or can not be accessed.
     */
    @Override
    public InputStream open(URL target) throws DownloadException
    {

        FileInputStream fis;

        try
        {
            File file = new File("/" + target.getHost() + target.getFile());
            fis = new FileInputStream(file);
        }
        catch (Exception e) // URISyntaxException or FileNotFoundException
        {
            final String msg = "Target file can not be resolved ";
            throw new DownloadException(msg, e);
        }

        return fis;
        // fis.getChannel().transferTo(0, fis.getChannel().size(), fos.getChannel());

    }

}
