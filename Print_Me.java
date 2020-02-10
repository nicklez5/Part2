import java.util.*;
public class Print_Me{
   public String intermediate_code;
   public int current_tab_space;
   public Print_Me(){
      intermediate_code = "";
      current_tab_space = 0;
   }
   public void print_me(){
      String result = String.format("%" + current_tab_space + "s",intermediate_code);
      System.out.println(result);
   }
   public void set_code(String xyz){
      intermediate_code = xyz;
   }
   public void increment_tab(){
       current_tab_space += 2;
   }
   public void decrement_tab(){
       current_tab_space -= 2;
   }
}
