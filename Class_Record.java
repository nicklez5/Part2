import java.util.*;

//Maps field names to their offsets.
public class Class_Record{
    public Map<String, Integer> record_map;

    public Class_Record(){
        record_map = new HashMap<String,Integer>();
    }
    public void add_offset(String field_name, int temp1){
      record_map.put(field_name,temp1);
   }
   public void print_class_record(){
      System.out.println("Printing Class Record");
      System.out.println("Field Name | Offset");
      for(Map.Entry<String,Integer> entry: record_map.entrySet()){
          String k = entry.getKey();
          int v = entry.getValue();
          System.out.println(k + ":" + v);
      }
   }
   public int return_offset(String field_name){
       int _offset = 0;
       _offset = record_map.get(field_name);
       return _offset;
   }
   public boolean check_field_found(String field_name){
       boolean is_found = false;
       if(record_map.containsKey(field_name)){
           is_found = true;
       }
       return is_found;
   }
   public int record_size(){
       int record_size = record_map.size();
       return record_size;
   }
}
