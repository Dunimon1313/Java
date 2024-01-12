import java.util.Scanner; 

public class main{
	
	public static Student stu1 = new Student();
	public static  Student stu2 = new Student();
	public static  Faculty fac1 = new Faculty();
	static Scanner scnr = new Scanner(System.in);
	
	//classes

public static class Student{
  private String name, numID;
  private double gpa, numCredits, counter;
  
}

public static class Faculty{//dep: mathematics, engineering, arts, science... rank: professor, adjunct
  private String name, numID, department, rank;
}

	//Main Code

  public static void main(String[] args) {

    int menuInput, studentIncount, stud1In, stud2In, facInput;


	    
    System.out.println("                              Welcome to my Personal Management Program");
    System.out.println("Choose one of the options:");
    menuInput = callMenu();
    menuSwitch(menuInput);


  }


  
//Methods
  
  
  
  	public static void menuSwitch(int menuInput) {
  	    int invoiceSel, studentIncount, studCount, stud1In, stud2In, facInput;
  		String resetStudent;
  	    double tuition1, tuition2;
  	    studCount = 0;
  	    
  	    studentIncount = 0;
  	    //menu controls - C1 = Faculty, c2 = 2 students, c3 tuition invoice, c4 = faculty info, c5 = end
  	    switch (menuInput) {
  	    
  	      case 1://only 1 faculty member
  	    	  System.out.println("Name of Faculty: ");
  	    	  fac1.name = scnr.nextLine();
  	    	  System.out.println("ID: ");
  	    	  fac1.numID = scnr.nextLine();  	    	
  	    	do {System.out.println("Rank (professor/adjunct): ");
  	    	  fac1.rank = scnr.nextLine();
  	    	  fac1.rank = fac1.rank.toLowerCase();
  	    	  	if(!(fac1.rank.equals("professor")||fac1.rank.equals("adjunct"))) {
  	    	  		System.out.println("Sorry entered rank is invalid");
  	    	  	}}while(!(fac1.rank.equals("professor")||fac1.rank.equals("adjunct")));
  	    	  
  	    	  do {System.out.println("Department : ");
  	    	  fac1.department = scnr.nextLine();
  	    	  fac1.department = fac1.department.toLowerCase();
  		  	  	if(!(fac1.department.equals("mathematics")||fac1.department.equals("engineering")||fac1.department.equals("physics")||fac1.department.equals("arts"))) {
  		  	  		System.out.println("Sorry entered department is invalid");
  		  	  	}
  	    	  }while(!(fac1.department.equals("mathematics")||fac1.department.equals("engineering")||fac1.department.equals("physics")||fac1.department.equals("arts")));
  	
  	    	  System.out.println("Thanks!");
  	    	  menuInput = callMenu();
  	    	menuSwitch(menuInput);
  	        break;
  	    	
  	      case 2://student info input
  	    	  if(stu2.counter==1) {  	    	  
  	    		  System.out.println("You already have two students filled in. Do you want to update their information? ");
  	    		  System.out.println("Yes or No: ");
  	    		  resetStudent = scnr.nextLine();
  	    		  resetStudent.toLowerCase();
  	    		  if(resetStudent.contentEquals("yes")) {
  	    			  System.out.println("Enter student info:");
  	  	    		  studentInput(stu1);
  	  	    		  //student 2
  	  	  		  System.out.println("Enter student info:");
  	  	    		  studentInput(stu2);
  	    		  }
  	    		  else {
  	  	    		 menuInput = callMenu();
  	  	    		menuSwitch(menuInput);
  	    		  }
  	    	  }
  	    	  else {
  	    		  //student 1
  			  System.out.println("Enter student info:");
  	    		  studentInput(stu1);
  	    		  //student 2
  	  		  System.out.println("Enter student info:");
  	    		  studentInput(stu2);
  	    		  stu2.counter=1;
  	    		 menuInput = callMenu();
  	    		menuSwitch(menuInput);
  	    	  }
  	        break;
  	        
  	      case 3://calculates invoice based on which student is asked
  	    	  System.out.println("Please select which student: 1 "+ stu1.name +" or 2 "+ stu2.name);
  	    	  invoiceSel = scnr.nextInt();
  	    	  
  	    	  if(invoiceSel == 1) {
  	    		  tuition1 = tuitionInv(stu1);
  	    	  }
  	    	  if(invoiceSel == 2) {
  	    		  tuition2 = tuitionInv(stu2);
  	    	  }
  	    	  menuInput = callMenu();
  	    	menuSwitch(menuInput);
  	        break;
  	        
  	      case 4:
  	    	  if(fac1.rank == "null") {
  	    		  System.out.println("There is no faculty information present. Please enter information.");
  	  	    	  menuInput = callMenu();
  	  	    	menuSwitch(menuInput);
  	    	  }
  	    	  else {
  	    	  printFacultyInfo(fac1);
  	    	  menuInput = callMenu();
  	    	  menuSwitch(menuInput);
  	    	  }
  	        break;
  	        
  	      case 5:
  	        System.out.println("Goodbye!");
  	        System.exit(0);
  	        break;
  	    
  		default:
	    menuInput = callMenu();
	    menuSwitch(menuInput);
	    break;
  	    }
  	}


public static int callMenu(){
    int menuInput;
    Scanner scnr = new Scanner(System.in);
      System.out.println("1- Enter the information of the faculty");
      System.out.println("2- Enter the information of the two students");
      System.out.println("3- Print tuition invoice");
      System.out.println("4- Print faculty information");
      System.out.println("5- Exit Program ");
      System.out.print("            Enter your selection: ");
    menuInput = scnr.nextInt();
      if(menuInput != 1 && menuInput != 2 && menuInput != 3 && menuInput != 4 && menuInput != 5){
        System.out.println("Invalid entry- please try again");
        menuInput = scnr.nextInt();
      }
        return menuInput;
  	}
	
