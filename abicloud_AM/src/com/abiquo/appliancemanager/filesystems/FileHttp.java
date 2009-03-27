package com.abiquo.appliancemanager.filesystems;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;

import com.abiquo.appliancemanager.exceptions.DownloadException;

public class FileHttp implements IFileSystem
{
    /** shared for all files **/
    private static HttpClient client = new HttpClient();

    /**
     * Open an input stream to read the content of a file located on a HTTP file system.
     * 
     * @param target, an URL with ''http:/'' protocol
     * @return an inputs stream ready to read the target file.
     * @throws DownloadException if the file do not exist or can not be accessed.
     * @TODO resume --
     *       http://www.notes411.com/dominosource/tips.nsf/0/480C4E3BE825F69D802571BC007D5AC9!
     *       opendocument
     * @TODO MultiThreadedHttpConnectionManager
     */
    @Override
    public InputStream open(URL target) throws DownloadException
    {
        InputStream isD;

        HttpMethod get = new GetMethod(target.toString());

        get.getParams().setParameter(HttpMethodParams.RETRY_HANDLER,
            new DefaultHttpMethodRetryHandler(3, false));

        try
        {
            int statusCode = client.executeMethod(get);
            if (statusCode != HttpStatus.SC_OK)
            {
                final String msg =
                    "The connection to " + target.toString() + " not accepted: "
                        + HttpStatus.getStatusText(statusCode);
                throw new DownloadException(msg);
            }
            /**
             * @TODO case HttpStatus.SC_INSUFFICIENT_STORAGE
             **/

            isD = get.getResponseBodyAsStream();
        }
        catch (Exception e) // HttpException or IOException
        {
            final String msg = "The URI " + target.toString() + " can not be downloaded";
            throw new DownloadException(msg, e);
        }

        return new HttpInputStream(get, isD);
    }

    /**
     * Wrap the HTTP response InputStream with its request HttpMethod, to release the connection
     * when finish stream read.
     */
    class HttpInputStream extends InputStream
    {
        HttpMethod method;

        InputStream source;

        public HttpInputStream(HttpMethod m, InputStream s)
        {
            method = m;
            source = s;
        }

        @Override
        public int read() throws IOException
        {
            return source.read();
        }

        public void close() throws IOException
        {
            method.releaseConnection();
            System.err.println("HTTP CONNECTION RELEASED"); // TODO remove
            source.close();
        }

    }

}
