package com.example.user.android_assignment_11_4;
//Package objects contain version information about the implementation and specification of a Java package.
import android.app.AlertDialog;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    //public is a method and fields can be accessed by the members of any class.
    //class is a collections of objects.
    //created MainActivity and extends with AppCompatActivity which is Parent class.

    DbHelper myDb;
    EditText firstName,lastName ,editTextId;
    Button btnAddData;
    Button btnviewAll;
    //Intializing the values.

    @Override
    //we use override to tells the compiler that the following method overrides a method of its superclass.
    protected void onCreate(Bundle savedInstanceState) {
        //protected can be accessed by within the package and class and subclasses
        //The Void class is an uninstantiable placeholder class to hold a reference to the Class object
        //representing the Java keyword void.
        //The savedInstanceState is a reference to a Bundle object that is passed into the onCreate method of every Android Activity
        // Activities have the ability, under special circumstances, to restore themselves to a previous state using the data stored in this bundle.
        super.onCreate(savedInstanceState);
        //Android class works in same.You are extending the Activity class which have onCreate(Bundle bundle) method in which meaningful code is written
        //So to execute that code in our defined activity. You have to use super.onCreate(bundle)
        setContentView(R.layout.activity_main);
        //R means Resource
        //layout means design
        //main is the xml you have created under res->layout->main.xml
        //Whenever you want to change your current Look of an Activity or when you move from one Activity to another .
        //he other Activity must have a design to show . So we call this method in onCreate and this is the second statement to set
        //the design

        myDb = new DbHelper(this);
// creating the object for main activity
        firstName = (EditText)findViewById(R.id.editText);
        lastName = (EditText)findViewById(R.id.editTextl);
        btnAddData = (Button)findViewById(R.id.button_add);
        btnviewAll = (Button)findViewById(R.id.button_viewAll);
        //editText:A user interface element for entering and modifying text.
        //findViewById:A user interface element that displays text to the user.

        AddData();
        //adding the data
        viewAll();
        //view all the data
    }
    public  void AddData() {
        btnAddData.setOnClickListener(
                //onclick of the adddata button the data will get added in database
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        boolean isInserted = myDb.insertData(firstName.getText().toString(),
                                lastName.getText().toString());
                        //we add values by converting them into string
                        if(isInserted == true)
                            //if added the it will show dara is inserted otherwise no
                            Toast.makeText(MainActivity.this,"Data Inserted",Toast.LENGTH_LONG).show();
                        else
                            Toast.makeText(MainActivity.this,"Data not Inserted", Toast.LENGTH_LONG).show();
                    }
                }
        );
    }

    public void viewAll() {
        //onclick of viewAll it will show the database whic we added
        btnviewAll.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Cursor res = myDb.getAllData();
                        // //This interface provides random read-write access to the result set returned by a database query.
                        //here we will get the data
                        //if count is 0 then it will show the message
                        if(res.getCount() == 0) {
                            // show message
                            showMessage("Error","Nothing found");
                            return;
                        }
                        StringBuffer buffer = new StringBuffer();
                        // The StringBuffer class is used to represent characters that can be modified.
                        while (res.moveToNext()) {
                            //once o char is checked the it need to be moved to next char
                            buffer.append("Id :"+ res.getString(0)+"\n");
                            buffer.append("firstName :"+ res.getString(1)+"\n");
                            buffer.append("lastName :"+ res.getString(2)+"\n");
                            //this method returns a reference to this object.
                        }
                        showMessage("Data",buffer.toString());
                        // Show all data
                    }
                }
        );
    }

    public void showMessage(String title,String Message){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        //to showMessage we need a alert dialog box
        //Creates a builder for an alert dialog that uses the default alert dialog theme.
        builder.setCancelable(true);
        //Sets whether this dialog is cancelable with the BACK key.
        builder.setTitle(title);
        //set the title
        builder.setMessage(Message);
        //set the message
        builder.show();
        //view the data
    }


    @Override
    //we use override to tells the compiler that the following method overrides a method of its superclass.
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        // Inflate the menu; this adds items to the action bar if it is present.
        return true;
    }

    @Override
    //we use override to tells the compiler that the following method overrides a method of its superclass.
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            //noinspection SimplifiableIfStatement
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
