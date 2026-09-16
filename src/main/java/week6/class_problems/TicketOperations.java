package week6.class_problems;

public class TicketOperations {

    public static String batchPrint(EventTicket[] tickets) {
        StringBuilder result = new StringBuilder();

        for (EventTicket ticket : tickets) {
            result.append(ticket.getAnnouncement()).append(" ");

            if (ticket instanceof WorkshopTicket) {
                WorkshopTicket workshop = (WorkshopTicket) ticket;
                result.append("[Track via downcast: ")
                      .append(workshop.getTrack())
                      .append("] ");
            }

            result.append("| ");
        }

        return result.toString();
    }
}