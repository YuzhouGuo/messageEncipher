//Name: GUO, Yuzhou
//ID: 260715042

package assignment1;
public class Message {
 
    public String message;
    public int lengthOfMessage;

    public Message (String m){
        message = m;
        lengthOfMessage = m.length();
        this.makeValid();
    }
 
    public Message (String m, boolean b){
        message = m;
        lengthOfMessage = m.length();
    }
 
    /**
     * makeValid modifies message to remove any character that is not a letter and turn Upper Case into Lower Case
    */
    public void makeValid(){
        // INSERT YOUR CODE HERE  
         String valid = this.message.replaceAll("[^a-zA-Z]", ""); //if the element is not in this range, delete it
         this.message = valid.toLowerCase();
         lengthOfMessage = this.message.length();
    }
    
    /**
    * prints the string message
    */
  
    public void print(){
        System.out.println(message);
    }
 
   /**
    * tests if two Messages are equal
    */
    public boolean equals(Message m){
        if (message.equals(m.message) && lengthOfMessage == m.lengthOfMessage){
            return true;
    }
        return false;
    }

   /**
    * caesarCipher implements the Caesar cipher : it shifts all letter by the number 'key' given as a parameter.
    * @param key
    */
    public void caesarCipher(int key){
        // INSERT YOUR CODE HERE  
        String s = "";
  
        for (int i=0;i<message.length();i++)
        {
            s = s + charAfterShift(message.charAt(i), key); //the helper method can be found at very bottom, which works just as the name says
        }
         this.message = s;
         lengthOfMessage = this.message.length(); //assign the message as well as the length back to the object
    }
 
    public void caesarDecipher(int key){
        this.caesarCipher(- key);
    }
 
   /**
    * caesarAnalysis breaks the Caesar cipher
    * you will implement the following algorithm :
    * - compute how often each letter appear in the message
    * - compute a shift (key) such that the letter that happens the most was originally an 'e'
    * - decipher the message using the key you have just computed
    */
    public void caesarAnalysis(){
        // INSERT YOUR CODE HERE  
        int arrayLength = 0;
        for (int i=97; i<123; i++)
        {
            for (int j=0; j<lengthOfMessage;j++)
            {
                if ((char)(i)==message.charAt(j))
                {
                    arrayLength++;
                }
            }
        }          //note how many elements we have in the message (since we ensure that only alphabets there, we can do this)
        
        int[] key = new int[arrayLength];
        char[] value = new char[arrayLength];  //these two arrays together work like HashMap, notice that "same index" is important here, we can
        int position = 0;                      //use this property to get the value or key.
        char v;
        for (int i=0; i<lengthOfMessage;i++)
        {
            if(containsValue(value, message.charAt(i))==false)  //containsValue is a helper method, does the same job as the method in HashMap class.
            {
                v = message.charAt(i);
                int c = 1;                //if it is not in the value array, then we count the total number of that character.
                for (int j=(i+1); j<message.length();j++)  //if so, then there's no need to count again, we can skip it and go for next one.
                {
                    if (message.charAt(i)==message.charAt(j))
                    {
                        c++;
                    }
                    else
                    {
                        continue;
                    }
                }
                value[position] = v;
                key[position] = c;
                position++;
            }
            else
            {
                continue;
            }
        }
        int shiftKey = 0;   //Declare the variable outside the loop.
        
        for (int i=lengthOfMessage; i>0; i--) //the maximum a number can show up in the message is the length of message.
        {
            if (keyPosition(key, i)!=(-1))
            {
                shiftKey = (int)(value[keyPosition(key, i)]-'e');
                break;
            }
        }
        this.caesarDecipher(shiftKey); //once we find out the key, we can call the methods we just wrote to finish the job.
    }
 
 /**
  * vigenereCipher implements the Vigenere Cipher : it shifts all letter from message by the corresponding shift in the 'key'
  * @param key
  */
    public void vigenereCipher (int[] key){
        // INSERT YOUR CODE HERE  
        String s = "";
  
        for (int i=0;i<message.length();i++)
        {   
            s = s + charAfterShift(message.charAt(i), key[i%key.length]);
        }
         this.message = s;
         lengthOfMessage = this.message.length();
    }

   /**
    * vigenereDecipher deciphers the message given the 'key' according to the Vigenere Cipher
    * @param key
    */
    
