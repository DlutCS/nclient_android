package org.dlutcs.nclient_android.util;

/**
 * Created by linwei on 15-10-8.
 */
//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//
import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.Authenticator;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.Flushable;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;

public abstract class OkRequest<T> extends Request<T> {
    public static final String HEADER_AUTHORIZATION = "Authorization";
    public static final String CHARSET_UTF8 = "UTF-8";
    public static final String ENCODING_GZIP = "gzip";
    public static final String HEADER_ACCEPT = "Accept";
    public static final String HEADER_ACCEPT_CHARSET = "Accept-Charset";
    public static final String HEADER_ACCEPT_ENCODING = "Accept-Encoding";
    public static final String HEADER_USER_AGENT = "User-Agent";
    public static final String HEADER_ACCEPT_VERSION = "X-Accept-Version";
    public static final String HEADER_CONTENT_TYPE = "Content-Type";
    public static final String HEADER_CONTENT_LENGTH = "Content-Length";
    public static final String HEADER_CONTENT_ENCODING = "Content-Encoding";
    public static final String HEADER_REFERER = "Referer";
    public static final String PROTOCOL_CONTENT_TYPE_JSON = String.format("application/json; charset=%s", new Object[]{"UTF-8"});
    public static final String PROTOCOL_CONTENT_TYPE_FORM = String.format("application/x-www-form-urlencoded; charset=%s", new Object[]{"UTF-8"});
    public static final String PARAM_CHARSET = "charset";
    private static final String BOUNDARY = "00douban0natalya0volley00";
    private static final String CONTENT_TYPE_MULTIPART = "multipart/form-data; boundary=00douban0natalya0volley00";
    private static final String CRLF = "\r\n";
    protected ConcurrentHashMap<String, String> mRequestHeaders;
    private Response.Listener mListener;
    private String mContentType;
    private boolean mMultipart;
    private boolean mForm;
    private OkRequest.RequestOutputStream mOutput;
    private int mBufferSize;
    private boolean mIgnoreCloseExceptions;
    private String mRequestUrl;

    public OkRequest(int method, String url, Response.ErrorListener errorListener) {
        super(method, url, errorListener);
        this.mContentType = PROTOCOL_CONTENT_TYPE_FORM;
        this.mBufferSize = 8192;
        this.mIgnoreCloseExceptions = true;
        this.mRequestHeaders = new ConcurrentHashMap();
    }

    public OkRequest(int method, String url, Response.Listener<T> listener, Response.ErrorListener errorListener) {
        super(method, url, errorListener);
        this.mContentType = PROTOCOL_CONTENT_TYPE_FORM;
        this.mBufferSize = 8192;
        this.mIgnoreCloseExceptions = true;
        this.mListener = listener;
        this.mRequestHeaders = new ConcurrentHashMap();
    }

    public OkRequest<T> setReseponseListener(Response.Listener<T> listener) {
        this.mListener = listener;
        return this;
    }

    public OkRequest<T> contentType(String contentType) {
        this.mContentType = contentType;
        return this;
    }

    protected OkRequest<T> openOutput() {
        if(this.mOutput != null) {
            return this;
        } else {
            this.mOutput = new OkRequest.RequestOutputStream("UTF-8");
            return this;
        }
    }

    protected OkRequest<T> startPart() throws IOException {
        this.openOutput();
        if(!this.mMultipart) {
            this.mMultipart = true;
            this.contentType("multipart/form-data; boundary=00douban0natalya0volley00");
            this.mOutput.write("--00douban0natalya0volley00\r\n");
        } else {
            this.mOutput.write("\r\n--00douban0natalya0volley00\r\n");
        }

        return this;
    }

    protected OkRequest<T> writePartHeader(String name, String filename) throws IOException {
        return this.writePartHeader(name, filename, (String)null);
    }

    protected OkRequest<T> writePartHeader(String name, String filename, String contentType) throws IOException {
        StringBuilder partBuffer = new StringBuilder();
        partBuffer.append("form-data; name=\"").append(name);
        if(filename != null) {
            partBuffer.append("\"; filename=\"").append(filename);
        }

        partBuffer.append('\"');
        this.partHeader("Content-Disposition", partBuffer.toString());
        if(contentType != null) {
            this.partHeader("Content-Type", contentType);
        }

        return this.send((CharSequence)"\r\n");
    }

