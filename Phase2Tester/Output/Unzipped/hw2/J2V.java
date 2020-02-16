import java.util.*;
import syntaxtree.*;
import visitor.*;
public class J2V extends GJDepthFirst<String, Integer> {
    public Symbol_Table_Parser sym_parser;
    public Print_Me random_print;
    public int current_label_no;
    public int current_sequence_no;
    public Symbol_Table main_table;
    public Vector<Node> l_nodes;
    public Scope current_scope;
    public Stack<String> sym_stack;
    public String current_class_name;
    public String super_class_name;
    public Helper_Function xyz;
    public Map<String, String> id_memoryaddr;
    public boolean main_case;
    public static void main(String[] args){
        Goal holy_goal;

        try{
           MiniJavaParser xyz = new MiniJavaParser(System.in);
           Symbol_Table_Parser test_me = new Symbol_Table_Parser();
           holy_goal = xyz.Goal();
           holy_goal.accept(test_me);
           J2V da_j2v = new J2V(test_me);
           holy_goal.accept(da_j2v,1);
           da_j2v.visit(holy_goal,1);


           //da_j2v.main_table.print_me();
           //da_j2v.main_table.print_scope();


        } catch (ParseException e){
  			System.out.println("Type error");
  		}
    }

    public J2V(){
        random_print = new Print_Me();
        sym_parser = new Symbol_Table_Parser();
        main_table = new Symbol_Table();
        current_label_no = -1;
        current_scope = new Scope();
        xyz = new Helper_Function();
        sym_stack = new Stack<String>();

        id_memoryaddr = new HashMap<String,String>();
        main_case = true;
    }
    public J2V(Symbol_Table_Parser xyz1){
        sym_parser = xyz1;
        main_table = sym_parser.sym_table;
        current_scope = new Scope();
        random_print = new Print_Me();
        current_label_no = -1;
        xyz = new Helper_Function(main_table);
        id_memoryaddr = new HashMap<String,String>();
        sym_stack = new Stack<String>();

        current_class_name = "";
        current_sequence_no = 0;
        main_case = true;
    }


    /**
    * f0 -> MainClass()
    * f1 -> ( TypeDeclaration() )*
    * f2 -> <EOF>
    */
    public String visit(Goal n, int argu) {
        String _ret = "";
        visit(n.f0,1);
        random_print.decrement_tab();
        l_nodes = n.f1.nodes;
        Iterator l_itr = l_nodes.iterator();
        while(l_itr.hasNext()){
            TypeDeclaration l_type = (TypeDeclaration)l_itr.next();
            visit(l_type,1);
        }
        n.f2.accept(this, argu);
        return _ret;
    }

    /**
    * f0 -> "class"
    * f1 -> Identifier()
    * f2 -> "{"
    * f3 -> "public"
    * f4 -> "static"
    * f5 -> "void"
    * f6 -> "main"
    * f7 -> "("
    * f8 -> "String"
    * f9 -> "["
    * f10 -> "]"
    * f11 -> Identifier()
    * f12 -> ")"
    * f13 -> "{"
    * f14 -> ( VarDeclaration() )*
    * f15 -> ( Statement() )*
    * f16 -> "}"
    * f17 -> "}"
    */
    public String visit(MainClass n, int argu) {
        String _ret = "";
        String class_name = "Main";
        //xyz.print_all_functions();
        xyz.print_main();
        random_print.increment_tab();
        //Set the current scope

        l_nodes = n.f14.nodes;
        Iterator _itr = l_nodes.iterator();
        while(_itr.hasNext()){
            VarDeclaration temp_x = (VarDeclaration)_itr.next();
            visit(temp_x,1);
        }

        l_nodes = n.f15.nodes;
        _itr = l_nodes.iterator();
        while(_itr.hasNext()){
            Statement temp_s = (Statement)_itr.next();
            visit(temp_s,1);
        }
        random_print.set_code("ret");
        random_print.print_me();
        System.out.println("");
        return _ret;
    }

    /**
    * f0 -> ClassDeclaration()
    *       | ClassExtendsDeclaration()
    */
    public String visit(TypeDeclaration n, int argu) {
        String _ret = "";

        //Super
        if(n.f0.which == 0){
            ClassDeclaration class_dec = (ClassDeclaration)n.f0.choice;
            current_class_name = class_dec.f1.f0.toString();
            if(super_class_name != null){
                if(current_class_name.equals(super_class_name)){
                    _ret = visit((ClassDeclaration)n.f0.choice,1);
                }
            }else{
                _ret = visit((ClassDeclaration)n.f0.choice,1);
            }

        //Sub
        }else if(n.f0.which == 1){
            ClassExtendsDeclaration class_ext_dec = (ClassExtendsDeclaration)n.f0.choice;
            super_class_name = class_ext_dec.f3.f0.toString();
            if(current_class_name != null){
                if(super_class_name.equals(current_class_name)){
                    _ret = visit((ClassExtendsDeclaration)n.f0.choice,1);
                }
            }
        }



        return _ret;
    }

