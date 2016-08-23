package webservice_control;

import android.util.Log;

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
public class Login_Webservices {
    public  static  String response;

    public  Login_Webservices(){
    }

    public  String Send_data(String name,String password) throws UnsupportedEncodingException {

        try {
            HttpClient httpClient = new DefaultHttpClient();
            HttpPost httpPost = new HttpPost(Login.URL+"login.php");
            List<NameValuePair> list = new ArrayList<NameValuePair>();
            list.add(new BasicNameValuePair("user_name", name));
            list.add(new BasicNameValuePair("user_password", password));
            httpPost.setEntity(new UrlEncodedFormEntity(list));
            HttpResponse httpResponse = httpClient.execute(httpPost);
            HttpEntity httpEntity = httpResponse.getEntity();
            response = EntityUtils.toString(httpEntity);


        }catch (Exception e){
            Login.check = true;
            Log.i("exception",e.getMessage().toString());

        }
        return response;
    }

}
