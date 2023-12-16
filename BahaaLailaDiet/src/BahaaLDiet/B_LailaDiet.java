package BahaaLDiet;

import java.io.*;
import java.util.*;
import java.text.*;
import java.math.*;
import java.util.regex.*;

public class B_LailaDiet {
	public int minCal = Integer.MAX_VALUE;
	public int minFill = Integer.MAX_VALUE;
	public int sumCal = 0;
	public int sumTemp = 0;
	public static int []ArrOfCal;
	public static int [] ArrOfSumDays;
	public static int [] ArrOfCounters;
	public static int [][] DynTabel;
	public static int [][] DynType;	
	public static int [] ArrOfSeq;
	public int []TypeValues = new int [2];
	public static int nuOfDaysC;
	
	public void TypeIndex (int Type) {
		if(Type == 1) {
			TypeValues[0]=2;
			TypeValues[1]=3;
		}
		else if (Type == 2) {
			TypeValues[0]=1;
			TypeValues[1]=3;
		}
		else { // type ==3
			TypeValues[0]=1;
			TypeValues[1]=2;
		}
		
	}
	
	public int NuOfCaloriesFind(int Type , int Day) {
		int NuOfCalFind = ((nuOfDaysC*Type)-(nuOfDaysC-Day));
		int CalFind =  ArrOfCal[NuOfCalFind - 1];
		return CalFind;
	}
	
	public void ClearALL() {
		minCal=Integer.MAX_VALUE;
		sumCal=0;
	}
	

	public int RecB_LailaD(int Type,int Day) {
		int CalFind;
		if(Day < 1) {
			return 0;
		}
			Arrays.fill(TypeValues, 0);
			TypeIndex(Type);
			CalFind = NuOfCaloriesFind(Type, Day);
			sumCal= sumCal + CalFind;
			ArrOfSumDays[Day-1]= sumCal;
			RecB_LailaD(TypeValues[0],Day-1);
			sumCal = ArrOfSumDays[Day-1];
			TypeIndex(Type);
			RecB_LailaD(TypeValues[1],Day-1);	
			if(sumCal < minCal) {
				minCal=sumCal;
			}
		return minCal;
	}
	
	public int Call_RecB_LailaD_Fn(B_LailaDiet B_LaD) {
		 int T1 , T2 , T3 , Ans;
		    T1 = B_LaD.RecB_LailaD(1, nuOfDaysC);
		    B_LaD.ClearALL();
		    
		    T2 = B_LaD.RecB_LailaD(2, nuOfDaysC);
		    B_LaD.ClearALL();
	
		    T3 = B_LaD.RecB_LailaD(3, nuOfDaysC);
		    B_LaD.ClearALL();
	
		    Ans = Math.min(Math.min(T1,T2), T3);
		    return Ans;
	}
	// end of RecB_LailaD fn
	
	public void ReturnCounter() {
		int D = nuOfDaysC;
		int SplitIndex= (int)((3)*(Math.pow(2,nuOfDaysC-1)));
		int SplitBy3= SplitIndex/3;
		for(int i=nuOfDaysC - 1 ; i>=0 ;i--) {
			ArrOfCounters[i] = (int)(SplitBy3/(Math.pow(2,D-1)));
			D--;
		//	System.out.println("ArrofCounter= "+ArrOfCounters[i] +" i="+i);
		}
		
	
	}
	public int DynamicFILL_BLailaD () {
		DynTabel[0][0] = NuOfCaloriesFind(1,1); DynType[0][0] = 1;
		DynTabel[1][0] = NuOfCaloriesFind(2,1); DynType[1][0] = 2;
		DynTabel[2][0] = NuOfCaloriesFind(3,1); DynType[2][0] = 3;

		for(int i=1;i<nuOfDaysC;i++) { // i --> columns
			for(int j=0;j<3;j++) { // j --> rows
				TypeIndex(j+1);
				int VTemp1 = DynTabel[TypeValues[0]-1][i-1];
				int VTemp2 = DynTabel[TypeValues[1]-1][i-1];
			
				int CurrDayV= NuOfCaloriesFind(j+1,i+1);
				if((VTemp1+CurrDayV)<(VTemp2+CurrDayV)){
					DynTabel[j][i]=VTemp1+CurrDayV;
					DynType[j][i]=TypeValues[0];
				}
				else {
					DynTabel[j][i]=VTemp2+CurrDayV;
					DynType[j][i]=TypeValues[1];
				}
				
				if(i==nuOfDaysC-1 && j==2) {
				 minFill=Math.min(Math.min(DynTabel[0][i],DynTabel[1][i]), DynTabel[2][i]);
				}
				
			}
		}
		
		return minFill;
	}
	
