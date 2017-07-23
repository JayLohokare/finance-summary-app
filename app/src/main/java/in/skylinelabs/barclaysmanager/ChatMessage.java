package in.skylinelabs.barclaysmanager;

public class ChatMessage {
    private long id;
    private int tag;
    String bankname;
    String card;
    String vendor;
    String amount;
    String balance;
    String date;
    String message;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getTag() {
        return tag;
    }

    public void setTag(int isMe) {
        this.tag = isMe;
    }

    public String getcard() {
        return card;
    }
    public String getvendor() {
        return vendor;
    }public String getamount() {
        return amount;
    }
    public String getbalance() {
        return balance;
    }public String getDate() {
        return date;
    }
    public String getbankname() {
        return bankname;
    }
    public String getmessage() {
        return message;
    }



    public void setDate(String dateTime) {
        this.date = dateTime;
    }
    public void setbalance(String balance) {
        this.balance = balance;
    }
    public void setbankname(String bankname) {
        this.bankname = bankname;
    }
    public void setcard(String card) {
        this.card = card;
    }
    public void setvendor(String vendor) {
        this.vendor = vendor;
    }
    public void setamount(String bankname) {
        this.amount = bankname;
    }
    public void setmessage(String message) {
        this.message = message;
    }


}
