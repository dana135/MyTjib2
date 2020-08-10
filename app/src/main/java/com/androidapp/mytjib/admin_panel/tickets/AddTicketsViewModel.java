package com.androidapp.mytjib.admin_panel.tickets;

import androidx.lifecycle.ViewModel;

import com.androidapp.mytjib.Event;
import com.androidapp.mytjib.Repository;

public class AddTicketsViewModel extends ViewModel {

    private Repository repository;

    public void createRepository(int id){
        repository  = new Repository();
        repository.setEventId(id);
    }

    public void addEventTickets(int numOfTickets, String section, int price, boolean marked){
        repository.addEventTickets(numOfTickets, section, price, marked);
    }
}
