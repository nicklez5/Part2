import java.util.*;

//Maps method name to an assign offset
public class V_Table{
    public Map<String, Integer> v_table_map;
    public V_Table(){
        v_table_map = new HashMap<String,Integer>();

    }
    public void add_offset(String method_name, int temp1){
      v_table_map.put(method_name,temp1);
   }
   public void print_vtable(){
      System.out.println("Printing V_Table");
      System.out.println("Method Name | Offset");
      for(Map.Entry<String,Integer> entry: v_table_map.entrySet()){
          String k = entry.getKey();
          int v = entry.getValue();
          System.out.println(k + ":" + v);
      }
   }
   
}
