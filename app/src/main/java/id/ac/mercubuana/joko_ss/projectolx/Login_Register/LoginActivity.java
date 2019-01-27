package id.ac.mercubuana.joko_ss.projectolx.Login_Register;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

import id.ac.mercubuana.joko_ss.projectolx.MainActivity;
import id.ac.mercubuana.joko_ss.projectolx.R;

public class LoginActivity extends AppCompatActivity {

    TextView txtActionReg;
    EditText txtEmail, txtPassword;
    Button btnLogin;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);



        txtActionReg = findViewById(R.id.txtRegister);

        txtActionReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });

    }
}
