package com.abiquo.appliancemanager.filesystems;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.abiquo.appliancemanager.exceptions.DownloadException;

/**
 * @see http://commons.apache.org/net/apidocs/org/apache/commons/net/ftp/FTPClient.html
 */
public class FileFTP implements IFileSystem
{

    private final static Logger log = LoggerFactory.getLogger(FileFTP.class);

    @Override
    public InputStream open(URL target) throws DownloadException
    {
        FTPClient client = new FTPClient();

        InputStream isFile;
        String host = target.getHost();
        int port = target.getPort();

        if (port == -1)
        {
            port = 21;
        }

        // log.debug("Open to host "+host+":"+port);
        // log.debug("Using file "+target.getFile());

        try
        {

            log.debug("connecting");

            client.connect(host, port);

            log.debug("logining");

            // TODO parse user and password from the URL
            client.login("anonymous", "");

            checkConnection(client);

            client.setFileTransferMode(FTP.STREAM_TRANSFER_MODE);// COMPRESSED_TRANSFER_MODE);
            client.setFileType(FTP.BINARY_FILE_TYPE); // FTP.ASCII_FILE_TYPE

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
                    log.error(" failed to disconect ", e);
                }
            }

            final String msg = "Can not transfer file form " + target.toString();
            throw new DownloadException(msg, e);
        }

        return new FtpInputStream(client, isFile);
    }

    /**
     * After connection attempt, you should check the reply code to verify success.
     * 
     * @throws DownloadException if the client reply code is not PositiveCompletion (starting with
     *             2)
     */
    private void checkConnection(FTPClient client) throws DownloadException
    {
        if (!FTPReply.isPositiveCompletion(client.getReplyCode()))
        {
            log
                .debug("Refused connection " + client.getReplyCode() + " "
                    + client.getReplyString());

            try
            {
                client.disconnect();
                log.debug("Disconnected");
            }
            catch (IOException e)
            {
                log.error("Error disconnecting a bad FTP connection ", e);
            }

            final String msg =
                "FTP server refused connection, caused by : " + client.getReplyString();
            throw new DownloadException(msg);
        }
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

        public int read() throws IOException
        {
            return source.read();
        }

        public void close() throws IOException
        {

            if (!client.completePendingCommand())
            {
                log.error("Some pending commands  ...."); // TODO check sucess
            }

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
