package webservice_control;

import android.util.Log;

import com.example.khasol.jobflow.FirstScreen;
import com.example.khasol.jobflow.SignUp;

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
public class SignUp_Webservices {
    public  static  String response;

    public SignUp_Webservices(){
    }

    public  String Send_data(String first_name,String last_name,String email,String phone,String password,String repeat_password)
            throws UnsupportedEncodingException {

        try {
            HttpClient httpClient = new DefaultHttpClient();
            HttpPost httpPost = new HttpPost(FirstScreen.URL+"signup.php");
            List<NameValuePair> list = new ArrayList<NameValuePair>();
            list.add(new BasicNameValuePair("first_name", first_name));
            list.add(new BasicNameValuePair("last_name", last_name));
            list.add(new BasicNameValuePair("email", email));
            list.add(new BasicNameValuePair("phone", phone));
            list.add(new BasicNameValuePair("password", password));
            list.add(new BasicNameValuePair("repeat_password", repeat_password));
            httpPost.setEntity(new UrlEncodedFormEntity(list));
            HttpResponse httpResponse = httpClient.execute(httpPost);
            HttpEntity httpEntity = httpResponse.getEntity();
            response = EntityUtils.toString(httpEntity);


        }catch (Exception e){
            SignUp.check = true;
            Log.i("exception",e.getMessage().toString());
        }
        return response;
    }

}