    /**
    * f0 -> "class"
    * f1 -> Identifier()
    * f2 -> "{"
    * f3 -> ( VarDeclaration() )*
    * f4 -> ( MethodDeclaration() )*
    * f5 -> "}"
    */
    public String visit(ClassDeclaration n, int argu) {
        //Translate v-table into a constant data segment
        String _ret = "";
        String temp_class_name = n.f1.f0.toString();
        if(main_table.check_for_scope(temp_class_name)){
            Scope da_scope = main_table.return_the_scope(temp_class_name);
            current_scope = da_scope;

        }
        Vector<Node> list_nodes2 = n.f3.nodes;
        Iterator _itr = list_nodes2.iterator();
        while(_itr.hasNext()){
            VarDeclaration temp_var = (VarDeclaration)_itr.next();
            visit(temp_var,1);
        }

        list_nodes2 = n.f4.nodes;
        _itr = list_nodes2.iterator();
        while(_itr.hasNext()){
            MethodDeclaration temp_method = (MethodDeclaration)_itr.next();
            visit(temp_method,1);
            random_print.decrement_tab();
        }

        return _ret;
    }

    /**
    * f0 -> "class"
    * f1 -> Identifier()
    * f2 -> "extends"
    * f3 -> Identifier()
    * f4 -> "{"
    * f5 -> ( VarDeclaration() )*
    * f6 -> ( MethodDeclaration() )*
    * f7 -> "}"
    */
    public String visit(ClassExtendsDeclaration n, int argu) {
        String _ret=null;
        String subClass_id = "";
        String superClass_id = "";
        n.f0.accept(this, argu);
        subClass_id = n.f1.f0.toString();
        superClass_id = n.f3.f0.toString();
        Scope temp_scope;
        if(main_table.check_for_scope(superClass_id)){
            current_scope = main_table.return_the_scope(superClass_id);
            if(!main_table.check_for_scope(subClass_id)){
                _ret = "false";
                return _ret;
            }
            temp_scope = main_table.return_the_scope(subClass_id);
            //Extensions
            temp_scope.class_record = current_scope.class_record;
            temp_scope.v_table = current_scope.v_table;
        }else{
            _ret = "false";
            return _ret;
        }

        Vector<Node> list_nodes2 = n.f5.nodes;
        Iterator _itr = list_nodes2.iterator();
        while(_itr.hasNext()){
            VarDeclaration temp_var = (VarDeclaration)_itr.next();
            visit(temp_var,1);
        }

        list_nodes2 = n.f6.nodes;
        _itr = list_nodes2.iterator();
        while(_itr.hasNext()){
            MethodDeclaration temp_method = (MethodDeclaration)_itr.next();
            visit(temp_method,1);
            random_print.decrement_tab();
        }

        return _ret;
    }

    /**
    * f0 -> Type()
    * f1 -> Identifier()
    * f2 -> ";"
    */
    public String visit(VarDeclaration n, int argu) {
        String _ret=null;
        n.f0.accept(this, argu);
        n.f1.accept(this, argu);
        n.f2.accept(this, argu);
        return _ret;
    }

    /**
    * f0 -> "public"
    * f1 -> Type()
    * f2 -> Identifier()
    * f3 -> "("
    * f4 -> ( FormalParameterList() )?
    * f5 -> ")"
    * f6 -> "{"
    * f7 -> ( VarDeclaration() )*
    * f8 -> ( Statement() )*
    * f9 -> "return"
    * f10 -> Expression()
    * f11 -> ";"
    * f12 -> "}"
    */
    public String visit(MethodDeclaration n, int argu) {
        String _ret = "";
        n.f0.accept(this, argu);
        n.f1.accept(this, argu);
        String method_id = n.f2.f0.toString();
        String formal_parameter_id = "";
        FormalParameterList temp_formal_list = (FormalParameterList)n.f4.node;
        if(temp_formal_list != null){
            formal_parameter_id = visit(temp_formal_list,1);
            random_print.set_code("func " + current_scope.name + "." + method_id + "(this " + formal_parameter_id + ")");
        }else{
            random_print.set_code("func " + current_scope.name + "." + method_id + "(this)");
        }
        random_print.print_me();
        random_print.increment_tab();

        Vector<Node> statement_nodes = n.f8.nodes;
        Iterator _itr = statement_nodes.iterator();
        while(_itr.hasNext()){
            Statement temp_var = (Statement)_itr.next();
            visit(temp_var,1);


        }

        n.f8.accept(this, argu);
        n.f9.accept(this, argu);
        _ret = visit(n.f10,1);
        random_print.set_code("ret " + _ret);
        random_print.print_me();
        System.out.println("");
        return _ret;
    }