    public void vigenereDecipher (int[] key){
        // INSERT YOUR CODE HERE  
        String s = "";
  
        for (int i=0;i<message.length();i++)
        {
            s = s + charAfterShift(message.charAt(i), -key[(i%key.length)]);
        }
         this.message = s;
         lengthOfMessage = this.message.length();
    }
 
   /**
    * transpositionCipher performs the transition cipher on the message by reorganizing the letters and eventually adding characters
    * @param key
    */
    public void transpositionCipher (int key){
        // INSERT YOUR CODE HERE
        int column = key; 
        int row = (lengthOfMessage/column)+1;
        
        boolean b = (((double)lengthOfMessage/(double)column)*10)%10==0;  //make sure we don't add 1 for mistake.
        if (b)
        {
            row = lengthOfMessage/column;
        }
        char[][] matrix = setMatrix1(message, column, row); //here we set the matrix structure and put elements in HORIZONTALLY.
        String s = readMatrixCByC(matrix); //then read column by column.
        this.message = s;
        lengthOfMessage = this.message.length(); 
    }
 
   /**
    * transpositionDecipher deciphers the message given the 'key'  according to the transition cipher.
    * @param key
    */
    public void transpositionDecipher (int key){
        // INSERT YOUR CODE HERE
        int column = key; 
        int row = (lengthOfMessage/column)+1;
        
        boolean b = (((double)lengthOfMessage/(double)column)*10)%10==0; //make sure we don't add 1 for mistake.
        if (b)
        {
            row = lengthOfMessage/column;
        }
        char[][] matrix = setMatrix2(message, column, row);  //here we set the matrix structure and put elements in VERTICALLY.
        String s = readMatrixRowByRow(matrix);
        String ss = s.replace("*", ""); 
        this.message = ss;
        lengthOfMessage = this.message.length(); 
     
    }
    
    //charAfterShift: in the form of string, but more clear to state that it represents a shifted character.
    public static String charAfterShift (char c, int key)
    {
        int shift = (int)(c)+(key%26);
        if (shift>'z')
        {
            shift = 'a' + (shift-'z'-1);
        }
            
        else if (shift<'a')
        {
            shift = 'z'-('a'-shift-1);
        }
            
        String s = ""+(char)(shift);
        return s;
    }
    
    //setMatrix1: set the matrix structure and put elements in horizontally.
    public char[][] setMatrix1 (String m, int column, int row)
    {
        int counter = 0;
        char[][] x = new char[row][column];
        for (int i=0; i<row;i++)
        {
            for (int j=0; j<column; j++)
            {
                if (counter<m.length())
                {
                    x[i][j]=m.charAt(counter);
                    counter++;
                }
                else if (counter>=m.length())
                {
                    x[i][j] = '*';
                }
            }
        }
        return x;
    }
    
    //setMatrix2: set the matrix structure and put elements in vertically.
    public char[][] setMatrix2 (String m, int column, int row)
    {
        int counter = 0;
        char[][] x = new char[row][column];
        for (int i=0; i<column;i++)
        {
            for (int j=0; j<row; j++)
            {
                if (counter<m.length())
                {
                    x[j][i]=m.charAt(counter);
                    counter++;
                }
            }
        }
            return x;
    }
    
    //readMatrixRowByRow: as the name says.
    public String readMatrixRowByRow (char[][] x)
    {
        String s = "";
        for (int i=0;i<x.length;i++)
        {
            for (int j=0;j<x[0].length;j++)
            {
                s = s + x[i][j]; 
            }
        }
        return s;
    }
    
    //readMatrixCByC: read the matrix column by column.
    public String readMatrixCByC (char[][] x)
    {
        String s = "";
        for (int i=0;i<x[0].length;i++)
        {
            for (int j=0;j<x.length;j++)
            {
                s = s + x[j][i]; 
            }
        }
        return s;
    }
 
    //containsValue: test if a character is in the array char[].
    public boolean containsValue (char[] s, char c)
    {
        for (int i=0; i<s.length;i++)
        {
            if (s[i]==c)
            {
              return true;
            }
        }
        return false;
    }
    
    //keyPosition: report the position of an integer in the array, also has the property to tell whether it exists or not.
    public int keyPosition (int[] s, int t)
    {
        for (int i=0; i<s.length;i++)
        {
            if (s[i]==t)
            {
              return i;
            }
        }
        return -1;  //so that we don't need another boolean method to tell us whether it exists or not.
    }
}
