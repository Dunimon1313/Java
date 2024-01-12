import java.util.ArrayList;
import java.util.Scanner;

/*
-	Project 2
-	Zachary Taylor
*/

public class Pro2 {
public static ArrayList<Person> group = new ArrayList<Person>();
public static Scanner scnr = new Scanner(System.in);	
public static int menuIn;

	public static void main(String[] args){	
	//Test code goes here
		String input1;
		

		menuIn = callMenu();
		while(menuIn!=5) {
		switch(menuIn) {
		
		
		case 1://add new faculty
			facultyIn();//###requires input
			break;
		case 2://add new student
			studentIn();//###requires input
			break;
		case 3://print student tuition
			studentIDCheck();
			break;
		case 4://print faculty
	    	facultyIDCheck();
			break;
		case 5://Exit
			System.out.println("");
  	        System.out.println("Goodbye!");
  	        System.exit(0);
			break;
		}
     }
	}



//---------------------------CLASSES
    abstract class Person{
    	protected String name, ID, type;
    	
    	public void setPersonName(String username) {
    		name = username;
    	}
    	public String getPersonName() {
    		return name;
    	}
    	
    	public void setID(String userid) {
    		ID = userid;
    	}
    	public String getID() {
    		return ID;
    	}
    	
    	public void setType(String userType) {
    		type = userType;
    	}
    	public String getType() {
    		return type;
    	}
    	abstract void printInfo();
    }

//---------------------------
    public class Student extends Person{
    	protected double GPA, numCredits, tuition, discount;
    	
    	public void setGPA(double userGPA) {
    		GPA = userGPA;
    	}
    	public double getGPA() {
    		return GPA;
    	}
    	
    	public void setNumCredits(double userNumCredits) {
    		numCredits = userNumCredits;
    	}
    	public double getNumCredits() {
    		return numCredits;
    	}
    	
    	public void setTuition() {
    		discount = 0;
    		tuition = (numCredits*236.45)+52;
    		if(GPA>3.84) {
    			tuition = tuition*3/4;
    			discount = tuition/4;
    		}
    	}
    	public double getTuition() {
    		return tuition;
    	}
    	public double getDiscount() {
    		return discount;
    	}
    	
    	public void printInfo() {
    		System.out.println("");
    		System.out.println("---------------------------------------------------------------------------");
    		System.out.println(getPersonName()+"				"+getID());
    		System.out.println("Credit Hours:"+getNumCredits()+" ($236.45/credit hour)");
    		System.out.println("Fees: $52");
    		System.out.println("");
    		System.out.println("Total payment: "+getTuition()+"				($"+getDiscount()+" discount applied)");
    		System.out.println("---------------------------------------------------------------------------");
	    	menuIn = callMenu();
    	}
   }
    
//---------------------------
    public class Faculty extends Person{
    	private String department;
		private String rank;
		private String checker;
    	
    	public void setDepartment(String userDepartment) {
    		char firstLetter, upper;
    		int size;
 		
    		while(!(userDepartment.equals("Engineering")||(userDepartment.equals("Physics")||(userDepartment.equals("Mathematics"))))) {
    			size = userDepartment.length();
        		firstLetter = userDepartment.charAt(0);
        		upper = Character.toUpperCase(firstLetter);
        		userDepartment.toLowerCase();//gets string into lower case format
        		userDepartment = upper+userDepartment.substring(1,size);  
        		
	    		if(!(userDepartment.equals("Mathematics")||userDepartment.equals("Physics")||userDepartment.equals("Engineering"))){//checks if user input proper rank and asks for correction if triggered
	    			System.out.println("			Sorry entered department  ("+userDepartment+")  is invalid");
	    			System.out.println("Please try again. (Hint: available departments are Mathematics, Physics, or Engineering. Capitals do not matter.)");
	    			userDepartment = scnr.nextLine();
	    		}
	    		else {
	    			department = userDepartment;   			
	    		}
    		}
    	}
    	public String getDepartment() {
    		return department;
    	}
    	
    	public void setRank(String userRank) {
    		char firstLetter, upper;
    		int size;

   		
    		while(!(userRank.equals("Professor")||(userRank.equals("Adjunct")))) {
    			size = userRank.length();
        		firstLetter = userRank.charAt(0);//gets 1st letter
        		upper = Character.toUpperCase(firstLetter);//capitalizes it
        		userRank.toLowerCase();//gets string into lower case format
        		userRank = upper+userRank.substring(1,size);
        		
	    		if(!(userRank.equals("Professor")||(userRank.equals("Adjunct")))) {//checks if user input proper rank and asks for correction if triggered
	    			System.out.println("			Sorry entered rank  ("+userRank+")  is invalid");
	    			System.out.println("Please try again. (Hint: available ranks are Professor or Adjunct, capitals do not matter.)");
	    			userRank = scnr.nextLine(); 			
	    		}
	    		else {
	    			rank = userRank;
	    		}
    		}

    	}
    	public String getRank() {
    		return rank;
    	}  	
    	
