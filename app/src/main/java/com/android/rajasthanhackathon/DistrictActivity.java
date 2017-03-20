package com.android.rajasthanhackathon;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.rajasthanhackathon.connectionutils.Connection;
import com.android.rajasthanhackathon.connectionutils.ConnectionPut;
import com.android.rajasthanhackathon.connectionutils.Utility;
import com.android.rajasthanhackathon.models.Employee;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;

public class DistrictActivity extends AppCompatActivity {

    TextView aadharno,description,uploaded_by;
    ImageView photo;
    int id;
    Button verify;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_district);
        setupviews();
        final Employee employee= (Employee)getIntent().getSerializableExtra("item");
        aadharno.setText("Aadhar No: "+employee.getAadharno());
        description.setText("Description: "+employee.getDescription());
        uploaded_by.setText("Gram Panchayat: "+employee.getUploaded_by());
        String image_base64=employee.getPhoto();
        id=employee.getId();

        InputStream stream = new ByteArrayInputStream(Base64.decode(image_base64.getBytes(), Base64.DEFAULT));
        Bitmap photo_encoded= BitmapFactory.decodeStream(stream);

        photo.setImageBitmap(photo_encoded);

        verify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                JSONObject verify_work=new JSONObject();

                try {
                    verify_work.put("emp_aadhar",employee.getAadharno());
                    verify_work.put("description",employee.getDescription());
                    verify_work.put("id",id);
                    verify_work.put("photo",employee.getPhoto());
                    verify_work.put("timestamp",employee.getTimestamp());
                    verify_work.put("uploaded_by",employee.getUploaded_by());
                    verify_work.put("verified_by",Utility.getDistrictId(DistrictActivity.this));
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                new VerifyWork(Utility.URL1+"work/"+id+"/",verify_work).execute();
            }
        });

    }


    private void setupviews() {

        aadharno=(TextView)findViewById(R.id.district_aadhar);
        description=(TextView)findViewById(R.id.district_description);
        uploaded_by=(TextView)findViewById(R.id.district_grampanchayat);
        photo=(ImageView)findViewById(R.id.district_photo);
        verify=(Button)findViewById(R.id.verify);

    }

    public class VerifyWork extends AsyncTask<Void,Void,String>
    {
        String url;
        JSONObject jsonObject;

        public VerifyWork(String url, JSONObject jsonObject) {
            this.url=url;
            this.jsonObject=jsonObject;
        }

        @Override
        protected void onPostExecute(String s) {



            Log.v(getApplication().getClass().getSimpleName(),s);
            try {
            JSONObject jsonObject=new JSONObject(s);
                Toast.makeText(DistrictActivity.this,"Success",Toast.LENGTH_LONG).show();
                finish();


            } catch (JSONException e) {
                e.printStackTrace();
                Toast.makeText(DistrictActivity.this,"Failure",Toast.LENGTH_LONG).show();

            }

        }

        @Override
        protected String doInBackground(Void... voids) {
            ConnectionPut connection=new ConnectionPut(url,jsonObject,DistrictActivity.this);
            return connection.connectiontask();
        }

    }

}
