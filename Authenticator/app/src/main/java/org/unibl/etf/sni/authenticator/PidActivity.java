package org.unibl.etf.sni.authenticator;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.unibl.etf.sni.bean.SessionBean;
import org.unibl.etf.sni.task.DocumentsTask;
import org.unibl.etf.sni.task.PidTask;

public class PidActivity extends AppCompatActivity {
    private EditText choosenPid;
    private RecyclerView documentsView;
    private SessionBean bean;
    private Button btnPid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pid);
        choosenPid=findViewById(R.id.chosenPid);
        documentsView=findViewById(R.id.restView);
        btnPid=findViewById(R.id.btnPid);
        bean=(SessionBean) getIntent().getSerializableExtra("BEAN");
        if(!bean.getAdmin()){
            btnPid.setVisibility(View.GONE);
            choosenPid.setVisibility(View.GONE);
            new PidTask(bean, bean.getPid(), documentsView, this).execute();
        }
    }

    public void getByPid(View view){
            String pid = choosenPid.getText().toString();
            if (pid.length() == 13 && pid.matches("^[0-9]{13}$")) {
                new PidTask(bean, pid, documentsView, this).execute();
            } else {
                Toast.makeText(this, "Person id is 13 digits long", Toast.LENGTH_LONG);
                choosenPid.getText().clear();
            }
    }
}
