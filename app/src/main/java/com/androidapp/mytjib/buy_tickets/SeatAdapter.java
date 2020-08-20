package com.androidapp.mytjib.buy_tickets;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.androidapp.mytjib.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Adapter for seats data of an event to display in grid view
 */

public class SeatAdapter extends BaseAdapter {

    public List<Ticket> tickets;
    public List<Ticket> standingTickets;
    public List<Ticket> vipTickets;
    public List<Ticket> selectedTickets;
    public Context context;

    public SeatAdapter(Context context) {  // constructor
        this.tickets = new ArrayList<>();
        this.standingTickets = new ArrayList<>();
        this.vipTickets = new ArrayList<>();
        this.selectedTickets = new ArrayList<>();
        this.context = context;
    }

    // getters and setters

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
    public View getView(int i, View view, ViewGroup viewGroup) { // create and inflate view
        View v = LayoutInflater.from(context).inflate(R.layout.seat_data, null);
        final Ticket seatTicket = tickets.get(i);
        ImageView seatImage = v.findViewById(R.id.seat_img);

        if(seatTicket.getStatus().equals("unavailable")){ // when ticket is unavailable(sold), seat is gray
            seatImage.setColorFilter(ContextCompat.getColor(context, R.color.graySeat));
        }
        if(seatTicket.getStatus().equals("selected")){ // when ticket is selected by customer, seat is green
            seatImage.setColorFilter(ContextCompat.getColor(context, R.color.greenSeat));
        }
        return v;
    }

    public void setTickets(List<Ticket> tickets) {
        ArrayList<Ticket> sittingTickets = new ArrayList<>();
        for(Ticket t : tickets) {
            if(t.getSection().equals("SITTING")) sittingTickets.add(t);
        }
        this.tickets = sittingTickets;
        notifyDataSetChanged(); // refresh the UI
    }

    public void setStandingTickets(List<Ticket> tickets) {
        this.standingTickets = tickets;
    }

    public void setVipTickets(List<Ticket> tickets) {
        this.vipTickets = tickets;
    }

    public void setTextViews(View sitting, View standing, View vip) {
        String sittingStr = "Section: SITTING ";
        if(tickets.size() == 0) sittingStr += "--Unavailable--";
        else sittingStr += ", Price: " + tickets.get(0).getPrice() + "₩";

        String standingStr = "Section: STANDING ";
        if(standingTickets.size() == 0) standingStr += "--Unavailable--";
        else standingStr += ", Price: " + standingTickets.get(0).getPrice() + "₩";

        String vipStr = "Section: VIP ";
        if(vipTickets.size() == 0) vipStr += "--Unavailable--";
        else vipStr += ", Price: " + vipTickets.get(0).getPrice() + "₩";

        ((TextView)sitting).setText(sittingStr);
        ((TextView)standing).setText(standingStr);
        ((TextView)vip).setText(vipStr);
    }

    public void selectTicket(int i) { // add or remove sitting tickets for current order
        Ticket t = tickets.get(i);

        if(!t.getStatus().equals("unavailable") && !selectedTickets.contains(t)){
            t.setStatus("selected");
            selectedTickets.add(t);
        }
        else if(!t.getStatus().equals("unavailable")){
            t.setStatus("available");
            selectedTickets.remove(t);
        }
        notifyDataSetChanged(); // refresh the UI

    }

    public void selectStandingTickets(int numOfTickets) { // add or remove standing tickets for current order
        int toAdd = numOfTickets;
        while(toAdd > 0){
            Ticket t = standingTickets.remove(0);
            selectedTickets.add(t);
            t.setStatus("unavailable");
            toAdd--;
        }
    }

    public void selectVipTickets(int numOfTickets) { // add or remove vip tickets for current order
        int toAdd = numOfTickets;
        while(toAdd > 0){
            Ticket t = vipTickets.remove(0);
            selectedTickets.add(t);
            t.setStatus("unavailable");
            toAdd--;
        }
    }

    public ArrayList<Integer> getTicketIds() { // ids of selected tickets to order
       ArrayList<Integer> ticketIds = new ArrayList<>();
       for(Ticket t : selectedTickets) ticketIds.add(t.getId());
       return ticketIds;
    }

    public int getTotalPrice() { // total price of selected tickets
        int price = 0;
        for(Ticket t : selectedTickets) price += t.getPrice();
        return price;
    }
}
