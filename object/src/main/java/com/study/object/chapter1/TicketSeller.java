package com.study.object.chapter1;

import lombok.Getter;

@Getter
public class TicketSeller {

    private TicketOffice ticketOffice;

    public TicketSeller(TicketOffice ticketOffice) {
        this.ticketOffice = ticketOffice;
    }

    public void sellToV1(Audience audience) {
        if (audience.getBag().hasInvitation()) {
            Ticket ticket = ticketOffice.getTicket();
            audience.getBag().setTicket(ticket);
        }else{
            Ticket ticket = ticketOffice.getTicket();
            audience.getBag().minusAmount(ticket.getFee());
            ticketOffice.plusAmount(ticket.getFee());
            audience.getBag().setTicket(ticket);
        }
    }

    public void sellToV2(Audience audience) {
        ticketOffice.plusAmount(audience.buy(ticketOffice.getTicket()));
    }

    //ticketoffice의 의존성을 낮췄지만, ticketoffice가 audience를 의존하는 문제가 생긴다.(항상 tradeoff가 존재한다)
    public void sellToV3(Audience audience) {
        ticketOffice.sellTicketTo(audience);
    }
}
