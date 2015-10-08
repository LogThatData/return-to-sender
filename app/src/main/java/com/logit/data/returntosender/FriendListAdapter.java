package com.logit.data.returntosender;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.logit.leon.returntosender.R;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by AdminProgram on 30/09/2015.
 */
public class FriendListAdapter extends BaseAdapter {

    private Activity context;
    private FriendsIO friendsIO;
    
    public FriendListAdapter(Activity _context, FriendsIO fio) {
        context = _context;
        friendsIO = fio;
    }
    
    @Override
    public int getCount()
    {
    	return friendsIO.getCount();
    }

    public String getText(int index)
    {

        String friendsNames = null;
        try {
            JSONObject friendsInformations = new JSONObject(friendsIO.getPresentationText(index));
            friendsNames = friendsInformations.getString("name");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return friendsNames;
    }

    @Override
    public Object getItem(int position)
    {
        return position;
    }

    @Override
    public long getItemId(int position)
    {
        return position;
    }

    @Override
    public View getView(int position, View reusableView, ViewGroup parent)
    {
        if (reusableView == null) {
            LayoutInflater li = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            reusableView = li.inflate(R.layout.listrow, null);

        }

        ((TextView)reusableView).setText(getText(position));
        return reusableView;
    }
}
