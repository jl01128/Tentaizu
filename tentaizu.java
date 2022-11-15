import java.util.*;
//import java.io.*;

public class tentaizu {

    public static char[][] result = new char[7][7];
    public static char[][] perm = new char[7][7];

    public static void main(String[] args) {
        
        Scanner stdin = new Scanner(System.in);
        int numCases = stdin.nextInt();


        String newLine = new String();
        newLine = stdin.nextLine(); 


        //long start = System.currentTimeMillis(); 
        for (int loop=1; loop<=numCases; loop++) {

                       
            System.out.println("Tentaizu Board #" + loop + ": ");
            String temp = new String();
            
            //7x7 tentaizu board
            

            // input values 
            for(int i = 0; i < 7; ++i){
                temp = stdin.nextLine();
                for(int j = 0; j < 7; ++j){
                    perm[i][j] = temp.charAt(j);

                    /*
                    if(Character.isDigit(perm[i][j])){
                        System.out.println(perm[i][j]);
                    }
                    */
                }
            }
            
            //store final result in variable
            result = solveIt(0,0,0);
            print();
         
            newLine = stdin.nextLine();
            System.out.println();
            
        // Declare other variables here.
        // Read input for one case.
        // Process information.
        // Output answer for one case.
        
        }

        //ong end = System.currentTimeMillis();   
        //System.out.println(end - start);
        
    }