    public OkRequest<T> part(String name, String part) throws IOException {
        return this.part(name, (String)null, (String)part);
    }

    public OkRequest<T> part(String name, String filename, String part) throws IOException {
        return this.part(name, filename, (String)null, (String)part);
    }

    public OkRequest<T> part(String name, String filename, String contentType, String part) throws IOException {
        this.startPart();
        this.writePartHeader(name, filename, contentType);
        this.mOutput.write(part);
        return this;
    }

    public OkRequest<T> part(String name, Number part) throws IOException {
        return this.part(name, (String)null, (Number)part);
    }

    public OkRequest<T> part(String name, String filename, Number part) throws IOException {
        return this.part(name, filename, part != null?part.toString():null);
    }

    public OkRequest<T> part(String name, File part) throws IOException {
        return this.part(name, (String)null, (File)part);
    }

    public OkRequest<T> part(String name, String filename, File part) throws IOException {
        return this.part(name, filename, (String)null, (File)part);
    }

    public OkRequest<T> part(String name, String filename, String contentType, File part) throws IOException {
        BufferedInputStream stream = new BufferedInputStream(new FileInputStream(part));
        return this.part(name, filename, contentType, (InputStream)stream);
    }

    public OkRequest<T> part(String name, InputStream part) throws IOException {
        return this.part(name, (String)null, (String)null, (InputStream)part);
    }

    public OkRequest<T> part(String name, String filename, String contentType, InputStream part) throws IOException {
        try {
            this.startPart();
            this.writePartHeader(name, filename, contentType);
            this.copy(part, this.mOutput);
        } catch (IOException var6) {
            throw var6;
        } catch (Exception var7) {
            VolleyLog.e(var7, "error on part", new Object[0]);
        }

        return this;
    }

    public OkRequest<T> partHeader(String name, String value) throws IOException {
        return this.send((CharSequence)name).send((CharSequence)": ").send((CharSequence)value).send((CharSequence)"\r\n");
    }

    public OkRequest<T> send(File input) throws IOException {
        BufferedInputStream stream = new BufferedInputStream(new FileInputStream(input));
        return this.send((InputStream)stream);
    }

    public OkRequest<T> send(byte[] input) throws IOException {
        return this.send((InputStream)(new ByteArrayInputStream(input)));
    }

    public OkRequest<T> send(InputStream input) throws IOException {
        this.openOutput();
        this.copy(input, this.mOutput);
        return this;
    }

    public OkRequest<T> send(CharSequence value) {
        this.openOutput();

        try {
            this.mOutput.write(value.toString());
        } catch (IOException var3) {
            this.deliverError(new ParseError(var3));
        }

        return this;
    }

    public OkRequest<T> form(Map<String, String> values) {
        return this.form(values, "UTF-8");
    }

    public OkRequest<T> form(Map.Entry<String, String> entry) {
        return this.form(entry, "UTF-8");
    }

    public OkRequest<T> form(Map.Entry<String, String> entry, String charset) {
        return this.form((String)entry.getKey(), (String)entry.getValue(), charset);
    }

    public OkRequest<T> form(String name, String value) {
        return this.form(name, value, "UTF-8");
    }

    public OkRequest<T> form(String name, String value, String charset) {
        boolean first = !this.mForm;
        if(first) {
            this.contentType(PROTOCOL_CONTENT_TYPE_FORM);
            this.mForm = true;
        }

        charset = getValidCharset(charset);
        this.openOutput();
        if(!first) {
            this.mOutput.write(38);
        }

        try {
            if(VolleyLog.DEBUG) {
                VolleyLog.d("name=%1$s, value=%2$s", new Object[]{name, value});
            }

            this.mOutput.write(URLEncoder.encode(name, charset));
            this.mOutput.write("=");
            if(value != null) {
                this.mOutput.write(URLEncoder.encode(value, charset));
            }
        } catch (IOException var6) {
            ;
        }

        return this;
    }

