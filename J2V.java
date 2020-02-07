import java.util.*;
import syntaxtree.*;
import visitor.*;
public class J2V extends GJDepthFirst<String, Integer> {
   public Symbol_Table_Parser sym_parser;
   public Print_Me random_print;
   public int current_random_number;
   public Symbol_Table main_table;
   public Vector<Node> l_nodes
   public static void main(String[] args){

   }

   public J2V(){
      random_print = new Print_Me();
      current_random_number = 0;
      sym_parser = new Symbol_Table_Parser();
      main_table = new Symbol_Table();
   }
   public J2V(Symbol_Table_Parser xyz1){
      sym_parser = xyz1;
      main_table = sym_parser.sym_table;
   }
   /**
   * f0 -> MainClass()
   * f1 -> ( TypeDeclaration() )*
   * f2 -> <EOF>
   */
   public String visit(Goal n, int argu) {
      String _ret = "";
      visit(n.f0,1);
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
      String _ret;
      String class_name = "Main";
      random_print.set_code("func " + class_name + "()");
      random_print.print_me();
      //random_print.set_code("t." + current_random_number + " = HeapAllocZ(4)" );

      n.f1.accept(this, argu);

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

      return _ret;
   }

   /**
   * f0 -> ClassDeclaration()
   *       | ClassExtendsDeclaration()
   */
   public String visit(TypeDeclaration n, int argu) {
      String _ret=null;
      n.f0.accept(this, argu);
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
      String _ret=null;
      String temp_class_name = n.f1.f0.toString();
      if(main_table.check_for_scope(temp_class_name)){
          Scope da_scope = main_table.return_the_scope(temp_class_name);
          da_scope.v_table_print_const();

      }
      n.f2.accept(this, argu);
      n.f3.accept(this, argu);
      n.f4.accept(this, argu);
      n.f5.accept(this, argu);
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
      n.f0.accept(this, argu);
      n.f1.accept(this, argu);
      n.f2.accept(this, argu);
      n.f3.accept(this, argu);
      n.f4.accept(this, argu);
      n.f5.accept(this, argu);
      n.f6.accept(this, argu);
      n.f7.accept(this, argu);
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
      String _ret=null;
      n.f0.accept(this, argu);
      n.f1.accept(this, argu);
      n.f2.accept(this, argu);
      n.f3.accept(this, argu);
      n.f4.accept(this, argu);
      n.f5.accept(this, argu);
      n.f6.accept(this, argu);
      n.f7.accept(this, argu);
      n.f8.accept(this, argu);
      n.f9.accept(this, argu);
      n.f10.accept(this, argu);
      n.f11.accept(this, argu);
      n.f12.accept(this, argu);
      return _ret;
   }

   /**
   * f0 -> FormalParameter()
   * f1 -> ( FormalParameterRest() )*
   */
   public String visit(FormalParameterList n, int argu) {
      String _ret=null;
      n.f0.accept(this, argu);
      n.f1.accept(this, argu);
      return _ret;
   }

   /**
   * f0 -> Type()
   * f1 -> Identifier()
   */
   public String visit(FormalParameter n, int argu) {
      String _ret=null;
      n.f0.accept(this, argu);
      n.f1.accept(this, argu);
      return _ret;
   }

