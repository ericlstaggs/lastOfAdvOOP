/**
 * 
 */

/**
 * 
 */
public class CreditCard {

	private String customer;
	private String bank;
	private String account;
	private double limit;
	protected double balance;

	public CreditCard(String cust, String bk, String acnt, double lim, double initialBal) {
		this.customer = cust;
		this.account = acnt;
		this.bank = bk;
		this.limit = lim;
		this.balance = initialBal;
	}

	public String getCustomer() {
		return customer;
	}

	public String getBank() {
		return bank;
	}

	public String getAccount() {
		return account;
	}

	public double getLimit() {
		return limit;
	}

	public double getBalance() {
		return balance;
	}

	public boolean charge(double price) {
		makePayment(price);
		return true;
	}

	public void makePayment(double amount) {
		balance -= amount;
	}

}
