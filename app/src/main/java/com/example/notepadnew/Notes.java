package com.example.notepadnew;

public class Notes {
    public String tittle;
    public String discription;
    public String date;
    public String uid;

    public Notes() {
        //emphty needed
    }
      public Notes(String head,String date,String discription)
      {
          this.tittle=head;
          this.date=date;
          this.discription=discription;
      }
    public Notes(String tittle, String discription,String date,String uid) {
        this.tittle = tittle;
        this.discription = discription;
        this.date=date;
        this.uid=uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getUid() {
        return uid;
    }

    public String getTittle() {
        return tittle;
    }

    public String getDiscription() {
        return discription;
    }

    public String getDate() {
        return date;
    }

    public void setTittle(String tittle) {
        this.tittle = tittle;
    }

    public void setDiscription(String discription) {
        this.discription = discription;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
