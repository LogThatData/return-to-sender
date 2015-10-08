package com.logit.data.returntosender;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.Toast;

import com.logit.leon.returntosender.R;

/**
 * Created by AdminProgram on 2/10/2015.
 */
public class ChooseEmailTypeActivity extends AppCompatActivity {

    private String[] emailToSendTo;
    private String friendToSendTo;
    private String userName;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.email_type_activity);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String[] _value = extras.getStringArray("friendsEmails");
            String _name = extras.getString("friendsNames");
            String _userName = extras.getString("userName");
            emailToSendTo = _value;
            friendToSendTo = _name;
            userName = _userName;
        }
        final Button sorryButton = (Button) findViewById(R.id.sorry_button);
        sorryButton.setOnClickListener(new View.OnClickListener() {
                                           public void onClick(View v) {
                                               System.err.println("sorry, " + friendToSendTo + ", " + emailToSendTo);
                                               final LayoutInflater apologyInflater = LayoutInflater.from(ChooseEmailTypeActivity.this);
                                               final View apologyView = apologyInflater.inflate(R.layout.sorry_popup, null);
                                               final PopupWindow apologyPopup = new PopupWindow(apologyView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, true);
                                               apologyPopup.showAtLocation(apologyView, Gravity.CENTER, 0, 0);

                                               final EditText apologySubject = (EditText) apologyView.findViewById(R.id.cancel_event_subject);
                                               final EditText apologyReason = (EditText) apologyView.findViewById(R.id.reason_for_apology);
                                               final Button sorryContinueButton = (Button) apologyView.findViewById(R.id.ok_apology);
                                               sorryContinueButton.setOnClickListener(new View.OnClickListener() {
                                                                                          public void onClick(View v) {
                                                                                              String subjectOfMail = apologySubject.getText().toString();
                                                                                              String reasonOfMail = apologyReason.getText().toString();
                                                                                              String senderName = userName;
                                                                                              String bodyOfMail = ("Dear " + friendToSendTo + ", I am sorry, that " + reasonOfMail + "\n \n Yours sincerely, \n " + senderName);

                                                                                              Intent i = new Intent(Intent.ACTION_SEND);
                                                                                              i.setType("message/rfc822");
                                                                                              i.putExtra(Intent.EXTRA_EMAIL, emailToSendTo);
                                                                                              i.putExtra(Intent.EXTRA_SUBJECT, subjectOfMail);
                                                                                              i.putExtra(Intent.EXTRA_TEXT, bodyOfMail);
                                                                                              try {
                                                                                                  startActivity(Intent.createChooser(i, "Send mail..."));
                                                                                              } catch (android.content.ActivityNotFoundException ex) {
                                                                                                  Toast.makeText(ChooseEmailTypeActivity.this, "There are no email clients installed.", Toast.LENGTH_SHORT).show();
                                                                                              }
                                                                                              apologyPopup.dismiss();


                                                                                          }
                                                                                      }
                                               );
                                               final Button sorryCancelButton = (Button) apologyView.findViewById(R.id.cancel_apology);
                                               sorryCancelButton.setOnClickListener(new View.OnClickListener() {
                                                                                        public void onClick(View v) {
                                                                                            apologyPopup.dismiss();
                                                                                        }
                                                                                    }
                                               );

//
                                           }
                                       }
        );
        final Button inviteButton = (Button) findViewById(R.id.invite_button);
        inviteButton.setOnClickListener(new View.OnClickListener() {
                                            public void onClick(View v) {
                                                System.err.println("You're invited, " + friendToSendTo + ", " + emailToSendTo);
                                                final LayoutInflater invitationInflater = LayoutInflater.from(ChooseEmailTypeActivity.this);
                                                final View invitationView = invitationInflater.inflate(R.layout.invitation_popup, null);
                                                final PopupWindow invitationPopup = new PopupWindow(invitationView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, true);
                                                invitationPopup.showAtLocation(invitationView, Gravity.CENTER, 0, 0);

                                                final EditText invitationSubject = (EditText) invitationView.findViewById(R.id.invitation_subject);
                                                final EditText invitationReason = (EditText) invitationView.findViewById(R.id.what_invited_for);
                                                final EditText invitationTime = (EditText) invitationView.findViewById(R.id.invitation_time);
                                                final EditText invitationPlace = (EditText) invitationView.findViewById(R.id.invitation_place);
                                                final EditText rsvpDate = (EditText) invitationView.findViewById(R.id.RSVP_date);
                                                final EditText plusOne = (EditText) invitationView.findViewById(R.id.plus_one);
                                                final Button invitationContinueButton = (Button) invitationView.findViewById(R.id.ok_invitation);
                                                invitationContinueButton.setOnClickListener(new View.OnClickListener() {
                                                                                                public void onClick(View v) {
                                                                                                    String when = invitationTime.getText().toString();
                                                                                                    String where = invitationPlace.getText().toString();
                                                                                                    String rsvp = rsvpDate.getText().toString();
                                                                                                    String plusAnyone = plusOne.getText().toString();
                                                                                                    String what = invitationReason.getText().toString();
                                                                                                    String subjectOfMail = invitationSubject.getText().toString();
                                                                                                    String senderName = userName;
                                                                                                    Intent i = new Intent(Intent.ACTION_SEND);
                                                                                                    i.setType("message/rfc822");
                                                                                                    i.putExtra(Intent.EXTRA_EMAIL, emailToSendTo);
                                                                                                    i.putExtra(Intent.EXTRA_SUBJECT, subjectOfMail);
                                                                                                    String bodyOfMail = ("Dear " + friendToSendTo + ", \n \n You are hereby invited to the " + what + ", taking place at " + where + ", on " + when + ". You can bring " + plusAnyone + " people with you, but please answer until " + rsvp + ". \n \n Sincerely, \n" + senderName);
                                                                                                    i.putExtra(Intent.EXTRA_TEXT, bodyOfMail);
                                                                                                    try {
                                                                                                        startActivity(Intent.createChooser(i, "Send mail..."));
                                                                                                    } catch (ActivityNotFoundException ex) {
                                                                                                        Toast.makeText(ChooseEmailTypeActivity.this, "There are no email clients installed.", Toast.LENGTH_SHORT).show();
                                                                                                    }
                                                                                                    invitationPopup.dismiss();
                                                                                                }
                                                                                            }
                                                );
                                                final Button inviteCancelButton = (Button) invitationView.findViewById(R.id.cancel_invitation);
                                                inviteCancelButton.setOnClickListener(new View.OnClickListener() {
                                                                                         public void onClick(View v) {
                                                                                             invitationPopup.dismiss();
                                                                                         }
                                                                                     }
                                                );
                                            }
                                        }
        );
        final Button sickButton = (Button) findViewById(R.id.sick_button);
        sickButton.setOnClickListener(new View.OnClickListener() {
                                          public void onClick(View v) {
                                              System.err.println("I'm sick, " + friendToSendTo + ", " + emailToSendTo);
                                              final LayoutInflater sicknessInflater = LayoutInflater.from(ChooseEmailTypeActivity.this);
                                              final View sicknessView = sicknessInflater.inflate(R.layout.sickness_popup, null);
                                              final PopupWindow sicknessPopup = new PopupWindow(sicknessView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, true);
                                              sicknessPopup.showAtLocation(sicknessView, Gravity.CENTER, 0, 0);

                                              final EditText sicknessSubject = (EditText) sicknessView.findViewById(R.id.sickness_subject);
                                              final EditText sicknessReason = (EditText) sicknessView.findViewById(R.id.what_sickness);
                                              final EditText sicknessAbsence = (EditText) sicknessView.findViewById(R.id.length_of_absence);
                                              final Button sicknessContinueButton = (Button) sicknessView.findViewById(R.id.ok_sickness);
                                              sicknessContinueButton.setOnClickListener(new View.OnClickListener() {
                                                                                            public void onClick(View v) {
                                                                                                String subjectOfMail = sicknessSubject.getText().toString();
                                                                                                String reasonOfMail = sicknessReason.getText().toString();
                                                                                                String lengthOfAbsence = sicknessAbsence.getText().toString();
                                                                                                String senderName = userName;
                                                                                                String bodyOfMail = ("Dear " + friendToSendTo + ", \n I have recently developed a case of " + reasonOfMail + " and am therefore not fit to go to work for " + lengthOfAbsence + " \n \n Yours sincerely, \n " + senderName);

                                                                                                Intent i = new Intent(Intent.ACTION_SEND);
                                                                                                i.setType("message/rfc822");
                                                                                                i.putExtra(Intent.EXTRA_EMAIL, emailToSendTo);
                                                                                                i.putExtra(Intent.EXTRA_SUBJECT, subjectOfMail);
                                                                                                i.putExtra(Intent.EXTRA_TEXT, bodyOfMail);
                                                                                                try {
                                                                                                    startActivity(Intent.createChooser(i, "Send mail..."));
                                                                                                } catch (ActivityNotFoundException ex) {
                                                                                                    Toast.makeText(ChooseEmailTypeActivity.this, "There are no email clients installed.", Toast.LENGTH_SHORT).show();
                                                                                                }
                                                                                                sicknessPopup.dismiss();


                                                                                            }
                                                                                        }
                                              );
                                              final Button sicknessCancelButton = (Button) sicknessView.findViewById(R.id.cancel_sickness);
                                              sicknessCancelButton.setOnClickListener(new View.OnClickListener() {
                                                                                          public void onClick(View v) {
                                                                                              sicknessPopup.dismiss();
                                                                                          }
                                                                                      }
                                              );
                                          }
                                      }
        );
        final Button cancelButton = (Button) findViewById(R.id.stop_event_button);
        cancelButton.setOnClickListener(new View.OnClickListener() {
                                            public void onClick(View v) {
                                                System.err.println("Don't come, " + friendToSendTo + ", " + emailToSendTo);
                                                final LayoutInflater eventCancelInflater = LayoutInflater.from(ChooseEmailTypeActivity.this);
                                                final View eventCancelView = eventCancelInflater.inflate(R.layout.event_cancel_popup, null);
                                                final PopupWindow eventCancelPopup = new PopupWindow(eventCancelView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, true);
                                                eventCancelPopup.showAtLocation(eventCancelView, Gravity.CENTER, 0, 0);

                                                final EditText eventCancelSubject = (EditText) eventCancelView.findViewById(R.id.event_cancel_subject);
                                                final EditText eventToCancel = (EditText) eventCancelView.findViewById(R.id.event_to_cancel);
                                                final EditText eventCancelReason = (EditText) eventCancelView.findViewById(R.id.why_event_cancel);
                                                final Button eventCancelButton = (Button) eventCancelView.findViewById(R.id.ok_cancel_event);
                                                eventCancelButton.setOnClickListener(new View.OnClickListener() {
                                                                                         public void onClick(View v) {
                                                                                             String subjectOfMail = eventCancelSubject.getText().toString();
                                                                                             String eventToCancelOfMail = eventToCancel.getText().toString();
                                                                                             String reasonOfMail = eventCancelReason.getText().toString();
                                                                                             String senderName = userName;
                                                                                             String bodyOfMail = ("Dear " + friendToSendTo + ", I regret to inform you, that " + eventToCancelOfMail + " has been cancelled, due to " + reasonOfMail + ".\n \n Yours sincerely, \n " + senderName);
                                                                                             Intent i = new Intent(Intent.ACTION_SEND);
                                                                                             i.setType("message/rfc822");
                                                                                             i.putExtra(Intent.EXTRA_EMAIL, emailToSendTo);
                                                                                             i.putExtra(Intent.EXTRA_SUBJECT, subjectOfMail);
                                                                                             i.putExtra(Intent.EXTRA_TEXT, bodyOfMail);
                                                                                             try {
                                                                                                 startActivity(Intent.createChooser(i, "Send mail..."));
                                                                                             } catch (android.content.ActivityNotFoundException ex) {
                                                                                                 Toast.makeText(ChooseEmailTypeActivity.this, "There are no email clients installed.", Toast.LENGTH_SHORT).show();
                                                                                             }
                                                                                             eventCancelPopup.dismiss();


                                                                                         }
                                                                                     }
                                                );
                                                final Button eventCancelCancelButton = (Button) eventCancelView.findViewById(R.id.cancel_cancel_event);
                                                eventCancelCancelButton.setOnClickListener(new View.OnClickListener() {
                                                                                               public void onClick(View v) {
                                                                                                   eventCancelPopup.dismiss();
                                                                                               }
                                                                                           }
                                                );
                                            }
                                        }
        );


    }


}