    public OkRequest<T> form(Map<String, String> values, String charset) {
        if(!values.isEmpty()) {
            Iterator i$ = values.entrySet().iterator();

            while(i$.hasNext()) {
                Map.Entry entry = (Map.Entry)i$.next();
                this.form(entry, charset);
            }
        }

        return this;
    }

    public OkRequest<T> param(String key, String value) {
        StringBuilder urlBuilder = new StringBuilder(this.getUrl());
        if(this.getUrl().contains("?")) {
            urlBuilder.append("&");
        } else {
            urlBuilder.append("?");
        }

        urlBuilder.append(key);
        urlBuilder.append("=");
        urlBuilder.append(value);
        this.mRequestUrl = urlBuilder.toString();
        return this;
    }

    public OkRequest<T> param(Map.Entry<String, String> entry) {
        return this.param((String)entry.getKey(), (String)entry.getValue());
    }

    public OkRequest<T> params(Map<String, String> values) {
        if(!values.isEmpty()) {
            Iterator i$ = values.entrySet().iterator();

            while(i$.hasNext()) {
                Map.Entry entry = (Map.Entry)i$.next();
                this.param(entry);
            }
        }

        return this;
    }

    public OkRequest<T> header(String name, String value) {
        this.mRequestHeaders.put(name, value);
        return this;
    }

    public OkRequest<T> header(String name, Number value) {
        return this.header(name, value != null?value.toString():null);
    }

    public OkRequest<T> headers(Map<String, String> headers) {
        if(!headers.isEmpty()) {
            Iterator i$ = headers.entrySet().iterator();

            while(i$.hasNext()) {
                Map.Entry header = (Map.Entry)i$.next();
                this.header(header);
            }
        }

        return this;
    }

    public OkRequest<T> header(Map.Entry<String, String> header) {
        return this.header((String)header.getKey(), (String)header.getValue());
    }

    public OkRequest<T> setAuthenticator(Authenticator authenticator) throws AuthFailureError {
        if(authenticator == null) {
            return this;
        } else {
            String accessToken = authenticator.getAuthToken();
            if(accessToken != null) {
                this.header("Authorization", String.format("Bearer %1$s", new Object[]{accessToken}));
            }

            return this;
        }
    }

    public OkRequest<T> accept(String accept) {
        return this.header("Accept", accept);
    }

    public OkRequest<T> acceptJson() {
        return this.accept(PROTOCOL_CONTENT_TYPE_JSON);
    }

    public OkRequest<T> acceptEncoding(String acceptEncoding) {
        return this.header("Accept-Encoding", acceptEncoding);
    }

    public OkRequest<T> acceptGzipEncoding() {
        return this.acceptEncoding("gzip");
    }

    public OkRequest<T> acceptCharset(String acceptCharset) {
        return this.header("Accept-Charset", acceptCharset);
    }

    public OkRequest<T> userAgent(String userAgent) {
        return this.header("User-Agent", userAgent);
    }

    public OkRequest<T> referer(String referer) {
        return this.header("Referer", referer);
    }

    protected OkRequest<T> copy(final InputStream input, final OutputStream output) throws IOException {
        return (new OkRequest.CloseOperation(input, this.mIgnoreCloseExceptions) {
            public OkRequest<T> call() throws IOException {
                boolean thrown = false;

                try {
                    byte[] e = new byte[OkRequest.this.mBufferSize];

                    int read;
                    while((read = input.read(e)) != -1) {
                        output.write(e, 0, read);
                    }

                    OkRequest var4 = OkRequest.this;
                    return var4;
                } catch (IOException var13) {
                    thrown = true;
                    throw var13;
                } finally {
                    try {
                        this.done();
                    } catch (IOException var12) {
                        if(!thrown) {
                            throw var12;
                        }
                    }

                }
            }
        }).call();
    }

    public Map<String, String> getHeaders() throws AuthFailureError {
        Map headers = super.getHeaders();
        if(!this.mRequestHeaders.isEmpty()) {
            if(headers.isEmpty()) {
                return this.mRequestHeaders;
            }

            headers.putAll(this.mRequestHeaders);
        }

        return headers;
    }

    protected abstract Response<T> parseNetworkResponse(NetworkResponse var1);