    /**
    * f0 -> FormalParameter()
    * f1 -> ( FormalParameterRest() )*
    */
    public String visit(FormalParameterList n, int argu) {
        String _ret = "";
        _ret = visit(n.f0,1);
        Vector<Node> l_nodes = n.f1.nodes;
        Iterator _itr = l_nodes.iterator();
        while(_itr.hasNext()){
            FormalParameterRest formal_rest = (FormalParameterRest)_itr.next();
            _ret = _ret + " " + visit(formal_rest,1);
        }

        return _ret;
    }

    /**
    * f0 -> Type()
    * f1 -> Identifier()
    */
    public String visit(FormalParameter n, int argu) {
        String _ret = "";

        n.f0.accept(this, argu);
        _ret = visit(n.f1,1);
        return _ret;
    }

    /**
    * f0 -> ","
    * f1 -> FormalParameter()
    */
    public String visit(FormalParameterRest n, int argu) {
        String _ret = "";
        _ret = visit(n.f1,1);
        return _ret;
    }

    /**
    * f0 -> ArrayType()
    *       | BooleanType()
    *       | IntegerType()
    *       | Identifier()
    */
    public String visit(Type n, int argu) {
        String _ret=null;
        n.f0.accept(this, argu);
        return _ret;
    }

    /**
    * f0 -> "int"
    * f1 -> "["
    * f2 -> "]"
    */
    public String visit(ArrayType n, int argu) {
        String _ret=null;
        n.f0.accept(this, argu);
        n.f1.accept(this, argu);
        n.f2.accept(this, argu);
        return _ret;
    }

    /**
    * f0 -> "boolean"
    */
    public String visit(BooleanType n, int argu) {
        String _ret=null;
        n.f0.accept(this, argu);
        return _ret;
    }

    /**
    * f0 -> "int"
    */
    public String visit(IntegerType n, int argu) {
        String _ret=null;
        n.f0.accept(this, argu);
        return _ret;
    }

    /**
    * f0 -> Block()
    *       | AssignmentStatement()
    *       | ArrayAssignmentStatement()
    *       | IfStatement()
    *       | WhileStatement()
    *       | PrintStatement()
    */
    public String visit(Statement n, int argu) {
        String _ret = "";
        if(n.f0.which == 0){
            visit((Block)n.f0.choice,1);
        }else if(n.f0.which == 1){
            visit((AssignmentStatement)n.f0.choice,1);
        }else if(n.f0.which == 2){
            visit((ArrayAssignmentStatement)n.f0.choice,1);
        }else if(n.f0.which == 3){
            visit((IfStatement)n.f0.choice,1);
        }else if(n.f0.which == 4){
            visit((WhileStatement)n.f0.choice,1);
        }else if(n.f0.which == 5){
            visit((PrintStatement)n.f0.choice,1);
        }
        return _ret;
    }

    /**
    * f0 -> "{"
    * f1 -> ( Statement() )*
    * f2 -> "}"
    */
    public String visit(Block n, int argu) {
        String _ret = "";
        n.f0.accept(this, argu);
        l_nodes = n.f1.nodes;
        Iterator _itr = l_nodes.iterator();
        while(_itr.hasNext()){
            Statement xyz_1 = (Statement)_itr.next();
            visit(xyz_1 , 1);
        }

        return _ret;
    }

    /**
    * f0 -> Identifier()
    * f1 -> "="
    * f2 -> Expression()
    * f3 -> ";"
    */
    public String visit(AssignmentStatement n, int argu) {
        String _ret = "FALSE";
        String operand_id = n.f0.f0.toString();
        String right_operand_value = "";
        int operand_offset_index = 0;
        int operand_offset = 0;
        String receiver_obj = "";
        //Check if the sym table contains the class record
        //System.out.println("Operand ID:" + operand_id);
        //Check if the identifier is a field
        if(main_table.field_check(operand_id)){
            random_print.set_code("t." + current_label_no + " = [this]");
            random_print.print_me();
            Class_Record tmp_record = current_scope.class_record;
            //If you found the field in the class record.
            if(tmp_record.check_field_found(operand_id)){
                operand_offset_index = tmp_record.return_offset(operand_id);
                operand_offset = operand_offset_index * 4 + 4;
                //tmp1 = [tmp1 + 8]
                random_print.set_code("t." + current_label_no + " = [t." + current_label_no + " + " + operand_offset + "]");
                random_print.print_me();
                receiver_obj = "t." + current_label_no;
                right_operand_value = visit(n.f2,1);

                //tmp1
                random_print.set_code(receiver_obj + " = " + right_operand_value);
                random_print.print_me();
                _ret = receiver_obj;
                if(!id_memoryaddr.containsKey(operand_id)){
                    id_memoryaddr.put(operand_id,_ret);
                }else{
                    id_memoryaddr.replace(operand_id,_ret);
                }

            }
        //Not a field
        }else{
            //System.out.println("Entering non field");
            right_operand_value = visit(n.f2,1);
            _ret = String.format(operand_id + " = " + right_operand_value);
            if(!id_memoryaddr.containsKey(operand_id)){
                id_memoryaddr.put(operand_id,sym_stack.pop());
            }else{
                id_memoryaddr.replace(operand_id,sym_stack.pop());
            }
            random_print.set_code(_ret);
            random_print.print_me();


        }




        return _ret;
    }