   /**
   * f0 -> ","
   * f1 -> FormalParameter()
   */
   public String visit(FormalParameterRest n, int argu) {
      String _ret=null;
      n.f0.accept(this, argu);
      n.f1.accept(this, argu);
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
          visit((PrintStatement)n.f0.choice,1)
      }
      return _ret;
   }

   /**
   * f0 -> "{"
   * f1 -> ( Statement() )*
   * f2 -> "}"
   */
   public String visit(Block n, int argu) {
      String _ret=null;
      n.f0.accept(this, argu);
      n.f1.accept(this, argu);
      n.f2.accept(this, argu);
      return _ret;
   }

   /**
   * f0 -> Identifier()
   * f1 -> "="
   * f2 -> Expression()
   * f3 -> ";"
   */
   public String visit(AssignmentStatement n, int argu) {
      String _ret=null;
      n.f0.accept(this, argu);
      n.f1.accept(this, argu);
      n.f2.accept(this, argu);
      n.f3.accept(this, argu);
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
      String _ret=null;
      n.f0.accept(this, argu);
      n.f1.accept(this, argu);
      n.f2.accept(this, argu);
      n.f3.accept(this, argu);
      n.f4.accept(this, argu);
      n.f5.accept(this, argu);
      n.f6.accept(this, argu);
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
      String _ret=null;
      n.f0.accept(this, argu);
      n.f1.accept(this, argu);
      n.f2.accept(this, argu);
      n.f3.accept(this, argu);
      n.f4.accept(this, argu);
      n.f5.accept(this, argu);
      n.f6.accept(this, argu);
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
      String _ret=null;
      n.f0.accept(this, argu);
      n.f1.accept(this, argu);
      n.f2.accept(this, argu);
      n.f3.accept(this, argu);
      n.f4.accept(this, argu);
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

      String output_code = visit(n.f2,1);

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
      String _ret=null;
      n.f0.accept(this, argu);
      n.f1.accept(this, argu);
      n.f2.accept(this, argu);
      return _ret;
   }

   /**
   * f0 -> PrimaryExpression()
   * f1 -> "<"
   * f2 -> PrimaryExpression()
   */
   public String visit(CompareExpression n, int argu) {
      String _ret=null;
      n.f0.accept(this, argu);
      n.f1.accept(this, argu);
      n.f2.accept(this, argu);
      return _ret;
   }

   /**
   * f0 -> PrimaryExpression()
   * f1 -> "+"
   * f2 -> PrimaryExpression()
   */
   public String visit(PlusExpression n, int argu) {
      String _ret=null;
      n.f0.accept(this, argu);
      n.f1.accept(this, argu);
      n.f2.accept(this, argu);
      return _ret;
   }

   /**
   * f0 -> PrimaryExpression()
   * f1 -> "-"
   * f2 -> PrimaryExpression()
   */
   public String visit(MinusExpression n, int argu) {
      String _ret=null;
      n.f0.accept(this, argu);
      n.f1.accept(this, argu);
      n.f2.accept(this, argu);
      return _ret;
   }

   /**
   * f0 -> PrimaryExpression()
   * f1 -> "*"
   * f2 -> PrimaryExpression()
   */
   public String visit(TimesExpression n, int argu) {
      String _ret=null;
      n.f0.accept(this, argu);
      n.f1.accept(this, argu);
      n.f2.accept(this, argu);
      return _ret;
   }

   /**
   * f0 -> PrimaryExpression()
   * f1 -> "["
   * f2 -> PrimaryExpression()
   * f3 -> "]"
   */
   public String visit(ArrayLookup n, int argu) {
      String _ret=null;
      n.f0.accept(this, argu);
      n.f1.accept(this, argu);
      n.f2.accept(this, argu);
      n.f3.accept(this, argu);
      return _ret;
   }

   /**
   * f0 -> PrimaryExpression()
   * f1 -> "."
   * f2 -> "length"
   */
   public String visit(ArrayLength n, int argu) {
      String _ret=null;
      n.f0.accept(this, argu);
      n.f1.accept(this, argu);
      n.f2.accept(this, argu);
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
      String _ret=null;
      n.f0.accept(this, argu);
      n.f1.accept(this, argu);
      n.f2.accept(this, argu);
      n.f3.accept(this, argu);
      n.f4.accept(this, argu);
      n.f5.accept(this, argu);
      return _ret;
   }

   /**
   * f0 -> Expression()
   * f1 -> ( ExpressionRest() )*
   */
   public String visit(ExpressionList n, int argu) {
      String _ret=null;
      n.f0.accept(this, argu);
      n.f1.accept(this, argu);
      return _ret;
   }

   /**
   * f0 -> ","
   * f1 -> Expression()
   */
   public String visit(ExpressionRest n, int argu) {
      String _ret=null;
      n.f0.accept(this, argu);
      n.f1.accept(this, argu);
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
      String _ret=null;
      n.f0.accept(this, argu);
      return _ret;
   }

   /**
   * f0 -> "true"
   */
   public String visit(TrueLiteral n, int argu) {
      String _ret=null;
      n.f0.accept(this, argu);
      return _ret;
   }

   /**
   * f0 -> "false"
   */
   public String visit(FalseLiteral n, int argu) {
      String _ret=null;
      n.f0.accept(this, argu);
      return _ret;
   }

   /**
   * f0 -> <IDENTIFIER>
   */
   public String visit(Identifier n, int argu) {
      String _ret=null;
      n.f0.accept(this, argu);
      return _ret;
   }

   /**
   * f0 -> "this"
   */
   public String visit(ThisExpression n, int argu) {
      String _ret=null;
      n.f0.accept(this, argu);
      return _ret;
   }

   /**
   * f0 -> "new"
   * f1 -> "int"
   * f2 -> "["
   * f3 -> Expression()
   * f4 -> "]"
   */
   public String visit(ArrayAllocationExpression n, int argu) {
      String _ret=null;
      n.f0.accept(this, argu);
      n.f1.accept(this, argu);
      n.f2.accept(this, argu);
      n.f3.accept(this, argu);
      n.f4.accept(this, argu);
      return _ret;
   }

   /**
   * f0 -> "new"
   * f1 -> Identifier()
   * f2 -> "("
   * f3 -> ")"
   */
   public String visit(AllocationExpression n, int argu) {
      String _ret=null;
      n.f0.accept(this, argu);
      n.f1.accept(this, argu);
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
      n.f0.accept(this, argu);
      n.f1.accept(this, argu);
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
