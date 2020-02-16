import java.util.*;
public class Helper_Function{
    public Symbol_Table sym_table_tmp;
    public Stack<Scope> current_stack_scope;
    public Print_Me printer;
    public Helper_Function(){
        sym_table_tmp = new Symbol_Table();
        current_stack_scope = new Stack<Scope>();
        printer = new Print_Me();
    }
    public Helper_Function(Symbol_Table random_table){
        sym_table_tmp = random_table;
        current_stack_scope = random_table.scope_stack;
        printer = new Print_Me();
    }
    public void print_main(){
        //System.out.println("Printing Main");
        print_all_functions();
        System.out.println("");
        printer.set_code(String.format("func Main()"));
        printer.print_me();
        printer.increment_tab();
        /*
        random_print.set_code("t.0 = HeapAllocZ(4)" );
        random_print.print_me();
        random_print.set_code("[t.0] = :vmt_" + )
        */
    }
    public void print_all_functions(){
        Iterator _itr = current_stack_scope.iterator();
        boolean first_value_ignore = false;
        String class_name_tmp = "";
        while(_itr.hasNext()){
            Scope temp_scope = (Scope)_itr.next();
            if(!first_value_ignore){
                first_value_ignore = true;
            }else{
                class_name_tmp = temp_scope.name;
                temp_scope.v_table.set_name_vtable("vmt_" + class_name_tmp);
                printer.set_code("const " + "vmt_" + class_name_tmp);
                printer.print_me();
                printer.increment_tab();

                Iterator new_itr = temp_scope.methods.iterator();
                while(new_itr.hasNext()){
                    String method_name = (String)new_itr.next();
                    printer.set_code(":" + class_name_tmp + "." + method_name);
                    printer.print_me();
                }
                printer.decrement_tab();

            }
        }
    }

}
