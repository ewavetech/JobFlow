package webservice_control;

import android.util.Log;

import com.example.khasol.jobflow.ShowAlljobs_WithoutLogin;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.UnsupportedEncodingException;

/**
 * Created by Khasol on 8/16/2016.
 */
public class AllJobs_Webservices {
    public  static  String response;

    public AllJobs_Webservices(){
    }

    public  String get_jobs() throws UnsupportedEncodingException {

        try {
            HttpClient httpClient = new DefaultHttpClient();
            HttpGet httpGet = new HttpGet("http://www.khasol.com/jobflow/myphpservices/alljobs.php");
            HttpResponse httpResponse = httpClient.execute(httpGet);
            HttpEntity httpEntity = httpResponse.getEntity();
            response = EntityUtils.toString(httpEntity);

        }catch (Exception e){
            ShowAlljobs_WithoutLogin.check = true;
            Log.i("exception",e.getMessage().toString());

        }
        return response;
    }



}
