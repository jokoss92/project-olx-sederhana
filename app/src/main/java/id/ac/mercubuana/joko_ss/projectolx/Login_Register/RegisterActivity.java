package id.ac.mercubuana.joko_ss.projectolx.Login_Register;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

import id.ac.mercubuana.joko_ss.projectolx.R;

public class RegisterActivity extends AppCompatActivity {

    FirebaseAuth auth;
    FirebaseUser user;
    DatabaseReference reference;
    EditText txtUsername, txtEmailReg, txtPassReg;
    Button btnRegister;
    ProgressDialog dialog;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        txtUsername = findViewById(R.id.txtUsername);
        txtEmailReg = findViewById(R.id.txtEmailReg);
        txtPassReg = findViewById(R.id.txtPassReg);
        btnRegister = findViewById(R.id.btnRegister);


        auth = FirebaseAuth.getInstance();
        user = FirebaseAuth.getInstance().getCurrentUser();
        dialog = new ProgressDialog(this);

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userName = txtUsername.getText().toString();
                String emailReg = txtEmailReg.getText().toString();
                String passReg = txtPassReg.getText().toString();

                if (userName.isEmpty() || emailReg.isEmpty() || passReg.isEmpty())
                {
                    Toast.makeText(RegisterActivity.this, "Form masih kosong", Toast.LENGTH_LONG).show();
                }
                else{
                    uploadData(userName, emailReg, passReg);
                }
            }
        });
    }

    private void uploadData(final String userName, final String emailReg, String passReg){
        dialog.setMessage("Loading");
        dialog.show();
        auth.createUserWithEmailAndPassword(emailReg, passReg)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful())
                        {
                            FirebaseUser user = auth.getCurrentUser();
                            assert user != null;
//                            user = FirebaseAuth.getInstance().getCurrentUser();
                            String userId = user.getUid();
                            reference = FirebaseDatabase.getInstance().getReference("User").child(userId);

//                            reference.child(userId);

                            HashMap<String, String> hashMap = new HashMap<>();
                            hashMap.put("userid", userId);
                            hashMap.put("username", userName);
                            hashMap.put("email", emailReg);
                            hashMap.put("imageProfile", "default");

                            reference.setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful())
                                    {
                                        Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                                        startActivity(intent);
                                        Toast.makeText(RegisterActivity.this, "Success Upload Data", Toast.LENGTH_LONG).show();
                                        dialog.dismiss();
                                    }
                                    else {
                                        Toast.makeText(RegisterActivity.this, "Gagal Upload Data", Toast.LENGTH_LONG).show();

                                    }
                                }
                            });

                        }
                        else
                        {
                            Toast.makeText(RegisterActivity.this, "Error Upload", Toast.LENGTH_LONG).show();
                        }
                    }
                });

    }
}
