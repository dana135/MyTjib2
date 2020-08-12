package com.androidapp.mytjib.buy_tickets;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import androidx.core.content.ContextCompat;

import com.androidapp.mytjib.R;

import java.util.ArrayList;
import java.util.List;

public class SeatAdapter extends BaseAdapter {

    public List<Ticket> tickets;
 //   public List<Ticket> selectedTickets;
    public List<Integer> selectedTickets;
    public Context context;

    public SeatAdapter(Context context) {
        this.selectedTickets = new ArrayList<>();
        this.tickets = new ArrayList<>();
        this.context = context;
    }

    @Override
    public int getCount() {
        return tickets.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View v = LayoutInflater.from(context).inflate(R.layout.seat_data, null);
        final Ticket seatTicket = tickets.get(i);
        ImageView seatImage = v.findViewById(R.id.seat_img);

        if(seatTicket.getStatus().equals("unavailable")){
            seatImage.setColorFilter(ContextCompat.getColor(context, R.color.graySeat));
        }
        if(seatTicket.getStatus().equals("selected")){
            seatImage.setColorFilter(ContextCompat.getColor(context, R.color.greenSeat));
        }
        return v;
    }

    public void setTickets(List<Ticket> tickets){
        this.tickets = tickets;
        notifyDataSetChanged(); // refresh the UI
    }

    public void selectTicket(int i){
        Ticket t = tickets.get(i);

        if(t.getStatus().equals("available")){
            t.setStatus("selected");
            selectedTickets.add(tickets.get(i).getId());
        }
        else if(t.getStatus().equals("selected")){
            t.setStatus("available");
            selectedTickets.remove(tickets.get(i));
        }
        notifyDataSetChanged(); // refresh the UI

    }

    public List<Integer> getSelectedTickets(){
        return selectedTickets;
    }
}
