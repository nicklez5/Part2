import java.util.*;
public class Print_Me{
   public String intermediate_code;
   public int current_tab_space;
   public Print_Me(){
      intermediate_code = "";
      current_tab_space = 3;
   }
   public void print_me(){
      String space = String.format("%" + current_tab_space + "s","");
      System.out.println(space + intermediate_code);

   }
   public void set_code(String xyz){
      intermediate_code = xyz;
   }
   public void increment_tab(){
       current_tab_space += 3;
   }
   public void decrement_tab(){
       if(current_tab_space > 3){
           current_tab_space -= 3;
       }
   }
}
