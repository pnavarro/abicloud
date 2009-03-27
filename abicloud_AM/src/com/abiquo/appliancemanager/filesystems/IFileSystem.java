package com.abiquo.appliancemanager.filesystems;

import java.io.InputStream;
import java.net.URL;

import com.abiquo.appliancemanager.exceptions.DownloadException;

public interface IFileSystem
{

    public InputStream open(URL target) throws DownloadException;

}
