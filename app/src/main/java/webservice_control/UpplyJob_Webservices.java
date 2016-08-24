package webservice_control;

import android.util.Log;

import com.example.khasol.jobflow.JobDetails;
import com.example.khasol.jobflow.Login;

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
public class UpplyJob_Webservices {
    public  static  String response;

    public UpplyJob_Webservices(){
    }

    public  String Send_data(String user_id,String job_id,String URL) throws UnsupportedEncodingException {

        try {
            HttpClient httpClient = new DefaultHttpClient();
            HttpPost httpPost = new HttpPost(Login.URL+URL);
            List<NameValuePair> list = new ArrayList<NameValuePair>();
            list.add(new BasicNameValuePair("user_id", user_id));
            list.add(new BasicNameValuePair("job_id", job_id));
            httpPost.setEntity(new UrlEncodedFormEntity(list));
            HttpResponse httpResponse = httpClient.execute(httpPost);
            HttpEntity httpEntity = httpResponse.getEntity();
            response = EntityUtils.toString(httpEntity);

        }catch (Exception e){
            JobDetails.check = true;
            Log.i("exception",e.getMessage().toString());

        }
        return response;
    }

}