    public String get_value(String id_value){
        String _ret = "";
        for(Map.Entry<String,String> entry: id_memoryaddr.entrySet()){
            String k = entry.getKey();
            String v = entry.getValue();
            if(v.equals(id_value)){
                _ret = k;
                break;
            }

        }
        return _ret;
    }
    /**
    * f0 -> Identifier()
    * f1 -> "["
    * f2 -> Expression()
    * f3 -> "]"
    * f4 -> "="
    * f5 -> Expression()
    * f6 -> ";"
    */
    public String visit(ArrayAssignmentStatement n, int argu) {
        String _ret = "";
        String array_id = n.f0.f0.toString();
        array_id = id_memoryaddr.get(array_id);
        String array_index = visit(n.f2,1);

        int field_index = 0;
        String receiver_obj = "";
        String exp_value = "";
        //Does the sym table have the array_id mapped
        if(main_table.does_it_contain(array_id)){

            //Is the array id a field.
            if(main_table.field_check(array_id)){
                //random_print.set_code("tmp" + current_label_no + " = " + array_index);
                Class_Record tmp_record = current_scope.class_record;
                field_index = tmp_record.return_offset(array_id);
                if(field_index == -1){

                }
                //If the Class record contains the id.
                if(tmp_record.check_field_found(array_id)){
                    random_print.set_code("t." + current_label_no + " = [" + array_id + "]");
                    random_print.print_me();
                    random_print.set_code("t." + current_label_no + " = Lt(" + array_index + " t." + current_label_no + ")");
                    random_print.print_me();
                    random_print.set_code("if t." + current_label_no + " goto :bound" + current_sequence_no);
                    random_print.print_me();
                    random_print.increment_tab();
                    random_print.set_code("Error(\"Array index out of bounds\")");
                    random_print.print_me();
                    random_print.decrement_tab();
                    random_print.set_code("bound" + current_sequence_no + ":");
                    random_print.print_me();
                    random_print.set_code("t." + current_label_no + " = MulS(" + array_index + " 4)");
                    receiver_obj = "t." + current_label_no;
                    random_print.print_me();
                    random_print.set_code("t." + current_label_no + " = Add(" + array_id + " " + receiver_obj + ")");
                    random_print.print_me();

                    exp_value = visit(n.f5,1);
                    random_print.set_code("[t." + current_label_no + "+4] = " + exp_value );
                    random_print.print_me();

                    _ret = "t." + current_label_no;
                    current_sequence_no++;

                }
            }
        }


        return _ret;
    }

    /**
    * f0 -> "if"
    * f1 -> "("
    * f2 -> Expression()
    * f3 -> ")"
    * f4 -> Statement()
    * f5 -> "else"
    * f6 -> Statement()
    */
    public String visit(IfStatement n, int argu) {
        String _ret = "";
        String operand_1 = "";
        n.f0.accept(this, argu);
        n.f1.accept(this, argu);
        operand_1 = visit(n.f2,1);
        //Could be LTS, and expression
        random_print.set_code("t." + current_label_no + " = Eq(" + operand_1 + " 1)");
        random_print.print_me();

        random_print.set_code("if0 t." + current_label_no + " goto :if" + current_sequence_no + "_else");
        random_print.print_me();
        random_print.increment_tab();
        visit(n.f4,1);
        random_print.set_code("goto :if" + current_sequence_no + "_end");
        random_print.print_me();
        random_print.decrement_tab();
        random_print.set_code("if" + current_sequence_no + "_else:");
        random_print.print_me();

        random_print.increment_tab();
        visit(n.f6,1);
        random_print.set_code("if" + current_sequence_no + "_end:");
        current_sequence_no++;
        random_print.print_me();
        return _ret;
    }

    /**
    * f0 -> "while"
    * f1 -> "("
    * f2 -> Expression()
    * f3 -> ")"
    * f4 -> Statement()
    */
    public String visit(WhileStatement n, int argu) {
        String _ret = "";
        String operand_1 = visit(n.f2,1);
        random_print.set_code("t." + current_label_no + " = Eq(" + operand_1 + " 1)");
        random_print.print_me();
        random_print.set_code("if0 t." + current_label_no + " goto :if" + current_sequence_no + "_end");
        random_print.print_me();
        random_print.increment_tab();
        visit(n.f4,1);
        random_print.set_code("goto :if" + current_sequence_no + "_end");
        random_print.print_me();
        random_print.decrement_tab();
        random_print.set_code("if" + current_sequence_no + "_end:");
        random_print.print_me();

        return _ret;
    }

