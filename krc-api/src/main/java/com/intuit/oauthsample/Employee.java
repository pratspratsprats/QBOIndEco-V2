package com.intuit.oauthsample;

public class Employee {

	public Employee(int id, String name , Double salary) {
		// TODO Auto-generated constructor stub
		
		this.id=id;
		this.name=name;
		this.salary=salary;
	}
	
	
	int id ;
	String name ;
	Double salary;
	
	
	
	

	public int getId() {
		return id;
	}





	public String getName() {
		return name;
	}







	public Double getSalary() {
		return salary;
	}


	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		Employee e = new Employee( 5, "Dhruba", 6000.00);
		System.out.println(""+ e.getId());
		System.out.println(""+ e.getName());
		System.out.println(""+ e.getSalary());

	}

}