    //moving from lef to right(top down order)
    public static char[][] solveIt(int nbombs, int i, int j) {

        //i is row
        //j is column

        //reached beyond the 7x7 digram or reached placed 10 bombs
		if(i > 6 || nbombs == 10){
            return perm;
        }
       
       //if colum is greter than 7, restart to 0
        else if(j > 6){
            solveIt(nbombs, i+1, 0);
        }

        //skip numbers
        else if(Character.isDigit(perm[i][j])){
            solveIt(nbombs, i, j+1);
        }
                
        else{
            //set given sqaure to bomb
            perm[i][j] = '*';

            //check if bomb can be placed
            if(safe(i, j, nbombs+1, perm)){
                
                //increment bomb num and move on to next sqaure
                char[][] possible = solveIt(nbombs+1, i, j+1);

                //check if returned bomb placements are valid to rules
                if(!correctrule(possible)){
                    perm[i][j] = '.';
                    solveIt(nbombs, i, j+1);
                }
         
            } 

            //if bomb can't be placed, revert back to . and move on
            else{
                perm[i][j] = '.';
                solveIt(nbombs, i, j+1);
            }
            
            
        }
        
        return perm;
    }

    

	
	// return star if follows rules
	public static boolean safe(int r, int c, int bomb_num, char[][] perm) {

        //check is bomb amount is greater than 10
        if(bomb_num > 10){
            return false;
        }
        

        //top row
        if(r == 0){
            //top left corner
            if(c == 0){
                //bomb cannot be adjacent to number 0
                if(Character.isDigit(perm[r][c+1]) || Character.isDigit(perm[r+1][c]) || Character.isDigit(perm[r+1][c+1])){
                    if(perm[r][c+1] == '0' || perm[r+1][c] == '0' || perm[r+1][c+1] == '0'){
                        return false;
                    }
                }
                //no numbers adjacent to it
                else{
                    return false;
                }
            } 
            //top right corner
            else if(c == 6){
                //bomb cannot be adjacent to number 0
                if(Character.isDigit(perm[r][c-1]) || Character.isDigit(perm[r+1][c]) || Character.isDigit(perm[r+1][c-1])){
                    if(perm[r][c-1] == '0' || perm[r+1][c] == '0' || perm[r+1][c-1] == '0'){
                        return false;
                    }
                }
                else{
                    return false;
                }
            }
            //rest in between corners
            else{
                
                /*if(perm[r][c+1] == '*' && perm[r+1][c] == '*' && perm[r+1][c+1] == '*' && perm[r][c-1] == '*' && perm[r+1][c-1] == '*'){
                    return false;
                }
                */
               
                //          right                                       bottom                              right diagonal           
                if(Character.isDigit(perm[r][c+1]) || Character.isDigit(perm[r+1][c]) || Character.isDigit(perm[r+1][c+1]) || 
                        Character.isDigit(perm[r][c-1]) || Character.isDigit(perm[r+1][c-1])){
                        //                  left                 left diagonal

                    //     right                   bottom                 right diagonal           left                 left diagonal
                    if(perm[r][c+1] == '0' || perm[r+1][c] == '0' || perm[r+1][c+1] == '0' || perm[r][c-1] == '0' && perm[r+1][c-1] == '0'){
                        return false;
                    }
                }
                else{
                    return false;
                }
            }
        } 
        //bottom row
        else if(r == 6){
            
            //bottom right corner
            if(c == 6){
                /*if(perm[i-1][j] == '*' && perm[i][j+1] == '*' && perm[i-1][j+1] == '*'){
                    return false;
                }
                */
                if(Character.isDigit(perm[r-1][c]) || Character.isDigit(perm[r][c-1]) || Character.isDigit(perm[r-1][c-1])){
                    if(perm[r-1][c] == '0' || perm[r][c-1] == '0' || perm[r-1][c-1] == '0'){
                        return false;
                    }
                }
                else{
                    return false;
                }
            }
            //bottom left corner
            else if(c == 0){
                /*if(perm[i-1][j] == '*' && perm[i-1][j] == '*' && perm[i][j-1] == '*'){
                    return false;
                }
                */
                if(Character.isDigit(perm[r-1][c]) || Character.isDigit(perm[r][c+1]) || Character.isDigit(perm[r-1][c+1])){
                    if(perm[r-1][c] == '0' || perm[r][c+1] == '0' || perm[r-1][c+1] == '0'){
                        return false;
                    }
                }
                else{
                    return false;
                }
            }

                //bottom edges
            else{
                //     right                   top                 right diagonal           left                 left diagonal
                /*if(perm[i][j+1] == '*' && perm[i-1][j] == '*' && perm[i-1][j+1] == '*' && perm[i][j-1] == '*' && perm[i-1][j-1] == '*'){
                    return false;
                }*/

                //          right                                       top                              right diagonal           
                if(Character.isDigit(perm[r][c+1]) || Character.isDigit(perm[r-1][c]) || Character.isDigit(perm[r-1][c+1]) || 
                        Character.isDigit(perm[r][c-1]) || Character.isDigit(perm[r-1][c-1])){
                        //                  left                 left diagonal

                    //     right                   top                 right diagonal           left                 left diagonal
                    if(perm[r][c+1] == '0' || perm[r-1][c] == '0' || perm[r-1][c+1] == '0' || perm[r][c-1] == '0' && perm[r-1][c-1] == '0'){
                        return false;
                    }
                }
                else{
                    return false;
                }
            }

        }

        else{

            //left edges
            if(c == 0){
                //     right                   bottom           right down diagonal            top                right up diagonal
                /*if(perm[i][j+1] == '*' && perm[i+1][j] == '*' && perm[i+1][j+1] == '*' && perm[i-1][j] == '*' && perm[i-1][j+1] == '*'){
                    return false;
                }
                */
                //          right                                       bottom                              right down diagonal           
                if(Character.isDigit(perm[r][c+1]) || Character.isDigit(perm[r+1][c]) || Character.isDigit(perm[r+1][c+1]) || 
                        Character.isDigit(perm[r-1][c]) || Character.isDigit(perm[r-1][c+1])){
                        //                  top                 right up diagonal

                    //     right                   bottom              right down diagonal           top                 right up diagonal
                    if(perm[r][c+1] == '0' || perm[r+1][c] == '0' || perm[r+1][c+1] == '0' || perm[r-1][c] == '0' && perm[r-1][c+1] == '0'){
                        return false;
                    }
                }
                else{
                    return false;
                }
            }

            //right edges
            else if(c == 6){
                
                //     left                   bottom           left down diagonal            top                left up diagonal
                /*
                if(perm[r][c-1] == '*' && perm[r+1][c] == '*' && perm[r+1][c-1] == '*' && perm[r-1][c] == '*' && perm[r-1][c-1] == '*'){
                    return false;
                }
                */
                //          left                                       bottom                              left down diagonal           
                if(Character.isDigit(perm[r][c-1]) || Character.isDigit(perm[r+1][c]) || Character.isDigit(perm[r+1][c-1]) || 
                        Character.isDigit(perm[r-1][c]) || Character.isDigit(perm[r-1][c-1])){
                        //                  top                 left up diagonal

                    //     left                   bottom              left down diagonal           top                 left up diagonal
                    if(perm[r][c-1] == '0' || perm[r+1][c] == '0' || perm[r+1][c-1] == '0' || perm[r-1][c-1] == '0' && perm[r-1][c-1] == '0'){
                        return false;
                    }
                }
                else{
                    return false;
                }
            }
            //middle
            else{
                //     top               right diagonal up            right              right diagonal down        bottom   
                /*                 
                if(perm[i-1][j] == '*' && perm[i-1][j+1] == '*' && perm[i][j+1] == '*' && perm[i+1][j+1] == '*' && perm[i+1][j] == '*' 
                    && perm[i+1][j-1] == '*' && perm[i][j-1] == '*' && perm[i-1][j-1] == '*'){
                        //left diagonal down        left                    left diagonal up
                    return false;
                }
                */
                // top,   right diagonal up,  right,  right diagonal down,  bottom,  left diagonal down , left, left diagonal up                 
                if(Character.isDigit(perm[r-1][c]) || Character.isDigit(perm[r-1][c+1]) || Character.isDigit(perm[r][c+1]) || 
                        Character.isDigit(perm[r+1][c+1]) || Character.isDigit(perm[r+1][c]) || Character.isDigit(perm[r+1][c-1])
                        || Character.isDigit(perm[r][c-1]) || Character.isDigit(perm[r-1][c-1])){
                    

                    //          top               right diagonal up            right              right diagonal down        bottom          
                    if(perm[r-1][c] == '0' || perm[r-1][c+1] == '0' || perm[r][c+1] == '0' || perm[r+1][c+1] == '0' && perm[r+1][c] == '0'
                        || perm[r+1][c-1] == '0' || perm[r][c-1] == '0'|| perm[r-1][c-1] == '0'){
                        //left diagonal down        left                    left diagonal up
                        return false;
                    }
                }
                else{
                    return false;
                }
            }
        }

    
        
        //check for bombs amount greater than given num
        for(int a = 0; a < 7; ++a){
            for(int b = 0; b < 7; ++b){
                if(Character.isDigit(perm[a][b])){
                    int count = 0;
                    int num = Character.getNumericValue(perm[a][b]);
                    
                    //top row
                    if(a == 0){
                        //top left corner
                        if(b == 0){
                            if(perm[a][b+1] == '*'){
                                ++count;
                            } 
                            if(perm[a+1][b] == '*'){
                                ++count;
                            }
                            if(perm[a+1][b+1] == '*'){
                                ++count;
                            }

                            if(count > num){
                                return false;
                            }
                        } 
                        //top right corner
                        else if(b == 6){
                            if(perm[a][b-1] == '*'){
                                ++count;
                            } 
                            if(perm[a+1][b] == '*'){
                                ++count;
                            }
                            if(perm[a+1][b-1] == '*'){
                                ++count;
                            }

                            if(count > num){
                                return false;
                            }
                        
                        }
                        //rest in between corners
                        else{
                            if(perm[a][b+1] == '*'){
                                ++count;
                            } 
                            if(perm[a+1][b] == '*'){
                                ++count;
                            } 
                            if(perm[a+1][b+1] == '*'){
                                ++count;
                            }
                            if(perm[a][b-1] == '*'){
                                ++count;
                            } 
                            if(perm[a+1][b-1] == '*'){
                                ++count;
                            }

                            if(count > num){
                                return false;
                            }                        
                        }
                    } 

                    //bottom row
                    else if(a == 6){
                        
                        //bottom right corner
                        if(b == 6){
                            if(perm[a-1][b] == '*'){
                                ++count;
                            } 
                            if(perm[a][b-1] == '*'){
                                ++count;
                            }
                            if(perm[a-1][b-1] == '*'){
                                ++count;
                            }

                            if(count > num){
                                return false;
                            }
                            
                        }
                        //bottom left corner
                        else if(b == 0){
                            if(perm[a-1][b] == '*'){
                                ++count;
                            }
                            if(perm[a][b+1] == '*'){
                                ++count;
                            } 
                            
                            if(perm[a-1][b+1] == '*'){
                                ++count;
                            }

                            if(count > num){
                                return false;
                            }
                        }

                        //bottom edges
                        else{
                            if(perm[a][b+1] == '*'){
                                ++count;
                            } 
                            if(perm[a-1][b] == '*'){
                                ++count;
                            } 
                            if (perm[a-1][b+1] == '*'){
                                ++count;
                            } 
                            if (perm[a][b-1] == '*'){
                                ++count;
                            } 
                            if(perm[a-1][b-1] == '*'){
                                ++count;
                            }

                            if(count > num){
                                return false;
                            }
                        }

                    }
                
                    else{
                        //left edges
                        if(b == 0){
                            if(perm[a][b+1] == '*'){
                                ++count;
                            } 
                            if(perm[a+1][b] == '*'){
                                ++count;
                            }
                            if(perm[a+1][b+1] == '*'){
                                ++count;
                            } 
                            if(perm[a-1][b] == '*'){
                                ++count;
                            } 
                            if(perm[a-1][b+1] == '*'){
                                ++count;
                            }

                            if(count > num){
                                return false;
                            }
                        }

                        //right edges
                        else if(b == 6){
                            if(perm[a][b-1] == '*'){
                                ++count;
                            } 
                            if(perm[a+1][b] == '*'){
                                ++count;
                            } 
                            if(perm[a+1][b-1] == '*'){
                                ++count;
                            }  
                            if(perm[a-1][b] == '*'){
                                ++count;
                            }  
                            if(perm[a-1][b-1] == '*'){
                                ++count;
                            }

                            if(count > num){
                                return false;
                            }
                        }
                        //middle 
                        else{
                            //     top                                          
                            if(perm[a-1][b] == '*'){
                                ++count;
                            } 
                            //right diagonal up 
                            if(perm[a-1][b+1] == '*'){
                                ++count;
                            } 
                            // right             
                            if(perm[a][b+1] == '*'){
                                ++count;
                            } 
                            // right diagonal down      
                            if(perm[a+1][b+1] == '*'){
                                ++count;
                            } 
                            //bottom
                            if(perm[a+1][b] == '*'){
                                ++count;
                            }
                            //left diagonal down
                            if(perm[a+1][b-1] == '*'){
                                ++count;
                            } 
                            //left
                            if(perm[a][b-1] == '*'){
                                ++count;
                            }
                            //left diagonal up
                            if(perm[a-1][b-1] == '*'){
                                ++count;
                            }

                            if(count > num){
                                return false;
                            }

                        }


                    }

                    
                }
            }
        }
        
		
		// Passed all the tests for now.
		return true;
	}
    