    protected void deliverResponse(T t) {
        if(this.mListener != null) {
            this.mListener.onResponse(t);
        }

    }

    public String getOriginUrl() {
        return super.getUrl();
    }

    public String getUrl() {
        if(this.mRequestUrl == null) {
            this.mRequestUrl = super.getUrl();
        }

        return this.mRequestUrl;
    }

    /** @deprecated */
    @Deprecated
    protected Map<String, String> getParams() throws AuthFailureError {
        return super.getParams();
    }

    public String getBodyContentType() {
        return this.mContentType;
    }

    public byte[] getBody() throws AuthFailureError {
        if(this.mOutput == null) {
            return super.getBody();
        } else {
            byte[] e1;
            try {
                if(this.mMultipart) {
                    this.mOutput.write("\r\n--00douban0natalya0volley00--\r\n");
                }

                byte[] e = this.mOutput.toByteArray();
                return e;
            } catch (IOException var12) {
                var12.printStackTrace();
                e1 = this.mOutput.toByteArray();
            } finally {
                try {
                    this.mOutput.close();
                } catch (IOException var11) {
                    var11.printStackTrace();
                }

                this.mOutput = null;
            }

            return e1;
        }
    }

    public OkRequest<T> setRequestQueue(RequestQueue requestQueue) {
        super.setRequestQueue(requestQueue);
        return this;
    }

    private static String getValidCharset(String charset) {
        return charset != null && charset.length() > 0?charset:"UTF-8";
    }

    public int getMethod() {
        return super.getMethod();
    }

    public Request<?> setTag(Object tag) {
        return super.setTag(tag);
    }

    public Object getTag() {
        return super.getTag();
    }

    public int getTrafficStatsTag() {
        return super.getTrafficStatsTag();
    }

    public Request<?> setRetryPolicy(RetryPolicy retryPolicy) {
        return super.setRetryPolicy(retryPolicy);
    }

    public void addMarker(String tag) {
        super.addMarker(tag);
    }

    public String getCacheKey() {
        return super.getCacheKey();
    }

    public Request<?> setCacheEntry(com.android.volley.Cache.Entry entry) {
        return super.setCacheEntry(entry);
    }

    public com.android.volley.Cache.Entry getCacheEntry() {
        return super.getCacheEntry();
    }

    public void cancel() {
        super.cancel();
    }

    protected String getParamsEncoding() {
        return super.getParamsEncoding();
    }

    public Priority getPriority() {
        return super.getPriority();
    }

    public RetryPolicy getRetryPolicy() {
        return super.getRetryPolicy();
    }

    public void markDelivered() {
        super.markDelivered();
    }

    public boolean hasHadResponseDelivered() {
        return super.hasHadResponseDelivered();
    }

    protected VolleyError parseNetworkError(VolleyError volleyError) {
        return super.parseNetworkError(volleyError);
    }

    public void deliverError(VolleyError error) {
        super.deliverError(error);
    }

    public int compareTo(Request<T> other) {
        return super.compareTo(other);
    }

    public String toString() {
        return super.toString();
    }

    public boolean isCanceled() {
        return super.isCanceled();
    }

    protected abstract static class CloseOperation<V> implements Callable<V> {
        private final Closeable closeable;
        private final boolean ignoreCloseExceptions;

        protected CloseOperation(Closeable closeable, boolean ignoreCloseExceptions) {
            this.closeable = closeable;
            this.ignoreCloseExceptions = ignoreCloseExceptions;
        }

        protected void done() throws IOException {
            if(this.closeable instanceof Flushable) {
                ((Flushable)this.closeable).flush();
            }

            if(this.ignoreCloseExceptions) {
                try {
                    this.closeable.close();
                } catch (IOException var2) {
                    ;
                }
            } else {
                this.closeable.close();
            }

        }
    }

    public static class RequestOutputStream extends ByteArrayOutputStream {
        private final String charset;

        public RequestOutputStream(String charset) {
            this.charset = charset;
        }

        public OkRequest.RequestOutputStream write(String value) throws IOException {
            super.write(value.getBytes(Charset.forName(this.charset)));
            return this;
        }
    }
}