	public void SequenceOfMoves_BLailaD() {
		int flagPrevious=-1;
		for(int i=nuOfDaysC-1;i>=0;i--) { // i --> columns
			for(int j=0;j<3;j++) { // j --> rows
				if(DynTabel[j][i]==minFill && i==nuOfDaysC-1) {
					ArrOfSeq[i]=j+1;
					flagPrevious=DynType[j][i];
				//	System.out.println("--Back to Back = "+(j+1)+" ,i= "+i);
					break; // this if statement True only for one time ! to initialize flagPrevious..
				}
				//System.out.println("ALONE-- i= "+i);
				if(flagPrevious==(j+1)) {
					ArrOfSeq[i]=j+1;
					flagPrevious=DynType[j][i];
				//	System.out.println("Back to Back = "+(j+1)+" ,i= "+i);
					break;
				}
				
			}
		}
	}
		

	
	public static void main(String[] args) {
		
		 
		B_LailaDiet B_LaD = new B_LailaDiet();
		Scanner inFromUser = new Scanner (System.in); // input from user
		  int nuOfDays = inFromUser.nextInt();
		  
		  nuOfDaysC=nuOfDays;
		  ArrOfSumDays= new int [nuOfDays];
		  ArrOfCounters= new int [nuOfDays];
		  DynTabel = new int[3][nuOfDaysC];
		  DynType = new int[3][nuOfDaysC];
		  ArrOfSeq = new int [nuOfDaysC];

		  int j=0;
		  System.out.println("NuofDays= "+nuOfDays);
		   ArrOfCal = new int [nuOfDays*3];
	    for (int i=0 ; i < nuOfDays*3 ; i++){ 
	
	  	  	  ArrOfCal[j]=inFromUser.nextInt();
	  	  	 j++;

	    } // end for loop - READING
	    
//--------------------------------------------------------------------------------------------------------------//
//%----%Calling divide and conqure Fn..
	    // int Ans = B_LaD.Call_RecB_LailaD_Fn(B_LaD);
	    // System.out.println("Ans From Divide And Conqure= "+Ans);
//-------------------------------------------------------------------------------------------
//%----%Calling Dynamic Programming Fill Tabel Fn..	    

        int FillAns = B_LaD.DynamicFILL_BLailaD();
        System.out.println(FillAns);
//-------------------------------------------------------------------------------------------
//%----%Calling Print the sequence Fn..	
        //as You Know you (Can't) call it , if you not calling the Dynamic Programming Code first..
        
        B_LaD.SequenceOfMoves_BLailaD();
        System.out.println("Sequence of moves = ");
       int ValSeq, sumSeq=0;
       for(int i=0 ; i<nuOfDaysC;i++) {
    	   ValSeq= B_LaD.NuOfCaloriesFind(ArrOfSeq[i], i+1);
    	   sumSeq+=ValSeq;
    	   System.out.println("Type # -> "+ArrOfSeq[i]+" // From Day= "+(i+1)+" // Value is= "+ValSeq);
       }
       
       System.out.println("sumSeq= "+sumSeq);
       

      
	}

}
