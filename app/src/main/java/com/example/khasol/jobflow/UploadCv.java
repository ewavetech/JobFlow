package com.example.khasol.jobflow;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import java.net.URISyntaxException;

/**
 * Created by Khasol on 9/8/2016.
 */
public class UploadCv extends Activity {
    private static final int FILE_SELECT_CODE = 0;
    public static String path;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        showFileChooser();
    }



    private void showFileChooser() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("file:/*");
        intent.addCategory(Intent.CATEGORY_OPENABLE);

        try {
            startActivityForResult(
                    Intent.createChooser(intent, "Select a File to Upload"),
                    FILE_SELECT_CODE);
        } catch (android.content.ActivityNotFoundException ex) {
            // Potentially direct the user to the Market with a Dialog
            Toast.makeText(this, "Please install a File Manager.",
                    Toast.LENGTH_SHORT).show();
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case FILE_SELECT_CODE:
                if (resultCode == RESULT_OK) {
                    // Get the Uri of the selected file
                    Uri uri = data.getData();
                    Log.d("file", "File Uri: " + uri.toString());
                    // Get the path
                     path = null;
                    try {
                        path = getPath(UploadCv.this, uri);
                        if(path == null){
                            Toast.makeText(UploadCv.this,"Please choose file form different directory",Toast.LENGTH_SHORT).show();
                            finish();
                        }
                        else{
                            Intent returnIntent = new Intent();
                            returnIntent.putExtra("path",path);
                            setResult(Activity.RESULT_OK,returnIntent);
                            finish();
                        }

                    } catch (URISyntaxException e) {
                        e.printStackTrace();
                    }
                    Log.d("file path", "File Path: " + path);
                                    }
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
    public static String getPath(Context context, Uri uri) throws URISyntaxException {
        if ("content".equalsIgnoreCase(uri.getScheme())) {
            String[] projection = { "_data" };
            Cursor cursor = null;

            try {
                cursor = context.getContentResolver().query(uri, projection, null, null, null);
                int column_index = cursor.getColumnIndexOrThrow("_data");
                if (cursor.moveToFirst()) {
                    return cursor.getString(column_index);
                }
            } catch (Exception e) {
                // Eat it
            }
        }
        else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }

        return null;
    }
}
