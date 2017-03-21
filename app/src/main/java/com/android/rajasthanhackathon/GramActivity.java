package com.android.rajasthanhackathon;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.rajasthanhackathon.connectionutils.Connection;
import com.android.rajasthanhackathon.connectionutils.ConnectionPost;
import com.android.rajasthanhackathon.connectionutils.Utility;
import com.android.rajasthanhackathon.models.Employee;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class GramActivity extends AppCompatActivity {

    AutoCompleteTextView edit_aadharno;
    EditText edit_description;
    Button upload,submit;
    ImageView image_upload;
    Bitmap photo;
    byte[] byteArray;
    List<Employee> employees;
    int CAMERA_REQUEST=100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gram);
        setupviews();

      if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
        == PackageManager.PERMISSION_DENIED)
      {
          ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.CAMERA},100);
      }


        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, CAMERA_REQUEST);
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String aadharno=edit_aadharno.getText().toString().split(" ")[0];
                String description=edit_description.getText().toString();
                int id= Utility.getGramId(GramActivity.this);
                String photo = Base64.encodeToString(byteArray, Base64.DEFAULT);
                String timestamp=new Date(System.currentTimeMillis()).toString();
                JSONObject jsonObject=new JSONObject();
                try {
                    jsonObject.put("emp_aadhar",aadharno);
                    jsonObject.put("description",description);
                    jsonObject.put("timestamp",timestamp);
                    jsonObject.put("uploaded_by",Utility.getGramId(GramActivity.this));
                    jsonObject.put("photo",photo);
                    new PostDetail(Utility.URL1+"work/",jsonObject).execute();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });

        new GetAadharDetails("https://apitest.sewadwaar.rajasthan.gov.in/app/live/Service/BhamashahgetNregaDetails/01012016/2711?client_id=ad7288a4-7764-436d-a727-783a977f1fe1",new JSONObject()).execute();

    }

    private void setupviews() {

        edit_aadharno=(AutoCompleteTextView)findViewById(R.id.edit_aadharno);
        edit_description=(EditText)findViewById(R.id.edit_description);
        upload=(Button)findViewById(R.id.button_photo_upload);
        submit=(Button)findViewById(R.id.gram_submit);
        image_upload=(ImageView)findViewById(R.id.image_upload);

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CAMERA_REQUEST && resultCode == Activity.RESULT_OK) {
            photo = (Bitmap) data.getExtras().get("data");
            image_upload.setImageBitmap(photo);
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            photo.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
            byteArray = byteArrayOutputStream .toByteArray();
        }
    }


    public class GetAadharDetails extends AsyncTask<Void,Void,String>
    {
        String url;
        JSONObject jsonObject;

        public GetAadharDetails(String url, JSONObject jsonObject) {
            this.url=url;
            this.jsonObject=jsonObject;
        }

        @Override
        protected void onPostExecute(String s) {

            Log.v(getApplication().getClass().getSimpleName(),s);
            try {
                employees=new ArrayList<>();
                JSONObject nregadetails=new JSONObject(s);
                JSONArray topArray=nregadetails.getJSONArray("nregaDetailsList");
                String employees_autocomplete[]=new String[topArray.length()];
                for(int i=0;i<topArray.length();i++)
                {
                    Employee employee=new Employee();
                    JSONObject single_element=topArray.getJSONObject(i);
                    employee.setAadharno(single_element.getString("AADHAR_ID"));
                    employee.setName(single_element.getString("NAME"));
                    employee.setBhamashah_ack_id(single_element.getString("BHAMASHAH_ACK_ID"));
                    employee.setNaregano(single_element.getString("NAREGA_NO"));
                    employees_autocomplete[i]=single_element.getString("AADHAR_ID")+" "+single_element.getString("NAME");
                    employees.add(employee);


                }

                ArrayAdapter<String> adapter = new ArrayAdapter<String>(GramActivity.this,
                        android.R.layout.simple_dropdown_item_1line, employees_autocomplete);

                edit_aadharno.setAdapter(adapter);


            } catch (JSONException e) {
                e.printStackTrace();
            }


        }

        @Override
        protected String doInBackground(Void... voids) {
            Connection connection=new Connection(url,jsonObject,GramActivity.this);
            return connection.connectiontask();
        }

    }




    public class PostDetail extends AsyncTask<Void,Void,String>
    {
        String url;
        JSONObject jsonObject;

        public PostDetail(String url, JSONObject jsonObject) {
            this.url=url;
            this.jsonObject=jsonObject;
        }

        @Override
        protected void onPostExecute(String s) {

            Log.v(getApplication().getClass().getSimpleName(),s);
//
            if (s.contentEquals("failed"))
            {
                Toast.makeText(GramActivity.this,"Failed",Toast.LENGTH_LONG).show();
            }
            else
            {
                Toast.makeText(GramActivity.this,"Success",Toast.LENGTH_LONG).show();

                Intent intent=new Intent(GramActivity.this,GramActivity.class);
                finish();
                startActivity(intent);
            }



        }

        @Override
        protected String doInBackground(Void... voids) {
            ConnectionPost connection=new ConnectionPost(url,jsonObject,GramActivity.this);
            return connection.connectiontask();
        }

    }

}