	public static int studentInput(Student In) {
		Scanner scnr = new Scanner(System.in);
		  System.out.println("Name of Student: ");
		  	In.name = scnr.nextLine();
		  System.out.println("		ID: ");
		  	In.numID = scnr.nextLine();
		  System.out.println("		Gpa: ");
		  	In.gpa = scnr.nextDouble();
		  System.out.println("		Credit hours: ");
		  	In.numCredits = scnr.nextDouble();
		  System.out.println("Thanks!");
		  return 1;
	}
	

  	public static double tuitionInv(Student stu) {
  		double invoice, hourFee, adminFee;
  		hourFee = 236.45;
  		adminFee = 52.00;
	  		
	  		if(stu.gpa>3.84) {
	  			invoice = ((stu.numCredits*hourFee)+adminFee)*0.75;
	  			System.out.println("Here is the tuition invoice for "+stu.name+" :");
	  			System.out.println("---------------------------------------------------------------------------");
	  			System.out.println(stu.name+"				"+stu.numID);
	  			System.out.println("Credit Hours:"+stu.numCredits+" ($236.45/credit hour)");
	  			System.out.println("Fees: $52");
	  			System.out.println("");
	  			System.out.println("Total payment: "+invoice+"				($"+(.25*((stu.numCredits*hourFee)+adminFee))+" discount applied)");
	  			System.out.println("---------------------------------------------------------------------------");
	  		}
	  		else {
	  			invoice = ((stu.numCredits*hourFee)+adminFee);
	  			System.out.println("Here is the tuition invoice for \"+ stu.name +\" :\"");
	  			System.out.println("---------------------------------------------------------------------------");
	  			System.out.println(stu.name+"				"+stu.numID);
	  			System.out.println("Credit Hours:"+stu.numCredits+" ($236.45/credit hour)");
	  			System.out.println("Fees: $52");
	  			System.out.println("");
	  			System.out.println("Total payment: "+invoice+"				($0 discount applied)");
	  			System.out.println("---------------------------------------------------------------------------");
	  			
	  		}
  		return invoice;
  	}
  	
  	public static int printFacultyInfo(Faculty faculty) {
  		System.out.println("---------------------------------------------------------------------------");
  		System.out.println(faculty.name);
  		System.out.println(faculty.department+"Department, "+ faculty.rank);
  		System.out.println("---------------------------------------------------------------------------");
  		return 0;
  	}
}