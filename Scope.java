import java.util.*;

public class Scope{
   public String name;
   public Vector<String> fields;
   public Vector<String> methods;
   public Class_Record class_record;
   public Class_Record super_class_record;
   public V_Table v_table;
   public Scope(){
      name = "";
      fields = new Vector<String>();
      methods = new Vector<String>();
      class_record = new Class_Record();
      super_class_record = new Class_Record();
      v_table = new V_Table();
   }
   public void print_scope_name(){
      System.out.println("Scope Name: " + name);
   }
   public void add_name(String temp_name){
      name = temp_name;
   }
   public void add_fields(String temp_fields){
      fields.add(temp_fields);
   }
   public void add_methods(String temp_methods){
      methods.add(temp_methods);
   }
   public void add_class_record(String id_name, int offset){
      System.out.println("Adding class_record name: " + id_name);
      class_record.add_offset(id_name,offset);
   }
   public void add_v_table(String id_name, int offset){
      v_table.add_offset(id_name,offset);
   }
   public void set_super_class(Class_Record xyz){
      super_class_record = xyz;
   }
   public void set_super_vtable(V_Table xyz){
      v_table = xyz;
   }
   public void print_scope(){
      System.out.println("Class Name: " + name);
      System.out.println("Field Name: " + fields);
      System.out.println("Method Name: " + methods);
      class_record.print_class_record();
      v_table.print_vtable();
   }
   public void v_table_print_const(){
       System.out.println("const vmt_" + name);
       Print_Me temp_xyz = new Print_Me();
       for(int i = 0 ;i < methods.size() ;i++){
           temp_xyz.current_tab_space = 2;
           String code_value = String.format(":" + name + "." + methods.elementAt(i));
           temp_xyz.set_code(code_value);
           temp_xyz.print_me();
       }
   }
}
