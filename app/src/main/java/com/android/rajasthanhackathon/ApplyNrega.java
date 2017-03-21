package com.android.rajasthanhackathon;

import android.app.Activity;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.StringReader;
import java.util.Calendar;

public class ApplyNrega extends AppCompatActivity {

    EditText aadharno,name,jobcardnumber,address,gender;
    Button scan;
    static TextView date;
    String text_aadharno,text_name,text_gender,text_address,text_district,text_state,text_dob;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apply_nrega);
        setupviews();

        scan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new IntentIntegrator(ApplyNrega.this).initiateScan();
            }
        });



    }

    private void setupviews() {
        aadharno=(EditText)findViewById(R.id.edit_aadharno);
        scan=(Button)findViewById(R.id.gram_scan);
        name=(EditText)findViewById(R.id.edit_name);
        address=(EditText)findViewById(R.id.edit_address);
        jobcardnumber=(EditText)findViewById(R.id.edit_address);
        gender=(EditText)findViewById(R.id.edit_gender);
        date=(TextView)findViewById(R.id.edit_date);


    }

    public void showDatePickerDialog(View v) {
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getSupportFragmentManager(), "datePicker");
    }



    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);


        if (resultCode == Activity.RESULT_OK) {
            // Parsing bar code reader result
            IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);
            //Toast.makeText(this,"its working"+result.getContents(),Toast.LENGTH_LONG).show();
            Log.v("Scanner",result.getContents());
            XmlPullParserFactory factory = null;
            try {
                factory = XmlPullParserFactory.newInstance();
                factory.setNamespaceAware(true);
                XmlPullParser xpp = factory.newPullParser();
                StringBuilder sb=new StringBuilder();
                xpp.setInput(new StringReader(result.getContents()));
                int eventType = xpp.getEventType();
                while (eventType != XmlPullParser.END_DOCUMENT) {
                    if(eventType == XmlPullParser.START_DOCUMENT) {
                        // sb.append("startdocument"+"\n");
                    } else if(eventType == XmlPullParser.END_DOCUMENT) {
                        //sb.append("enddocument" + "\n");
                    } else if(eventType == XmlPullParser.START_TAG&&xpp.getName().equals("PrintLetterBarcodeData")) {
                        // <?xml version="1.0" encoding="UTF-8"?> <PrintLetterBarcodeData uid="841801324797" name="Shivam Jindal" gender="M" yob="1996" co="S/O Jai Bhagwan Jindal" lm="old hospital road" loc="142- palia kalan" vtc="Palia Kalan" po="Palia" dist="Kheri" state="Uttar Pradesh" pc="262902" dob="1996-08-11"/>
                        text_aadharno=xpp.getAttributeValue(0);
                        text_name=xpp.getAttributeValue(1);
                        text_gender=xpp.getAttributeValue(2);
                        text_address=xpp.getAttributeValue(6);

                    } else if(eventType == XmlPullParser.END_TAG) {
                        // sb.append("End tag " + xpp.getName() + "\n");
                    } else if(eventType == XmlPullParser.TEXT) {
                        // sb.append("Text " + xpp.getText() + "\n");
                    }
                    eventType = xpp.next();
                }



            } catch (XmlPullParserException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            aadharno.setText(text_aadharno);
            name.setText(text_name);
            gender.setText(text_gender);
            address.setText(text_address);

            
        }


    }



    public static class DatePickerFragment extends DialogFragment
            implements DatePickerDialog.OnDateSetListener {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current date as the default date in the picker
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);

            // Create a new instance of DatePickerDialog and return it
            return new DatePickerDialog(getActivity(), this, year, month, day);
        }

        public void onDateSet(DatePicker view, int year, int month, int day) {

            // Do something with the date chosen by the user
            date.setText(""+year+"-"+month+"-"+day);

        }
    }
}
