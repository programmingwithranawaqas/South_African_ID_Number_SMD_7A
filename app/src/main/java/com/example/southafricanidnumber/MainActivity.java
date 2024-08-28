package com.example.southafricanidnumber;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    // Hooks
    Button btnSubmit, btnClear;
    TextView tvResult;
    EditText etId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String id = etId.getText().toString().trim();
                if(TextUtils.isEmpty(id))
                {
                    //etId.setError("ID can't be empty");
                    Toast.makeText(MainActivity.this, getString(R.string.id_can_t_be_empty), Toast.LENGTH_SHORT).show();
                    return;
                }
                if(id.length()!=13) {
                    etId.setError("Length must be 13");
                    return;
                }

               String resulted = parseId(id);
                tvResult.setText(resulted);

            }
        });

    }

    private String parseId(String id)
    {
        String dob = id.substring(0, 6);
        String year = "19"+dob.substring(0,2);
        String []months = {"","January", "Feb", "March", "April", "May", "June", "July", "August", "Sept", "Oct", "Nov", "Dec"};
        String m = months[Integer.parseInt(dob.substring(2, 4))];
        dob = id.substring(4, 6)+" "+m+" "+year;

        String gender = ((Integer.parseInt(id.substring(6, 10))<5000)?"Female":"Male");
        String citizenship = (Boolean.parseBoolean(id.charAt(10)+""))?"Permanent Resident":"SA Citizen";
        String validity;
        if(id.charAt(12)!='0')
            validity = "Valid";
        else
            validity="Invalid";

        return dob+"\n"+gender+"\n"+citizenship+"\n"+validity+"\n";
    }

    private void init()
    {
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        btnSubmit = findViewById(R.id.btnSubmit);
        btnClear = findViewById(R.id.btnClear);
        etId = findViewById(R.id.etId);
        tvResult = findViewById(R.id.tvResult);
    }
}