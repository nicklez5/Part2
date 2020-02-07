import java.util.*;
public class Symbol_Table{
    public Map<String,String> id_type_map;
    public Stack<Scope> scope_stack;

    public Symbol_Table(){
        id_type_map = new HashMap<String,String>();
        scope_stack = new Stack<Scope>();

    }
    public void add_map_value(String _id, String _value){
        id_type_map.put(_id,_value);
    }
    public void add_scope(Scope temp_scope){
        scope_stack.push(temp_scope);
    }
    public void print_me(){
      System.out.println("Printing Map");
      for(Map.Entry<String,String> entry: id_type_map.entrySet()){
          String k = entry.getKey();
          String v = entry.getValue();
          System.out.println(k + ":" + v);
      }
   }
   public void print_scope(){
      System.out.println("Printing Scope");
      Iterator _itr = scope_stack.iterator();
      while(_itr.hasNext()){
         Scope temp_scope = (Scope)_itr.next();
         temp_scope.print_scope();
      }
   }
   public boolean check_for_scope(String scope_name){
      boolean found_AUTO = false;
      Iterator _itr = scope_stack.iterator();
      while(_itr.hasNext()){
         Scope temp_scope = (Scope)_itr.next();
         if(temp_scope.name.equals(scope_name)){
            found_AUTO = true;
            break;
         }
      }
      return found_AUTO;
   }
   public Scope return_the_scope(String scope_name){
      Scope fake_scope = new Scope();
      Iterator _itr = scope_stack.iterator();
      while(_itr.hasNext()){
         Scope temp_scope = (Scope)_itr.next();
         if(temp_scope.name.equals(scope_name)){
            fake_scope = temp_scope;
            break;
         }
      }
      return fake_scope;
   }
}
