package fr.dut.ptut2021.game;

import fr.dut.ptut2021.models.Card;
import java.util.ArrayList;
import java.util.Collections;
import java.util.concurrent.TimeUnit;

public class Memory {

    private ArrayList<Card> listCard;
    private int idLastCardReturn=-1;

    public Memory(ArrayList<Card> listCard){
        this.listCard = new ArrayList<>();
        for(Card card : listCard){
            this.listCard.add(card);
            this.listCard.add( new Card(card));
        }
        shuffle();
        display();
    }

    private void shuffle(){
        Collections.shuffle(listCard);
        Collections.shuffle(listCard);
        Collections.shuffle(listCard);
        Collections.shuffle(listCard);
        Collections.shuffle(listCard);
        Collections.shuffle(listCard);
    }

    public void returnCard(int idCard) throws InterruptedException {
        listCard.get(idCard).setHidden(false);
        display();
        TimeUnit.SECONDS.sleep(1);

        if(idLastCardReturn == -1){
            idLastCardReturn = idCard;
        }
        else{
            if(listCard.get(idLastCardReturn).getValue() == listCard.get(idCard).getValue()){

                idLastCardReturn = -1;
            }else{
                listCard.get(idCard).setHidden(true);
                listCard.get(idLastCardReturn).setHidden(true);
                display();
                idLastCardReturn=-1;
            }
        }
    }

    public void display(){
        int compteur=1;
        for (Card card : listCard){
            if(!card.isHidden())
                System.out.print(card.getValue()+" ");
            else
                System.out.print("X ");

            if(compteur%4 ==0)
                System.out.println("");

            compteur++;
        }
    }
}