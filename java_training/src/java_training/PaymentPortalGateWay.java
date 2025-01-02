package java_training;

interface PaymentMethod {
    boolean processPayment(double amount); 
    void getPaymentDetails(); 
}

class CreditCard implements PaymentMethod {
    private long cardno;
    private String name;
    private String expdate;
    private double balance;

   
    public CreditCard(long cardno, String name, String expdate, double balance) {
        this.cardno = cardno;
        this.name = name;
        this.expdate = expdate;
        this.balance = balance;
    }

    public boolean processPayment(double amount) {
        if (balance >= amount && amount > 0) {
            balance -= amount;
            return true;
        }
        return false;
    }

   
    public void getPaymentDetails() {
    	 System.out.println("Credit Card - Name:"+name);
         System.out.println("Expiry:" + expdate);
         System.out.println("Remaining Balance:"+balance);
         System.out.println("---------------------------------------------------------");
    }
    

    public long getCardno() {
        return cardno;
    }
}

class PayPal implements PaymentMethod {
    private String email;
    private double balance;

    
    public PayPal(String email, double balance) {
        this.email = email;
        this.balance = balance;
    }

   
    public boolean processPayment(double amount) {
        if (balance >= amount && amount > 0) {
            balance -= amount;
            return true;
        }
        return false;
    }

   
    public void getPaymentDetails() {
        
        System.out.println("Pay pal-Email:"+email);
        System.out.println("Remaining Balance:"+balance);
        System.out.println("---------------------------------------------------------");
    }
}

class PaymentProcessor {
    public void processPayment(PaymentMethod paymentMethod, double amount) {
        if (paymentMethod.processPayment(amount)) {
            System.out.println("Payment of " + amount + " processed successfully.");
        } else {
            System.out.println("Payment of " + amount + " failed.");
        }
    }

    public void displayPaymentDetails(PaymentMethod paymentMethod) {
       paymentMethod.getPaymentDetails();
    }
}

public class PaymentPortalGateWay {

    public static void main(String[] args) {
    	PaymentMethod creditCard = new CreditCard(1234567890123456L, "KASI RAM", "12/25", 5000);
    	PaymentMethod payPal = new PayPal("kasiram186@gmail.com", 1000);
        PaymentProcessor processor = new PaymentProcessor();
        processor.displayPaymentDetails(creditCard);
        processor.displayPaymentDetails(payPal);
        processor.processPayment(creditCard, 300); 
        processor.processPayment(payPal, 1200);   
    }
}
