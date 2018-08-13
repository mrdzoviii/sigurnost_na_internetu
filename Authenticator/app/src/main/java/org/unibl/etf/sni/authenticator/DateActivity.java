package org.unibl.etf.sni.authenticator;

import android.app.DatePickerDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;

import org.unibl.etf.sni.bean.SessionBean;
import org.unibl.etf.sni.task.DocumentsTask;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class DateActivity extends AppCompatActivity {

    private EditText choosenDate;
    private RecyclerView documentsView;
    private Calendar myDate=Calendar.getInstance();
    private static final SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_date);
        choosenDate=findViewById(R.id.chosenDate);
        documentsView=findViewById(R.id.documentsView);

        choosenDate.setKeyListener(null);
        choosenDate.setInputType(InputType.TYPE_NULL);

        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myDate.set(Calendar.YEAR, year);
                myDate.set(Calendar.MONTH, monthOfYear);
                myDate.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }

        };

        choosenDate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(DateActivity.this, date, myDate
                        .get(Calendar.YEAR), myDate.get(Calendar.MONTH),
                        myDate.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

    }

    public void getByDate(View view){
        SessionBean bean= (SessionBean) getIntent().getSerializableExtra("BEAN");
        new DocumentsTask(bean,choosenDate.getText().toString(),documentsView,this).execute();
    }

    private void updateLabel() {
        choosenDate.setText(
                sdf.format(myDate.getTime()));
    }



}
