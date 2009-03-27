package com.abiquo.appliancemanager.filesystems;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;

import com.abiquo.appliancemanager.exceptions.DownloadException;

public class FileFTP implements IFileSystem
{

    @Override
    public InputStream open(URL target) throws DownloadException
    {
        FTPClient client = new FTPClient();

        InputStream isFile;
        int connectReply;
        try
        {

            client.connect(target.getHost(), target.getPort());

            // After connection attempt, you should check the reply code to verify success.
            connectReply = client.getReplyCode();

            if (!FTPReply.isPositiveCompletion(connectReply))
            {
                client.disconnect();

                final String msg =
                    "FTP server refused connection to ." + target.toString() + " Reply: "
                        + client.getReplyString();
                throw new DownloadException(msg);
            }

            isFile = client.retrieveFileStream(target.getFile());

        }
        catch (IOException e)
        {
            if (client.isConnected())
            {
                try
                {
                    client.disconnect();
                }
                catch (IOException ioe)
                {
                    // do nothing
                }
            }

            final String msg = "Can not transfer file form " + target.toString();
            throw new DownloadException(msg, e);
        }

        return new FtpInputStream(client, isFile);
    }

    /**
     * Wrap the HTTP response InputStream with its request HttpMethod, to release the connection
     * when finish stream read.
     */
    class FtpInputStream extends InputStream
    {
        FTPClient client;

        InputStream source;

        public FtpInputStream(FTPClient c, InputStream s)
        {
            client = c;
            source = s;
        }

        @Override
        public int read() throws IOException
        {
            return source.read();
        }

        public void close() throws IOException
        {
            client.logout();
            if (client.isConnected())
            {
                client.disconnect();
                System.err.println("FTP CONNECTION RELEASED"); // TODO remove
            }

            source.close();
        }

    }
}
