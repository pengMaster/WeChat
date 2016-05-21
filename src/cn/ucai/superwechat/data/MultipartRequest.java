package cn.ucai.superwechat.data;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.toolbox.HttpHeaderParser;
import com.google.gson.Gson;

import java.util.Map;

public class MultipartRequest<T> extends Request<T> {
    private final Gson mGson = new Gson();
    private final Class<T> mClazz;
    private final Listener<T> mListener;
    private final Map<String, String> mHeaders;
    private final String mMimeType;
    private final byte[] mMultipartBody;

    public MultipartRequest(String url, Class<T> clazz, Map<String, String> headers,
                            Listener<T> listener, ErrorListener errorListener,
                            String mimeType, byte[] multipartBody) {
        this(Method.POST, url, clazz, headers,mimeType, multipartBody, listener, errorListener);
    }

    public MultipartRequest(int method, String url, Class<T> clazz, Map<String, String> headers,
                            String mimeType, byte[] multipartBody,
                            Listener<T> listener, ErrorListener errorListener) {
        super(method, url, errorListener);
        this.mClazz = clazz;
        this.mHeaders = headers;
        this.mListener = listener;
        this.mMimeType = mimeType;
        this.mMultipartBody = multipartBody;
    }

    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        return (mHeaders != null) ? mHeaders : super.getHeaders();
    }

    @Override
    public String getBodyContentType() {
        return mMimeType;
    }

    @Override
    public byte[] getBody() throws AuthFailureError {
        return mMultipartBody;
    }

    @Override
    protected Response<T> parseNetworkResponse(NetworkResponse response) {
        try {
            String json = new String(response.data, HttpHeaderParser.parseCharset(response.headers));
            return Response.success(mGson.fromJson(json, mClazz),
                    HttpHeaderParser.parseCacheHeaders(response));
        } catch (Exception e) {
            return Response.error(new ParseError(e));
        }
    }

    @Override
    protected void deliverResponse(T response) {
        mListener.onResponse(response);
    }

}
