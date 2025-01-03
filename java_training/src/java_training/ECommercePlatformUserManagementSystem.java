package java_training;
/* 
    Problem Statement: E-Commerce Platform User Management System
Overview:
Design and implement a User Management System for an E-Commerce platform. The system should be able to manage different types of users with varying access levels and functionalities. You will utilize inheritance to represent the relationship between different user roles and interfaces to define the common operations that must be implemented by each user type.

Requirements:
User Roles: The system needs to support the following types of users:

Admin: Has full access to all platform functionalities, including managing users, products, and orders.
Customer: Can browse products, add items to the cart, place orders, and view order history.
Seller: Can list products, manage their inventory, and view orders related to their products.
Interface - IUserOperations: Define an interface IUserOperations with the following common methods:

viewProfile(): View user profile details.
updateProfile(): Update user profile details.
logout(): Logout from the system.
All user types (Admin, Customer, Seller) must implement this interface.

Inheritance Structure: Use inheritance to model the following:

A base class User that implements the IUserOperations interface and contains common attributes like username, email, and password.
The Admin, Customer, and Seller classes inherit from the User class and extend it with role-specific functionalities:
Admin: Can manage users and platform settings.
Customer: Can add products to the cart and view order history.
Seller: Can manage their product listings and view orders related to their products.
Additional Functionalities:

The Admin can create, delete, or update any user's profile.
The Customer can place an order and check the status of their order.
The Seller can view their product inventory and manage orders for their products.
 */
interface IUserOperations
{
	void viewProfile();
	void updateProfile(String username, String email, String Password);
	void logout();
}
class user implements IUserOperations
{
	private String username;
	private String email;
	private String Password;
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return Password;
	}

	public void setPassword(String password) {
		Password = password;
	}

	public user(String username, String email, String Password) {
		this.username = username;
		this.email = email;
		this.Password = Password;
	}

	public void viewProfile()
	{
		System.out.println("username:"+username);
		System.out.println("email"+email);
		System.out.println("Password:"+Password);
		System.out.println("-----------------------------------");
	}
	public void updateProfile(String username, String email, String Password)
	{
	    	this.username=username;
	    	this.email=email;
	    	this.Password=Password;
	}
	public void logout()
	{
		System.out.println("Logout successful");
	}
	
}

class customer extends user
{
    public String items;
    public int cost;
	public customer(String username, String email, String Password,String items,int cost) {
		super(username, email, Password);
		this.items=items;
		this.cost=cost;
		
	}
	public void viewProfile()

	{
		System.out.println("username:"+getUsername());
		System.out.println("email:"+getEmail());
		System.out.println("items:"+items);
		System.out.println("cost:"+cost);
		System.out.println("-----------------------------------");
	}
}
class Admin 
{
	public  user createUserProfile(String username, String email, String Password)
	{
		 user obj=new user(username,email,Password);
		 return obj;
	}
	public  void displayUser(user obj)
	{
		 obj.viewProfile();
	}
}
public class ECommercePlatformUserManagementSystem {

	public static void main(String[] args) {
		Admin ad=new Admin();
		user user1=new user("kaisram","kasiram1862gmail.com","98908908980");
		ad.displayUser(user1);
		user user2=new customer("kaisram","kasiram1862gmail.com","98908908980","apple",100); 
		ad.displayUser(user2);
		user user3=ad.createUserProfile("kasi","kjks@gmail.com","7768767");
		ad.displayUser(user3);
		//above line denotes admin can acess user and customer details
		//now customer  can acess only customer details 
		customer custom=new customer("kaibcd","kasidhjbhjbgmail.com","1234556","strawberry",110);
		custom.viewProfile();
		
	}

}