    /**
    * f0 -> "System.out.println"
    * f1 -> "("
    * f2 -> Expression()
    * f3 -> ")"
    * f4 -> ";"
    */
    public String visit(PrintStatement n, int argu) {
        String _ret = "";

        String output_id = visit(n.f2,1);
        random_print.set_code("PrintIntS(" + output_id + ")");
        random_print.print_me();
        return _ret;
    }

    /**
    * f0 -> AndExpression()
    *       | CompareExpression()
    *       | PlusExpression()
    *       | MinusExpression()
    *       | TimesExpression()
    *       | ArrayLookup()
    *       | ArrayLength()
    *       | MessageSend()
    *       | PrimaryExpression()
    */
    public String visit(Expression n, int argu) {
        String _ret = "";

        if(n.f0.which == 0){
            _ret = visit((AndExpression)n.f0.choice,1);
        }else if(n.f0.which == 1){
            _ret = visit((CompareExpression)n.f0.choice,1);
        }else if(n.f0.which == 2){
            _ret = visit((PlusExpression)n.f0.choice,1);
        }else if(n.f0.which == 3){
            _ret = visit((MinusExpression)n.f0.choice,1);
        }else if(n.f0.which == 4){
            _ret = visit((TimesExpression)n.f0.choice,1);
        }else if(n.f0.which == 5){
            _ret = visit((ArrayLookup)n.f0.choice,1);
        }else if(n.f0.which == 6){
            _ret = visit((ArrayLength)n.f0.choice,1);
        }else if(n.f0.which == 7){
            _ret = visit((MessageSend)n.f0.choice,1);
        }else if(n.f0.which == 8){
            _ret = visit((PrimaryExpression)n.f0.choice,1);
        }
        return _ret;
    }

    /**
    * f0 -> PrimaryExpression()
    * f1 -> "&&"
    * f2 -> PrimaryExpression()
    */
    public String visit(AndExpression n, int argu) {
        String _ret = "";
        String operand_1 = visit(n.f0,1);
        String operand_2 = visit(n.f2,1);
        current_label_no++;
        String result = String.format("t." + current_label_no);
        current_label_no++;
        _ret = String.format("t." + current_label_no + " = " + "Eq(" + operand_1 + " " + "0)");
        random_print.set_code(_ret);
        random_print.print_me();

        _ret = String.format("if0 t." + current_label_no + " goto :if" + current_sequence_no + "_else" );
        random_print.set_code(_ret);
        random_print.print_me();

        _ret = String.format(result + " = 0");
        random_print.set_code(_ret);
        random_print.print_me();

        _ret = String.format("t." + current_label_no + " = Eq(" + operand_2 + " 0)" );
        random_print.set_code(_ret);
        random_print.print_me();

        _ret = String.format("if0 = t." + current_label_no + " goto :if" + current_sequence_no + "_else");
        random_print.set_code(_ret);
        random_print.print_me();

        _ret = String.format(result + " = 1");
        random_print.set_code(_ret);
        random_print.print_me();

        _ret = String.format("goto :if" + current_sequence_no + "_end");
        random_print.set_code(_ret);
        random_print.print_me();

        _ret = String.format("if" + current_sequence_no + "_else:");
        random_print.set_code(_ret);
        random_print.print_me();
        random_print.increment_tab();


        _ret = String.format(result + " = 0");
        random_print.set_code(_ret);
        random_print.print_me();

        random_print.decrement_tab();

        _ret = String.format("if" + current_sequence_no + "_end:");
        random_print.set_code(_ret);
        random_print.print_me();

        current_sequence_no++;

        return result;
    }

    /**
    * f0 -> PrimaryExpression()
    * f1 -> "<"
    * f2 -> PrimaryExpression()
    */
    public String visit(CompareExpression n, int argu) {
        String _ret = "";
        String first_operand = visit(n.f0,1);
        String second_operand = visit(n.f2,1);

        random_print.set_code("t." + current_label_no + " = LtS(" + first_operand + " " + second_operand + ")");
        random_print.print_me();
        _ret = String.format("t." + current_label_no);

        return _ret;
    }

    /**
    * f0 -> PrimaryExpression()
    * f1 -> "+"
    * f2 -> PrimaryExpression()
    */
    public String visit(PlusExpression n, int argu) {
        String _ret = "";
        String first_operand = visit(n.f0,1);
        String second_operand = visit(n.f2,1);
        current_label_no++;
        _ret = "t." + current_label_no + " = " + "Add(" + first_operand + " " + second_operand + ")";
        random_print.set_code(_ret);
        random_print.print_me();
        _ret = "t." + current_label_no;
        return _ret;
    }

