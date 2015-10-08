package com.logit.data.returntosender;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.logit.leon.returntosender.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;


public class MainActivity extends AppCompatActivity {

    private ListView listView;
    private FriendListAdapter fListAdapter;

    private FriendsIO friendsIO;
    private JSONObject userName;
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fla_layout);


        try {
			friendsIO = new FriendsIO(this);
		} catch (JSONException e) {
			e.printStackTrace();
			
			// hier könnte man dem User ein popup geben
			friendsIO = new FriendsIO();
		}

      listView = (ListView) findViewById(R.id.listLV);
      fListAdapter = new FriendListAdapter(this, friendsIO);
      listView.setAdapter(fListAdapter);
      
      listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                                          @Override
                                          public void onItemClick(AdapterView parentView, View childView, int position, long id) {
                                              System.err.println("clicked " + position);
                                              System.err.println("clicked " + friendsIO.getPresentationText(position));
                                              try {
                                                  JSONObject friendInformations = new JSONObject(friendsIO.getPresentationText(position));

                                                  final LayoutInflater peopleInflater = LayoutInflater.from(MainActivity.this);
                                                  final View peopleView = peopleInflater.inflate(R.layout.popup2, null);
                                                  final PopupWindow peoplePopup = new PopupWindow(peopleView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, true);
                                                  peoplePopup.showAtLocation(peopleView, Gravity.CENTER, 0, 0);
                                                  final String friendNames = friendInformations.getString("name");
                                                  System.err.println(friendNames);
                                                  TextView peopleName = (TextView) peopleView.findViewById(R.id.peoplePerson);
                                                  peopleName.setText(friendNames);


                                                  final String friendEmails = friendInformations.getString("email");
                                                  System.err.println(friendEmails);
                                                  String friendNumbers = friendInformations.getString("phone number");
                                                  System.err.println(friendNumbers);
                                                  String friendEmailsNumbers = friendEmails + "\n" + friendNumbers;
                                                  TextView peopleInformations = (TextView) peopleView.findViewById(R.id.peopleInfo);
                                                  peopleInformations.setText(friendEmailsNumbers);
                                                  final String[] friendsEmails = new String[]{friendEmails};
                                                  final Button sendEmail = (Button) peopleView.findViewById(R.id.send_email);
                                                  sendEmail.setOnClickListener(new View.OnClickListener() {
                                                                                   public void onClick(View v) {
                                                                                       peoplePopup.dismiss();
                                                                                       String yourName = friendsIO.getUserName();
                                                                                       Intent i = new Intent("com.logit.data.returntosender.CHOOSE_IT");
                                                                                       i.putExtra("friendsEmails", friendsEmails);
                                                                                       i.putExtra("friendsNames", friendNames);
                                                                                       i.putExtra("userName", yourName);
                                                                                       startActivity(i);
                                                                                   }
                                                                               }
                                                  );
                                                  final Button peopleBack = (Button) peopleView.findViewById(R.id.cancel_button);
                                                  peopleBack.setOnClickListener(new View.OnClickListener() {
                                                                                    public void onClick(View v) {
                                                                                        peoplePopup.dismiss();
                                                                                    }
                                                                                }
                                                  );

                                              } catch (Exception e) {
                                                  e.printStackTrace();
                                              }


                                          }

                                      }
      );
      
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.add_Friends) {
            //popup asking for email
            final LayoutInflater inflater = LayoutInflater.from(MainActivity.this);
            final View v1 = inflater.inflate(R.layout.popup, null);
            final PopupWindow pw = new PopupWindow(v1, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, true);
            pw.showAtLocation(v1, Gravity.CENTER, 0, 0);
            final Button button1 = (Button) v1.findViewById(R.id.ok_button);
            button1.setOnClickListener(new View.OnClickListener() {
                                           public void onClick(View v) {
                                               EditText input_name = (EditText) v1.findViewById(R.id.name);
                                               String name = input_name.getText().toString();
                                               EditText input_email = (EditText) v1.findViewById(R.id.email_address);
                                               String email = input_email.getText().toString();
                                               EditText input_number = (EditText) v1.findViewById(R.id.phone_number);
                                               String number = input_number.getText().toString();

                                               friendsIO.addFriend(name, number, email);
                                               pw.dismiss();
                                           }
                                       }
            );
            final Button buttonC = (Button) v1.findViewById(R.id.stop_it);
            buttonC.setOnClickListener(new View.OnClickListener() {
                                           public void onClick(View v) {
                                               pw.dismiss();
                                           }
                                       }
            );
            final Button buttonDelete = (Button) v1.findViewById(R.id.delete_all);
            buttonDelete.setOnClickListener(new View.OnClickListener() {
                                                public void onClick(View v) {
                                                    pw.dismiss();
                                                    final LayoutInflater deleteInflater = LayoutInflater.from(MainActivity.this);
                                                    final View deleteView = deleteInflater.inflate(R.layout.delete_popup, null);
                                                    final PopupWindow deletePopup = new PopupWindow(deleteView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, true);
                                                    deletePopup.showAtLocation(deleteView, Gravity.CENTER, 0, 0);
                                                    final Button iWantToDeleteIt = (Button) deleteView.findViewById(R.id.continue_delete);
                                                    iWantToDeleteIt.setOnClickListener(new View.OnClickListener() {
                                                                                           public void onClick(View v) {
                                                                                               try {
                                                                                                   friendsIO.delete();
                                                                                               } catch (IOException e) {
                                                                                                   e.printStackTrace();
                                                                                               }
                                                                                               deletePopup.dismiss();
                                                                                           }


                                                                                       }
                                                    );
                                                    final Button doNotDelete = (Button) deleteView.findViewById(R.id.cancel_delete);
                                                    doNotDelete.setOnClickListener(new View.OnClickListener() {
                                                                                       public void onClick(View v) {
                                                                                           deletePopup.dismiss();
                                                                                       }
                                                                                   }
                                                    );
                                                }
                                            }
            );


        }


        return super.onOptionsItemSelected(item);
    }
}
