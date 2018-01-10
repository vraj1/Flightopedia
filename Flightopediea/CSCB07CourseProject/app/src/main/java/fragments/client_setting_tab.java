package fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.text.ParseException;

import client.Client;
import client.ClientDateBase;
import cs.b07.cscb07courseproject.MainActivity;
import cs.b07.cscb07courseproject.R;
import exception.NoSuchClientException;

/**
 * Class for the client for the tab that holds all the settings.
 */
public class client_setting_tab extends Fragment implements View.OnClickListener {

    private EditText email_change;
    private EditText pass_change;
    private EditText fname_change;
    private EditText lname_change;
    private EditText address_change;
    private EditText cc_change;
    private EditText exp_change;
    private Button client_updrage;

    private ClientDateBase clientdb;
    private Client client;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.client_setting_tab,container,false);


        clientdb = new ClientDateBase(getActivity());
        try{
            client = clientdb.getClient(MainActivity.account);
        }catch (NoSuchClientException e){
            e.printStackTrace();
        }

        email_change = (EditText)view.findViewById(R.id.email_edit);
        pass_change = (EditText)view.findViewById(R.id.pass_edit);
        fname_change = (EditText)view.findViewById(R.id.first_name);
        lname_change = (EditText)view.findViewById(R.id.last_name);
        address_change = (EditText)view.findViewById(R.id.address);
        cc_change = (EditText)view.findViewById(R.id.creditcard);
        exp_change = (EditText)view.findViewById(R.id.exp_date);
        updateHint();
        client_updrage = (Button)view.findViewById(R.id.client_button);
        client_updrage.setOnClickListener(this);
        return view;
    }

    /**
     * Used to change/update client information.
     */
    private void updateHint(){
        email_change.setHint("Email: "+client.getEmail());
        pass_change.setHint("Password: " + client.getPassword());
        fname_change.setHint("FirstName: " +client.getFirstName());
        lname_change.setHint("Last_Name: " + client.getLastName());
        address_change.setHint("Address: " + client.getAddress());
        cc_change.setHint("CreditCard: " + client.getCreditCardNumber());
        exp_change.setHint("Exp Date: " + client.getExpiryDate());
    }
    @Override
    public void onClick(View view){
        Toast.makeText(getActivity(), "Client Info Updated",
                Toast.LENGTH_SHORT).show();
        if(!client.getPassword().equals(pass_change.getText().toString())
                && !pass_change.getText().toString().equals("")){
            client.setPassword(pass_change.getText().toString());
        }

        if(!client.getFirstName().equals(fname_change.getText().toString())
                && !fname_change.getText().toString().equals("")){
            client.setFirstName(fname_change.getText().toString());
        }

        if(!client.getLastName().equals(lname_change.getText().toString())
                && !lname_change.getText().toString().equals("")){
            client.setLastName(lname_change.getText().toString());
        }

        if(!client.getAddress().equals(address_change.getText().toString())
                && !address_change.getText().toString().equals("")){
            client.setAddress(address_change.getText().toString());
        }

        if(!client.getCreditCardNumber().equals(cc_change.getText().toString())
                && !cc_change.getText().toString().equals("")){
            client.setCreditCardNumber(cc_change.getText().toString());
        }
        if(!client.getExpiryDate().equals(exp_change.getText().toString())
                && !exp_change.getText().toString().equals("")){
            try{
                client.setExpiryDate(exp_change.getText().toString());
            }catch (ParseException e){
                e.printStackTrace();
            }
        }

        if(!client.getEmail().equals(email_change.getText().toString())
                && !email_change.getText().toString().equals("")){
            clientdb.deleteClient(client.getEmail());
            client.setEmail(email_change.getText().toString());
            clientdb.addClient(client);
        }else{
            clientdb.update(client,null);
        }

        email_change.setText("");
        pass_change.setText("");
        fname_change.setText("");
        lname_change.setText("");
        address_change.setText("");
        cc_change.setText("");
        exp_change.setText("");
        updateHint();
    }
}