    /**
    * f0 -> PrimaryExpression()
    * f1 -> "-"
    * f2 -> PrimaryExpression()
    */
    public String visit(MinusExpression n, int argu) {
        String _ret = "";
        String first_operand = visit(n.f0,1);
        String second_operand = visit(n.f2,1);
        current_label_no++;
        _ret = "t." + current_label_no + " = " + "Sub(" + first_operand + " " + second_operand + ")";
        random_print.set_code(_ret);
        random_print.print_me();
        _ret = "t." + current_label_no;

        return _ret;
    }

    /**
    * f0 -> PrimaryExpression()
    * f1 -> "*"
    * f2 -> PrimaryExpression()
    */
    public String visit(TimesExpression n, int argu) {
        String _ret = "";
        String first_operand = visit(n.f0,1);
        String second_operand = visit(n.f2,1);
        current_label_no++;
        _ret = "t." + current_label_no + " = " + "MulS(" + first_operand + " " + second_operand + ")";
        random_print.set_code(_ret);
        random_print.print_me();
        _ret = "t." + current_label_no;


        return _ret;
    }

    /**
    * f0 -> PrimaryExpression()
    * f1 -> "["
    * f2 -> PrimaryExpression()
    * f3 -> "]"
    */
    public String visit(ArrayLookup n, int argu) {
        String _ret = "";
        String array_id = visit(n.f0,1);
        String base_addr = id_memoryaddr.get(array_id);
        String index = visit(n.f2,1);
        random_print.set_code("t." + ++current_label_no + " = [" + base_addr + "]");
        random_print.print_me();
        String receiver_obj = "t." + current_label_no;
        random_print.set_code("t." + current_label_no + " = Lt(" + receiver_obj + " " + index + ")");
        random_print.print_me();
        random_print.set_code("if t." + current_label_no + " goto :bound" + current_sequence_no);
        random_print.print_me();
        random_print.increment_tab();
        random_print.set_code("Error(\"array index out of bounds\")");
        random_print.print_me();
        random_print.decrement_tab();
        random_print.set_code("bound" + current_sequence_no + ":");
        random_print.print_me();
        random_print.set_code("t." + current_label_no + " = MulS(" + index + " 4" + ")" );
        random_print.print_me();
        random_print.set_code("t." + current_label_no + " = Add(" + receiver_obj + " " + base_addr + ")");
        random_print.print_me();

        receiver_obj = "t." + current_label_no;
        random_print.set_code("t." + ++current_label_no + " = " + "[" + receiver_obj + "+4]");
        random_print.print_me();
        _ret = "t." + current_label_no;
        current_sequence_no++;
        return _ret;
    }

    /**
    * f0 -> PrimaryExpression()
    * f1 -> "."
    * f2 -> "length"
    */
    public String visit(ArrayLength n, int argu) {
        String _ret = "";
        String array_id = visit(n.f0,1);
        String base_addr = id_memoryaddr.get(array_id);
        current_label_no++;
        random_print.set_code("t." + current_label_no + " = [" + base_addr + "]");
        random_print.print_me();
        _ret = String.format("t." + current_label_no);

        return _ret;
    }

    /**
    * f0 -> PrimaryExpression()
    * f1 -> "."
    * f2 -> Identifier()
    * f3 -> "("
    * f4 -> ( ExpressionList() )?
    * f5 -> ")"
    */
    public String visit(MessageSend n, int argu) {
        String _ret = "";
        String operand_id = visit(n.f0,1);
        //System.out.println("Message Send exp:" + operand_id);
        String argument_list = "";
        ExpressionList temp_exp_list = (ExpressionList)n.f4.node;
        String func_name = "";
        if(!operand_id.equals("this")){
            //Check for null pointer
            //if t.1 goto :null1
            random_print.set_code("if " + operand_id  + " goto :null" + current_sequence_no);
            random_print.print_me();
            random_print.set_code("Error(\"null pointer\")");
            random_print.print_me();
            random_print.set_code("null" + current_sequence_no + ":");
            random_print.print_me();
        }
        func_name = n.f2.f0.toString();
        int func_offset = 0;
        if(current_scope.check_method(func_name)){
            func_offset = current_scope.v_table.get_offset(func_name);
        }else{
            Stack<Scope> temp_stack = main_table.scope_stack;
            Iterator _itr = temp_stack.iterator();
            while(_itr.hasNext()){
                Scope temp_scope = (Scope)_itr.next();
                if(temp_scope.check_method(func_name)){
                    func_offset = temp_scope.v_table.get_offset(func_name);
                }
            }
        }
        current_sequence_no++;
        current_label_no++;

        //Look for the field. OPERAND _ ID
        random_print.set_code("t." + current_label_no + " = [" + operand_id + "]");
        random_print.print_me();
        String receiver_obj = operand_id;
        func_offset = func_offset - 1;
        func_offset = func_offset * 4 + 4;
        random_print.set_code("t." + current_label_no + " = [t." + current_label_no + "+" + func_offset + "]");
        random_print.print_me();
        String temp_receiver = String.format("t." + current_label_no);

        current_label_no++;

        //Check for an argument list avaliable.
        if(temp_exp_list != null){
            argument_list = visit(temp_exp_list,1);
            if(main_case){
                random_print.set_code("t." + current_label_no + " = call " + temp_receiver + "(" + receiver_obj + " " + argument_list + ")");
                main_case = false;
            }else{
                random_print.set_code("t." + current_label_no + " = call " + temp_receiver + "(this " + argument_list + ")");
            }
        }else{
            if(main_case){
                random_print.set_code("t." + current_label_no + " = call " + temp_receiver + "(" + receiver_obj + "" + argument_list + ")");
                main_case = false;
            }else{
                random_print.set_code("t." + current_label_no + " = call " + temp_receiver + "(this)");
            }

        }
        random_print.print_me();
        _ret = String.format("t." + current_label_no);
        sym_stack.push(_ret);
        return _ret;
    }

