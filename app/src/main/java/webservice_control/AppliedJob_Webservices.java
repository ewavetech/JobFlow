package webservice_control;

import android.util.Log;

import com.example.khasol.jobflow.Login;
import com.example.khasol.jobflow.ShowAlljobs_WithoutLogin;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Khasol on 8/16/2016.
 */
public class AppliedJob_Webservices {
    public  static  String response;

    public AppliedJob_Webservices(){
    }

    public  String get_jobs(String user_id) throws UnsupportedEncodingException {

        try {
            HttpClient httpClient = new DefaultHttpClient();
            HttpPost httpPost = new HttpPost(Login.URL+"appliedjob.php");
            List<NameValuePair> list = new ArrayList<NameValuePair>();
            list.add(new BasicNameValuePair("user_id", user_id));
            httpPost.setEntity(new UrlEncodedFormEntity(list));
            HttpResponse httpResponse = httpClient.execute(httpPost);
            HttpEntity httpEntity = httpResponse.getEntity();
            response = EntityUtils.toString(httpEntity);

        }catch (Exception e){
            ShowAlljobs_WithoutLogin.check = true;
            Log.i("exception",e.getMessage().toString());

        }
        return response;
    }



}
