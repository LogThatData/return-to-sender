package com.logit.data.returntosender;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedList;
import java.util.List;


public class FriendsIO {

    private static final String FILENAME = "Names_Mails_Numbers";
    private static final String NAMELOCATION = "Your_Name";
    private JSONObject friends;
    private Context context;

    public FriendsIO(Context c) throws JSONException {

        context = c;

        FileInputStream fis;
        try {
            fis = c.openFileInput(FILENAME);
            String readedBytes = new String(readAllBytes(fis), "UTF-8");
            fis.close();
            friends = new JSONObject(readedBytes);

        } catch (FileNotFoundException e) {
            // 1st start
        } catch (Exception e) {
            e.printStackTrace();
        }



        if (friends == null) {
            friends = new JSONObject();
            friends.put("array", new JSONArray());
        }
    }


    public FriendsIO() {
        try {
            friends = new JSONObject();
            friends.put("array", new JSONArray());
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private static byte[] readAllBytes(InputStream inputStream)
            throws IOException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int size;
        while ((size = inputStream.read(buffer)) != -1) {
            bos.write(buffer, 0, size);
        }
        bos.flush();
        return bos.toByteArray();
    }

    private void save() throws IOException {
        FileOutputStream fos = context.openFileOutput(FILENAME,
                Context.MODE_PRIVATE);
        fos.write(friends.toString().getBytes());
        fos.close();
    }

    public void delete() throws IOException {
        context.deleteFile(FILENAME);
    }

    public void addFriend(String name, String number, String email) {

        try {
            JSONObject friend = new JSONObject();

            friend.put("name", name);
            friend.put("phone number", number);
            friend.put("email", email);

            JSONArray array = friends.getJSONArray("array");
            array.put(friend);

            save();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public int getCount() {
        try {
            return friends.getJSONArray("array").length();
        } catch (JSONException e) {
            e.printStackTrace();
            return 0;
        }
    }

    public String getPresentationText(int index) {
        try {
            return friends.getJSONArray("array").getJSONObject(index).toString();
        } catch (JSONException e) {
            e.printStackTrace();
            return "error";
        }
    }

    public String getUserName() {
        AccountManager manager = AccountManager.get(context);
        Account[] accounts = manager.getAccountsByType("com.google");
        List<String> possibleEmails = new LinkedList<String>();

        for (Account account : accounts) {
            possibleEmails.add(account.name);
        }

        if (!possibleEmails.isEmpty() && possibleEmails.get(0) != null) {
            String email = possibleEmails.get(0);
            String[] parts = email.split("@");

            if (parts.length > 1)
                return parts[0];
        }
        return null;

    }



    // public String printAllBytes(Context c) {
    // try {
    // FileInputStream fis = c.openFileInput(FILENAME);
    // String readedBytes = new String(readAllBytes(fis), "UTF-8");
    // System.err.println("XXX " + readedBytes);
    // return readedBytes;
    //
    // } catch (Exception e) {
    // e.printStackTrace();
    // return null;
    // }
    //
    // }

}