    /**
    * f0 -> Expression()
    * f1 -> ( ExpressionRest() )*
    */
    public String visit(ExpressionList n, int argu) {
        String _ret = "";
        _ret = visit(n.f0,1);

        Vector<Node> exp_list_nodes = n.f1.nodes;
        Iterator _itr = exp_list_nodes.iterator();
        while(_itr.hasNext()){
            ExpressionRest temp_exp_rest = (ExpressionRest)_itr.next();
            if(temp_exp_rest != null){
                _ret = " " + _ret + " " + visit(temp_exp_rest,1);
            }
        }
        return _ret;
    }

    /**
    * f0 -> ","
    * f1 -> Expression()
    */
    public String visit(ExpressionRest n, int argu) {
        String _ret = "";
        n.f0.accept(this, argu);
        _ret = visit(n.f1,1);
        return _ret;
    }

    /**
    * f0 -> IntegerLiteral()
    *       | TrueLiteral()
    *       | FalseLiteral()
    *       | Identifier()
    *       | ThisExpression()
    *       | ArrayAllocationExpression()
    *       | AllocationExpression()
    *       | NotExpression()
    *       | BracketExpression()
    */
    public String visit(PrimaryExpression n, int argu) {
        String _ret = "";
        if(n.f0.which == 0){
            _ret = visit((IntegerLiteral)n.f0.choice,1);
        }else if(n.f0.which == 1){
            _ret = visit((TrueLiteral)n.f0.choice,1);
        }else if(n.f0.which == 2){
            _ret = visit((FalseLiteral)n.f0.choice,1);
        }else if(n.f0.which == 3){
            _ret = visit((Identifier)n.f0.choice,1);
        }else if(n.f0.which == 4){
            _ret = visit((ThisExpression)n.f0.choice,1);
        }else if(n.f0.which == 5){
            _ret = visit((ArrayAllocationExpression)n.f0.choice,1);
        }else if(n.f0.which == 6){
            _ret = visit((AllocationExpression)n.f0.choice,1);
        }else if(n.f0.which == 7){
            _ret = visit((NotExpression)n.f0.choice,1);
        }else if(n.f0.which == 8){
            _ret = visit((BracketExpression)n.f0.choice,1);
        }
        return _ret;
    }

    /**
    * f0 -> <INTEGER_LITERAL>
    */
    public String visit(IntegerLiteral n, int argu) {
        String _ret = "";
        _ret = n.f0.toString();
        /*
        if(!id_memoryaddr.containsKey(n.f0.toString())){
            _ret = String.format("t." + current_label_no);
            random_print.set_code("t." + current_label_no + " = " + n.f0.toString());
            random_print.print_me();
            id_memoryaddr.put(n.f0.toString(),_ret);
            current_label_no++;
        }else{
            _ret = id_memoryaddr.get(n.f0.toString());
        }
        */
        return _ret;
    }

    /**
    * f0 -> "true"
    */
    public String visit(TrueLiteral n, int argu) {
        String _ret = "1";
        return _ret;
    }

    /**
    * f0 -> "false"
    */
    public String visit(FalseLiteral n, int argu) {
        String _ret = "0";
        return _ret;
    }

    /**
    * f0 -> <IDENTIFIER>
    */
    public String visit(Identifier n, int argu) {
        String _ret = "";
        String value_id = n.f0.toString();
        String value_type = main_table.id_type_map.get(value_id);
        if(value_type.equals(Constants.FIELD_TYPE)){
            if(!id_memoryaddr.containsKey(value_type)){
                _ret = String.format("t." + current_label_no);
                random_print.set_code("t." + current_label_no + " = " + n.f0.toString());
                random_print.print_me();
                id_memoryaddr.put(n.f0.toString(),_ret);
            }else{
                _ret = id_memoryaddr.get(n.f0.toString());
            }
        }else if(value_type.equals(Constants.LOCAL_TYPE)){
            _ret = value_id;
        }

        return _ret;
    }