    //checks if digram follows tentaizu guidlines
    public static boolean correctrule(char[][] perm){
        //check for bombs equaling given num
        for(int a = 0; a < 7; ++a){
            for(int b = 0; b < 7; ++b){
                if(Character.isDigit(perm[a][b])){
                    int count = 0;
                    int num = Character.getNumericValue(perm[a][b]);

                    
                    //top row
                    if(a == 0){
                        //top left corner
                        if(b == 0){
                            if(perm[a][b+1] == '*'){
                                ++count;
                            } 
                            if(perm[a+1][b] == '*'){
                                ++count;
                            }
                            if(perm[a+1][b+1] == '*'){
                                ++count;
                            }

                            if(count != num){
                                return false;
                            }
                        } 
                        //top right corner
                        else if(b == 6){
                            if(perm[a][b-1] == '*'){
                                ++count;
                            } 
                            if(perm[a+1][b] == '*'){
                                ++count;
                            }
                            if(perm[a+1][b-1] == '*'){
                                ++count;
                            }

                            if(count != num){
                                return false;
                            }
                        
                        }
                        //rest in between corners
                        else{
                            if(perm[a][b+1] == '*'){
                                ++count;
                            } 
                            if(perm[a+1][b] == '*'){
                                ++count;
                            } 
                            if(perm[a+1][b+1] == '*'){
                                ++count;
                            }
                            if(perm[a][b-1] == '*'){
                                ++count;
                            } 
                            if(perm[a+1][b-1] == '*'){
                                ++count;
                            }

                            if(count != num){
                                return false;
                            }                        
                        }
                    } 

                    //bottom row
                    else if(a == 6){
                        
                        //bottom right corner
                        if(b == 6){
                            if(perm[a-1][b] == '*'){
                                ++count;
                            } 
                            if(perm[a][b-1] == '*'){
                                ++count;
                            }
                            if(perm[a-1][b-1] == '*'){
                                ++count;
                            }

                            if(count != num){
                                return false;
                            }
                            
                        }
                        //bottom left corner
                        else if(b == 0){
                            if(perm[a-1][b] == '*'){
                                ++count;
                            }
                            if(perm[a][b+1] == '*'){
                                ++count;
                            } 
                            
                            if(perm[a-1][b+1] == '*'){
                                ++count;
                            }

                            if(count != num){
                                return false;
                            }
                        }

                        //bottom edges
                        else{
                            if(perm[a][b+1] == '*'){
                                ++count;
                            } 
                            if(perm[a-1][b] == '*'){
                                ++count;
                            } 
                            if (perm[a-1][b+1] == '*'){
                                ++count;
                            } 
                            if (perm[a][b-1] == '*'){
                                ++count;
                            } 
                            if(perm[a-1][b-1] == '*'){
                                ++count;
                            }

                            if(count != num){
                                return false;
                            }
                        }

                    }

                    //left and right edges, and middle
                    else{
                        //left edges
                        if(b == 0){
                            if(perm[a][b+1] == '*'){
                                ++count;
                            } 
                            if(perm[a+1][b] == '*'){
                                ++count;
                            }
                            if(perm[a+1][b+1] == '*'){
                                ++count;
                            } 
                            if(perm[a-1][b] == '*'){
                                ++count;
                            } 
                            if(perm[a-1][b+1] == '*'){
                                ++count;
                            }

                            if(count != num){
                                return false;
                            }
                        }

                        //right edges
                        else if(b == 6){
                            if(perm[a][b-1] == '*'){
                                ++count;
                            } 
                            if(perm[a+1][b] == '*'){
                                ++count;
                            } 
                            if(perm[a+1][b-1] == '*'){
                                ++count;
                            }  
                            if(perm[a-1][b] == '*'){
                                ++count;
                            }  
                            if(perm[a-1][b-1] == '*'){
                                ++count;
                            }

                            if(count != num){
                                return false;
                            }
                        }
                        //middle 
                        else{
                            //     top                                          
                            if(perm[a-1][b] == '*'){
                                ++count;
                            } 
                            //right diagonal up 
                            if(perm[a-1][b+1] == '*'){
                                ++count;
                            } 
                            // right             
                            if(perm[a][b+1] == '*'){
                                ++count;
                            } 
                            // right diagonal down      
                            if(perm[a+1][b+1] == '*'){
                                ++count;
                            } 
                            //bottom
                            if(perm[a+1][b] == '*'){
                                ++count;
                            }
                            //left diagonal down
                            if(perm[a+1][b-1] == '*'){
                                ++count;
                            } 
                            //left
                            if(perm[a][b-1] == '*'){
                                ++count;
                            }
                            //left diagonal up
                            if(perm[a-1][b-1] == '*'){
                                ++count;
                            }

                            if(count != num){
                                return false;
                            }

                        }
                        
                    }
                }
            }
        }
        return true; 
    }

    public static void print(){
        int i,j;
     
     	// Loop through each row.
     	for (i=0; i<result.length; i++) {
         
         	// Go through each column in row i.
         	for (j=0; j<result.length; j++) {
                System.out.print(result[i][j]);
         	}
         	System.out.println();
     	}
    }
}



