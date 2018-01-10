package cs.b07.cscb07courseproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import client.ClientDateBase;
import exception.NoSuchClientException;

/**
 * Login class for user.
 */
public class Login extends AppCompatActivity{

    public static final String USER_MESSAGE = "USERNAME";
    private ClientDateBase clientDB;
    private EditText username;
    private EditText pass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        username = (EditText) findViewById(R.id.loginEmail);
        pass = (EditText)findViewById(R.id.loginPassword);
        clientDB = new ClientDateBase(this);
    }

    /**
     * Try's to login and opens up client and admin activties.
     * @param view gets the layout
     */
    public void tryLogin(View view){
        if(username.getText().toString().equals(getResources().getString(R.string.adminuser))){
            if(pass.getText().toString().equals(getResources().getString(R.string.adminpass))){
                Intent intent = new Intent(this, AdminActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                intent.putExtra(USER_MESSAGE, getResources().getString(R.string.adminuser));
                startActivity(intent);
            }else{
                Toast.makeText(this, "Invalid Admin Login",
                        Toast.LENGTH_SHORT).show();
            }
        }else{
            try{
                String emailClient = clientDB.isLogin(username.getText().toString(),
                        pass.getText().toString());
                Intent intent = new Intent(this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                intent.putExtra(USER_MESSAGE, emailClient);
                startActivity(intent);
            }catch (NoSuchClientException e){
                Toast.makeText(this, "Invalid Email or Password",
                        Toast.LENGTH_SHORT).show();
            }
        }
    }
}