    	public void printInfo() {
    		System.out.println("---------------------------------------------------------------------------");
    		System.out.println(getPersonName());
    		System.out.println(getDepartment()+", "+getRank());
    		System.out.println("---------------------------------------------------------------------------");
	    	menuIn = callMenu();
    	}
   }
    
//---------------------------------------------METHODS
    public static int callMenu(){
        String inputCheck;
          System.out.println("1-	Add a new Faculty member");
          System.out.println("2-	Add a new Student");
          System.out.println("3-	Print tuition invoice for a student");
          System.out.println("4-	Print information of a faculty");
          System.out.println("5-	Exit Program ");
          System.out.print("            Enter your selection: ");
          inputCheck = scnr.nextLine();
          try {
        	  menuIn = Integer.parseInt(inputCheck);
          }
          catch (NumberFormatException e)  {
        	  System.out.println("Invalid entry- please try again");
        	  menuIn = callMenu();
          }
          if(!(menuIn>0||menuIn<6)) {
        	  System.out.println("Invalid entry- please try again");
        	  menuIn = callMenu();
          }
            return menuIn;
      	}
//---------------------------
    public static void studentIDCheck() {//searches student ID's for match
    	int i = 0;
    	String IDSearch;
		
    	if(group.isEmpty()) {
    		System.out.println("There is no student information entered yet.");
			System.out.println("");
			menuIn = callMenu();
    	}
    	else {
    	System.out.println("Enter the student’s id: ");
    	IDSearch = scnr.nextLine();
    	for(i=0; i<100; i++) {
	    		if ((IDSearch.equals(group.get(i).getID())) && (group.get(i).getType().equals("student"))) {
	    			System.out.println("Here is the tuition invoice for "+group.get(i).getPersonName()+" :");
	    			group.get(i).printInfo();
	    		}
	    		else {
	    			System.out.println("Sorry-student not found!");
	    			menuIn = callMenu();
	    		}
	    	}
    	}
    }

//---------------------------
    public static void facultyIDCheck() {//searches faculty ID's for match
    	int i = 0;
    	String IDSearch;
    	
    	if(group.isEmpty()) {
    		System.out.println("There is no faculty information entered yet.");
			System.out.println("");
			menuIn = callMenu();
    	}
    	else {
        	System.out.println("Enter the faculty’s id: ");
        	IDSearch = scnr.nextLine();
	    	for(i=0; i<group.size(); i++) {
	    		if ((IDSearch.equals(group.get(i).getID() ))&&( group.get(i).getType().equals("faculty"))) {
	    			System.out.println("Faculty found:");
	    			group.get(i).printInfo();
	    		}
	    		else {
	    			System.out.println("Sorry "+IDSearch+" doesn't exist");
	    			System.out.println("");
	    			menuIn = callMenu();
	    		}
	    	}
    	}
    }

//---------------------------
    public static void studentIn() {//creates new student under ArrayList
    	String studInput;
    	double doubleIns;
    	Pro2 pro = new Pro2();//creates new class for each call
    	Student newStudent = pro.new Student();// ^^
    	newStudent.setType("student");
    	//collects info from user   	
    	System.out.println("	Enter the student’s info:");
    	System.out.println("			Name of Student: ");
    	studInput = scnr.nextLine();
    	newStudent.setPersonName(studInput);
    	System.out.println("			ID: ");
    	studInput = scnr.nextLine();
    	newStudent.setID(studInput);
    	System.out.println("			Gpa: ");
    	doubleIns = scnr.nextDouble();
    	newStudent.setGPA(doubleIns);
    	System.out.println("			Credit hours: ");
    	doubleIns = scnr.nextDouble();
    	newStudent.setNumCredits(doubleIns);
    	scnr.nextLine();
    	newStudent.setTuition();
    	group.add(newStudent);
    	menuIn = callMenu();
    }

//---------------------------
    public static void facultyIn() {//creates new faculty under ArrayList
    	String facInput;
    	Pro2 pro = new Pro2();
    	Faculty newFaculty = pro.new Faculty();
    	newFaculty.setType("faculty");
    	//collects info from user
    	System.out.println("Enter the faculty’s info:");
    	System.out.println("			Name of the faculty:  ");
    	facInput = scnr.nextLine();
    	newFaculty.setPersonName(facInput);
    	System.out.println("			ID: ");
    	facInput = scnr.nextLine();
    	newFaculty.setID(facInput);
    	System.out.println("			Rank: ");
    	facInput = scnr.nextLine();
    	newFaculty.setRank(facInput);//setrank checks for errors, if none, accepts the input. else, queries user for more input
    	System.out.println("			Department: ");
    	facInput = scnr.nextLine();//setdepartment checks for errors, if none, accepts the input. else, queries user for more input
    	newFaculty.setDepartment(facInput);
    	group.add(newFaculty);
    	menuIn = callMenu();
    	}
    
}