    /**
    * f0 -> "this"
    */
    public String visit(ThisExpression n, int argu) {

        String _ret = "";
        _ret = "this";
        return _ret;

        //_ret = "this";
        //return _ret;
        /*
        current_label_no++;
        _ret = "t." + current_label_no + " = [this]";
        random_print.set_code(_ret);
        random_print.print_me();
        String _value = "t." + current_label_no;
        */


    }

    /**
    * f0 -> "new"
    * f1 -> "int"
    * f2 -> "["
    * f3 -> Expression()
    * f4 -> "]"
    */
    public String visit(ArrayAllocationExpression n, int argu) {
        String _ret = "";
        n.f0.accept(this, argu);
        n.f1.accept(this, argu);
        n.f2.accept(this, argu);
        String array_size = visit(n.f3,1);
        current_label_no++;
        //Get the value;
        random_print.set_code("t." + current_label_no + " = MulS(" + array_size + " 4)" );
        random_print.print_me();
        random_print.set_code("t." + current_label_no + " = Add(t." + current_label_no + " 4)" );
        random_print.print_me();
        String receiver_obj = "t." + current_label_no;
        random_print.set_code("t." + ++current_label_no + " = HeapAllocZ(" + receiver_obj + ")" );
        random_print.print_me();
        receiver_obj = "t." + current_label_no;
        sym_stack.push(receiver_obj);
        random_print.set_code("[t." + current_label_no + "] = " + array_size);
        random_print.print_me();
        random_print.set_code("if t." + current_label_no + " goto :null" + current_sequence_no);
        random_print.print_me();
        random_print.increment_tab();
        random_print.set_code("Error(\"null pointer\")");
        random_print.print_me();
        random_print.decrement_tab();
        random_print.set_code("null" + current_sequence_no + ":");
        random_print.print_me();
        current_sequence_no++;
        _ret = String.format("[t." + current_label_no + "]");
        return _ret;
    }

    /**
    * f0 -> "new"
    * f1 -> Identifier()
    * f2 -> "("
    * f3 -> ")"
    */
    public String visit(AllocationExpression n, int argu) {
        String _ret = "";
        String class_id = "";

        n.f0.accept(this, argu);
        class_id = n.f1.f0.toString();
        current_label_no++;
        if(main_table.check_for_scope(class_id)){
            Scope temp_random_scope = main_table.return_the_scope(class_id);
            current_scope = temp_random_scope;
            int get_record_size = temp_random_scope.class_record.record_size();
            int result = 4 * get_record_size + 4;
            random_print.set_code("t." + current_label_no + " = HeapAllocZ(" + result + ")");
            random_print.print_me();
            sym_stack.push(String.format("t." + current_label_no));
            //Why is it printing that.
            random_print.set_code("[t." + current_label_no + "] = :" +  temp_random_scope.v_table.name_vtable);
            random_print.print_me();
            _ret = String.format("t." + current_label_no);


        }
        n.f2.accept(this, argu);
        n.f3.accept(this, argu);
        return _ret;
    }

    /**
    * f0 -> "!"
    * f1 -> Expression()
    */
    public String visit(NotExpression n, int argu) {
        String _ret=null;
        String exp_id = visit(n.f1,1);
        String result = String.format("t." + current_label_no);
        current_label_no++;
        _ret = String.format("t." + current_label_no + " = " + "Eq(" + exp_id + " " + "0)");
        random_print.set_code(_ret);
        random_print.print_me();
        random_print.set_code("if0 t." + current_label_no  + " goto :if" + current_sequence_no + "_else");
        random_print.print_me();
        random_print.increment_tab();
        random_print.set_code(result + " = 0");
        random_print.print_me();
        random_print.set_code("goto :if" + current_sequence_no + "_end");
        random_print.print_me();
        random_print.decrement_tab();
        random_print.set_code("if" + current_sequence_no + "_else:");
        random_print.print_me();
        random_print.increment_tab();
        random_print.set_code(result + " = 1");
        random_print.print_me();
        random_print.decrement_tab();
        random_print.set_code("if" + current_sequence_no + "_end:");
        random_print.print_me();
        current_sequence_no++;
        _ret = result;
        return _ret;
    }

    /**
    * f0 -> "("
    * f1 -> Expression()
    * f2 -> ")"
    */
    public String visit(BracketExpression n, int argu) {
        String _ret=null;
        n.f0.accept(this, argu);
        n.f1.accept(this, argu);
        n.f2.accept(this, argu);
        return _ret;
    }
}
